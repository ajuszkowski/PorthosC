//package mousquetaires.languages.syntax.ytree.visitors.ytree;
//
//import mousquetaires.languages.syntax.ytree.YEntity;
//import mousquetaires.languages.syntax.ytree.YSyntaxTree;
//import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
//import mousquetaires.languages.syntax.ytree.expressions.accesses.YIndexerExpression;
//import mousquetaires.languages.syntax.ytree.expressions.accesses.YInvocationExpression;
//import mousquetaires.languages.syntax.ytree.expressions.accesses.YMemberAccessExpression;
//import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
//import mousquetaires.languages.syntax.ytree.expressions.atomics.YConstant;
//import mousquetaires.languages.syntax.ytree.expressions.atomics.YLabeledVariable;
//import mousquetaires.languages.syntax.ytree.expressions.atomics.YParameter;
//import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariable;
//import mousquetaires.languages.syntax.ytree.expressions.operations.YBinaryExpression;
//import mousquetaires.languages.syntax.ytree.expressions.operations.YBinaryOperator;
//import mousquetaires.languages.syntax.ytree.expressions.operations.YUnaryExpression;
//import mousquetaires.languages.syntax.ytree.expressions.operations.YUnaryOperator;
//import mousquetaires.languages.syntax.ytree.expressions.ternary.YTernaryExpression;
//import mousquetaires.languages.syntax.ytree.litmus.YAssertionStatement;
//import mousquetaires.languages.syntax.ytree.litmus.YPostludeStatement;
//import mousquetaires.languages.syntax.ytree.litmus.YPreludeStatement;
//import mousquetaires.languages.syntax.ytree.litmus.YProcessStatement;
//import mousquetaires.languages.syntax.ytree.statements.*;
//import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpStatement;
//import mousquetaires.languages.syntax.ytree.types.YMethodSignature;
//import mousquetaires.languages.syntax.ytree.types.YType;
//import mousquetaires.utils.StringUtils;
//import mousquetaires.utils.exceptions.ytree.YtreeVisitorIllegalStateException;
//
//
//public abstract class YtreeVisitorStrictBase<T> implements YtreeVisitor<T> {
//    @Override
//    public T visit(YSyntaxTree node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//
//    // -- Litmus-litmus elements: ------------------------------------------------------------------------------------
//
//    @Override
//    public T visit(YPreludeStatement node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YProcessStatement node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YPostludeStatement node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YAssertionStatement node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YLabeledVariable node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    // -- END OF Litmus-litmus elements ------------------------------------------------------------------------------
//
//    @Override
//    public T visit(YConstant node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YIndexerExpression node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YMemberAccessExpression node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YInvocationExpression node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YFunctionDefinition node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YBinaryOperator node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YBinaryExpression node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YUnaryOperator node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YUnaryExpression node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YTernaryExpression node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YVariable node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YType node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YAssignmentExpression node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YBranchingStatement node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YCompoundStatement node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YLinearStatement node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YWhileLoopStatement node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YVariableDeclarationStatement node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YJumpStatement node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YMethodSignature node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    @Override
//    public T visit(YParameter node) {
//        throw new YtreeVisitorIllegalStateException(getExceptionMessage(node));
//    }
//
//    private String getExceptionMessage(YEntity node) {
//        return "Visit action for node (" + node.getClass().getSimpleName() + ")" +
//                StringUtils.wrap(node.toString()) + " is not defined";
//    }
//}
