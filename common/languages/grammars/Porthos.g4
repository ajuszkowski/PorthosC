grammar Porthos;

@header{
package dartagnan;
import dartagnan.program.*;
import dartagnan.expression.*;
import dartagnan.program.Thread;
import java.util.HashMap;
import java.util.Map;
}
@parser::members
{
private Map<String, Location> mapLocs = new HashMap<String, Location>();
private Map<String, Map<String, Register>> mapRegs = new HashMap<String, Map<String, Register>>();	
}

arith_expr [String mainThread] returns [AExpr expr]:
	| e1 = arith_atom [mainThread] op = ARITH_OP e2 = arith_atom [mainThread] {
		$expr = new AExpr($e1.expr, $op.getText(), $e2.expr);
	}
	| e = arith_atom [mainThread] {
		$expr = $e.expr;
	};
arith_atom [String mainThread] returns [AExpr expr]:
	| num = DIGIT {$expr = new AConst(Integer.parseInt($num.getText()));}
	| r = register {
		Map<String, Register> mapThreadRegs = mapRegs.get(mainThread);
		$expr = mapThreadRegs.get($r.reg.getName());
	} 
	| LPAR e = arith_expr [mainThread] RPAR {
		$expr = $e.expr;
	};
arith_comp [String mainThread] returns [BExpr expr]: 
	LPAR a1 = arith_expr [mainThread] op = COMP_OP a2 = arith_expr [mainThread] RPAR {
		$expr = new Atom($a1.expr, $op.getText(), $a2.expr);
	};

bool_expr [String mainThread] returns [BExpr expr]: 
	| b = bool_atom [mainThread] {$expr = $b.expr;}
	| b1 = bool_atom [mainThread] op = BOOL_OP b2 = bool_atom [mainThread] {
		$expr = new BExpr($b1.expr, $op.getText(), $b2.expr);
	};
bool_atom [String mainThread] returns [BExpr expr]: 
	| ('True' | 'true') {$expr = new BConst(true);}
	| ('False' | 'false') {$expr = new BConst(false);} 
	| ae = arith_comp [mainThread] {$expr = $ae.expr;} 
	| LPAR be = bool_expr [mainThread] RPAR {$expr = $be.expr;};

location returns [Location loc]:
	l = WORD {$loc = new Location($l.getText());};
register returns [Register reg]:
	r = WORD {$reg = new Register($r.getText());};

local [String mainThread] returns [Thread t]:
	r = register '<-' e = arith_expr [mainThread] {
		Map<String, Register> mapThreadRegs = mapRegs.get(mainThread);
		if(!(mapThreadRegs.keySet().contains($r.reg.getName()))) {
			mapThreadRegs.put($r.reg.getName(), $r.reg);
		}
		Register pointerReg = mapThreadRegs.get($r.reg.getName());
		$t = new Local(pointerReg, $e.expr);
	};
load [String mainThread] returns [Thread t]:
	r = register '<:-' l = location {
		Map<String, Register> mapThreadRegs = mapRegs.get(mainThread);
		if(!(mapThreadRegs.keySet().contains($r.reg.getName()))) {
			mapThreadRegs.put($r.reg.getName(), $r.reg);
		}
		if(!(mapLocs.keySet().contains($l.loc.getName()))) {
			System.out.println(String.format("Location %s must be initialized", $l.loc.getName()));
		}
		Register pointerReg = mapThreadRegs.get($r.reg.getName());
		Location pointerLoc = mapLocs.get($l.loc.getName());
		$t = new Load(pointerReg, pointerLoc);
	};
store [String mainThread] returns [Thread t]:
	l = location ':=' r = register {
		if(!(mapLocs.keySet().contains($l.loc.getName()))) {
			System.out.println(String.format("Location %s must be initialized", $l.loc.getName()));
		}
		Map<String, Register> mapThreadRegs = mapRegs.get(mainThread);
		if(!(mapThreadRegs.keySet().contains($r.reg.getName()))) {
			System.out.println(String.format("Register %s must be initialized", $r.reg.getName()));
		}
		Register pointerReg = mapThreadRegs.get($r.reg.getName());
		Location pointerLoc = mapLocs.get($l.loc.getName());
		$t = new Store(pointerLoc, pointerReg);
	};
read [String mainThread] returns [Thread t]:
	r = register '=' l = location POINT 'load' LPAR at = ATOMIC RPAR {
		Map<String, Register> mapThreadRegs = mapRegs.get(mainThread);
		if(!(mapThreadRegs.keySet().contains($r.reg.getName()))) {
			mapThreadRegs.put($r.reg.getName(), $r.reg);
		}
		if(!(mapLocs.keySet().contains($l.loc.getName()))) {
			System.out.println(String.format("Location %s must be initialized", $l.loc.getName()));
		}
		Register pointerReg = mapThreadRegs.get($r.reg.getName());
		Location pointerLoc = mapLocs.get($l.loc.getName());
		$t = new Read(pointerReg, pointerLoc, $at.getText());
	};
