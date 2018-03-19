package mousquetaires.languages.syntax.ytree.visitors.ytree;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YIndexerExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YInvocationExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YMemberAccessExpression;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariableRef;
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
import mousquetaires.utils.StringUtils;

import java.util.Iterator;


public abstract class YtreeVisitorBase<T> implements YtreeVisitor<T> {
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
    public T visit(YFunctionDefinition node) {
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
    public T visit(YIntegerUnaryExpression node) {
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
    public T visit(YTernaryExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YVariableRef node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YType node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YAssignmentExpression node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YBranchingStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YCompoundStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YLinearStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YWhileLoopStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YVariableDeclarationStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YJumpStatement node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YMethodSignature node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    @Override
    public T visit(YParameter node) {
        visitChildren(node);
        throw new IllegalStateException(getExceptionMessage(node));
    }

    private String getExceptionMessage(YEntity node) {
        return "Visit action for node (" + node.getClass().getSimpleName() + ")" +
                StringUtils.wrap(node.toString()) + " is not defined";
    }
}
