package mousquetaires.languages.visitors;

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
import mousquetaires.types.ZTypeName;
import mousquetaires.types.ZTypeSpecifier;


public interface YtreeVisitor<T> {

    T visit(YSyntaxTree node);

    // -- Litmus-specific elements: ------------------------------------------------------------------------------------

    T visit(YPreludeStatement node);
    T visit(YProcessStatement node);
    T visit(YPostludeStatement node);
    T visit(YVariableAssertion node);

    // -- END OF Litmus-specific elements ------------------------------------------------------------------------------

    // general nodes:
    T visit(YExpression node);
    T visit(YStatement node);
    T visit(YMemoryLocation node);
    T visit(YAssignee node);

    T visit(YConstant node);

    // accesses:
    T visit(YIndexerExpression node);
    T visit(YMemberAccessExpression node);
    T visit(YInvocationExpression node);

    T visit(YFunctionDefinitionStatement node);

    // binary expressions:
    T visit(YRelativeBinaryExpression node);
    T visit(YLogicalBinaryExpression node);
    T visit(YIntegerBinaryExpression node);

    // unary expressions:
    T visit(YLogicalUnaryExpression node);
    T visit(YPointerUnaryExpression node);
    T visit(YIntegerPostfixUnaryExpression node);

    T visit(YTernaryExpression node);

    T visit(YAssignmentExpression node);

    T visit(YSequenceStatement node);

    T visit(YLinearStatement node);

    T visit(YVariableDeclarationStatement node);

    T visit(ZType node);

    T visit(ZTypeName node);

    T visit(ZTypeSpecifier node);

    T visit(YVariableRef node);

    T visit(YBranchingStatement node);

    T visit(YLoopStatement node);

    T visit(YJumpStatement node);

    // Temp node:
    //T visit(YTempEntity node);
}
