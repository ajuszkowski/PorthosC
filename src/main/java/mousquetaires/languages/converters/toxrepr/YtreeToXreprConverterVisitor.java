package mousquetaires.languages.converters.toxrepr;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.syntax.xrepr.XAssertion;
import mousquetaires.languages.syntax.xrepr.XEntity;
import mousquetaires.languages.syntax.xrepr.XProgram;
import mousquetaires.languages.syntax.xrepr.XProgramBuilder;
import mousquetaires.languages.syntax.xrepr.datamodels.DataModel;
import mousquetaires.languages.syntax.xrepr.events.computation.XBinaryOperationEvent;
import mousquetaires.languages.syntax.xrepr.events.computation.XOperator;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XValue;
import mousquetaires.languages.syntax.ytree.expressions.*;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YIndexerExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YInvocationExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YMemberAccessExpression;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignee;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YIntegerBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YLogicalBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YIntegerPostfixUnaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YLogicalUnaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YPointerUnaryExpression;
import mousquetaires.languages.syntax.ytree.specific.YPreludeStatement;
import mousquetaires.languages.syntax.ytree.specific.YProcessStatement;
import mousquetaires.languages.syntax.ytree.specific.YVariableAssertion;
import mousquetaires.languages.syntax.ytree.statements.YFunctionDefinitionStatement;
import mousquetaires.languages.syntax.ytree.statements.YSequenceStatement;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.languages.syntax.ytree.statements.labeled.*;
import mousquetaires.languages.visitors.YtreeBaseVisitor;
import mousquetaires.types.ZType;
import mousquetaires.types.ZTypeName;
import mousquetaires.types.ZTypeSpecifier;
import mousquetaires.utils.exceptions.NotImplementedException;


class YtreeToXreprConverterVisitor extends YtreeBaseVisitor<XEntity> {

    //private final XCompiler interpreter;
    private final XProgramBuilder programBuilder;
    private final XMemoryManager memoryManager;

    YtreeToXreprConverterVisitor(ProgramLanguage language, DataModel dataModel) {
        //this.interpreter = interpreter;
        this.memoryManager = new XMemoryManager(language, dataModel);
        this.programBuilder = new XProgramBuilder(memoryManager);
    }

    public XProgram getProgram() {
        return programBuilder.build();
    }

    // TODO: All expressions must return XMemoryUnit instances, tentatively generating write events to them.


    // -- Litmus-specific elements: ------------------------------------------------------------------------------------

    @Override
    public XEntity visit(YPreludeStatement node) {
        programBuilder.beginPreludeDefinition();
        visit(node.getBody());
        programBuilder.endProcessDefinition();
        return null;
    }

    @Override
    public XEntity visit(YProcessStatement node) {
        programBuilder.beginProcessDefinition(node.getProcessId());
        visit(node.getBody());
        programBuilder.endProcessDefinition();
        return null;
    }

    @Override
    public XAssertion visit(YVariableAssertion node) {
        XMemoryUnit memoryUnit = visit(node.getLeftExpression());
        XMemoryUnit value = visit(node.getRightExpression());
        XLocalMemoryUnit localValue = programBuilder.moveToLocalMemoryIfNecessary(value);
        return new XAssertion(memoryUnit, localValue);
    }

    // -- END OF Litmus-specific elements ------------------------------------------------------------------------------


    @Override
    public XMemoryUnit visit(YExpression node) {
        return (XMemoryUnit) super.visit(node);
    }

    @Override
    public XMemoryUnit visit(YMemoryLocation node) {
        return (XMemoryUnit) super.visit(node);
    }

    @Override
    public XMemoryUnit visit(YAssignee node) {
        return (XMemoryUnit) super.visit(node);
    }

    @Override
    public XValue visit(YConstant node) {
        return new XValue(node.getValue(), node.getType());
    }

    @Override
    public XEntity visit(YIndexerExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YMemberAccessExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YInvocationExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YFunctionDefinitionStatement node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YRelativeBinaryExpression node) {
        XMemoryUnit left = visit(node.getLeftExpression());
        XMemoryUnit right = visit(node.getRightExpression());
        XOperator operator = XOperatorConverter.convert(node.getKind());
        return programBuilder.emitComputationEvent(operator, left, right);
    }

    @Override
    public XBinaryOperationEvent visit(YLogicalBinaryExpression node) {
        XMemoryUnit left = visit(node.getLeftExpression());
        XMemoryUnit right = visit(node.getRightExpression());
        XOperator operator = XOperatorConverter.convert(node.getKind());
        return programBuilder.emitComputationEvent(operator, left, right);
    }

    @Override
    public XEntity visit(YIntegerBinaryExpression node) {
        XMemoryUnit left = visit(node.getLeftExpression());
        XMemoryUnit right = visit(node.getRightExpression());
        XOperator operator = XOperatorConverter.convert(node.getKind());
        return programBuilder.emitComputationEvent(operator, left, right);
    }

    @Override
    public XEntity visit(YLogicalUnaryExpression node) {
        //XMemoryUnit expression = visit(node.getExpression());
        //XOperator operator = XOperatorConverter.convert(node.getKind());
        //return programBuilder.emitComputationEvent(operator, expression);
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YPointerUnaryExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YIntegerPostfixUnaryExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YTernaryExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YAssignmentExpression node) {
        XMemoryUnit assignee = visit(node.getAssignee());
        XMemoryUnit expression = visit(node.getExpression());
        return programBuilder.emitMemoryEvent(assignee, expression);
    }

    @Override
    public XEntity visit(YLinearStatement node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YVariableDeclarationStatement node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(ZType node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(ZTypeName node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(ZTypeSpecifier node) {
        throw new NotImplementedException();
    }

    @Override
    public XMemoryUnit visit(YVariableRef node) {
        return node.isGlobal()
                ? memoryManager.getSharedMemoryUnit(node.getName())
                : memoryManager.getLocalMemoryUnit (node.getName());
    }

    @Override
    public XEntity visit(YStatement node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YSequenceStatement node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YBranchingStatement node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YLoopStatement node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YJumpStatement node) {
        throw new NotImplementedException();
    }
}
