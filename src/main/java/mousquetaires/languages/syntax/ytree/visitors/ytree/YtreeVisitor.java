package mousquetaires.languages.syntax.ytree.visitors.ytree;

import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YIndexerExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YInvocationExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YMemberAccessExpression;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YLabeledVariable;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariable;
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
import mousquetaires.languages.syntax.ytree.types.YMethodSignature;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YParameter;


public interface YtreeVisitor<T> {
    T visit(YSyntaxTree node);

    // -- Litmus-specific elements: ------------------------------------------------------------------------------------

    T visit(YPreludeStatement node);
    T visit(YProcessStatement node);
    T visit(YPostludeStatement node);
    T visit(YVariableAssertion node);

    // -- END OF Litmus-specific elements ------------------------------------------------------------------------------

    T visit(YConstant node);

    // accesses:
    T visit(YIndexerExpression node);
    T visit(YMemberAccessExpression node);
    T visit(YInvocationExpression node);

    T visit(YFunctionDefinition node);

    // binary expressions:
    T visit(YRelativeBinaryExpression node);
    T visit(YLogicalBinaryExpression node);
    T visit(YIntegerBinaryExpression node);

    // unary expressions:
    T visit(YIntegerUnaryExpression node);
    T visit(YLogicalUnaryExpression node);
    T visit(YPointerUnaryExpression node); //todo: remove YPointerUnaryExpression, instead use global YVariableRef or array of variables

    T visit(YTernaryExpression node);

    T visit(YVariable node);
    T visit(YLabeledVariable node);
    T visit(YType node);
    T visit(YAssignmentExpression node);

    T visit(YBranchingStatement node);
    T visit(YCompoundStatement node);
    T visit(YLinearStatement node);
    T visit(YWhileLoopStatement node);
    T visit(YVariableDeclarationStatement node);
    T visit(YJumpStatement node);

    T visit(YMethodSignature node);
    T visit(YParameter node);
}
