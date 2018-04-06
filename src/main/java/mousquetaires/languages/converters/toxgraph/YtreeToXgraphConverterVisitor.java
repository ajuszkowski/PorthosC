package mousquetaires.languages.converters.toxgraph;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.converters.toxgraph.interpretation.XProgramInterpreter;
import mousquetaires.languages.syntax.xgraph.XEntity;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperator;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.*;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YIndexerExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YInvocationExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YMemberAccessExpression;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariableRef;
import mousquetaires.languages.syntax.ytree.expressions.binary.YBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YIntegerBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YLogicalBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.ternary.YTernaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YIntegerUnaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YLogicalUnaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YPointerUnaryExpression;
import mousquetaires.languages.syntax.ytree.specific.YPostludeStatement;
import mousquetaires.languages.syntax.ytree.specific.YPreludeStatement;
import mousquetaires.languages.syntax.ytree.specific.YProcessStatement;
import mousquetaires.languages.syntax.ytree.specific.YVariableAssertion;
import mousquetaires.languages.syntax.ytree.statements.*;
import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpStatement;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.languages.syntax.ytree.types.signatures.YMethodSignature;
import mousquetaires.languages.syntax.ytree.types.signatures.YParameter;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.xgraph.XInterpretationError;
import mousquetaires.utils.exceptions.xgraph.XInterpreterUsageError;

import static mousquetaires.utils.StringUtils.wrap;


public class YtreeToXgraphConverterVisitor implements YtreeVisitor<XEntity> {

    private final XProgramInterpreter program;

    public YtreeToXgraphConverterVisitor(XProgramInterpreter program) {
        this.program = program;
    }

    public XProgram getProgram() {
        return program.build();
    }

    @Override
    public XEvent visit(YSyntaxTree node) {
        for (YEntity root : node.getRoots()) {
            root.accept(this);
        }
        return null;
    }

    @Override
    public XEvent visit(YPreludeStatement node) {
        throw new NotImplementedException();
    }

    @Override
    public XEvent visit(YPostludeStatement node) {
        throw new NotImplementedException();
    }

    @Override
    public XEvent visit(YVariableAssertion node) {
        throw new NotImplementedException();
    }

    // start of Litmus-specific visits:

    @Override
    public XEvent visit(YProcessStatement node) {
        program.startProcessDefinition(node.getProcessId());

        node.getBody().accept(this);

        program.finishProcessDefinition();
        return null;
    }

    @Override
    public XEvent visit(YCompoundStatement node) {
        XEvent lastEvent = null;
        for (YStatement statement : node.getStatements()) {
            XEntity visited = statement.accept(this);
            if (visited instanceof XEvent) {
                lastEvent = (XEvent) visited;
            }
        }
        if (lastEvent == null) {
            lastEvent = program.currentProcess.emitNopEvent();
        }
        return lastEvent;
    }

