package mousquetaires.languages.cmin.transformer;


import mousquetaires.interpretation.internalrepr.exceptions.ParserException;
import mousquetaires.languages.SyntaxTreeToInternalTransformer;
import mousquetaires.languages.internalrepr.InternalEntity;
import mousquetaires.languages.internalrepr.InternalSyntaxTree;
import mousquetaires.languages.internalrepr.InternalSyntaxTreeBuilder;
import mousquetaires.languages.internalrepr.expressions.*;
import mousquetaires.languages.internalrepr.expressions.lvalue.InternalLvalueExpression;
import mousquetaires.languages.internalrepr.expressions.lvalue.InternalVariableRef;
import mousquetaires.languages.internalrepr.statements.*;
import mousquetaires.languages.internalrepr.temporaries.InternalSequenceStatementBuilder;
import mousquetaires.languages.internalrepr.temporaries.InternalMultiVariableDeclarationBuilder;
import mousquetaires.languages.parsers.CminBaseVisitor;
import mousquetaires.languages.parsers.CminParser;
import mousquetaires.utils.exceptions.NotImplementedException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;


public class CminToInternalTransformer
        extends CminBaseVisitor<InternalEntity>
        implements SyntaxTreeToInternalTransformer {

    private final InternalSyntaxTreeBuilder builder = new InternalSyntaxTreeBuilder();
    private final CminToInternalTransformerInterior interior = new CminToInternalTransformerInterior();

    public InternalSyntaxTree transform(ParserRuleContext parserRuleContext) {
        parserRuleContext.accept(this);
        return builder.build();
    }

    @Override
    public InternalEntity visitMain(CminParser.MainContext ctx) {
        int childrenCount = ctx.getChildCount();
        for (int i = 0; i < childrenCount; i++) {
            InternalEntity root = this.visit(ctx.getChild(i));
            builder.addRoot(root);
        }
        return null;
    }

    @Override
    public InternalEntity visitPrimaryExpression(CminParser.PrimaryExpressionContext ctx) {
        InternalVariableRef variableRef = interior.visitVariableName(ctx.variableName());
        if (variableRef != null) {
            return variableRef;
        }

        InternalConstant constant = interior.visitConstant(ctx.constant());
        if (constant != null) {
            return constant;
        }

        throw new IllegalStateException();
    }

    @Override
    public InternalEntity visitPostfixExpression(CminParser.PostfixExpressionContext ctx) {
        return super.visitPostfixExpression(ctx);
    }

    @Override
    public InternalEntity visitFunctionArgumentExpressionList(CminParser.FunctionArgumentExpressionListContext ctx) {
        return super.visitFunctionArgumentExpressionList(ctx);
    }

    @Override
    public InternalEntity visitFunctionArgumentExpression(CminParser.FunctionArgumentExpressionContext ctx) {
        return super.visitFunctionArgumentExpression(ctx);
    }

    @Override
    public InternalEntity visitUnaryOrNullaryExpression(CminParser.UnaryOrNullaryExpressionContext ctx) {
        return super.visitUnaryOrNullaryExpression(ctx);
    }

    @Override
    public InternalEntity visitUnaryOperator(CminParser.UnaryOperatorContext ctx) {
        return super.visitUnaryOperator(ctx);
    }

    @Override
    public InternalEntity visitBinaryOrTernaryExpression(CminParser.BinaryOrTernaryExpressionContext ctx) {
        CminParser.EqualityExpressionContext equalityExpressionCtx = ctx.equalityExpression();
        if (equalityExpressionCtx != null) {
            InternalExpression expression = (InternalExpression) visitEqualityExpression(equalityExpressionCtx);
            return expression;
        }
        // todo: others

        return super.visitBinaryOrTernaryExpression(ctx);
    }

    @Override
    public InternalEntity visitMultiplicativeExpression(CminParser.MultiplicativeExpressionContext ctx) {
        return super.visitMultiplicativeExpression(ctx);
    }

    @Override
    public InternalEntity visitAdditiveExpression(CminParser.AdditiveExpressionContext ctx) {
        return super.visitAdditiveExpression(ctx);
    }

    @Override
    public InternalEntity visitShiftExpression(CminParser.ShiftExpressionContext ctx) {
        return super.visitShiftExpression(ctx);
    }

    @Override
    public InternalEntity visitRelationalExpression(CminParser.RelationalExpressionContext ctx) {
        return super.visitRelationalExpression(ctx);
    }

    @Override
    public InternalEntity visitEqualityExpression(CminParser.EqualityExpressionContext ctx) {

        CminParser.EqualityExpressionContext leftExpressionCtx = ctx.equalityExpression();
        if (leftExpressionCtx == null) {
            return visitRelationalExpression(ctx.relationalExpression());
        }

        InternalExpression leftExpression = (InternalExpression) visitEqualityExpression(leftExpressionCtx);
        InternalExpression rightExpression = (InternalExpression) visitRelationalExpression(ctx.relationalExpression());

        InternalEqualityExpression equalityExpression = new InternalEqualityExpression(leftExpression, rightExpression);
        if (ctx.Equals() != null) {
            return equalityExpression;
        } else {
            assert ctx.NotEquals() != null;
            return new InternalNotExpression(equalityExpression);
        }
    }

    @Override
    public InternalEntity visitAndExpression(CminParser.AndExpressionContext ctx) {
        return super.visitAndExpression(ctx);
    }

    @Override
    public InternalEntity visitExclusiveOrExpression(CminParser.ExclusiveOrExpressionContext ctx) {
        return super.visitExclusiveOrExpression(ctx);
    }

    @Override
    public InternalEntity visitInclusiveOrExpression(CminParser.InclusiveOrExpressionContext ctx) {
        return super.visitInclusiveOrExpression(ctx);
    }

    @Override
    public InternalEntity visitLogicalAndExpression(CminParser.LogicalAndExpressionContext ctx) {
        return super.visitLogicalAndExpression(ctx);
    }

    @Override
    public InternalEntity visitLogicalOrAndExpression(CminParser.LogicalOrAndExpressionContext ctx) {
        return super.visitLogicalOrAndExpression(ctx);
    }

    @Override
    public InternalEntity visitTernaryExpression(CminParser.TernaryExpressionContext ctx) {
        return super.visitTernaryExpression(ctx);
    }

    @Override
    public InternalEntity visitConstantExpression(CminParser.ConstantExpressionContext ctx) {
        return super.visitConstantExpression(ctx);
    }

    @Override
    public InternalEntity visitAssignmentExpression(CminParser.AssignmentExpressionContext ctx) {
        InternalLvalueExpression leftExpression = (InternalLvalueExpression) visitLvalueExpression(ctx.lvalueExpression());
        InternalExpression rightExpression = (InternalExpression) visitRvalueExpression(ctx.rvalueExpression());

        return new InternalAssignmentExpression(leftExpression, rightExpression);
    }

    @Override
    public InternalEntity visitLvalueExpression(CminParser.LvalueExpressionContext ctx) {

    }

    @Override
    public InternalEntity visitRvalueExpression(CminParser.RvalueExpressionContext ctx) {
        // as-is
        return super.visitRvalueExpression(ctx);
    }

    @Override
    public InternalEntity visitAssignmentOperator(CminParser.AssignmentOperatorContext ctx) {
        return super.visitAssignmentOperator(ctx);
    }

    @Override
    public InternalEntity visitVariableDeclarationStatement(CminParser.VariableDeclarationStatementContext ctx) {
        return interior.visitVariableDeclarationStatement(ctx);
    }

    @Override
    public InternalEntity visitTypeSpecifier(CminParser.TypeSpecifierContext ctx) {
        return super.visitTypeSpecifier(ctx);
    }

    @Override
    public InternalEntity visitVariableInitialisationList(CminParser.VariableInitialisationListContext ctx) {
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
    public InternalEntity visitVariableDeclarator(CminParser.VariableDeclaratorContext ctx) {
        InternalVariableRef variable = (InternalVariableRef) visitVariableName(ctx.variableName());
        assert variable != null;

        InternalExpression initializer = null;
        CminParser.VariableInitializerContext initializerContext = ctx.variableInitializer();
        if (initializerContext != null) {
            initializer = (InternalExpression) visitVariableInitializer(initializerContext);
        }
        return new InternalVariableDeclarationTemp(variable.name, initializer);
    }

    @Override
    public InternalEntity visitStorageClassSpecifier(CminParser.StorageClassSpecifierContext ctx) {
        return super.visitStorageClassSpecifier(ctx);
    }

    @Override
    public InternalEntity visitTypeDeclarator(CminParser.TypeDeclaratorContext ctx) {
        return interior.visitTypeDeclarator(ctx);
    }

    @Override
    public InternalEntity visitPrimitiveTypeDeclarator(CminParser.PrimitiveTypeDeclaratorContext ctx) {
        return interior.visitPrimitiveTypeDeclarator(ctx);
    }

    @Override
    public InternalEntity visitPrimitiveTypeKeyword(CminParser.PrimitiveTypeKeywordContext ctx) {
        return interior.visitPrimitiveTypeKeyword(ctx);
    }

    @Override
    public InternalEntity visitPrimitiveTypeSpecifier(CminParser.PrimitiveTypeSpecifierContext ctx) {
        return interior.visitPrimitiveTypeSpecifier(ctx);
    }

    @Override
    public InternalEntity visitVariableTypeSpecifierQualifierList(CminParser.VariableTypeSpecifierQualifierListContext ctx) {
        return super.visitVariableTypeSpecifierQualifierList(ctx);
    }

    @Override
    public InternalEntity visitVariableInitializer(CminParser.VariableInitializerContext ctx) {
        // as-is
        return super.visitVariableInitializer(ctx);
    }

    //@Override
    //public InternalEntity visitStatement(CminParser.StatementContext ctx) {
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
    public InternalEntity visitStatementExpression(CminParser.StatementExpressionContext ctx) {
        CminParser.ExpressionContext expressionCtx = ctx.expression();
        if (expressionCtx != null) {
            InternalExpression expression = (InternalExpression) visitExpression(expressionCtx);
            return new InternalLinearStatement(expression);
        }
        return null;
    }

    @Override
    public InternalEntity visitLabeledStatement(CminParser.LabeledStatementContext ctx) {
        return super.visitLabeledStatement(ctx);
    }

    @Override
    public InternalEntity visitBlockStatement(CminParser.BlockStatementContext ctx) {
        List<CminParser.StatementContext> statementsCtxList = ctx.statement();
        int count = statementsCtxList.size();
        if (count == 0) {
            return new InternalLinearStatement();
        }
        // optimisation: if single statement
        if (count == 1) {
            CminParser.StatementContext statementCtx = statementsCtxList.get(0);
            return visit(statementCtx);
        }
        InternalSequenceStatementBuilder builder = new InternalSequenceStatementBuilder();
        for (CminParser.StatementContext statementContext : statementsCtxList) {
            InternalStatement statement = (InternalStatement) visit(statementContext);
            builder.append(statement);
        }
        InternalSequenceStatement sequenceStatement = builder.build();

        return sequenceStatement.toBlockStatement();
    }

    @Override
    public InternalEntity visitBranchingStatement(CminParser.BranchingStatementContext ctx) {
        InternalExpression condition = (InternalExpression) visit(ctx.condition());
        InternalStatement trueBranch = (InternalStatement) visit(ctx.statement());
        InternalStatement falseBranch = (InternalStatement) visit(ctx.falseStatement());
        return new InternalBranchingStatement(condition, trueBranch, falseBranch);
    }


    @Override
    public InternalEntity visitLoopStatement(CminParser.LoopStatementContext ctx) {
        return super.visitLoopStatement(ctx);
    }

    @Override
    public InternalEntity visitForCondition(CminParser.ForConditionContext ctx) {
        return super.visitForCondition(ctx);
    }

    @Override
    public InternalEntity visitForDeclaration(CminParser.ForDeclarationContext ctx) {
        return super.visitForDeclaration(ctx);
    }

    @Override
    public InternalEntity visitForExpression(CminParser.ForExpressionContext ctx) {
        return super.visitForExpression(ctx);
    }

    @Override
    public InternalEntity visitJumpStatement(CminParser.JumpStatementContext ctx) {
        return super.visitJumpStatement(ctx);
    }
}
