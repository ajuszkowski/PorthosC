package mousquetaires.languages.cmin.transformer;


import mousquetaires.languages.ProgramToYtreeTransformer;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.InternalSyntaxTree;
import mousquetaires.languages.ytree.InternalSyntaxTreeBuilder;
import mousquetaires.languages.ytree.expressions.*;
import mousquetaires.languages.ytree.expressions.lvalue.YLvalueExpression;
import mousquetaires.languages.ytree.expressions.lvalue.YVariableRef;
import mousquetaires.languages.ytree.statements.*;
import mousquetaires.languages.ytree.temporaries.InternalSequenceStatementBuilder;
import mousquetaires.languages.ytree.temporaries.InternalMultiVariableDeclarationBuilder;
import mousquetaires.languages.parsers.CminBaseVisitor;
import mousquetaires.languages.parsers.CminParser;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;


public class CminToYtreeTransformer
        extends CminBaseVisitor<YEntity>
        implements ProgramToYtreeTransformer {

    private final InternalSyntaxTreeBuilder builder = new InternalSyntaxTreeBuilder();
    private final CminToYtreeTransformerInterior interior = new CminToYtreeTransformerInterior();

    public InternalSyntaxTree transform(ParserRuleContext parserRuleContext) {
        parserRuleContext.accept(this);
        return builder.build();
    }

    @Override
    public YEntity visitMain(CminParser.MainContext ctx) {
        int childrenCount = ctx.getChildCount();
        for (int i = 0; i < childrenCount; i++) {
            YEntity root = this.visit(ctx.getChild(i));
            builder.addRoot(root);
        }
        return null;
    }

    @Override
    public YEntity visitPrimaryExpression(CminParser.PrimaryExpressionContext ctx) {
        YVariableRef variableRef = interior.visitVariableName(ctx.variableName());
        if (variableRef != null) {
            return variableRef;
        }

        YConstant constant = interior.visitConstant(ctx.constant());
        if (constant != null) {
            return constant;
        }

        throw new IllegalStateException();
    }

    @Override
    public YEntity visitPostfixExpression(CminParser.PostfixExpressionContext ctx) {
        return super.visitPostfixExpression(ctx);
    }

    @Override
    public YEntity visitFunctionArgumentExpressionList(CminParser.FunctionArgumentExpressionListContext ctx) {
        return super.visitFunctionArgumentExpressionList(ctx);
    }

    @Override
    public YEntity visitFunctionArgumentExpression(CminParser.FunctionArgumentExpressionContext ctx) {
        return super.visitFunctionArgumentExpression(ctx);
    }

    @Override
    public YEntity visitUnaryOrNullaryExpression(CminParser.UnaryOrNullaryExpressionContext ctx) {
        return super.visitUnaryOrNullaryExpression(ctx);
    }

    @Override
    public YEntity visitUnaryOperator(CminParser.UnaryOperatorContext ctx) {
        return super.visitUnaryOperator(ctx);
    }

    @Override
    public YEntity visitBinaryOrTernaryExpression(CminParser.BinaryOrTernaryExpressionContext ctx) {
        CminParser.EqualityExpressionContext equalityExpressionCtx = ctx.equalityExpression();
        if (equalityExpressionCtx != null) {
            YExpression expression = (YExpression) visitEqualityExpression(equalityExpressionCtx);
            return expression;
        }
        // todo: others

        return super.visitBinaryOrTernaryExpression(ctx);
    }

    @Override
    public YEntity visitMultiplicativeExpression(CminParser.MultiplicativeExpressionContext ctx) {
        return super.visitMultiplicativeExpression(ctx);
    }

    @Override
    public YEntity visitAdditiveExpression(CminParser.AdditiveExpressionContext ctx) {
        return super.visitAdditiveExpression(ctx);
    }

    @Override
    public YEntity visitShiftExpression(CminParser.ShiftExpressionContext ctx) {
        return super.visitShiftExpression(ctx);
    }

    @Override
    public YEntity visitRelationalExpression(CminParser.RelationalExpressionContext ctx) {
        return super.visitRelationalExpression(ctx);
    }

    @Override
    public YEntity visitEqualityExpression(CminParser.EqualityExpressionContext ctx) {

        CminParser.EqualityExpressionContext leftExpressionCtx = ctx.equalityExpression();
        if (leftExpressionCtx == null) {
            return visitRelationalExpression(ctx.relationalExpression());
        }

        YExpression leftExpression = (YExpression) visitEqualityExpression(leftExpressionCtx);
        YExpression rightExpression = (YExpression) visitRelationalExpression(ctx.relationalExpression());

        YEqualityExpression equalityExpression = new YEqualityExpression(leftExpression, rightExpression);
        if (ctx.Equals() != null) {
            return equalityExpression;
        } else {
            assert ctx.NotEquals() != null;
            return new YNotExpression(equalityExpression);
        }
    }

    @Override
    public YEntity visitAndExpression(CminParser.AndExpressionContext ctx) {
        return super.visitAndExpression(ctx);
    }

    @Override
    public YEntity visitExclusiveOrExpression(CminParser.ExclusiveOrExpressionContext ctx) {
        return super.visitExclusiveOrExpression(ctx);
    }

    @Override
    public YEntity visitInclusiveOrExpression(CminParser.InclusiveOrExpressionContext ctx) {
        return super.visitInclusiveOrExpression(ctx);
    }

    @Override
    public YEntity visitLogicalAndExpression(CminParser.LogicalAndExpressionContext ctx) {
        return super.visitLogicalAndExpression(ctx);
    }

    @Override
    public YEntity visitLogicalOrAndExpression(CminParser.LogicalOrAndExpressionContext ctx) {
        return super.visitLogicalOrAndExpression(ctx);
    }

    @Override
    public YEntity visitTernaryExpression(CminParser.TernaryExpressionContext ctx) {
        return super.visitTernaryExpression(ctx);
    }

    @Override
    public YEntity visitConstantExpression(CminParser.ConstantExpressionContext ctx) {
        return super.visitConstantExpression(ctx);
    }

    @Override
    public YEntity visitAssignmentExpression(CminParser.AssignmentExpressionContext ctx) {
        YLvalueExpression leftExpression = (YLvalueExpression) visitLvalueExpression(ctx.lvalueExpression());
        YExpression rightExpression = (YExpression) visitRvalueExpression(ctx.rvalueExpression());

        return new YAssignmentExpression(leftExpression, rightExpression);
    }

    @Override
    public YEntity visitLvalueExpression(CminParser.LvalueExpressionContext ctx) {

    }

    @Override
    public YEntity visitRvalueExpression(CminParser.RvalueExpressionContext ctx) {
        // as-is
        return super.visitRvalueExpression(ctx);
    }

    @Override
    public YEntity visitAssignmentOperator(CminParser.AssignmentOperatorContext ctx) {
        return super.visitAssignmentOperator(ctx);
    }

    @Override
    public YEntity visitVariableDeclarationStatement(CminParser.VariableDeclarationStatementContext ctx) {
        return interior.visitVariableDeclarationStatement(ctx);
    }

    @Override
    public YEntity visitTypeSpecifier(CminParser.TypeSpecifierContext ctx) {
        return super.visitTypeSpecifier(ctx);
    }

    @Override
    public YEntity visitVariableInitialisationList(CminParser.VariableInitialisationListContext ctx) {
        return visitVariableInitialisationListImpl(ctx);
    }

    public InternalMultiVariableDeclarationBuilder visitVariableInitialisationListImpl(CminParser.VariableInitialisationListContext ctx) {
        InternalMultiVariableDeclarationBuilder declarationBuilder;
        CminParser.VariableInitialisationListContext initialisationListCtx = ctx.variableInitialisationList();
        if (initialisationListCtx != null) {
            declarationBuilder = visitVariableInitialisationListImpl(initialisationListCtx);
        } else {
            declarationBuilder = new InternalMultiVariableDeclarationBuilder();
        }

        InternalVariableDeclarationTemp declarator =
                (InternalVariableDeclarationTemp) visitVariableDeclarator(ctx.variableDeclarator());
        declarationList.addDeclarator(declarator);

        return declarationList;
    }

    @Override
    public YEntity visitVariableDeclarator(CminParser.VariableDeclaratorContext ctx) {
        YVariableRef variable = (YVariableRef) visitVariableName(ctx.variableName());
        assert variable != null;

        YExpression initializer = null;
        CminParser.VariableInitializerContext initializerContext = ctx.variableInitializer();
        if (initializerContext != null) {
            initializer = (YExpression) visitVariableInitializer(initializerContext);
        }
        return new InternalVariableDeclarationTemp(variable.name, initializer);
    }

    @Override
    public YEntity visitStorageClassSpecifier(CminParser.StorageClassSpecifierContext ctx) {
        return super.visitStorageClassSpecifier(ctx);
    }

    @Override
    public YEntity visitTypeDeclarator(CminParser.TypeDeclaratorContext ctx) {
        return interior.visitTypeDeclarator(ctx);
    }

    @Override
    public YEntity visitPrimitiveTypeDeclarator(CminParser.PrimitiveTypeDeclaratorContext ctx) {
        return interior.visitPrimitiveTypeDeclarator(ctx);
    }

    @Override
    public YEntity visitPrimitiveTypeKeyword(CminParser.PrimitiveTypeKeywordContext ctx) {
        return interior.visitPrimitiveTypeKeyword(ctx);
    }

    @Override
    public YEntity visitPrimitiveTypeSpecifier(CminParser.PrimitiveTypeSpecifierContext ctx) {
        return interior.visitPrimitiveTypeSpecifier(ctx);
    }

    @Override
    public YEntity visitVariableTypeSpecifierQualifierList(CminParser.VariableTypeSpecifierQualifierListContext ctx) {
        return super.visitVariableTypeSpecifierQualifierList(ctx);
    }

    @Override
    public YEntity visitVariableInitializer(CminParser.VariableInitializerContext ctx) {
        // as-is
        return super.visitVariableInitializer(ctx);
    }

    //@Override
    //public YEntity visitStatement(CminParser.StatementContext ctx) {
    //    CminParser.VariableDeclarationStatementContext variableDeclarationCtx = ctx.variableDeclarationStatement();
    //    if (variableDeclarationCtx != null) {
    //        return visitVariableDeclarationStatement(variableDeclarationCtx);
    //    }
    //    CminParser.StatementExpressionContext statementExpressionCtx = ctx.statementExpression();
    //    if (statementExpressionCtx != null) {
    //        return visitStatementExpression(statementExpressionCtx);
    //    }
    //    CminParser.LabeledStatementContext labeledStatementCtx = ctx.labeledStatement();
    //    if (labeledStatementCtx != null) {
    //        return visitLabeledStatement(labeledStatementCtx);
    //    }
    //    CminParser.BlockStatementContext blockStatementCtx = ctx.blockStatement();
    //    if (blockStatementCtx != null) {
    //        return visitBlockStatement(blockStatementCtx);
    //    }
    //    CminParser.BranchingStatementContext branchingStatementCtx = ctx.branchingStatement();
    //    if (branchingStatementCtx != null) {
    //        return visitBranchingStatement(branchingStatementCtx);
    //    }
    //    CminParser.LoopStatementContext loopStatementCtx = ctx.loopStatement();
    //    if (loopStatementCtx != null) {
    //        return visitLoopStatement(loopStatementCtx);
    //    }
    //    CminParser.JumpStatementContext jumpStatementCtx = ctx.jumpStatement();
    //    if (jumpStatementCtx != null) {
    //        return visitJumpStatement(jumpStatementCtx);
    //    }
    //
    //    throw new NotImplementedException();
    //}

    @Override
    public YEntity visitStatementExpression(CminParser.StatementExpressionContext ctx) {
        CminParser.ExpressionContext expressionCtx = ctx.expression();
        if (expressionCtx != null) {
            YExpression expression = (YExpression) visitExpression(expressionCtx);
            return new YLinearStatement(expression);
        }
        return null;
    }

    @Override
    public YEntity visitLabeledStatement(CminParser.LabeledStatementContext ctx) {
        return super.visitLabeledStatement(ctx);
    }

    @Override
    public YEntity visitBlockStatement(CminParser.BlockStatementContext ctx) {
        List<CminParser.StatementContext> statementsCtxList = ctx.statement();
        int count = statementsCtxList.size();
        if (count == 0) {
            return new YLinearStatement();
        }
        // optimisation: if single statement
        if (count == 1) {
            CminParser.StatementContext statementCtx = statementsCtxList.get(0);
            return visit(statementCtx);
        }
        InternalSequenceStatementBuilder builder = new InternalSequenceStatementBuilder();
        for (CminParser.StatementContext statementContext : statementsCtxList) {
            YStatement statement = (YStatement) visit(statementContext);
            builder.append(statement);
        }
        YSequenceStatement sequenceStatement = builder.build();

        return sequenceStatement.toBlockStatement();
    }

    @Override
    public YEntity visitBranchingStatement(CminParser.BranchingStatementContext ctx) {
        YExpression condition = (YExpression) visit(ctx.condition());
        YStatement trueBranch = (YStatement) visit(ctx.statement());
        YStatement falseBranch = (YStatement) visit(ctx.falseStatement());
        return new YBranchingStatement(condition, trueBranch, falseBranch);
    }


    @Override
    public YEntity visitLoopStatement(CminParser.LoopStatementContext ctx) {
        return super.visitLoopStatement(ctx);
    }

    @Override
    public YEntity visitForCondition(CminParser.ForConditionContext ctx) {
        return super.visitForCondition(ctx);
    }

    @Override
    public YEntity visitForDeclaration(CminParser.ForDeclarationContext ctx) {
        return super.visitForDeclaration(ctx);
    }

    @Override
    public YEntity visitForExpression(CminParser.ForExpressionContext ctx) {
        return super.visitForExpression(ctx);
    }

    @Override
    public YEntity visitJumpStatement(CminParser.JumpStatementContext ctx) {
        return super.visitJumpStatement(ctx);
    }
}
