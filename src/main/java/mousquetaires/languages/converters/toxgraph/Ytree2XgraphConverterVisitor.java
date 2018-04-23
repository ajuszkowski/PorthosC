package mousquetaires.languages.converters.toxgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.common.Type;
import mousquetaires.languages.converters.toxgraph.interpretation.XInterpreter;
import mousquetaires.languages.converters.toxgraph.interpretation.XProgramInterpreter;
import mousquetaires.languages.syntax.xgraph.program.XCyclicProgram;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperator;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryOperator;
import mousquetaires.languages.syntax.xgraph.events.memory.XMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.languages.syntax.xgraph.process.XProcessKind;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YIndexerExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YInvocationExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YMemberAccessExpression;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YLabeledVariable;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YParameter;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariable;
import mousquetaires.languages.syntax.ytree.expressions.operations.YBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.operations.YBinaryOperator;
import mousquetaires.languages.syntax.ytree.expressions.operations.YUnaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.operations.YUnaryOperator;
import mousquetaires.languages.syntax.ytree.expressions.ternary.YTernaryExpression;
import mousquetaires.languages.syntax.ytree.litmus.YPostludeStatement;
import mousquetaires.languages.syntax.ytree.litmus.YPreludeStatement;
import mousquetaires.languages.syntax.ytree.litmus.YProcessStatement;
import mousquetaires.languages.syntax.ytree.statements.*;
import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpStatement;
import mousquetaires.languages.syntax.ytree.types.YMethodSignature;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.xgraph.XInterpretationError;
import mousquetaires.utils.exceptions.xgraph.XInterpreterUsageError;

import java.util.HashSet;
import java.util.Set;

import static mousquetaires.utils.StringUtils.wrap;


public class Ytree2XgraphConverterVisitor implements YtreeVisitor<XEntity> {

    private final XProgramInterpreter program;

    public Ytree2XgraphConverterVisitor(XProgramInterpreter program) {
        this.program = program;
    }

    public XCyclicProgram getProgram() {
        return program.build();
    }

    @Override
    public XEvent visit(YSyntaxTree node) {
        for (YEntity root : node.getRoots()) {
            root.accept(this);
        }
        return null;
    }

    // start of Litmus-litmus visits:

    @Override
    public XEvent visit(YPreludeStatement node) {
        program.startProcessDefinition(XProcessKind.Prelude, XProcessId.PreludeProcessId);
        for (YAssignmentExpression assignment : node.getInitialWrites()) {
            assignment.accept(this);
        }
        program.finishProcessDefinition();
        return null; //statements return null
    }

    @Override
    public XEvent visit(YProcessStatement node) {
        program.startProcessDefinition(XProcessKind.ConcurrentProcess, node.getProcessId());
        for (YParameter parameter : node.getSignature().getParameters()) {
            YVariable parameterVariable = parameter.getVariable();
            String name = parameterVariable.getName();
            Type type = YType2TypeConverter.convert(parameter.getType());
            if (parameterVariable.isGlobal()) {
                program.declareLocation(name, type);
            }
            else {
                program.declareRegister(name, type);
            }
        }

        node.getBody().accept(this);

        program.finishProcessDefinition();
        return null; //statements return null
    }

    @Override
    public XEntity visit(YPostludeStatement node) {
        program.startProcessDefinition(XProcessKind.Postlude, XProcessId.PostludeProcessId);
        node.getExpression().accept(this);
        program.finishProcessDefinition();
        return null; //statements return null
    }

// end of Litmus-litmus visits.

    @Override
    public XEvent visit(YCompoundStatement node) {
        for (YStatement statement : node.getStatements()) {
            statement.accept(this);
        }
        return null; //statements return null
    }

    @Override
    public XEvent visit(YMethodSignature node) {
        throw new NotImplementedException();
    }

    // == event visits: ================================================================================================

    @Override
    public XEvent visit(YType node) {
        throw new NotImplementedException();
    }

