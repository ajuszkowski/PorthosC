package mousquetaires.languages.visitors;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
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
import mousquetaires.languages.syntax.ytree.specific.YPostludeStatement;
import mousquetaires.languages.syntax.ytree.specific.YPreludeStatement;
import mousquetaires.languages.syntax.ytree.specific.YProcessStatement;
import mousquetaires.languages.syntax.ytree.specific.YVariableAssertion;
import mousquetaires.languages.syntax.ytree.statements.YFunctionDefinitionStatement;
import mousquetaires.languages.syntax.ytree.statements.YSequenceStatement;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.languages.syntax.ytree.statements.labeled.*;
import mousquetaires.types.ZType;
import mousquetaires.types.ZTypeSpecifier;

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

    // -- Litmus-specific elements: ------------------------------------------------------------------------------------

    @Override
    public T visit(YPreludeStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YProcessStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YPostludeStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YVariableAssertion node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    // -- END OF Litmus-specific elements ------------------------------------------------------------------------------

    @Override
    public T visit(YExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YMemoryLocation node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YAssignee node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YConstant node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YIndexerExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YMemberAccessExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YInvocationExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YFunctionDefinitionStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YRelativeBinaryExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YLogicalBinaryExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YIntegerBinaryExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YLogicalUnaryExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YPointerUnaryExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YIntegerPostfixUnaryExpression node) {
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
    public T visit(YLinearStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YSequenceStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YVariableDeclarationStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(ZType node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(ZTypeSpecifier node) {
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

    private String getExceptionMessage(YEntity node) {
        return "Visit action for node returnType " + node.getClass().getName() + " is not defined";
    }
}
