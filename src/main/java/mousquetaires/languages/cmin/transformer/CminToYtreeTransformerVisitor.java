package mousquetaires.languages.cmin.transformer;

import mousquetaires.interpretation.internalrepr.exceptions.ParserException;
import mousquetaires.languages.cmin.transformer.temporaries.YSequenceStatementBuilder;
import mousquetaires.languages.cmin.transformer.temporaries.YVariableInitialiser;
import mousquetaires.languages.cmin.transformer.temporaries.YVariableInitialiserList;
import mousquetaires.languages.cmin.transformer.tokens.CminKeyword;
import mousquetaires.languages.parsers.CminParser;
import mousquetaires.languages.parsers.CminVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.ytree.YSyntaxTreeBuilder;
import mousquetaires.languages.ytree.expressions.*;
import mousquetaires.languages.ytree.statements.*;
import mousquetaires.languages.ytree.types.YType;
import mousquetaires.utils.exceptions.ArgumentNullException;
import mousquetaires.utils.exceptions.NotImplementedException;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;


class CminToYtreeTransformerVisitor
        extends AbstractParseTreeVisitor<YEntity>
        implements CminVisitor<YEntity> {
    //extends CminBaseVisitor<YEntity> {

    private final YSyntaxTreeBuilder syntaxTreeBuilder = new YSyntaxTreeBuilder();


    /**
     * main
     * :   statement+
     * ;
     */
    public YSyntaxTree visitMain(CminParser.MainContext ctx) {
        int childrenCount = ctx.getChildCount();
        for (int i = 0; i < childrenCount; i++) {
            YEntity root = this.visit(ctx.getChild(i));
            syntaxTreeBuilder.addRoot(root);
        }
        return syntaxTreeBuilder.build();
    }


    // may receive null argument
    // may return null result

    /**
     * primaryExpression
     * :   variableName
     * |   constant
     * ;
     */
    public YExpression visitPrimaryExpression(CminParser.PrimaryExpressionContext ctx) {
        assert ctx != null;
        YVariableRef variableRef = visitVariableName(ctx.variableName());
        if (variableRef != null) {
            return variableRef;
        }
        YConstant constant = visitConstant(ctx.constant());
        if (constant != null) {
            return constant;
        }
        throw new IllegalStateException();
    }

    /**
     * variableName
     * :   Identifier
     * ;
     */
    public YVariableRef visitVariableName(CminParser.VariableNameContext ctx) {
        if (ctx == null) {
            return null;
        }
        String name = ctx.Identifier().getText(); // todo: get token name correctly
        return new YVariableRef(name);
    }

    /**
     * constant
     * :   Constant
     * |   StringLiteral
     * ;
     */
    public YConstant visitConstant(CminParser.ConstantContext ctx) {
        if (ctx == null) {
            return null;
        }
        TerminalNode constant = ctx.Constant();
        if (constant != null) {
            String constantText = constant.getText();  // todo: get token symbol text properly here
            YConstant parsedConstant = YConstant.tryParse(constantText);
            if (parsedConstant == null) {
                throw new ParserException(ctx, "Could not determine type of constant " + constantText);
            }
            return parsedConstant;
        }
        TerminalNode stringLiteral = ctx.StringLiteral();
        if (stringLiteral != null) {
            String constantText = stringLiteral.getText();  // todo: get token symbol text properly here
            YConstant parsedConstant = YConstant.tryParse(constantText);
            if (parsedConstant == null) {
                throw new ParserException(ctx, "Could not parse string constant " + constantText);
            }
            return parsedConstant;
        }
        return null;
    }

    /**
     * postfixExpression
     * :   primaryExpression
     * |   postfixExpression '(' functionArgumentExpressionList? ')'
     * |   postfixExpression (PlusPlus | MinusMinus)
     * ;
     */
    public YExpression visitPostfixExpression(CminParser.PostfixExpressionContext ctx) {
        CminParser.PrimaryExpressionContext primaryExpressionCtx = ctx.primaryExpression();
        if (primaryExpressionCtx != null) {
            return visitPrimaryExpression(primaryExpressionCtx);
        }
        throw new IllegalStateException();
    }

    /**
     * functionArgumentExpressionList
     * :   functionArgumentExpression
     * |   functionArgumentExpressionList ',' functionArgumentExpression
     * ;
     */
    public YEntity visitFunctionArgumentExpressionList(CminParser.FunctionArgumentExpressionListContext ctx) {
        throw new IllegalStateException();
    }

    /**
     * functionArgumentExpression
     * :   unaryOrNullaryExpression
     * ;
     */
    public YExpression visitFunctionArgumentExpression(CminParser.FunctionArgumentExpressionContext ctx) {
        return visitUnaryOrNullaryExpression(ctx.unaryOrNullaryExpression());
    }

    /**
     * unaryOrNullaryExpression
     * :   postfixExpression
     * |   (And|Asterisk|Plus|Minus|Tilde|Not|PlusPlus|MinusMinus) unaryOrNullaryExpression
     * ;
     */
    public YExpression visitUnaryOrNullaryExpression(CminParser.UnaryOrNullaryExpressionContext ctx) {
        CminParser.PostfixExpressionContext postfixExpressionCtx = ctx.postfixExpression();
        if (postfixExpressionCtx != null) {
            return visitPostfixExpression(postfixExpressionCtx);
        }
        YExpression expression = visitUnaryOrNullaryExpression(ctx.unaryOrNullaryExpression());
        YUnaryExpression.Operator operator = visitUnaryOperator(ctx.unaryOperator());

        return new YUnaryExpression(expression, operator);
    }

    /* unaryOperator
     *     :   (And|Asterisk|Plus|Minus|Tilde|Not|PlusPlus|MinusMinus)
     *     ;
     */
    public YUnaryExpression.Operator visitUnaryOperator(CminParser.UnaryOperatorContext ctx) {
        if (ctx.And() != null ||
                ctx.Asterisk() != null ||
                ctx.Plus() != null ||
                ctx.Minus() != null ||
                ctx.Tilde() != null) {
            // todo; support these cases
            throw new NotImplementedException();
        }
        if (ctx.Not() != null) {
            return YUnaryExpression.Operator.Not;
        }
        if (ctx.PlusPlus() != null) {
            return YUnaryExpression.Operator.IncrementPrefix;
        }
        if (ctx.MinusMinus() != null) {
            return YUnaryExpression.Operator.DecrementPrefix;
        }
        throw new IllegalStateException();
    }

    /**
     * binaryOrTernaryExpression
     * :   multiplicativeExpression
     * |   additiveExpression
     * |   shiftExpression
     * |   relationalExpression
     * |   equalityExpression
     * |   andExpression
     * |   exclusiveOrExpression
     * |   inclusiveOrExpression
     * |   logicalAndExpression
     * |   logicalOrExpression
     * |   ternaryExpression
     * ;
     */
    public YExpression visitBinaryOrTernaryExpression(CminParser.BinaryOrTernaryExpressionContext ctx) {
        YExpression equalityExpression = visitEqualityExpression(ctx.equalityExpression());
        if (equalityExpression != null) {
            return equalityExpression;
        }

        // todo: others
        throw new IllegalStateException();
    }

    /**
     * multiplicativeExpression
     * :   unaryOrNullaryExpression
     * |   multiplicativeExpression (Asterisk | Div | Mod) unaryOrNullaryExpression
     * ;
     */
    public YExpression visitMultiplicativeExpression(CminParser.MultiplicativeExpressionContext ctx) {
        // TODO: support second rule
        return visitUnaryOrNullaryExpression(ctx.unaryOrNullaryExpression());
    }

    /**
     * additiveExpression
     * :   multiplicativeExpression
     * |   additiveExpression (Plus | Minus) multiplicativeExpression
     * ;
     */
    public YExpression visitAdditiveExpression(CminParser.AdditiveExpressionContext ctx) {
        // TODO: support second rule
        return visitMultiplicativeExpression(ctx.multiplicativeExpression());
    }

    /**
     * shiftExpression
     * :   additiveExpression
     * |   shiftExpression (LeftShift | RightShift) additiveExpression
     * ;
     */
    public YExpression visitShiftExpression(CminParser.ShiftExpressionContext ctx) {
        // TODO: support second rule
        return visitAdditiveExpression(ctx.additiveExpression());
    }

    /**
     * relationalExpression
     * :   shiftExpression
     * |   relationalExpression (Less | LessEqual | Greater | GreaterEqual) shiftExpression
     * ;
     */
    public YExpression visitRelationalExpression(CminParser.RelationalExpressionContext ctx) {
        // TODO: support second rule
        return visitShiftExpression(ctx.shiftExpression());
    }

    /**
     * equalityExpression
     * :   relationalExpression
     * |   equalityExpression (Equals | NotEquals) relationalExpression
     * ;
     */
    public YExpression visitEqualityExpression(CminParser.EqualityExpressionContext ctx) {
        if (ctx == null) {
            return null;
        }

        boolean isEquality = ctx.Equals() != null;
        boolean isNonEquality = ctx.NotEquals() != null;
        if (isEquality || isNonEquality) {
            YExpression leftExpression = visitEqualityExpression(ctx.equalityExpression());
            YExpression rightExpression = visitRelationalExpression(ctx.relationalExpression());

            YEqualityExpression equalityExpression = new YEqualityExpression(leftExpression, rightExpression);
            if (isEquality) {
                return equalityExpression;
            } else {
                return new YUnaryExpression(equalityExpression, YUnaryExpression.Operator.Not);
            }
        }

        return visitRelationalExpression(ctx.relationalExpression());
    }

    /**
     * andExpression
     * :   equalityExpression
     * |   andExpression And equalityExpression
     * ;
     */
    public YExpression visitAndExpression(CminParser.AndExpressionContext ctx) {
        YExpression equalityExpression = visitEqualityExpression(ctx.equalityExpression());
        CminParser.AndExpressionContext andExpressionCtx = ctx.andExpression();
        if (andExpressionCtx != null) {
            YExpression andExpression = visitAndExpression(andExpressionCtx);
            return new YBinaryExpression(andExpression, equalityExpression, YBinaryExpression.Operator.BitOr);
        }
        return equalityExpression;
    }

    /**
     * exclusiveOrExpression
     * :   andExpression
     * |   exclusiveOrExpression Xor andExpression
     * ;
     */
    public YExpression visitExclusiveOrExpression(CminParser.ExclusiveOrExpressionContext ctx) {
        YExpression andExpression = visitAndExpression(ctx.andExpression());
        CminParser.ExclusiveOrExpressionContext exclusiveOrCtx = ctx.exclusiveOrExpression();
        if (exclusiveOrCtx != null) {
            YExpression exclusiveOrExpression = visitExclusiveOrExpression(exclusiveOrCtx);
            return new YBinaryExpression(exclusiveOrExpression, andExpression, YBinaryExpression.Operator.BitOr);
        }
        return andExpression;
    }

    /**
     * inclusiveOrExpression
     * :   exclusiveOrExpression
     * |   inclusiveOrExpression Or exclusiveOrExpression
     * ;
     */
    public YExpression visitInclusiveOrExpression(CminParser.InclusiveOrExpressionContext ctx) {
        YExpression exclusiveOrExpression = visitExclusiveOrExpression(ctx.exclusiveOrExpression());
        CminParser.InclusiveOrExpressionContext inclusiveOrCtx = ctx.inclusiveOrExpression();
        if (inclusiveOrCtx != null) {
            YExpression inclusiveOrExpression = visitInclusiveOrExpression(inclusiveOrCtx);
            return new YBinaryExpression(exclusiveOrExpression, inclusiveOrExpression, YBinaryExpression.Operator.BitOr);
        }
        return exclusiveOrExpression;
    }

    /**
     * logicalAndExpression
     * :   inclusiveOrExpression
     * |   logicalAndExpression AndAnd inclusiveOrExpression
     * ;
     */
    public YExpression visitLogicalAndExpression(CminParser.LogicalAndExpressionContext ctx) {
        YExpression inclusiveOrExpression = visitInclusiveOrExpression(ctx.inclusiveOrExpression());
        CminParser.LogicalAndExpressionContext logicalAndCtx = ctx.logicalAndExpression();
        if (logicalAndCtx != null) {
            YExpression logicalAndExpression = visitLogicalAndExpression(logicalAndCtx);
            return new YBinaryExpression(inclusiveOrExpression, logicalAndExpression, YBinaryExpression.Operator.BitOr);
        }
        return inclusiveOrExpression;
    }

    /**
     * logicalOrExpression
     * :   logicalAndExpression
     * |   logicalOrExpression OrOr logicalAndExpression
     * ;
     */
    public YExpression visitLogicalOrExpression(CminParser.LogicalOrExpressionContext ctx) {
        YExpression logicalAndExpression = visitLogicalAndExpression(ctx.logicalAndExpression());
        CminParser.LogicalOrExpressionContext logicalOrCtx = ctx.logicalOrExpression();
        if (logicalOrCtx != null) {
            YExpression logicalOrExpression = visitLogicalOrExpression(logicalOrCtx);
            return new YBinaryExpression(logicalOrExpression, logicalAndExpression, YBinaryExpression.Operator.BitOr);
        }
        return logicalAndExpression;
    }

    /**
     * constantExpression
     * :   ternaryExpression
     * ;
     */
    public YEntity visitConstantExpression(CminParser.ConstantExpressionContext ctx) {
        return visitTernaryExpression(ctx.ternaryExpression());
    }

    /**
     * ternaryExpression
     * :   logicalOrExpression (Question expression Colon ternaryExpression)?
     * ;
     */
    public YExpression visitTernaryExpression(CminParser.TernaryExpressionContext ctx) {
        YExpression condition = visitLogicalOrExpression(ctx.logicalOrExpression());
        if (ctx.Question() != null) {
            YExpression trueExpression = visitExpression(ctx.expression());
            YExpression falseExpression = visitTernaryExpression(ctx.ternaryExpression());
            return new YTernaryExpression(condition, trueExpression, falseExpression);
        }
        return condition;
    }

    /**
     * assignmentExpression
     * :   rvalueExpression
     * |   lvalueExpression assignmentOperator assignmentExpression
     * ;
     */
    public YExpression visitAssignmentExpression(CminParser.AssignmentExpressionContext ctx) {
        CminParser.AssignmentOperatorContext assignmentOperatorCtx = ctx.assignmentOperator();
        if (assignmentOperatorCtx != null) {
            YAssignmentExpression.Operator assignmentOperator = visitAssignmentOperator(assignmentOperatorCtx);
            YExpression leftExpression = visitLvalueExpression(ctx.lvalueExpression());
            YExpression rightExpression = visitAssignmentExpression(ctx.assignmentExpression());
            return new YAssignmentExpression(assignmentOperator, leftExpression, rightExpression);
        }
        return visitRvalueExpression(ctx.rvalueExpression());
    }

    /**
     * assignmentOperator
     * :   Assign
     * |   MultiplyAssign
     * |   DivideAssign
     * |   ModuloAssign
     * |   PlusAssign
     * |   MinusAssign
     * |   LeftShiftAssign
     * |   RightShiftAssign
     * |   AndAssign
     * |   OrAssign
     * |   XorAssign
     * ;
     */
    public YAssignmentExpression.Operator visitAssignmentOperator(CminParser.AssignmentOperatorContext ctx) {
        if (ctx.Assign() != null) {
            return YAssignmentExpression.Operator.Assign;
        }
        if (ctx.MultiplyAssign() != null) {
            return YAssignmentExpression.Operator.MultiplyAssign;
        }
        if (ctx.DivideAssign() != null) {
            return YAssignmentExpression.Operator.DivideAssign;
        }
        if (ctx.ModuloAssign() != null) {
            return YAssignmentExpression.Operator.ModuloAssign;
        }
        if (ctx.PlusAssign() != null) {
            return YAssignmentExpression.Operator.PlusAssign;
        }
        if (ctx.MinusAssign() != null) {
            return YAssignmentExpression.Operator.MinusAssign;
        }
        if (ctx.LeftShiftAssign() != null) {
            return YAssignmentExpression.Operator.LeftShiftAssign;
        }
        if (ctx.RightShiftAssign() != null) {
            return YAssignmentExpression.Operator.RightShiftAssign;
        }
        if (ctx.AndAssign() != null) {
            return YAssignmentExpression.Operator.AndAssign;
        }
        if (ctx.OrAssign() != null) {
            return YAssignmentExpression.Operator.OrAssign;
        }
        if (ctx.XorAssign() != null) {
            return YAssignmentExpression.Operator.XorAssign;
        }

        throw new IllegalArgumentException();
    }

    /**
     * lvalueExpression
     * :   unaryOrNullaryExpression
     * ;
     */
    public YExpression visitLvalueExpression(CminParser.LvalueExpressionContext ctx) {
        return visitUnaryOrNullaryExpression(ctx.unaryOrNullaryExpression());
    }

    /**
     * rvalueExpression
     * :   ternaryExpression
     * ;
     */
    public YExpression visitRvalueExpression(CminParser.RvalueExpressionContext ctx) {
        return visitTernaryExpression(ctx.ternaryExpression());
    }

    /**
     * typeSpecifier
     * :   storageClassSpecifier
     * ;
     */
    public YEntity visitTypeSpecifier(CminParser.TypeSpecifierContext ctx) {
        throw new IllegalStateException();
    }

    /**
     * variableInitialisation
     * :   variableName
     * |   variableName '=' rvalueExpression
     * ;
     */
    public YVariableInitialiser visitVariableInitialisation(CminParser.VariableInitialisationContext ctx) {
        if (ctx == null) {
            return null;
        }
        CminParser.VariableNameContext variableNameCtx = ctx.variableName();
        if (variableNameCtx == null) {
            throw new ParserException(ctx, "Missing variable name in variable declaration");
        }
        YVariableRef variable = visitVariableName(variableNameCtx);
        CminParser.RvalueExpressionContext expressionCtx = ctx.rvalueExpression();
        YExpression expression = expressionCtx != null
                ? (YExpression) visitRvalueExpression(expressionCtx)
                : null;
        return new YVariableInitialiser(variable, expression);
    }

    /**
     * storageClassSpecifier
     * :   Typedef
     * |   Extern
     * |   Static
     * |   ThreadLocal
     * |   Auto
     * |   Register
     * ;
     */
    public YEntity visitStorageClassSpecifier(CminParser.StorageClassSpecifierContext ctx) {
        throw new IllegalStateException();
    }

    /**
     * primitiveTypeDeclarator
     * :   primitiveTypeSpecifier? primitiveTypeKeyword
     * ;
     */
    public YType visitPrimitiveTypeDeclarator(CminParser.PrimitiveTypeDeclaratorContext ctx) {
        // todo: use primitive type specifier as well
        CminKeyword typeKeyword = visitPrimitiveTypeKeyword(ctx.primitiveTypeKeyword());
        YType type = CminKeyword.tryConvert(typeKeyword);
        if (type == null) {
            throw new ParserException(ctx, "Could not parse primitive type " + ctx.getText());
        }
        return type;
    }

    /**
     * primitiveTypeSpecifier
     * :   Signed
     * |   Unsigned
     * ;
     */
    public CminKeyword visitPrimitiveTypeSpecifier(CminParser.PrimitiveTypeSpecifierContext ctx) {
        if (ctx == null) {
            return null;
        }
        int childCount = ctx.getChildCount();
        assert childCount > 0 : childCount;
        String keyword = ctx.getChild(0).getText(); // todo: get token characters correctly, something like: `ctx.getToken(...)`

        switch (keyword) {
            case "signed":
                return CminKeyword.Signed;
            case "unsigned":
                return CminKeyword.Unsigned;
            default:
                throw new ParserException(ctx, "Unexpected primitive type specifier: " + keyword);
        }
    }

    /**
     * primitiveTypeKeyword
     * :   Void
     * |   Char
     * |   Short Int?
     * |   Short Short Int?
     * |   Int
     * |   Long
     * |   Long Long Int?
     * |   Float
     * |   Double
     * |   Long Double
     * |   Bool
     * |   Auto
     * ;
     */
    public CminKeyword visitPrimitiveTypeKeyword(CminParser.PrimitiveTypeKeywordContext ctx) {
        if (ctx == null) {
            throw new ArgumentNullException();
        }
        int childCount = ctx.getChildCount();
        assert childCount > 0 : childCount;
        String keyword = ctx.getChild(0).getText(); // todo: get token bitness correctly, something like: `ctx.getToken(...)`

        switch (keyword) {
            case "short":
            case "short int":
                return CminKeyword.Short;
            case "long":
            case "long int":
                return CminKeyword.Long;
            case "long long":
            case "long long int":
                return CminKeyword.LongLong;
            // TODO: process some common custom types
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
            case "long double":
                return CminKeyword.LongDouble;
            default:
                throw new ParserException(ctx, "Unexpected primitive type keyword: " + keyword);
        }
    }

    /**
     * variableTypeSpecifierQualifierList
     * :   typeSpecifier         variableTypeSpecifierQualifierList?
     * ;
     */
    public YEntity visitVariableTypeSpecifierQualifierList(CminParser.VariableTypeSpecifierQualifierListContext ctx) {
        throw new IllegalStateException();
    }

    /**
     * statement
     * :   variableDeclarationStatement
     * |   expressionStatement
     * |   labeledStatement
     * |   blockStatement
     * |   branchingStatement
     * |   loopStatement
     * |   jumpStatement
     * ;
     */
    public YStatement visitStatement(CminParser.StatementContext ctx) {
        CminParser.VariableDeclarationStatementContext variableDeclarationCtx = ctx.variableDeclarationStatement();
        if (variableDeclarationCtx != null) {
            return visitVariableDeclarationStatement(variableDeclarationCtx);
        }
        CminParser.ExpressionStatementContext expressionStatementCtx = ctx.expressionStatement();
        if (expressionStatementCtx != null) {
            return visitExpressionStatement(expressionStatementCtx);
        }
        CminParser.LabeledStatementContext labeledStatementCtx = ctx.labeledStatement();
        if (labeledStatementCtx != null) {
            return visitLabeledStatement(labeledStatementCtx);
        }
        CminParser.BlockStatementContext blockStatementCtx = ctx.blockStatement();
        if (blockStatementCtx != null) {
            return visitBlockStatement(blockStatementCtx);
        }
        CminParser.BranchingStatementContext branchingStatementCtx = ctx.branchingStatement();
        if (branchingStatementCtx != null) {
            return visitBranchingStatement(branchingStatementCtx);
        }
        CminParser.LoopStatementContext loopStatementCtx = ctx.loopStatement();
        if (loopStatementCtx != null) {
            return visitLoopStatement(loopStatementCtx);
        }
        CminParser.JumpStatementContext jumpStatementCtx = ctx.jumpStatement();
        if (jumpStatementCtx != null) {
            return visitJumpStatement(jumpStatementCtx);
        }

        throw new IllegalStateException();
    }

    /**
     * variableDeclarationStatement
     * :   typeDeclaration variableInitialisationList ';'
     * ;
     */
    public YSequenceStatement visitVariableDeclarationStatement(CminParser.VariableDeclarationStatementContext ctx) {
        YType type = visitTypeDeclaration(ctx.typeDeclaration());
        YVariableInitialiserList initialisationList = visitVariableInitialisationList(ctx.variableInitialisationList());
        YSequenceStatementBuilder builder = new YSequenceStatementBuilder();
        for (YVariableInitialiser initialiser : initialisationList) {
            YVariableRef variable = initialiser.variable;
            YExpression initExpression = initialiser.initExpression;
            builder.add(new YVariableDeclarationStatement(type, variable));
            if (initialiser.hasInitExpression()) {
                YAssignmentExpression assignmentExpression = new YAssignmentExpression(variable, initExpression);
                builder.add(new YLinearStatement(assignmentExpression));
            }
        }
        return builder.build();
    }

    /**
     * typeDeclaration
     * :   typeSpecifier* typeDeclarator
     * ;
     */
    public YType visitTypeDeclaration(CminParser.TypeDeclarationContext ctx) {
        YType type = visitTypeDeclarator(ctx.typeDeclarator());
        // todo: process typeSpecifier
        return type;
    }

    /**
     * variableInitialisationList
     * :   variableInitialisation
     * |   variableInitialisationList ',' variableInitialisation
     * ;
     */
    public YVariableInitialiserList visitVariableInitialisationList(CminParser.VariableInitialisationListContext ctx) {
        YVariableInitialiserList builder = new YVariableInitialiserList();
        YVariableInitialiser initialisation = visitVariableInitialisation(ctx.variableInitialisation());
        if (initialisation != null) {
            builder.add(initialisation);
        }
        CminParser.VariableInitialisationListContext recursiveListCtx = ctx.variableInitialisationList();
        if (recursiveListCtx != null) {
            YVariableInitialiserList recursiveList = visitVariableInitialisationList(recursiveListCtx);
            builder.addAll(recursiveList.values);
        }
        return builder;
    }

    /**
     * typeDeclarator
     * :   LeftParen typeDeclarator RightParen
     * |   typeDeclarator Asterisk
     * |   primitiveTypeDeclarator
     * ;
     */
    // TODO: Check this method's efficiency
    public YType visitTypeDeclarator(CminParser.TypeDeclaratorContext ctx) {
        CminParser.TypeDeclaratorContext typeDeclaratorCtx = ctx.typeDeclarator();
        if (typeDeclaratorCtx != null) {
            boolean isPointer = ctx.Asterisk() != null;
            YType result = visitTypeDeclarator(ctx.typeDeclarator());
            if (isPointer) {
                return result.withPointerLevel(result.pointerLevel + 1);
            }
            return result;
        }
        YType primitiveTypeDeclarator = visitPrimitiveTypeDeclarator(ctx.primitiveTypeDeclarator());
        if (primitiveTypeDeclarator != null) {
            return primitiveTypeDeclarator;
        }
        throw new ParserException(ctx, "Could not parse type declarator");
    }

    /**
     * statementExpression
     * :   expression? ';'
     * ;
     */
    public YStatement visitExpressionStatement(CminParser.ExpressionStatementContext ctx) {
        CminParser.ExpressionContext expressionCtx = ctx.expression();
        if (ctx.expression() == null) {
            return null; // empty statement
        }
        YExpression expression = visitExpression(expressionCtx);
        return new YLinearStatement(expression);
    }

    /**
     * labeledStatement
     * :   Identifier ':' statement
     * |   Case caseExpression ':' statement
     * |   Default ':' statement
     * ;
     */
    public YStatement visitLabeledStatement(CminParser.LabeledStatementContext ctx) {
        throw new IllegalStateException();
    }

    /**
     * blockStatement
     * :   LeftBrace statement* RightBrace
     * ;
     */
    public YBlockStatement visitBlockStatement(CminParser.BlockStatementContext ctx) {
        YSequenceStatementBuilder builder = new YSequenceStatementBuilder();
        for (CminParser.StatementContext statementContext : ctx.statement()) {
            YStatement statement = visitStatement(statementContext);
            builder.add(statement);
        }
        return builder.build().toBlockStatement();
    }

    /**
     * expression
     * :   LeftParen expression RightParen
     * |   unaryOrNullaryExpression
     * |   binaryOrTernaryExpression
     * |   assignmentExpression
     * ;
     */
    public YExpression visitExpression(CminParser.ExpressionContext ctx) {
        if (ctx.LeftParen() != null && ctx.RightParen() != null) {
            return visitExpression(ctx.expression());
        }
        CminParser.UnaryOrNullaryExpressionContext unaryOrNullaryCtx = ctx.unaryOrNullaryExpression();
        if (unaryOrNullaryCtx != null) {
            return visitUnaryOrNullaryExpression(unaryOrNullaryCtx);
        }
        CminParser.BinaryOrTernaryExpressionContext binaryOrTernaryCtx = ctx.binaryOrTernaryExpression();
        if (binaryOrTernaryCtx != null) {
            return visitBinaryOrTernaryExpression(binaryOrTernaryCtx);
        }
        CminParser.AssignmentExpressionContext assignmentExpressionCtx = ctx.assignmentExpression();
        if (assignmentExpressionCtx != null) {
            return visitAssignmentExpression(assignmentExpressionCtx);
        }
        throw new IllegalStateException();
    }

    /**
     * condition
     * :   expression
     * ;
     */
    public YExpression visitCondition(CminParser.ConditionContext ctx) {
        return visitExpression(ctx.expression());
    }

    /**
     * falseStatement
     * :   statement
     * ;
     */
    public YStatement visitFalseStatement(CminParser.FalseStatementContext ctx) {
        return visitStatement(ctx.statement());
    }

    /**
     * branchingStatement
     * :   If '(' condition ')' statement (Else falseStatement)?
     * |   Switch '(' condition ')' statement
     * ;
     */
    public YBranchingStatement visitBranchingStatement(CminParser.BranchingStatementContext ctx) {
        if (ctx.If() != null) {
            YExpression condition = visitCondition(ctx.condition());
            YStatement trueStatement = visitStatement(ctx.statement());
            CminParser.FalseStatementContext falseStatementCtx = ctx.falseStatement();
            YStatement falseStatement = falseStatementCtx != null
                    ? visitFalseStatement(falseStatementCtx)
                    : null;
            return new YBranchingStatement(condition, trueStatement, falseStatement);
        }

        throw new IllegalStateException();
    }

    /**
     * loopStatement
     * :   While '(' condition ')' statement
     * |   Do statement While '(' condition ')' ';'
     * |   For '(' forCondition ')' statement
     * ;
     */
    public YLoopStatement visitLoopStatement(CminParser.LoopStatementContext ctx) {
        throw new IllegalStateException();
    }

    /**
     * forCondition
     * :   forDeclaration ';' forExpression? ';' forExpression?
     * |   expression? ';' forExpression? ';' forExpression?
     * ;
     * forDeclaration
     * :   typeDeclarator variableInitialisationList
     * ;
     */
    public YExpression visitForCondition(CminParser.ForConditionContext ctx) {
        throw new IllegalStateException();
    }

    /**
     * forDeclaration
     * :   typeDeclarator variableInitialisationList
     * ;
     */
    public YStatement visitForDeclaration(CminParser.ForDeclarationContext ctx) {
        throw new IllegalStateException();
    }

    /**
     * forExpression
     * :   expression
     * |   forExpression ',' expression
     * ;
     */
    public YExpression visitForExpression(CminParser.ForExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /**
     * jumpStatement
     * :   Goto Identifier ';'
     * |   Continue ';'
     * |   Break ';'
     * |   Return expression? ';'
     * ;
     */
    public YSequenceStatement visitJumpStatement(CminParser.JumpStatementContext ctx) {
        throw new IllegalStateException();
    }
}