    @Override
    public XEvent visit(YIndexerExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XEvent visit(YMemberAccessExpression node) {
        // TODO: to process member accesses correctly we need to set up the work with arrays (+structures). Copy receiver into register (as 'self' parameter in python)
        throw new NotImplementedException();
        //YExpression yBaseExpression = node.getBaseExpression();
        //if (!(yBaseExpression instanceof YVariableRef)) {
        //    throw new NotImplementedException("For now, only one-level member accesses are supported (a.b, not a.b.c): "
        //                                              + wrap(node));
        //}
        //String receiverName = ((YVariableRef) yBaseExpression).getName();
    }

    @Override
    public XEvent visit(YInvocationExpression node) {
        YExpression yBaseExpression = node.getBaseExpression();
        //if (!(yBaseExpression instanceof YVariableRef)) {
        //    throw new NotImplementedException("For now, only simple named method invocations are supported: unsupported base expression: " + wrap(yBaseExpression));
        //}
        XMemoryUnit receiver = null;
        // TODO: work with signature here!
        String methodName;
        if (yBaseExpression instanceof YVariable) {
            methodName = ((YVariable) yBaseExpression).getName();
        }
        else if (yBaseExpression instanceof YMemberAccessExpression) {
            YMemberAccessExpression asMemberAccess = (YMemberAccessExpression) yBaseExpression;
            methodName = asMemberAccess.getMemberName();
            receiver = tryCastToMemoryUnit(asMemberAccess.getBaseExpression().accept(this));
        }
        else {
            throw new NotImplementedException("only 'receiver.method()' and 'method()' invocations are supported so far");
        }
        ImmutableList<YExpression> yArguments = node.getArguments();
        List<XMemoryUnit> argumentsList = new ArrayList<>(yArguments.size());
        for (YExpression yArgument : yArguments) {
            argumentsList.add(tryCastToMemoryUnit(yArgument.accept(this)));
        }
        XMemoryUnit[] arguments = argumentsList.toArray(new XMemoryUnit[0]);

        XEntity methodCallResult = program.processMethodCall(methodName, receiver, arguments);
        if (methodCallResult instanceof XEvent) {
            return (XEvent) methodCallResult;
        }
        return program.tryEvaluateComputation(methodCallResult);
    }

    @Override
    public XEvent visit(YFunctionDefinition node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YUnaryExpression node) {
        YUnaryOperator yOperator = node.getOperator();
        YExpression yBaseExpression = node.getExpression();
        //XLocalMemoryUnit baseLocal = tryConvertToLocal(yBaseExpression.accept(this));
        XMemoryUnit base = tryCastToMemoryUnit(yBaseExpression.accept(this));

        boolean isPostfixIncrement = (yOperator == YUnaryOperator.PostfixIncrement);
        boolean isPostfixDecrement = (yOperator == YUnaryOperator.PostfixDecrement);

        if (isPostfixIncrement || isPostfixDecrement) {
            XRegister tempReg = program.newTempRegister(base.getType());
            XConstant constantOne = XConstant.create(1, base.getType());
            XBinaryOperator operator = isPostfixIncrement ? XBinaryOperator.Addition : XBinaryOperator.Subtraction;
            XComputationEvent increment = program.createComputationEvent(operator, tempReg, constantOne);
            if (base instanceof XLocalLvalueMemoryUnit) {
                XLocalLvalueMemoryUnit baseLocal = (XLocalLvalueMemoryUnit) base;
                program.emitMemoryEvent(tempReg, baseLocal);
                program.emitMemoryEvent(baseLocal, increment);
            }
            else if (base instanceof XSharedLvalueMemoryUnit) {
                XSharedLvalueMemoryUnit baseShared = (XSharedLvalueMemoryUnit) base;
                program.emitMemoryEvent(tempReg, baseShared);
                program.emitMemoryEvent(baseShared, increment);
            }
            else {
                throw new XInterpretationError("memory unit may be either local or shared l-value: " + wrap(base));
            }
            return tempReg;
        }
        else {
            XUnaryOperator resultOperator = (XUnaryOperator) yOperator.accept(this);
            return program.createComputationEvent(resultOperator, tryConvertToLocal(base));
        }
    }

    @Override
    public XUnaryOperator visit(YUnaryOperator node) {
        switch (node) {
            case PrefixIncrement:
            case PrefixDecrement:
            case PostfixIncrement:
            case PostfixDecrement:
                throw new IllegalStateException("on x-level, it is a binary operation and it should be processed on the upper level");
            case IntegerNegation:
                throw new NotImplementedException();
            case BitwiseComplement:
                return XUnaryOperator.BitNegation;
            case Negation:
                throw new NotImplementedException();
            default:
                throw new IllegalArgumentException(node.name());
        }
    }

    @Override
    public XEntity visit(YBinaryExpression node) {
        XBinaryOperator operator = (XBinaryOperator) node.getOperator().accept(this);
        XLocalMemoryUnit leftLocal = tryConvertToLocal(node.getLeftExpression().accept(this));
        XLocalMemoryUnit rightLocal = tryConvertToLocal(node.getRightExpression().accept(this));
        return program.createComputationEvent(operator, leftLocal, rightLocal);
    }

    @Override
    public XBinaryOperator visit(YBinaryOperator node) {
        switch (node) {
            case Equals:            return XBinaryOperator.CompareEquals;
            case NotEquals:         return XBinaryOperator.CompareNotEquals;
            case Greater:           return XBinaryOperator.CompareGreater;
            case GreaterOrEquals:   return XBinaryOperator.CompareGreaterOrEquals;
            case Less:              return XBinaryOperator.CompareLess;
            case LessOrEquals:      return XBinaryOperator.CompareLessOrEquals;
            case Conjunction:     return XBinaryOperator.Conjunction;
            case Disjunction:     return XBinaryOperator.Disjunction;
            case Plus:       return XBinaryOperator.Addition;
            case Minus:      return XBinaryOperator.Subtraction;
            case Multiply:   return XBinaryOperator.Multiplication;
            case Divide:     return XBinaryOperator.Division;
            case Modulo:     return XBinaryOperator.Modulo;
            case LeftShift:  return XBinaryOperator.LeftShift;
            case RightShift: return XBinaryOperator.RightShift;
            case BitAnd:     return XBinaryOperator.BitAnd;
            case BitOr:      return XBinaryOperator.BitOr;
            case BitXor:     return XBinaryOperator.BitXor;
            default:
                throw new IllegalArgumentException(node.name());
        }
    }

    @Override
    public XComputationEvent visit(YTernaryExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XMemoryEvent visit(YAssignmentExpression node) {
        YExpression assignee = node.getAssignee();
        YExpression expression = node.getExpression();

        XEntity leftVisited = assignee.accept(this);
        if (!(leftVisited instanceof XLvalueMemoryUnit)) {
            throw new XInterpretationError(getUnexpectedOperandTypeErrorMessage(assignee, XLvalueMemoryUnit.class));
        }
        XLvalueMemoryUnit leftLvalue = (XLvalueMemoryUnit) leftVisited;
        XEntity right = expression.accept(this);

        XLocalLvalueMemoryUnit leftLocal;
        XLocalMemoryUnit rightLocal;
        if (leftLvalue instanceof XSharedLvalueMemoryUnit) {
            XSharedLvalueMemoryUnit leftShared = (XSharedLvalueMemoryUnit) leftLvalue;
            if (right instanceof XSharedMemoryUnit) {
                rightLocal = program.copyToLocalMemory((XSharedMemoryUnit) right);
            }
            else if (right instanceof XLocalMemoryUnit) {
                // computation events and constants are local memory units
                rightLocal = (XLocalMemoryUnit) right;
            }
            else {
                throw new XInterpretationError(getUnexpectedOperandTypeErrorMessage(right));
            }
            return program.emitMemoryEvent(leftShared, rightLocal);
        }
        else if (leftLvalue instanceof XLocalLvalueMemoryUnit) {
            leftLocal = (XLocalLvalueMemoryUnit) leftLvalue;
            if (right instanceof XSharedMemoryUnit) {
                return program.emitMemoryEvent(leftLocal, (XSharedMemoryUnit) right);
            }
            else if (right instanceof XLocalMemoryUnit) {
                // computation events and constants are local memory units
                return program.emitMemoryEvent(leftLocal, (XLocalMemoryUnit) right);
            }
            else {
                throw new XInterpretationError(getUnexpectedOperandTypeErrorMessage(right));
            }
        }
        else {
            throw new XInterpretationError(getUnexpectedOperandTypeErrorMessage(leftLvalue));
        }
    }

    @Override
    public XEvent visit(YLinearStatement node) {
        YExpression yExpression = node.getExpression();
        if (yExpression != null) {
            XEntity visited = yExpression.accept(this);
            if (!(visited instanceof XEvent)) {
                throw new XInterpretationError("Could not interpret linear statement: " + wrap(node.getExpression()));
            }
            return null; //statements return null
        }
        program.emitNopEvent();
        return null; //statements return null
    }

    @Override
    public XEvent visit(YBranchingStatement node) {
        program.startBlockDefinition(XInterpreter.BlockKind.Branching);

        program.startBlockConditionDefinition();
        XComputationEvent condition = program.tryEvaluateComputation(node.getCondition().accept(this));
        program.finishBlockConditionDefinition(condition);

        program.startBlockBranchDefinition(XInterpreter.BranchKind.Then);
        node.getThenBranch().accept(this);
        program.finishBlockBranchDefinition();

        YStatement elseBranch = node.getElseBranch();
        program.startBlockBranchDefinition(XInterpreter.BranchKind.Else);
        if (elseBranch != null) {
            elseBranch.accept(this);
        }
        else {
            program.emitNopEvent(); // needed for encoding, TODO: add reference to the doc here
        }
        program.finishBlockBranchDefinition();


        program.finishNonlinearBlockDefinition();

        return null; //statements return null
    }

    @Override
    public XEvent visit(YWhileLoopStatement node) {
        program.startBlockDefinition(XInterpreter.BlockKind.Loop);

        program.startBlockConditionDefinition();
        XComputationEvent condition = program.tryEvaluateComputation(node.getCondition().accept(this));
        program.finishBlockConditionDefinition(condition);

        program.startBlockBranchDefinition(XInterpreter.BranchKind.Then);
        node.getBody().accept(this);
        program.finishBlockBranchDefinition();

        program.finishNonlinearBlockDefinition();
        return null; //statements return null
    }

    @Override
    public XEvent visit(YJumpStatement node) {
        switch (node.getKind()) {
            case Goto:
                throw new NotImplementedException();
                //break;
            case Return:
                throw new NotImplementedException();
                //break;
            case Break:
                program.processJumpStatement(XInterpreter.JumpKind.Break);
                break;
            case Continue:
                program.processJumpStatement(XInterpreter.JumpKind.Continue);
                break;
            default:
                throw new XInterpretationError("Unknown jump statement kind: " + node.getKind());
        }
        return null; //statements return null
    }


    // == memory visits: ===============================================================================================

    @Override
    public XLvalueMemoryUnit visit(YVariableDeclarationStatement node) {
        YVariable variable = node.getVariable();
        String name = variable.getName();
        Type type = YType2TypeConverter.convert(node.getType());
        if (variable.isGlobal()) {
            program.declareLocation(name, type);
        }
        else {
            program.declareRegister(name, type);
        }
        return null; //statements return null
    }
    //
    //@Override
    //public XSharedMemoryUnit visit(YPointerUnaryExpression node) {
    //    XEntity visited = node.getExpression().accept(this);
    //    if (!(visited instanceof XLvalueMemoryUnit)) {
    //        throw new XInterpretationError("Pointer argument is not an l-value: " + wrap(visited));
    //    }
    //    XLvalueMemoryUnit lvalue = (XLvalueMemoryUnit) visited;
    //    String name = lvalue.getName();
    //    return program.memoryManager.redeclareAsSharedIfNeeded(name);
    //}

    @Override
    public XLvalueMemoryUnit visit(YVariable variable) {
        String name = variable.getName();
        XLvalueMemoryUnit result = program.getDeclaredUnitOrNull(name);
        return (result == null)
                ? program.declareUnresolvedUnit(name, variable.isGlobal())
                : result;
    }

    public XLvalueMemoryUnit visit(YLabeledVariable variable) {
        return program.getDeclaredRegister(variable.getName(), new XProcessId(variable.getLabel()));
    }

    @Override
    public XLocalMemoryUnit visit(YConstant node) {
        Type type = YType2TypeConverter.convert(node.getType());
        return XConstant.create(node.getValue(), type);
    }

    @Override
    public XEvent visit(YParameter node) {
        throw new NotImplementedException();
    }

    private XMemoryUnit tryCastToMemoryUnit(XEntity expression) {
        if (expression instanceof XMemoryUnit) {
            return (XMemoryUnit) expression;
        }
        throw new XInterpreterUsageError("Could not convert expression " + wrap(expression) + " to a memory unit");
    }

    private XLvalueMemoryUnit tryCastToLvalue(XMemoryUnit expression) {
        if (expression instanceof XLvalueMemoryUnit) {
            return (XLvalueMemoryUnit) expression;
        }
        throw new XInterpreterUsageError("Could not convert expression " + wrap(expression) + " to an l-value memory unit");
    }

    private XLocalMemoryUnit tryConvertToLocal(XEntity expression) {
        XLocalMemoryUnit converted = program.tryConvertToLocalOrNull(expression);
        if (converted == null) {
            throw new XInterpreterUsageError("Could not convert expression " + wrap(expression) + " to a local memory unit");
        }
        return converted;
    }

    private static <T> String getUnexpectedOperandTypeErrorMessage(T operand) {
        return "Unexpected type of operand " + wrap(operand) +
                " type of: " + operand.getClass().getSimpleName();
    }

    private static <T, S> String getUnexpectedOperandTypeErrorMessage(T operand, Class<S> expected) {
        return "Unexpected type of operand " + wrap(operand) +
                ", expected type of: " + expected.getSimpleName() +
                ", found type of: " + operand.getClass().getSimpleName();
    }
}
