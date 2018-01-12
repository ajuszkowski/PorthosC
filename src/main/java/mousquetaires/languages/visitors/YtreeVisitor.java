package mousquetaires.languages.visitors;

import mousquetaires.languages.transformers.cmin.temp.YMultiStatementTemp;
import mousquetaires.languages.transformers.cmin.temp.YVariableInitialiserTemp;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.ytree.expressions.*;
import mousquetaires.languages.ytree.expressions.invocation.YFunctionInvocationExpression;
import mousquetaires.languages.ytree.expressions.invocation.YFunctionArgument;
import mousquetaires.languages.ytree.signatures.YFunctionParameter;
import mousquetaires.languages.ytree.statements.*;
import mousquetaires.languages.ytree.statements.artificial.YBugonStatement;
import mousquetaires.languages.ytree.statements.artificial.YProcess;
import mousquetaires.languages.ytree.types.YPrimitiveTypeName;
import mousquetaires.languages.ytree.types.YPrimitiveTypeSpecifier;
import mousquetaires.languages.ytree.types.YType;


public interface YtreeVisitor {

    void visit(YSyntaxTree node);

    void visit(YProcess node);

    void visit(YBugonStatement node);

    void visit(YConstant node);

    void visit(YMemberAccess node);

    void visit(YFunctionArgument node);

    void visit(YFunctionInvocationExpression node);

    void visit(YFunctionParameter node);

    void visit(YFunctionDefinitionStatement node);

    void visit(YUnaryExpression node);

    void visit(YUnaryExpression.Operator node);

    void visit(YBinaryExpression node);

    void visit(YBinaryExpression.Operator node);

    void visit(YTernaryExpression node);

    void visit(YAssignmentExpression node);

    void visit(YAssignmentExpression.Operator node);

    void visit(YLinearStatement node);

    void visit(YVariableDeclarationStatement node);

    void visit(YType node);

    void visit(YPrimitiveTypeName node);

    void visit(YPrimitiveTypeSpecifier node);

    //void visit(StorageClassSpecifier node);
    //
    //void visit(PrimitiveTypeDeclarator node);
    //
    //void visit(PrimitiveTypeSpecifier node);

    void visit(YVariableRef node);

    void visit(YVariableRef.Kind node);

    void visit(YBlockStatement node);

    void visit(YBranchingStatement node);

    void visit(YLoopStatement node);

    void visit(YJumpStatement node);
}
