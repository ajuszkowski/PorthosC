package mousquetaires.languages.visitors;

import mousquetaires.languages.converters.toytree.cmin.temporaries.YTempEntity;
import mousquetaires.languages.types.YXType;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.expressions.*;
import mousquetaires.languages.syntax.ytree.expressions.invocation.YFunctionArgument;
import mousquetaires.languages.syntax.ytree.expressions.invocation.YFunctionInvocationExpression;
import mousquetaires.languages.syntax.ytree.signatures.YFunctionParameter;
import mousquetaires.languages.syntax.ytree.statements.*;
import mousquetaires.languages.syntax.ytree.statements.artificial.YBugonStatement;
import mousquetaires.languages.syntax.ytree.statements.artificial.YProcess;
import mousquetaires.languages.types.YXTypeName;
import mousquetaires.languages.types.YXTypeSpecifier;

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
    public T visit(YXType node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YXTypeName node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YXTypeSpecifier node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YVariableRef node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YBlockStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YExpression node) {
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
