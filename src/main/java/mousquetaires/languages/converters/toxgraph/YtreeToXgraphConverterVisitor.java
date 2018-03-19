package mousquetaires.languages.converters.toxgraph;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.converters.toxgraph.helpers.MemoryUnitConverter;
import mousquetaires.languages.converters.toxgraph.helpers.XIntegerUnaryOperatorHelper;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.XProgramInterpretationBuilder;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperator;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XZOperatorHelper;
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
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitorBase;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.xgraph.XInterpretationError;


// TODO: move this visitor to converter (replace conv. with visitor)
public class YtreeToXgraphConverterVisitor extends YtreeVisitorBase<XEvent> {

    // Every visit() may not return something, but MUST process an XEvent or throw an exception

    // TODO: non-null checks everywhere!

    private final XProgramInterpretationBuilder program;
    private final MemoryUnitConverter memoryUnitConverter;

    YtreeToXgraphConverterVisitor(ProgramLanguage language, DataModel dataModel) {
        //this.interpreter = interpreter;
        XMemoryManager memoryManager = new XMemoryManager(language, dataModel);
        this.program = new XProgramInterpretationBuilder(memoryManager);
        this.memoryUnitConverter = new MemoryUnitConverter(memoryManager, program, this);
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
        XEvent result = node.getBody().accept(this);
        program.finishProcessDefinition();
        return result;
    }

    @Override
    public XEvent visit(YCompoundStatement node) {
        for (YStatement statement : node.getStatements()) {
            statement.accept(this);
        }
        return null;
    }

    // end of Litmus-specific visits.

    //@Override
    //public XComputationEvent visit(YConstant node) {
    //    XMemoryUnit constant = memoryUnitConverter.convert(node);
    //    XLocalMemoryUnit constantLocal = program.currentProcess.copyToLocalMemoryIfNecessary(constant);
    //    return program.currentProcess.emitComputationEvent(constantLocal);
    //}

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
        program.startProcessDefinition("P0"); // todo: function name
        XEvent result = node.getBody().accept(this);
        program.finishProcessDefinition();
        return result;
    }

    @Override
    public XEvent visit(YIntegerUnaryExpression node) {
        YIntegerUnaryExpression.Kind yOperator = node.getKind();
        YExpression yBaseExpression = node.getExpression();

        if (XIntegerUnaryOperatorHelper.isPrefixOperator(yOperator)) {
            XLocalMemoryUnit baseLocal = memoryUnitConverter.convertToLocal(yBaseExpression);
            XZOperator xOperator = XIntegerUnaryOperatorHelper.isPrefixIncrement(yOperator)
                    ? XZOperator.IntegerPlus
                    : XZOperator.IntegerMinus;
            XConstant one = program.currentProcess.getConstant(1);
            XComputationEvent incremented = program.currentProcess.emitComputationEvent(xOperator, baseLocal, one);
            return program.currentProcess.emitMemoryEvent(baseLocal, incremented);
        }
        else if (XIntegerUnaryOperatorHelper.isPostfixOperator(yOperator)) {
            throw new NotImplementedException("Postfix operators are not supported for now");
        }

        throw new NotImplementedException();
    }

    @Override
    public XEvent visit(YLogicalUnaryExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XEvent visit(YPointerUnaryExpression node) {
        XMemoryUnit location = memoryUnitConverter.convert(node);
        return program.currentProcess.evaluateMemoryUnit(location);
    }

    @Override
    public XComputationEvent visit(YRelativeBinaryExpression node) {
        XLocalMemoryUnit left  = memoryUnitConverter.convertToLocal(node.getLeftExpression());
        XLocalMemoryUnit right = memoryUnitConverter.convertToLocal(node.getRightExpression());
        XZOperator operator = XZOperatorHelper.convert(node.getKind());
        return program.currentProcess.emitComputationEvent(operator, left, right);
    }

    @Override
    public XEvent visit(YLogicalBinaryExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XEvent visit(YIntegerBinaryExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XComputationEvent visit(YTernaryExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XMemoryEvent visit(YAssignmentExpression node) {
        XMemoryUnit left = node.getAssignee().accept(memoryUnitConverter);
        XMemoryUnit right = node.getExpression().accept(memoryUnitConverter);

        XLocalMemoryUnit rightLocal, leftLocal;
        if (left instanceof XSharedMemoryUnit) {
            XSharedMemoryUnit leftShared = (XSharedMemoryUnit) left;
            if (right instanceof XSharedMemoryUnit) {
                rightLocal = program.currentProcess.copyToLocalMemory((XSharedMemoryUnit) right);
            }
            else if (right instanceof XLocalMemoryUnit) {
                rightLocal = (XLocalMemoryUnit) right;
            }
            else {
                throw new IllegalStateException(getUnexpectedOperandTypeErrorMessage(right));
            }
            return program.currentProcess.emitMemoryEvent(leftShared, rightLocal);
        }
        else if (left instanceof XLocalMemoryUnit) {
            leftLocal = (XLocalMemoryUnit) left;
            if (right instanceof XSharedMemoryUnit) {
                return program.currentProcess.emitMemoryEvent(leftLocal, (XSharedMemoryUnit) right);
            }
            else if (right instanceof XLocalMemoryUnit) {
                return program.currentProcess.emitMemoryEvent(leftLocal, (XLocalMemoryUnit) right);
            }
            else {
                throw new IllegalStateException(getUnexpectedOperandTypeErrorMessage(right));
            }
        }
        else {
            throw new IllegalStateException(getUnexpectedOperandTypeErrorMessage(left));
        }
    }

    @Override
    public XEvent visit(YLinearStatement node) {
        YExpression yExpression = node.getExpression();
        if (yExpression != null) {
            XEvent xExpression = yExpression.accept(this);
            if (xExpression != null) {
                return xExpression;
            }
        }
        return null;//program.currentProcess.emitFakeComputationEvent();
    }

    @Override
    public XEvent visit(YVariableDeclarationStatement node) {
        // TODO: case global variable
        //return program.memoryManager.newLocalMemoryUnit(node.getVariable().getName());
        throw new NotImplementedException();
    }

    @Override
    public XEvent visit(YType node) {
        //todo: parse type
        //return new XMockType();
        // todo: do this via helper, not visit!
        throw new IllegalStateException("Should be visited by another visitor");
    }

    //@Override
    //public XComputationEvent visit(YVariableRef node) {
    //    XMemoryUnit variable = memoryUnitConverter.convert(node);
    //    XLocalMemoryUnit variableLocal = program.currentProcess.copyToLocalMemoryIfNecessary(variable);
    //    return program.currentProcess.emitComputationEvent(variableLocal);
    //}

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
        if (elseBranch != null) {
            program.currentProcess.startElseBranchDefinition();
            elseBranch.accept(this);
            program.currentProcess.finishBranchDefinition();
        }

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

    @Override
    public XEvent visit(YMethodSignature node) {
        throw new NotImplementedException();
    }

    @Override
    public XEvent visit(YParameter node) {
        throw new NotImplementedException();
    }

    private static String getUnexpectedOperandTypeErrorMessage(XMemoryUnit operand) {
        return "Unexpected type of operand: " + operand + ", type of " + operand.getClass().getSimpleName();
    }
}