write [String mainThread] returns [Thread t]:
	l = location POINT 'store' LPAR at = ATOMIC COMMA r = register RPAR {
		if(!(mapLocs.keySet().contains($l.loc.getName()))) {
			System.out.println(String.format("Location %s must be initialized", $l.loc.getName()));
		}
		Map<String, Register> mapThreadRegs = mapRegs.get(mainThread);
		if(!(mapThreadRegs.keySet().contains($r.reg.getName()))) {
			System.out.println(String.format("Register %s must be initialized", $r.reg.getName()));
		}
		Register pointerReg = mapThreadRegs.get($r.reg.getName());
		Location pointerLoc = mapLocs.get($l.loc.getName());
		$t = new Write(pointerLoc, pointerReg, $at.getText());
	};
fence returns [Thread t]:
	| mfence {$t = new Mfence();}
	| sync {$t = new Sync();}
	| lwsync {$t = new Lwsync();}
	| isync {$t = new Isync();}; 

mfence : 'mfence';
sync : 'sync';
lwsync : 'lwsync';
isync : 'isync';

inst [String mainThread] returns [Thread t]:
	| t1 = atom [mainThread] {$t = $t1.t;}
	| t2 = seq [mainThread] {$t = $t2.t;} 
	| t3 = while_ [mainThread] {$t = $t3.t;} 
	| t4 = if1 [mainThread] {$t = $t4.t;}
	| t5 = if2 [mainThread] {$t = $t5.t;};
atom [String mainThread] returns [Thread t]:
	| t1 = local [mainThread] {$t = $t1.t;}
	| t2 = load [mainThread] {$t = $t2.t;}
	| t3 = store [mainThread] {$t = $t3.t;}
	| t4 = fence {$t = $t4.t;}
	| t5 = read [mainThread] {$t = $t5.t;}
	| t6 = write [mainThread] {$t = $t6.t;};
seq [String mainThread] returns [Thread t]: 
	| t1 = atom [mainThread] ';' t2 = inst[mainThread] {$t = new Seq($t1.t, $t2.t);} 
	| t3 = while_ [mainThread] ';' t4 = inst[mainThread] {$t = new Seq($t3.t, $t4.t);}
	| t5 = if1 [mainThread] ';' t6 = inst[mainThread] {$t = new Seq($t5.t, $t6.t);}
	| t7 = if2 [mainThread] ';' t8 = inst[mainThread] {$t = new Seq($t7.t, $t8.t);};
if1 [String mainThread] returns [Thread t]: 
	'if' b = bool_expr [mainThread] ('then')* LCBRA t1 = inst [mainThread] RCBRA 'else' LCBRA t2 = inst [mainThread] RCBRA {
		$t = new If($b.expr, $t1.t, $t2.t);
	};
if2 [String mainThread] returns [Thread t]: 
	'if' b = bool_expr [mainThread] ('then')* LCBRA t1 = inst [mainThread] RCBRA {
		$t = new If($b.expr, $t1.t, new Skip());
	};

while_ [String mainThread] returns [Thread t]:
	'while' b = bool_expr [mainThread] LCBRA t1 = inst [mainThread] RCBRA {
		$t = new While($b.expr, $t1.t);
	};

program [String name] returns [Program p]:
	{
		Program p = new Program(name);
		p.ass = new Assert();
	} 
	LCBRA l = location {
		mapLocs.put($l.loc.getName(), $l.loc);
	} 
	(COMMA l = location {
		mapLocs.put($l.loc.getName(), $l.loc);
	})* RCBRA 
	('thread t' mainThread = DIGIT {mapRegs.put($mainThread.getText(), new HashMap<String, Register>());} 
		LCBRA t1=inst [$mainThread.getText()] RCBRA {p.add($t1.t);})+ {$p = p;}
	('exists'
	(l = location '=' value = DIGIT ','
	{
		Location loc = $l.loc;
		p.ass.addPair(loc, Integer.parseInt($value.getText()));
	}
	|
	thrd = DIGIT ':' r = register '=' value = DIGIT ','
	{
		Register regPointer = $r.reg;
		Register reg = mapRegs.get($thrd.getText()).get(regPointer.getName());
		p.ass.addPair(reg, Integer.parseInt($value.getText()));
	}
	)*
	)*;

///////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////

// 										LEXER

///////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////

COMP_OP : EQ | NEQ | LEQ | LT | GEQ | GT;
ARITH_OP : ADD | SUB | MULT | DIV | MOD;
BOOL_OP : AND | OR; 

DIGIT : [0-9];
WORD : (LETTER | DIGIT)+;
LETTER : 'a'..'z' | 'A'..'Z';

WS : [ \t\r\n]+ -> skip;
LPAR : '(';
RPAR : ')';
LCBRA : '{';
RCBRA : '}';
COMMA : ',';
POINT : '.';
EQ : '==';
NEQ : '!=';
LEQ : '<=';
LT : '<';
GEQ : '>=';
GT : '>';
ADD : '+';
SUB : '-';
MULT : '*';
DIV : '/';
MOD : '%';
AND : 'and';
OR : 'or';

ATOMIC : '_na' | '_sc' | '_rx' | '_acq' | '_rel' | '_con';