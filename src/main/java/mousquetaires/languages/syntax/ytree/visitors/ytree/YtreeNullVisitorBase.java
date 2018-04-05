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


public abstract class YtreeNullVisitorBase<T> implements YtreeVisitor<T> {

    @Override
    public T visit(YSyntaxTree node) {
        return null;
    }

    // -- Litmus-specific elements: ------------------------------------------------------------------------------------

    @Override
    public T visit(YPreludeStatement node) {
        return null;
    }

    @Override
    public T visit(YProcessStatement node) {
        return null;
    }

    @Override
    public T visit(YPostludeStatement node) {
        return null;
    }

    @Override
    public T visit(YVariableAssertion node) {
        return null;
    }

    // -- END OF Litmus-specific elements ------------------------------------------------------------------------------

    @Override
    public T visit(YConstant node) {
        return null;
    }

    @Override
    public T visit(YIndexerExpression node) {
        return null;
    }

    @Override
    public T visit(YMemberAccessExpression node) {
        return null;
    }

    @Override
    public T visit(YInvocationExpression node) {
        return null;
    }

    @Override
    public T visit(YFunctionDefinition node) {
        return null;
    }

    @Override
    public T visit(YRelativeBinaryExpression node) {
        return null;
    }

    @Override
    public T visit(YLogicalBinaryExpression node) {
        return null;
    }

    @Override
    public T visit(YIntegerBinaryExpression node) {
        return null;
    }

    @Override
    public T visit(YIntegerUnaryExpression node) {
        return null;
    }

    @Override
    public T visit(YLogicalUnaryExpression node) {
        return null;
    }

    @Override
    public T visit(YPointerUnaryExpression node) {
        return null;
    }

    @Override
    public T visit(YTernaryExpression node) {
        return null;
    }

    @Override
    public T visit(YVariableRef node) {
        return null;
    }

    @Override
    public T visit(YType node) {
        return null;
    }

    @Override
    public T visit(YAssignmentExpression node) {
        return null;
    }

    @Override
    public T visit(YBranchingStatement node) {
        return null;
    }

    @Override
    public T visit(YCompoundStatement node) {
        return null;
    }

    @Override
    public T visit(YLinearStatement node) {
        return null;
    }

    @Override
    public T visit(YWhileLoopStatement node) {
        return null;
    }

    @Override
    public T visit(YVariableDeclarationStatement node) {
        return null;
    }

    @Override
    public T visit(YJumpStatement node) {
        return null;
    }

    @Override
    public T visit(YMethodSignature node) {
        return null;
    }

    @Override
    public T visit(YParameter node) {
        return null;
    }
}
