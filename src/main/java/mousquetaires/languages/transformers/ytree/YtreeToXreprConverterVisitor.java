package mousquetaires.languages.transformers.ytree;

import mousquetaires.interpretation.eventrepr.Interpreter;
import mousquetaires.interpretation.internalrepr.exceptions.UndeclaredMemoryLocationException;
import mousquetaires.languages.transformers.cmin.temp.YTempEntity;
import mousquetaires.languages.visitors.YtreeBaseVisitor;
import mousquetaires.languages.xrepr.XProgram;
import mousquetaires.languages.xrepr.memory.XMemoryLocation;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.ytree.expressions.*;
import mousquetaires.languages.ytree.expressions.invocation.YFunctionArgument;
import mousquetaires.languages.ytree.expressions.invocation.YFunctionInvocationExpression;
import mousquetaires.languages.ytree.signatures.YFunctionParameter;
import mousquetaires.languages.ytree.statements.*;
import mousquetaires.languages.ytree.statements.artificial.YBugonStatement;
import mousquetaires.languages.ytree.statements.artificial.YProcess;
import mousquetaires.languages.ytree.types.YPrimitiveTypeName;
import mousquetaires.languages.ytree.types.YPrimitiveTypeSpecifier;
import mousquetaires.languages.ytree.types.YType;


public class YtreeToXreprConverterVisitor extends YtreeBaseVisitor {

    private XProgram resultProgram;
    private final Interpreter interpreter;

    public YtreeToXreprConverterVisitor(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public XProgram getResult() {
        return resultProgram;
    }

    @Override
    public void visit(YSyntaxTree node) {
        super.visit(node);
    }

    @Override
    public void visit(YProcess node) {
        super.visit(node);
    }

    @Override
    public void visit(YBugonStatement node) {
        super.visit(node);
    }

    @Override
    public void visit(YConstant node) {
        super.visit(node);
    }

    @Override
    public void visit(YMemberAccess node) {
        super.visit(node);
    }

    @Override
    public void visit(YFunctionArgument node) {
        super.visit(node);
    }

    @Override
    public void visit(YFunctionInvocationExpression node) {
        super.visit(node);
    }

    @Override
    public void visit(YFunctionParameter node) {
        super.visit(node);
    }

    @Override
    public void visit(YFunctionDefinitionStatement node) {
        super.visit(node);
    }

    @Override
    public void visit(YUnaryExpression node) {
        super.visit(node);
    }

    @Override
    public void visit(YUnaryExpression.Operator node) {
        super.visit(node);
    }

    @Override
    public void visit(YBinaryExpression node) {
        super.visit(node);
    }

    @Override
    public void visit(YBinaryExpression.Operator node) {
        super.visit(node);
    }

    @Override
    public void visit(YTernaryExpression node) {
        super.visit(node);
    }

    @Override
    public void visit(YAssignmentExpression node) {
        super.visit(node);
    }

    @Override
    public void visit(YAssignmentExpression.Operator node) {
        super.visit(node);
    }

    @Override
    public void visit(YLinearStatement node) {
        super.visit(node);
    }

    @Override
    public void visit(YVariableDeclarationStatement node) {
        // TODO: determine type of memory here
        XMemoryLocation.Kind memoryKind = XMemoryLocation.Kind.SharedMemory;
        XMemoryLocation location = interpreter.createMemoryLocation(node.variable.name, memoryKind);

    }

    @Override
    public void visit(YType node) {
        super.visit(node);
    }

    @Override
    public void visit(YPrimitiveTypeName node) {
        super.visit(node);
    }

    @Override
    public void visit(YPrimitiveTypeSpecifier node) {
        super.visit(node);
    }

    @Override
    public void visit(YVariableRef node) {
        String name = node.name;
        XMemoryLocation location = interpreter.tryGetMemoryLocation(name);
        if (location == null) {
            throw new UndeclaredMemoryLocationException(name);
        }
    }

    @Override
    public void visit(YVariableRef.Kind node) {
        super.visit(node);
    }

    @Override
    public void visit(YBlockStatement node) {
        super.visit(node);
    }

    @Override
    public void visit(YBranchingStatement node) {
        super.visit(node);
    }

    @Override
    public void visit(YLoopStatement node) {
        super.visit(node);
    }

    @Override
    public void visit(YJumpStatement node) {
        super.visit(node);
    }

    @Override
    public void visit(YTempEntity node) {
        super.visit(node);
    }
}
