package mousquetaires.languages.converters.toxgraph;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.converters.toxgraph.helpers.MemoryUnitConverter;
import mousquetaires.languages.converters.toxgraph.helpers.XBinaryOperatorConverter;
import mousquetaires.languages.converters.toxgraph.helpers.XUnaryOperatorHelper;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.XProgramBuilder;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.operators.XOperator;
import mousquetaires.languages.syntax.xgraph.events.memory.XLocalMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryManager;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xgraph.processes.contexts.XBlockContextKind;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.ternary.YTernaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariableRef;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YIndexerExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YInvocationExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YMemberAccessExpression;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YUnaryExpression;
import mousquetaires.languages.syntax.ytree.specific.YPostludeStatement;
import mousquetaires.languages.syntax.ytree.specific.YPreludeStatement;
import mousquetaires.languages.syntax.ytree.specific.YProcessStatement;
import mousquetaires.languages.syntax.ytree.specific.YVariableAssertion;
import mousquetaires.languages.syntax.ytree.statements.*;
import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpStatement;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.languages.syntax.ytree.types.signatures.YMethodSignature;
import mousquetaires.languages.syntax.ytree.types.signatures.YParameter;
import mousquetaires.languages.visitors.ytree.YtreeVisitorBase;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.xgraph.XCompilationError;


// TODO: move this visitor to converter (replace conv. with visitor)
class YtreeToXgraphConverterVisitor extends YtreeVisitorBase<XEvent> {

    // Every visit() may not return something, but MUST process an XEvent or throw an exception

    // TODO: non-null checks everywhere!

    private final XProgramBuilder program;
    private final MemoryUnitConverter memoryUnitConverter;

    YtreeToXgraphConverterVisitor(ProgramLanguage language, DataModel dataModel) {
        //this.interpreter = interpreter;
        XMemoryManager memoryManager = new XMemoryManager(language, dataModel);
        this.program = new XProgramBuilder(memoryManager);
        this.memoryUnitConverter = new MemoryUnitConverter(memoryManager);
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

    @Override
    public XComputationEvent visit(YConstant node) {
        XMemoryUnit constant = memoryUnitConverter.convert(node);
        XLocalMemoryUnit constantLocal = program.currentProcess.copyToLocalMemoryIfNecessary(constant);
        return program.currentProcess.emitComputationEvent(constantLocal);
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
        program.startProcessDefinition("?"); // todo: function name
        XEvent result = node.getBody().accept(this);
        program.finishProcessDefinition();
        return result;
    }

    @Override
    public XLocalMemoryEvent visit(YUnaryExpression node) {
        XEvent base = node.getExpression().accept(this); //todo:evaluate
        XLocalMemoryUnit baseLocal = program.currentProcess.copyToLocalMemoryIfNecessary(base);
        YUnaryExpression.Kind yOperator = node.getKind();
        if (XUnaryOperatorHelper.isPrefixIntegerOperator(yOperator)) {
            XOperator xOperator = XUnaryOperatorHelper.isPrefixIncrement(yOperator)
                    ? XOperator.IntegerPlus
                    : XOperator.IntegerMinus;
            XConstant one = program.currentProcess.getConstant(1);
            XComputationEvent incremented = program.currentProcess.emitComputationEvent(xOperator, baseLocal, one);
            return program.currentProcess.emitMemoryEvent(baseLocal, incremented);
        } else if (XUnaryOperatorHelper.isPostfixIntegerOperator(yOperator)) {
            throw new NotImplementedException("Postfix operators are not supported for now");
        }

        throw new NotImplementedException("Unsupported unary operator: " + yOperator); //todo
    }

    @Override
    public XComputationEvent visit(YBinaryExpression node) {
        XLocalMemoryUnit leftLocal  = convertOrEvaluateExpression(node.getLeftExpression());
        XLocalMemoryUnit rightLocal = convertOrEvaluateExpression(node.getRightExpression());
        XOperator operator = XBinaryOperatorConverter.convert(node.getKind());
        return program.currentProcess.emitComputationEvent(operator, leftLocal, rightLocal);
    }

    @Override
    public XComputationEvent visit(YTernaryExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XLocalMemoryEvent visit(YAssignmentExpression node) {
        XMemoryUnit assignee = memoryUnitConverter.convert(node.getAssignee());
        XLocalMemoryUnit localAssignee = program.currentProcess.copyToLocalMemoryIfNecessary(assignee);
        XLocalMemoryUnit expressionLocal = convertOrEvaluateExpression(node.getExpression());
        return program.currentProcess.emitMemoryEvent(localAssignee, expressionLocal);
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

    @Override
    public XComputationEvent visit(YVariableRef node) {
        XMemoryUnit variable = memoryUnitConverter.convert(node);
        XLocalMemoryUnit variableLocal = program.currentProcess.copyToLocalMemoryIfNecessary(variable);
        return program.currentProcess.emitComputationEvent(variableLocal);
    }

    @Override
    public XEvent visit(YBranchingStatement node) {
        program.currentProcess.startNonlinearBlockDefinition(XBlockContextKind.Branching);

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
        program.currentProcess.startNonlinearBlockDefinition(XBlockContextKind.Loop);

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
                throw new XCompilationError("Unknown jump statement kind: " + node.getKind());
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


    //private XComputationEvent evaluate(XEntity expression) {
    //    XLocalMemoryUnit conditionLocal = TypeCastHelper.castToLocalMemoryUnitOrThrow(expression);
    //    return conditionLocal instanceof XComputationEvent
    //            ? (XComputationEvent) conditionLocal
    //            :  program.currentProcess.emitComputationEvent(conditionLocal);
    //}

    //private XEvent WTFprocessExpression(XEvent event) {
    //    if (event instanceof XRegister) {
    //        return program.currentProcess.emitComputationEvent((XRegister) event);
    //    }
    //    return event;
    //    //throw new XCompilatorUsageError("Unexpected expression type: " + expression.getClass().getSimpleName());
    //}

    private XLocalMemoryUnit convertOrEvaluateExpression(YExpression expression) {
        XMemoryUnit converted = memoryUnitConverter.tryConvert(expression);
        return converted != null
                ? program.currentProcess.copyToLocalMemoryIfNecessary(converted)
                : program.currentProcess.copyToLocalMemoryIfNecessary(expression.accept(this));
    }
}
