package mousquetaires.languages.common.visitors;

import mousquetaires.languages.common.transformers.cmin.temp.YTempEntity;
import mousquetaires.languages.common.types.YXType;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.ytree.expressions.*;
import mousquetaires.languages.ytree.expressions.invocation.YFunctionArgument;
import mousquetaires.languages.ytree.expressions.invocation.YFunctionInvocationExpression;
import mousquetaires.languages.ytree.signatures.YFunctionParameter;
import mousquetaires.languages.ytree.statements.*;
import mousquetaires.languages.ytree.statements.artificial.YBugonStatement;
import mousquetaires.languages.ytree.statements.artificial.YProcess;
import mousquetaires.languages.common.types.YXTypeName;
import mousquetaires.languages.common.types.YXTypeSpecifier;


public interface YtreeVisitor<T> {

    T visit(YSyntaxTree node);

    T visit(YProcess node);

    T visit(YBugonStatement node);

    T visit(YConstant node);

    T visit(YMemberAccess node);

    T visit(YFunctionArgument node);

    T visit(YFunctionInvocationExpression node);

    T visit(YFunctionParameter node);

    T visit(YFunctionDefinitionStatement node);

    T visit(YUnaryExpression node);

    T visit(YUnaryExpression.Operator node);

    T visit(YBinaryExpression node);

    T visit(YBinaryExpression.Operator node);

    T visit(YTernaryExpression node);

    T visit(YAssignmentExpression node);

    T visit(YAssignmentExpression.Operator node);

    T visit(YLinearStatement node);

    T visit(YVariableDeclarationStatement node);

    T visit(YXType node);

    T visit(YXTypeName node);

    T visit(YXTypeSpecifier node);

    //T visit(StorageClassSpecifier node);
    //
    //T visit(PrimitiveTypeDeclarator node);
    //
    //T visit(PrimitiveTypeSpecifier node);

    T visit(YVariableRef node);

    T visit(YVariableRef.Kind node);

    T visit(YStatement node);

    T visit(YBlockStatement node);

    T visit(YExpression node);

    T visit(YBranchingStatement node);

    T visit(YLoopStatement node);

    T visit(YJumpStatement node);

    // Temp node:
    T visit(YTempEntity node);
}
