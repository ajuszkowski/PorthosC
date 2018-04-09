// Define a grammar called model
grammar Model;
@header{
import mousquetaires.wmm.*;
}
@parser::members
{
String test="test";
}
mcm returns [Wmm value]: {$value =  new Wmm();}  
(ax1=axiom {$value.addAxiom($ax1.value);} | r1=reldef {$value.addRel($r1.value);})+ 
;

axiom returns [Axiom value]: 'acyclic' m1=relation  {$value =  new Acyclic($m1.value);} ('as' NAME)?| 'irreflexive' m1=relation {$value =  new Irreflexive($m1.value);}('as' NAME)?;

reldef returns [Relation value]:
'let' n=NAME '=' m1=relation {$value =$m1.value; $value.setName($n.text);};

relation returns [Relation value]: 
b1=base {$value =$b1.value;} 
| '(' ( m1=relation '|' {$value =$m1.value;}) ( m2=relation '|' {$value =new RelUnion($value, $m2.value);} )* m3=relation ')'{$value =new RelUnion($value, $m3.value);} 
| '(' m1=relation '\\' m2=relation ')' {$value =new RelUnion($m1.value, $m2.value);}//TODO: implement
| '(' m1=relation '&' relation ')' {$value =new RelInterSect($m1.value, $m2.value);}
| m1=relation'+' {$value =new RelTrans($m1.value);}
| m1=relation'*' {$value =new RelTransRef($m1.value);}
| '(' ( m1=relation ';' {$value =$m1.value;}) ( m2=relation ';' {$value =new RelComposition($value, $m2.value);} )* m3=relation ')'{$value =new RelComposition($value, $m3.value);} 
;

base returns [Relation value]: 
PO {$value=new BasicRelation("po");}
| RF {$value=new BasicRelation("rf");}
| CO {$value=new BasicRelation("co");}
| AD {$value=new BasicRelation("po");}
| DD {$value=new BasicRelation("dd");}
| CD {$value=new BasicRelation("cd");}
| STHD {$value=new BasicRelation("sthd");}
| SLOC {$value=new BasicRelation("slow");}
| MFENCE {$value=new BasicRelation("mfence");}
| SYNC {$value=new BasicRelation("sync");}
| LWSYNC {$value=new BasicRelation("lwsync");}
| ISYNC {$value=new BasicRelation("isync");}
| ID {$value=new BasicRelation("po");} //TODO: implement
| '(' SET 'x' SET ')'  {$value=new BasicRelation("po");}//TODO: implement
| n=NAME {$value=new RelDummy($n.text);} 
;
ST : 'st' ;
PO : 'po' ;
POLOC : 'poloc' ;
RFE : 'rfe' ;
RF : 'rf' ;
CO : 'co' ;
AD : 'ad' ;
DD : 'dd' ;
CD : 'cd' ;
STHD : 'sthd' ;
SLOC : 'sloc' ;
MFENCE : 'mfence' ;
SYNC : 'sync' ;
LWSYNC : 'lwsync' ;
ISYNC : 'isync' ;
ID : 'id(' SET ')' ;
SET : E | W | R ;
E : 'E' ;
W : 'W' ;
R : 'R' ;
NAME : [a-z]+ ;            // match lower-case identifiers
WS : [ \t\n\r]+ -> skip ; // skip spaces, tabs, newlines
//ENDE : EOF -> skip ;
ML_COMMENT
   : '(*' .*? '*)' -> skip
;