    // end of Litmus-specific visits.


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
        throw new NotImplementedException();
    }

    @Override
    public XEvent visit(YInvocationExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XEvent visit(YFunctionDefinition node) {
        throw new NotImplementedException();
    }

    @Override
    public XEvent visit(YIntegerUnaryExpression node) {
        YIntegerUnaryExpression.Kind yOperator = node.getKind();
        YExpression yBaseExpression = node.getExpression();

        if (YToXOperatorConverter.isPrefixOperator(yOperator)) {
            XLocalLvalueMemoryUnit baseLocal = tryVisitToLocalLvalueOrThrow(yBaseExpression);
            XBinaryOperator xOperator = YToXOperatorConverter.isPrefixIncrement(yOperator)
                    ? XBinaryOperator.Addition
                    : XBinaryOperator.Subtraction;
            XConstant one = program.currentProcess.getConstant(1, Type.int32);
            XComputationEvent incremented = program.currentProcess.emitComputationEvent(xOperator, baseLocal, one);
            return program.currentProcess.emitMemoryEvent(baseLocal, incremented);
        }
        else if (YToXOperatorConverter.isPostfixOperator(yOperator)) {
            throw new NotImplementedException("Postfix operators are not supported for now");
        }
        else {
            throw new NotImplementedException();
        }
    }

    @Override
    public XEvent visit(YLogicalUnaryExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XComputationEvent visit(YRelativeBinaryExpression node) {
        return visitBinaryExpression(node);
    }

    @Override
    public XComputationEvent visit(YLogicalBinaryExpression node) {
        return visitBinaryExpression(node);
    }

    @Override
    public XComputationEvent visit(YIntegerBinaryExpression node) {
        return visitBinaryExpression(node);
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
                rightLocal = program.currentProcess.copyToLocalMemory((XSharedMemoryUnit) right);
            }
            else if (right instanceof XLocalMemoryUnit) {
                // computation events and constants are local memory units
                rightLocal = (XLocalMemoryUnit) right;
            }
            else {
                throw new IllegalStateException(getUnexpectedOperandTypeErrorMessage(right));
            }
            return program.currentProcess.emitMemoryEvent(leftShared, rightLocal);
        }
        else if (leftLvalue instanceof XLocalLvalueMemoryUnit) {
            leftLocal = (XLocalLvalueMemoryUnit) leftLvalue;
            if (right instanceof XSharedMemoryUnit) {
                return program.currentProcess.emitMemoryEvent(leftLocal, (XSharedMemoryUnit) right);
            }
            else if (right instanceof XLocalMemoryUnit) {
                // computation events and constants are local memory units
                return program.currentProcess.emitMemoryEvent(leftLocal, (XLocalMemoryUnit) right);
            }
            else {
                throw new IllegalStateException(getUnexpectedOperandTypeErrorMessage(right));
            }
        }
        else {
            throw new IllegalStateException(getUnexpectedOperandTypeErrorMessage(leftLvalue));
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
            return (XEvent) visited;
        }
        return program.currentProcess.emitNopEvent();
    }

    @Override
    public XEvent visit(YBranchingStatement node) {
        program.currentProcess.startBranchingBlockDefinition();

        program.currentProcess.startConditionDefinition();
        node.getCondition().accept(this);
        program.currentProcess.finishConditionDefinition();

        program.currentProcess.startThenBranchDefinition();
        node.getThenBranch().accept(this);
        program.currentProcess.finishBranchDefinition();

        YStatement elseBranch = node.getElseBranch();
        program.currentProcess.startElseBranchDefinition();
        if (elseBranch != null) {
            elseBranch.accept(this);
        }
        else {
            program.currentProcess.emitNopEvent();
        }
        program.currentProcess.finishBranchDefinition();


        program.currentProcess.finishNonlinearBlockDefinition();

        return null;
    }

    @Override
    public XEvent visit(YWhileLoopStatement node) {
        program.currentProcess.startLoopBlockDefinition();

        program.currentProcess.startConditionDefinition();
        node.getCondition().accept(this);
        program.currentProcess.finishConditionDefinition();

        program.currentProcess.startThenBranchDefinition();
        node.getBody().accept(this);
        program.currentProcess.finishBranchDefinition();

        program.currentProcess.finishNonlinearBlockDefinition();
        return null;
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
                program.currentProcess.processLoopBreakStatement();
                break;
            case Continue:
                program.currentProcess.processLoopContinueStatement();
                break;
            default:
                throw new XInterpretationError("Unknown jump statement kind: " + node.getKind());
        }
        return null; //program.currentProcess.emitFakeEvent();
    }


    // == memory visits: ===============================================================================================

    @Override
    public XLvalueMemoryUnit visit(YVariableDeclarationStatement node) {
        YVariableRef variable = node.getVariable();
        String name = variable.getName();
        Type type = YTypeToBitnessConverter.convert(node.getType());
        return variable.isGlobal()
                ? program.memoryManager.declareLocation(name, type)
                : program.memoryManager.declareRegister(name, type);
    }

    @Override
    public XSharedMemoryUnit visit(YPointerUnaryExpression node) {
        XEntity visited = node.getExpression().accept(this);
        if (!(visited instanceof XLvalueMemoryUnit)) {
            throw new XInterpretationError("Pointer argument is not an l-value: " + wrap(visited));
        }
        XLvalueMemoryUnit lvalue = (XLvalueMemoryUnit) visited;
        String name = lvalue.getName();
        return program.memoryManager.redeclareAsSharedIfNeeded(name);
    }

    @Override
    public XLvalueMemoryUnit visit(YVariableRef variable) {
        return program.memoryManager.getUnit(variable.getName());
    }

    @Override
    public XConstant visit(YConstant node) {
        Type type = YTypeToBitnessConverter.convert(node.getType());
        return XConstant.create(node.getValue(), type);
    }

    @Override
    public XEvent visit(YParameter node) {
        throw new NotImplementedException();
    }


    private XComputationEvent visitBinaryExpression(YBinaryExpression node) {
        XBinaryOperator operator = YToXOperatorConverter.convert(node.getKind());
        XLocalMemoryUnit leftLocal = tryVisitAsLocalOrThrow(node.getLeftExpression());
        XLocalMemoryUnit rightLocal = tryVisitAsLocalOrThrow(node.getRightExpression());
        return program.currentProcess.emitComputationEvent(operator, leftLocal, rightLocal);
    }

    private XLocalMemoryUnit tryVisitAsLocalOrThrow(YExpression expression) {
        XEntity visited = expression.accept(this);
        if (visited != null) {
            if (visited instanceof XLocalMemoryUnit) {
                // computation events, constants here
                return (XLocalMemoryUnit) visited;
            }
            if (visited instanceof XSharedMemoryUnit) {
                return program.currentProcess.copyToLocalMemory((XSharedMemoryUnit) visited);
            }
        }
        throw new XInterpreterUsageError(
                "Could not convert expression " + wrap(expression) + " to a local memory unit");
    }

    private XLocalLvalueMemoryUnit tryVisitToLocalLvalueOrThrow(YExpression expression) {
        XLocalMemoryUnit local = tryVisitAsLocalOrThrow(expression);
        if (!(local instanceof XLocalLvalueMemoryUnit)) {
            throw new XInterpreterUsageError(
                    "Could not convert expression " + wrap(expression) + " to a local l-value memory unit");
        }
        return (XLocalLvalueMemoryUnit) local;
    }

    private static <T, S> String getUnexpectedOperandTypeErrorMessage(T operand) {
        return "Unexpected type of operand " + wrap(operand) +
                " type of: " + operand.getClass().getSimpleName();
    }

    private static <T, S> String getUnexpectedOperandTypeErrorMessage(T operand, Class<S> expected) {
        return "Unexpected type of operand " + wrap(operand) +
                ", expected type of: " + expected.getSimpleName() +
                ", found type of: " + operand.getClass().getSimpleName();
    }
}
