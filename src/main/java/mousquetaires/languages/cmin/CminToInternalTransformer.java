package mousquetaires.languages.cmin;


import mousquetaires.execution.Programme;
import mousquetaires.execution.ProgrammeBuilder;
import mousquetaires.interpretation.Interpreter;
import mousquetaires.interpretation.exceptions.ParserException;
import mousquetaires.languages.SyntaxTreeToInternalTransformer;
import mousquetaires.languages.cmin.types.CminPrimitiveTypeBuilder;
import mousquetaires.languages.internal.AbstractEntity;
import mousquetaires.languages.parsers.CminBaseVisitor;
import mousquetaires.languages.parsers.CminParser;
import org.antlr.v4.runtime.ParserRuleContext;


public class CminToInternalTransformer
        extends CminBaseVisitor<AbstractEntity>
        implements SyntaxTreeToInternalTransformer {

    private final Interpreter interpreter;
    private final ProgrammeBuilder builder = new ProgrammeBuilder();

    public CminToInternalTransformer(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public Programme transform(ParserRuleContext parserRuleContext) {
        parserRuleContext.accept(this);
        return builder.build();
    }

    @Override
    public AbstractEntity visitMain(CminParser.MainContext ctx) {
        int childrenCount = ctx.getChildCount();
        for (int i = 0; i < childrenCount; i++) {
            visit(ctx.getChild(i));
        }
        return null;
    }

    @Override
    public AbstractEntity visitPrimaryExpression(CminParser.PrimaryExpressionContext ctx) {
        /*
        CminParser.AssignmentExpressionListContext assignmentExpressionList = ctx.assignmentExpressionList();
        if (assignmentExpressionList != null) {
            return visitAssignmentExpressionList(assignmentExpressionList);
        }

        TerminalNode identifier = ctx.Identifier();
        if (identifier != null) {
            String name = identifier.getText();
            MemoryLocation memoryLocation = interpreter.tryGetMemoryLocation(name);
            if (memoryLocation == null) {
                throw new MemoryLocationNotFoundException(name);
            }
            return memoryLocation;
        }
        */
        return super.visitPrimaryExpression(ctx);
    }

    @Override
    public AbstractEntity visitPostfixExpression(CminParser.PostfixExpressionContext ctx) {
        /*
        CminParser.PrimaryExpressionContext primaryExpressionContext = ctx.primaryExpression();
        if (primaryExpressionContext != null) {
            return visitPrimaryExpression(primaryExpressionContext);
        }


        CminParser.PostfixExpressionContext postfixExpressionContext = ctx.postfixExpression();
        if (postfixExpressionContext != null) {
            return visitPostfixExpression(postfixExpressionContext);
        }
         */

        return super.visitPostfixExpression(ctx);
    }

    @Override
    public AbstractEntity visitFunctionArgumentExpressionList(CminParser.FunctionArgumentExpressionListContext ctx) {
        return super.visitFunctionArgumentExpressionList(ctx);
    }

    @Override
    public AbstractEntity visitFunctionArgumentExpression(CminParser.FunctionArgumentExpressionContext ctx) {
        return super.visitFunctionArgumentExpression(ctx);
    }

    @Override
    public AbstractEntity visitUnaryOrNullaryExpression(CminParser.UnaryOrNullaryExpressionContext ctx) {
        return super.visitUnaryOrNullaryExpression(ctx);
    }

    @Override
    public AbstractEntity visitUnaryOperator(CminParser.UnaryOperatorContext ctx) {
        return super.visitUnaryOperator(ctx);
    }

    @Override
    public AbstractEntity visitCastExpression(CminParser.CastExpressionContext ctx) {
        return super.visitCastExpression(ctx);
    }

    @Override
    public AbstractEntity visitBinaryOrTernaryExpression(CminParser.BinaryOrTernaryExpressionContext ctx) {
        return super.visitBinaryOrTernaryExpression(ctx);
    }

    @Override
    public AbstractEntity visitMultiplicativeExpression(CminParser.MultiplicativeExpressionContext ctx) {
        return super.visitMultiplicativeExpression(ctx);
    }

    @Override
    public AbstractEntity visitAdditiveExpression(CminParser.AdditiveExpressionContext ctx) {
        return super.visitAdditiveExpression(ctx);
    }

    @Override
    public AbstractEntity visitShiftExpression(CminParser.ShiftExpressionContext ctx) {
        return super.visitShiftExpression(ctx);
    }

    @Override
    public AbstractEntity visitRelationalExpression(CminParser.RelationalExpressionContext ctx) {
        return super.visitRelationalExpression(ctx);
    }

    @Override
    public AbstractEntity visitEqualityExpression(CminParser.EqualityExpressionContext ctx) {
        return super.visitEqualityExpression(ctx);
    }

    @Override
    public AbstractEntity visitAndExpression(CminParser.AndExpressionContext ctx) {
        return super.visitAndExpression(ctx);
    }

    @Override
    public AbstractEntity visitExclusiveOrExpression(CminParser.ExclusiveOrExpressionContext ctx) {
        return super.visitExclusiveOrExpression(ctx);
    }

    @Override
    public AbstractEntity visitInclusiveOrExpression(CminParser.InclusiveOrExpressionContext ctx) {
        return super.visitInclusiveOrExpression(ctx);
    }

    @Override
    public AbstractEntity visitLogicalAndExpression(CminParser.LogicalAndExpressionContext ctx) {
        return super.visitLogicalAndExpression(ctx);
    }

    @Override
    public AbstractEntity visitLogicalOrAndExpression(CminParser.LogicalOrAndExpressionContext ctx) {
        return super.visitLogicalOrAndExpression(ctx);
    }

    @Override
    public AbstractEntity visitTernaryExpression(CminParser.TernaryExpressionContext ctx) {
        return super.visitTernaryExpression(ctx);
    }

    @Override
    public AbstractEntity visitConstantExpression(CminParser.ConstantExpressionContext ctx) {
        return super.visitConstantExpression(ctx);
    }

    @Override
    public AbstractEntity visitAssignmentExpression(CminParser.AssignmentExpressionContext ctx) {
        return super.visitAssignmentExpression(ctx);
    }

    @Override
    public AbstractEntity visitAssignmentOperator(CminParser.AssignmentOperatorContext ctx) {
        return super.visitAssignmentOperator(ctx);
    }

    @Override
    public AbstractEntity visitAssignmentExpressionList(CminParser.AssignmentExpressionListContext ctx) {
        return super.visitAssignmentExpressionList(ctx);
    }

    @Override
    public AbstractEntity visitVariableDeclarationStatement(CminParser.VariableDeclarationStatementContext ctx) {
        /*CminParser.DeclarationSpecifiersContext declarationSpecifiersContext = ctx.declarationSpecifiers();
        assert declarationSpecifiersContext != null;
        Type type = (Type) visitDeclarationSpecifiers(declarationSpecifiersContext);

        return super.visitDeclaration(ctx);
        */
        return super.visitVariableDeclarationStatement(ctx);
    }

    @Override
    public AbstractEntity visitTypeSpecifier(CminParser.TypeSpecifierContext ctx) {
        return super.visitTypeSpecifier(ctx);
    }

    @Override
    public AbstractEntity visitVariableDeclaratorList(CminParser.VariableDeclaratorListContext ctx) {
        return super.visitVariableDeclaratorList(ctx);
    }

    @Override
    public AbstractEntity visitVariableDeclarator(CminParser.VariableDeclaratorContext ctx) {
        return super.visitVariableDeclarator(ctx);
    }

    @Override
    public AbstractEntity visitStorageClassSpecifier(CminParser.StorageClassSpecifierContext ctx) {
        return super.visitStorageClassSpecifier(ctx);
    }

    @Override
    public AbstractEntity visitVariableTypeQualifier(CminParser.VariableTypeQualifierContext ctx) {
        return super.visitVariableTypeQualifier(ctx);
    }

    @Override
    public AbstractEntity visitTypeDeclaration(CminParser.TypeDeclarationContext ctx) {
        return super.visitTypeDeclaration(ctx);
    }

    @Override
    public AbstractEntity visitCustomTypeName(CminParser.CustomTypeNameContext ctx) {
        return super.visitCustomTypeName(ctx);
    }

    @Override
    public AbstractEntity visitTypeDeclarator(CminParser.TypeDeclaratorContext ctx) {
        return super.visitTypeDeclarator(ctx);
    }

    @Override
    public AbstractEntity visitPointerTypeDeclarator(CminParser.PointerTypeDeclaratorContext ctx) {
        return super.visitPointerTypeDeclarator(ctx);
    }

    @Override
    public AbstractEntity visitPrimitiveTypeDeclarator(CminParser.PrimitiveTypeDeclaratorContext ctx) {
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
    public AbstractEntity visitPrimitiveTypeKeyword(CminParser.PrimitiveTypeKeywordContext ctx) {
        if (ctx == null) {
            throw new ParserException(ctx, "Cannot find expected primitive type keyword.");
        }
        int childCount = ctx.getChildCount();
        assert childCount > 0 : childCount;
        String keyword = ctx.getChild(0).getText(); // todo: get token value correctly, something like: `ctx.getToken(...)`

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
    public AbstractEntity visitPrimitiveTypeSpecifier(CminParser.PrimitiveTypeSpecifierContext ctx) {
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
    public AbstractEntity visitAtomicTypeDeclarator(CminParser.AtomicTypeDeclaratorContext ctx) {


        return super.visitAtomicTypeDeclarator(ctx);
    }

    @Override
    public AbstractEntity visitVariableStructOrUnionTypeDeclarator(CminParser.VariableStructOrUnionTypeDeclaratorContext ctx) {
        return super.visitVariableStructOrUnionTypeDeclarator(ctx);
    }

    @Override
    public AbstractEntity visitStructOrUnionDeclaration(CminParser.StructOrUnionDeclarationContext ctx) {
        return super.visitStructOrUnionDeclaration(ctx);
    }

    @Override
    public AbstractEntity visitStructOrUnion(CminParser.StructOrUnionContext ctx) {
        return super.visitStructOrUnion(ctx);
    }

    @Override
    public AbstractEntity visitStructOrUnionName(CminParser.StructOrUnionNameContext ctx) {
        return super.visitStructOrUnionName(ctx);
    }

    @Override
    public AbstractEntity visitStructElementDeclarationList(CminParser.StructElementDeclarationListContext ctx) {
        return super.visitStructElementDeclarationList(ctx);
    }

    @Override
    public AbstractEntity visitStructElementDeclaration(CminParser.StructElementDeclarationContext ctx) {
        return super.visitStructElementDeclaration(ctx);
    }

    @Override
    public AbstractEntity visitStructElementDeclaratorList(CminParser.StructElementDeclaratorListContext ctx) {
        return super.visitStructElementDeclaratorList(ctx);
    }

    @Override
    public AbstractEntity visitStructElementDeclarator(CminParser.StructElementDeclaratorContext ctx) {
        return super.visitStructElementDeclarator(ctx);
    }

    @Override
    public AbstractEntity visitVariableEnumTypeDeclarator(CminParser.VariableEnumTypeDeclaratorContext ctx) {
        return super.visitVariableEnumTypeDeclarator(ctx);
    }

    @Override
    public AbstractEntity visitCustomTypeNameDeclarator(CminParser.CustomTypeNameDeclaratorContext ctx) {
        return super.visitCustomTypeNameDeclarator(ctx);
    }

    @Override
    public AbstractEntity visitEnumDeclarator(CminParser.EnumDeclaratorContext ctx) {
        return super.visitEnumDeclarator(ctx);
    }

    @Override
    public AbstractEntity visitEnumeratorList(CminParser.EnumeratorListContext ctx) {
        return super.visitEnumeratorList(ctx);
    }

    @Override
    public AbstractEntity visitEnumerator(CminParser.EnumeratorContext ctx) {
        return super.visitEnumerator(ctx);
    }

    @Override
    public AbstractEntity visitEnumerationConstant(CminParser.EnumerationConstantContext ctx) {
        return super.visitEnumerationConstant(ctx);
    }

    @Override
    public AbstractEntity visitFunctionSpecifier(CminParser.FunctionSpecifierContext ctx) {
        return super.visitFunctionSpecifier(ctx);
    }

    @Override
    public AbstractEntity visitVariableName(CminParser.VariableNameContext ctx) {
        return super.visitVariableName(ctx);
    }

    @Override
    public AbstractEntity visitNestedParenthesesBlock(CminParser.NestedParenthesesBlockContext ctx) {
        return super.visitNestedParenthesesBlock(ctx);
    }

    @Override
    public AbstractEntity visitParameterFullList(CminParser.ParameterFullListContext ctx) {
        return super.visitParameterFullList(ctx);
    }

    @Override
    public AbstractEntity visitParameterList(CminParser.ParameterListContext ctx) {
        return super.visitParameterList(ctx);
    }

    @Override
    public AbstractEntity visitParameterDeclaration(CminParser.ParameterDeclarationContext ctx) {
        return super.visitParameterDeclaration(ctx);
    }

    @Override
    public AbstractEntity visitTypeSpecifierQualifierList(CminParser.TypeSpecifierQualifierListContext ctx) {
        return super.visitTypeSpecifierQualifierList(ctx);
    }

    @Override
    public AbstractEntity visitVariableInitializer(CminParser.VariableInitializerContext ctx) {
        return super.visitVariableInitializer(ctx);
    }

    @Override
    public AbstractEntity visitInitializerList(CminParser.InitializerListContext ctx) {
        return super.visitInitializerList(ctx);
    }

    @Override
    public AbstractEntity visitStatement(CminParser.StatementContext ctx) {
        return super.visitStatement(ctx);
    }

    @Override
    public AbstractEntity visitLabeledStatement(CminParser.LabeledStatementContext ctx) {
        return super.visitLabeledStatement(ctx);
    }

    @Override
    public AbstractEntity visitCompoundStatement(CminParser.CompoundStatementContext ctx) {
        return super.visitCompoundStatement(ctx);
    }

    @Override
    public AbstractEntity visitBlockItemList(CminParser.BlockItemListContext ctx) {
        return super.visitBlockItemList(ctx);
    }

    @Override
    public AbstractEntity visitBlockItem(CminParser.BlockItemContext ctx) {
        return super.visitBlockItem(ctx);
    }

    @Override
    public AbstractEntity visitExpressionStatement(CminParser.ExpressionStatementContext ctx) {
        return super.visitExpressionStatement(ctx);
    }

    @Override
    public AbstractEntity visitExpression(CminParser.ExpressionContext ctx) {
        return super.visitExpression(ctx);
    }

    @Override
    public AbstractEntity visitBranchingStatement(CminParser.BranchingStatementContext ctx) {
        return super.visitBranchingStatement(ctx);
    }

    @Override
    public AbstractEntity visitLoopStatement(CminParser.LoopStatementContext ctx) {
        return super.visitLoopStatement(ctx);
    }

    @Override
    public AbstractEntity visitForCondition(CminParser.ForConditionContext ctx) {
        return super.visitForCondition(ctx);
    }

    @Override
    public AbstractEntity visitForDeclaration(CminParser.ForDeclarationContext ctx) {
        return super.visitForDeclaration(ctx);
    }

    @Override
    public AbstractEntity visitForExpression(CminParser.ForExpressionContext ctx) {
        return super.visitForExpression(ctx);
    }

    @Override
    public AbstractEntity visitJumpStatement(CminParser.JumpStatementContext ctx) {
        return super.visitJumpStatement(ctx);
    }

    @Override
    public AbstractEntity visitFunctionDefinition(CminParser.FunctionDefinitionContext ctx) {
        return super.visitFunctionDefinition(ctx);
    }

    @Override
    public AbstractEntity visitFunctionSpecifiers(CminParser.FunctionSpecifiersContext ctx) {
        return super.visitFunctionSpecifiers(ctx);
    }
}
