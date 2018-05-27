package mousquetaires.languages.syntax.ytree.visitors;

import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.YEmptyExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YIndexerExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YInvocationExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YMemberAccessExpression;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YLabeledVariableRef;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YParameter;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariableRef;
import mousquetaires.languages.syntax.ytree.expressions.operations.YBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.operations.YBinaryOperator;
import mousquetaires.languages.syntax.ytree.expressions.ternary.YTernaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.operations.YUnaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.operations.YUnaryOperator;
import mousquetaires.languages.syntax.ytree.litmus.YPostludeStatement;
import mousquetaires.languages.syntax.ytree.litmus.YPreludeStatement;
import mousquetaires.languages.syntax.ytree.litmus.YProcessStatement;
import mousquetaires.languages.syntax.ytree.statements.*;
import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpStatement;
import mousquetaires.languages.syntax.ytree.types.YFunctionSignature;
import mousquetaires.languages.syntax.ytree.types.YType;


public interface YtreeVisitor<T> {
    T visit(YSyntaxTree node);

    // -- Litmus-litmus elements: ------------------------------------------------------------------------------------

    T visit(YPreludeStatement node);
    T visit(YProcessStatement node);
    T visit(YPostludeStatement node);

    // -- END OF Litmus-litmus elements ------------------------------------------------------------------------------

    T visit(YConstant node);

    // accesses:
    T visit(YIndexerExpression node);
    T visit(YMemberAccessExpression node);
    T visit(YInvocationExpression node);

    T visit(YFunctionDefinition node);

    T visit(YEmptyExpression node);
    T visit(YUnaryExpression node);
    T visit(YUnaryOperator node);

    T visit(YBinaryExpression node);
    T visit(YBinaryOperator node);

    T visit(YTernaryExpression node);

    T visit(YVariableRef node);
    T visit(YLabeledVariableRef node);
    T visit(YType node);
    T visit(YAssignmentExpression node);

    T visit(YBranchingStatement node);
    T visit(YCompoundStatement node);
    T visit(YLinearStatement node);
    T visit(YLoopStatement node);
    T visit(YVariableDeclarationStatement node);
    T visit(YJumpStatement node);

    T visit(YFunctionSignature node);
    T visit(YParameter node);
}
