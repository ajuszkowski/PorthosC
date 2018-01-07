package mousquetaires.languages.cmin.transformer;


import mousquetaires.interpretation.eventrepr.Interpreter;
import mousquetaires.interpretation.internalrepr.exceptions.ParserException;
import mousquetaires.languages.SyntaxTreeToInternalTransformer;
import mousquetaires.languages.cmin.types.CminPrimitiveTypeBuilder;
import mousquetaires.languages.internalrepr.InternalEntity;
import mousquetaires.languages.internalrepr.InternalSyntaxTree;
import mousquetaires.languages.internalrepr.InternalSyntaxTreeBuilder;
import mousquetaires.languages.internalrepr.expressions.InternalAssignmentExpression;
import mousquetaires.languages.internalrepr.expressions.InternalExpression;
import mousquetaires.languages.internalrepr.statements.InternalCompoundStatement;
import mousquetaires.languages.internalrepr.temporaries.InternalVariableDeclarationListTemp;
import mousquetaires.languages.internalrepr.temporaries.InternalVariableDeclarationTemp;
import mousquetaires.languages.internalrepr.types.InternalType;
import mousquetaires.languages.internalrepr.variables.InternalLvalueExpression;
import mousquetaires.languages.parsers.CminBaseVisitor;
import mousquetaires.languages.parsers.CminParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;


