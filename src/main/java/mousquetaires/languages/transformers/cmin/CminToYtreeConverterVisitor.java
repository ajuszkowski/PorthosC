package mousquetaires.languages.transformers.cmin;

import mousquetaires.interpretation.internalrepr.exceptions.ParserException;
import mousquetaires.languages.parsers.CminParser;
import mousquetaires.languages.parsers.CminVisitor;
import mousquetaires.languages.transformers.cmin.temp.YVariableInitialiserListTemp;
import mousquetaires.languages.transformers.cmin.temp.YVariableInitialiserTemp;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.ytree.YSyntaxTreeBuilder;
import mousquetaires.languages.ytree.expressions.*;
import mousquetaires.languages.ytree.statements.*;
import mousquetaires.languages.ytree.statements.artificial.YBugonStatement;
import mousquetaires.languages.ytree.statements.artificial.YProcess;
import mousquetaires.languages.ytree.types.YPrimitiveTypeName;
import mousquetaires.languages.ytree.types.YPrimitiveTypeSpecifier;
import mousquetaires.languages.ytree.types.YType;
import mousquetaires.languages.ytree.types.YTypeFactory;
import mousquetaires.utils.exceptions.ArgumentNullException;
import mousquetaires.utils.exceptions.NotImplementedException;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;


