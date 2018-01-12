package mousquetaires.languages.visitors;

import mousquetaires.languages.transformers.cmin.temp.YTempEntity;
import mousquetaires.languages.ytree.YEntity;
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

import java.util.Iterator;


public abstract class YtreeBaseVisitor<T> implements YtreeVisitor<T> {

    public final void visitChildren(YEntity node) {
        Iterator<? extends YEntity> iterator = node.getChildrenIterator();
        while (iterator.hasNext()) {
            YEntity child = iterator.next();
            child.accept(this);
        }
    }

    @Override
    public T visit(YSyntaxTree node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YProcess node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YBugonStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YConstant node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YMemberAccess node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YFunctionArgument node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YFunctionInvocationExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YFunctionParameter node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YFunctionDefinitionStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YUnaryExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YUnaryExpression.Operator node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YBinaryExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YBinaryExpression.Operator node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YTernaryExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YAssignmentExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YAssignmentExpression.Operator node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YLinearStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YVariableDeclarationStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YType node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YPrimitiveTypeName node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YPrimitiveTypeSpecifier node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YVariableRef node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YVariableRef.Kind node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YBlockStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YBranchingStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YLoopStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YJumpStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YTempEntity node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }
    
    
    private String getExceptionMessage(YEntity node) {
        return "Visit action for node type " + node.getClass().getName() + " is not defined";
    }
}