public class CminToInternalTransformer
        extends CminBaseVisitor<InternalEntity>
        implements SyntaxTreeToInternalTransformer {

    private final InternalSyntaxTreeBuilder builder = new InternalSyntaxTreeBuilder();

    public InternalSyntaxTree transform(ParserRuleContext parserRuleContext) {
        parserRuleContext.accept(this);
        return builder.build();
    }

    @Override
    public InternalEntity visitMain(CminParser.MainContext ctx) {
        int childrenCount = ctx.getChildCount();
        for (int i = 0; i < childrenCount; i++) {
            InternalEntity root = visit(ctx.getChild(i));
            builder.addRoot(root);
        }
        return null;
    }

    @Override
    public InternalEntity visitPrimaryExpression(CminParser.PrimaryExpressionContext ctx) {
        TerminalNode identifier = ctx.Identifier();
        if (identifier != null) {
            return new CminIdentifier(identifier.getText());
        }

        TerminalNode constant = ctx.Constant();
        if (constant != null) {
            String constantText = constant.getText();
            try {
                int value = Integer.parseInt(constantText);
                return new CminIntegerConstant(value);
            }
            catch (NumberFormatException e) {
            }

            // TODO: Determine other constant types

            throw new ParserException(ctx, "Could not determine type of constant " + constantText);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (TerminalNode stringLiteral : ctx.StringLiteral()) {
            stringBuilder.append(stringLiteral.getSymbol().getText());
        }
        return new CminStringConstant(stringBuilder.toString());
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
        return super.visitEqualityExpression(ctx);
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
    public InternalEntity visitLvalueExpression(CminParser.LvalueExpressionContext ctx) {
        // as-is
        return super.visitLvalueExpression(ctx);
    }

    @Override
    public InternalEntity visitRvalueExpression(CminParser.RvalueExpressionContext ctx) {
        // as-is
        return super.visitRvalueExpression(ctx);
    }

    @Override
    public InternalEntity visitAssignmentExpression(CminParser.AssignmentExpressionContext ctx) {
        InternalExpression leftExpression = (InternalExpression) visitLvalueExpression(ctx.lvalueExpression());
        assert  leftExpression != null;
        if (!(leftExpression instanceof InternalLvalueExpression)) {
            throw new ParserException(ctx, "Invalid l-value expression: `" + leftExpression + "`");
        }
        InternalLvalueExpression leftAssignableExpression = (InternalLvalueExpression) leftExpression;

        InternalExpression rightExpression = (InternalExpression) visitRvalueExpression(ctx.rvalueExpression());

        return new InternalAssignmentExpression(leftAssignableExpression, rightExpression);
    }

    @Override
    public InternalEntity visitAssignmentOperator(CminParser.AssignmentOperatorContext ctx) {
        return super.visitAssignmentOperator(ctx);
    }

    @Override
    public InternalEntity visitAssignmentExpressionList(CminParser.AssignmentExpressionListContext ctx) {
        return super.visitAssignmentExpressionList(ctx);
    }

    @Override
    public InternalEntity visitVariableDeclarationStatement(CminParser.VariableDeclarationStatementContext ctx) {
        InternalType type = (InternalType) visitTypeDeclarator(ctx.typeDeclarator());
        // for now ignore type specifiers ('extern', 'static', etc)
        // todo: set specifier to the 'type'
        //for (CminParser.TypeSpecifierContext typeSpecifierContext : ctx.typeSpecifier()) {
        //    CminKeyword typeSpecifier = (CminKeyword) visitTypeSpecifier(typeSpecifierContext);
        //}

        InternalVariableDeclarationListTemp declaratorList =
                (InternalVariableDeclarationListTemp) visitVariableDeclaratorList(ctx.variableDeclaratorList());
        declaratorList.setType(type);

        InternalCompoundStatement result = declaratorList.toCompoundStatement();
        return result;
    }

    @Override
    public InternalEntity visitTypeSpecifier(CminParser.TypeSpecifierContext ctx) {
        return super.visitTypeSpecifier(ctx);
    }

    @Override
    public InternalEntity visitVariableDeclaratorList(CminParser.VariableDeclaratorListContext ctx) {
        InternalVariableDeclarationListTemp declarationList;

        CminParser.VariableDeclaratorListContext varDeclListCtx = ctx.variableDeclaratorList();
        if (varDeclListCtx != null) {
             declarationList = (InternalVariableDeclarationListTemp) visitVariableDeclaratorList(varDeclListCtx);
        }
        else {
            declarationList = new InternalVariableDeclarationListTemp();
        }

        InternalVariableDeclarationTemp declarator =
                (InternalVariableDeclarationTemp) visitVariableDeclarator(ctx.variableDeclarator());
        declarationList.addDeclarator(declarator);

        return declarationList;
    }

    @Override
    public InternalEntity visitVariableDeclarator(CminParser.VariableDeclaratorContext ctx) {
        CminIdentifier variableName = (CminIdentifier) visitVariableName(ctx.variableName());
        assert variableName != null;

        InternalExpression initializer = null;
        CminParser.VariableInitializerContext initializerContext = ctx.variableInitializer();
        if (initializerContext != null) {
            initializer = (InternalExpression) visitVariableInitializer(initializerContext);
        }
        return new InternalVariableDeclarationTemp(variableName.getValue(), initializer);
    }

    @Override
    public InternalEntity visitStorageClassSpecifier(CminParser.StorageClassSpecifierContext ctx) {
        return super.visitStorageClassSpecifier(ctx);
    }

    @Override
    public InternalEntity visitVariableTypeQualifier(CminParser.VariableTypeQualifierContext ctx) {
        return super.visitVariableTypeQualifier(ctx);
    }

    @Override
    public InternalEntity visitTypeDeclarator(CminParser.TypeDeclaratorContext ctx) {
        InternalEntity primitiveTypeDeclarator = visitPrimitiveTypeDeclarator(ctx.primitiveTypeDeclarator());
        if (primitiveTypeDeclarator != null) {
            return primitiveTypeDeclarator;
        }

        InternalEntity atomicTypeDeclarator = visitAtomicTypeDeclarator(ctx.atomicTypeDeclarator());
        if (atomicTypeDeclarator != null) {
            return atomicTypeDeclarator;
        }

        return super.visitTypeDeclarator(ctx);
    }

    @Override
    public InternalEntity visitPointerTypeDeclarator(CminParser.PointerTypeDeclaratorContext ctx) {
        return super.visitPointerTypeDeclarator(ctx);
    }

    @Override
    public InternalEntity visitPrimitiveTypeDeclarator(CminParser.PrimitiveTypeDeclaratorContext ctx) {
        CminPrimitiveTypeBuilder builder = new CminPrimitiveTypeBuilder();
        CminKeyword primitiveTypeKeyword = (CminKeyword) visitPrimitiveTypeKeyword(ctx.primitiveTypeKeyword());
        builder.addModifier(primitiveTypeKeyword);

        CminKeyword primitiveTypeSpecifierKeyword = (CminKeyword) visitPrimitiveTypeSpecifier(ctx.primitiveTypeSpecifier());
        if (primitiveTypeSpecifierKeyword != null) {
            builder.addModifier(primitiveTypeSpecifierKeyword);
        }

        return builder.build();
    }

    @Override
    public InternalEntity visitPrimitiveTypeKeyword(CminParser.PrimitiveTypeKeywordContext ctx) {
        if (ctx == null) {
            throw new ParserException(ctx, "Cannot find expected primitive type keyword.");
        }
        int childCount = ctx.getChildCount();
        assert childCount > 0 : childCount;
        String keyword = ctx.getChild(0).getText(); // todo: get token bitness correctly, something like: `ctx.getToken(...)`

        switch (keyword) {
            case "short":
                return CminKeyword.Short;
            case "long":
                return CminKeyword.Long;
            case "long long":
                return CminKeyword.LongLong;
            // TODO: process custom types
            //case "int8_t":
            //    this.sizeModifier = CminPrimitiveType.SizeModifier.int8_t;
            //    return true;
            //case "int16_t":
            //    this.sizeModifier = CminPrimitiveType.SizeModifier.int16_t;
            //    return true;
            //case "int32_t":
            //    this.sizeModifier = CminPrimitiveType.SizeModifier.int32_t;
            //    return true;
            //case "int64_t":
            //    this.sizeModifier = CminPrimitiveType.SizeModifier.int64_t;
            //    return true;
            case "int":
                return CminKeyword.Int;
            case "char":
                return CminKeyword.Char;
            case "float":
                return CminKeyword.Float;
            case "double":
                return CminKeyword.Double;
            default:
                throw new ParserException(ctx, "Unexpected primitive type keyword: " + keyword);
        }
    }

    @Override
    public InternalEntity visitPrimitiveTypeSpecifier(CminParser.PrimitiveTypeSpecifierContext ctx) {
        if (ctx == null) {
            return null;
        }
        int childCount = ctx.getChildCount();
        assert childCount > 0 : childCount;
        String keyword = ctx.getChild(0).getText(); // todo: get token value correctly, something like: `ctx.getToken(...)`

        switch (keyword) {
            case "signed":
                return CminKeyword.Signed;
            case "unsigned":
                return CminKeyword.Unsigned;
            default:
                throw new ParserException(ctx, "Unexpected primitive type specifier: " + keyword);
        }
    }

    @Override
    public InternalEntity visitAtomicTypeDeclarator(CminParser.AtomicTypeDeclaratorContext ctx) {
        return super.visitAtomicTypeDeclarator(ctx);
    }

    @Override
    public InternalEntity visitVariableName(CminParser.VariableNameContext ctx) {
        int childCount = ctx.getChildCount();
        assert childCount > 0 : childCount;
        String identifier = ctx.getChild(0).getText(); // todo: get token value correctly, something like: `ctx.getToken(...)`

        return new CminIdentifier(identifier);
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

    @Override
    public InternalEntity visitStatement(CminParser.StatementContext ctx) {
        return super.visitStatement(ctx);
    }

    @Override
    public InternalEntity visitLabeledStatement(CminParser.LabeledStatementContext ctx) {
        return super.visitLabeledStatement(ctx);
    }

    @Override
    public InternalEntity visitCompoundStatement(CminParser.CompoundStatementContext ctx) {
        return super.visitCompoundStatement(ctx);
    }

    @Override
    public InternalEntity visitBlockItemList(CminParser.BlockItemListContext ctx) {
        return super.visitBlockItemList(ctx);
    }

    @Override
    public InternalEntity visitBlockItem(CminParser.BlockItemContext ctx) {
        return super.visitBlockItem(ctx);
    }

    @Override
    public InternalEntity visitExpressionStatement(CminParser.ExpressionStatementContext ctx) {
        return super.visitExpressionStatement(ctx);
    }

    @Override
    public InternalEntity visitExpression(CminParser.ExpressionContext ctx) {
        return super.visitExpression(ctx);
    }

    @Override
    public InternalEntity visitBranchingStatement(CminParser.BranchingStatementContext ctx) {
        return super.visitBranchingStatement(ctx);
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