class CminToYtreeConverterVisitor
        extends AbstractParseTreeVisitor<YEntity>
        implements CminVisitor<YEntity> {
    //extends CminBaseVisitor<YEntity> {

    private final YSyntaxTreeBuilder syntaxTreeBuilder = new YSyntaxTreeBuilder();

    /**
     * main
     * :   statement+
     * |   processStatement+ // temporary
     * ;
     */
    public YSyntaxTree visitMain(CminParser.MainContext ctx) {
        int childrenCount = ctx.getChildCount();
        for (int i = 0; i < childrenCount; i++) {
            ParseTree childTree = ctx.getChild(i);
            assert childTree != null;
            YStatement root = (YStatement) this.visit(childTree);
            syntaxTreeBuilder.addRoot(root);
        }
        return syntaxTreeBuilder.build();
    }

    // Temporary: explicitly specified processes

    /**
     * processStatement
     * :   'process' Identifier blockStatement
     * |   bugonStatement
     * ;
     */
    public YStatement visitProcessStatement(CminParser.ProcessStatementContext ctx) {
        CminParser.BlockStatementContext blockStatementCtx = ctx.blockStatement();
        if (blockStatementCtx != null) {
            YBlockStatement blockStatement = visitBlockStatement(blockStatementCtx);
            String processName = ctx.Identifier().getText();
            return new YProcess(processName, blockStatement);
        }
        CminParser.BugonStatementContext bugonStatementCtx = ctx.bugonStatement();
        if (bugonStatementCtx != null) {
            return visitBugonStatement(bugonStatementCtx);
        }
        throw new IllegalStateException();
    }

    /**
     * bugOnStatement
     * :   'bug_on' '(' expression ')'
     * ;
     */
    public YBugonStatement visitBugonStatement(CminParser.BugonStatementContext ctx) {
        YExpression expression = visitExpression(ctx.expression());
        return new YBugonStatement(expression);
    }

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
        return YVariableRef.create(name);
    }

    /**
     * constant
     * :   Constant
     * |   StringLiteral
     * ;
     */
    public YConstant visitConstant(CminParser.ConstantContext ctx) {
        int childCount = ctx.getChildCount();
        assert childCount == 1 : childCount;
        TerminalNode literal = (TerminalNode) ctx.getChild(0);
        if (literal != null) {
            String constantText = literal.getText();  // todo: get token symbol text properly here
            YConstant parsedConstant = YConstant.tryParse(constantText);
            if (parsedConstant == null) {
                throw new ParserException(ctx, "Could not parse string constant " + constantText);
            }
            return parsedConstant;
        }
        throw new IllegalStateException();
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
        // todo: postfix expression cases
        throw new NotImplementedException();
        //throw new IllegalStateException();
    }

    /**
     * functionArgumentExpressionList
     * :   functionArgumentExpression
     * |   functionArgumentExpressionList ',' functionArgumentExpression
     * ;
     */
    public YEntity visitFunctionArgumentExpressionList(CminParser.FunctionArgumentExpressionListContext ctx) {
        throw new NotImplementedException();
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

    /**
     * unaryOperator
     * :   (And|Asterisk|Plus|Minus|Tilde|Not|PlusPlus|MinusMinus)
     * ;
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
        throw new NotImplementedException();
        //throw new IllegalStateException();
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
        //if (ctx == null) {
        //    return null;  // recursion exit
        //}
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
            return new YAssignmentExpression(leftExpression, rightExpression, assignmentOperator);
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
        throw new NotImplementedException();
    }

    /**
     * variableInitialisation
     * :   variableName
     * |   variableName '=' rvalueExpression
     * ;
     */
    public YVariableInitialiserTemp visitVariableInitialisation(CminParser.VariableInitialisationContext ctx) {
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
                ? visitRvalueExpression(expressionCtx)
                : null;
        return new YVariableInitialiserTemp(variable, expression);
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
        throw new NotImplementedException();
    }

    /**
     * primitiveTypeDeclarator
     * :   primitiveTypeSpecifier? primitiveTypeKeyword
     * ;
     */
    public YType visitPrimitiveTypeDeclarator(CminParser.PrimitiveTypeDeclaratorContext ctx) {
        YPrimitiveTypeSpecifier typeSpecifier = visitPrimitiveTypeSpecifier(ctx.primitiveTypeSpecifier());
        YPrimitiveTypeName typeName = visitPrimitiveTypeKeyword(ctx.primitiveTypeKeyword());
        return YTypeFactory.getPrimitiveType(typeName, typeSpecifier);
    }

    /**
     * primitiveTypeSpecifier
     * :   Signed
     * |   Unsigned
     * ;
     */
    public YPrimitiveTypeSpecifier visitPrimitiveTypeSpecifier(CminParser.PrimitiveTypeSpecifierContext ctx) {
        if (ctx == null) {
            return null;
        }
        int childCount = ctx.getChildCount();
        assert childCount > 0 : childCount;
        String keyword = ctx.getChild(0).getText(); // todo: get token characters correctly, something like: `ctx.getToken(...)`

        switch (keyword) {
            case "specifier":
                return YPrimitiveTypeSpecifier.Signed;
            case "unsigned":
                return YPrimitiveTypeSpecifier.Unsigned;
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
    public YPrimitiveTypeName visitPrimitiveTypeKeyword(CminParser.PrimitiveTypeKeywordContext ctx) {
        if (ctx == null) {
            throw new ArgumentNullException();
        }
        int childCount = ctx.getChildCount();
        assert childCount > 0 : childCount;

        // todo: get token correctly, something like: `ctx.getToken(...)`
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < childCount; ++i) {
            builder.append(ctx.getChild(i).getText());
            if (i != childCount - 1) {
                builder.append(' ');
            }
        }
        String keywordText = builder.toString();

        switch (keywordText) {
            case "short":
            case "short int":
                return YPrimitiveTypeName.Short;
            case "long":
            case "long int":
                return YPrimitiveTypeName.Long;
            case "long long":
            case "long long int":
                return YPrimitiveTypeName.LongLong;
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
                return YPrimitiveTypeName.Int;
            case "char":
                return YPrimitiveTypeName.Char;
            case "float":
                return YPrimitiveTypeName.Float;
            case "double":
                return YPrimitiveTypeName.Double;
            case "long double":
                return YPrimitiveTypeName.LongDouble;
            case "void":
                return YPrimitiveTypeName.Void;
            default:
                throw new ParserException(ctx, "Unexpected primitive type keyword: " + keywordText);
        }
    }

    /**
     * variableTypeSpecifierQualifierList
     * :   typeSpecifier variableTypeSpecifierQualifierList?
     * ;
     */
    public YEntity visitVariableTypeSpecifierQualifierList(CminParser.VariableTypeSpecifierQualifierListContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * variableDeclarationStatement
     * :   typeDeclaration variableInitialisationList ';'
     * ;
     */
    public YStatement visitVariableDeclarationStatement(CminParser.VariableDeclarationStatementContext ctx) {
        CminParser.TypeDeclarationContext typeDeclarationCtx = ctx.typeDeclaration();
        if (typeDeclarationCtx != null) {
            YType type = visitTypeDeclaration(typeDeclarationCtx);

            YVariableInitialiserListTemp initList = visitVariableInitialisationList(ctx.variableInitialisationList());
            YSequenceStatementBuilder builder = new YSequenceStatementBuilder();
            for (YVariableInitialiserTemp initialiser : initList.initialisers) {
                YVariableRef variable = initialiser.variable;
                YExpression initExpression = initialiser.initExpression;
                builder.add(new YVariableDeclarationStatement(type, variable));
                if (initExpression != null) {
                    YAssignmentExpression assignmentExpression = new YAssignmentExpression(variable, initExpression);
                    builder.add(new YLinearStatement(assignmentExpression));
                }
            }
            return builder.build();
        }
        throw new IllegalStateException();
    }

    /**
     * typeDeclaration
     * :   typeSpecifier* typeDeclarator
     * ;
     */
    public YType visitTypeDeclaration(CminParser.TypeDeclarationContext ctx) {
        CminParser.TypeDeclaratorContext typeDeclaratorCtx = ctx.typeDeclarator();
        if (typeDeclaratorCtx != null) {
            YType type = visitTypeDeclarator(typeDeclaratorCtx);
            // todo: process typeSpecifier
            return type;
        }
        throw new IllegalStateException();
    }

    /**
     * variableInitialisationList
     * :   variableInitialisation
     * |   variableInitialisationList ',' variableInitialisation
     * ;
     */
    public YVariableInitialiserListTemp visitVariableInitialisationList(CminParser.VariableInitialisationListContext ctx) {
        YVariableInitialiserListTemp builder = new YVariableInitialiserListTemp();
        YVariableInitialiserTemp initialisation = visitVariableInitialisation(ctx.variableInitialisation());
        if (initialisation != null) {
            builder.add(initialisation);
        }
        CminParser.VariableInitialisationListContext recursiveListCtx = ctx.variableInitialisationList();
        if (recursiveListCtx != null) {
            YVariableInitialiserListTemp recursiveList = visitVariableInitialisationList(recursiveListCtx);
            builder.addAll(recursiveList);
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
        CminParser.StatementContext statementCtx = ctx.statement();
        assert statementCtx != null;
        YStatement statement = visitStatement(statementCtx);
        // todo: test
        TerminalNode identifierNode = ctx.Identifier();
        if (identifierNode != null) {
            String identifier = identifierNode.getSymbol().getText();  // todo: get terminal node text correctly
            return statement.withLabel(identifier);
        }

        // todo: rest
        throw new NotImplementedException();
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
        int childrenCount = ctx.getChildCount();
        assert childrenCount == 1 : childrenCount;
        return (YStatement) visit(ctx.getChild(0));
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
        return YBlockStatement.create(builder.build());
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
        if (ctx.Switch() != null) {
            throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
    }

    /**
     * forDeclaration
     * :   typeDeclarator variableInitialisationList
     * ;
     */
    public YStatement visitForDeclaration(CminParser.ForDeclarationContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * forExpression
     * :   expression
     * |   forExpression ',' expression
     * ;
     */
    public YExpression visitForExpression(CminParser.ForExpressionContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * jumpStatement
     * :   Goto Identifier ';'
     * |   Continue ';'
     * |   Break ';'
     * |   Return expression? ';'
     * ;
     */
    public YStatement visitJumpStatement(CminParser.JumpStatementContext ctx) {
        throw new NotImplementedException();
    }
}
