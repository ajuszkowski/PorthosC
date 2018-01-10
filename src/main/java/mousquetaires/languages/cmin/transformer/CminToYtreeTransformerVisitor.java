package mousquetaires.languages.cmin.transformer;

import mousquetaires.interpretation.internalrepr.exceptions.ParserException;
import mousquetaires.languages.parsers.CminBaseVisitor;
import mousquetaires.languages.parsers.CminParser;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.ytree.YSyntaxTreeBuilder;
import mousquetaires.languages.ytree.expressions.*;
import mousquetaires.languages.ytree.expressions.lvalue.YLvalueExpression;
import mousquetaires.languages.ytree.expressions.lvalue.YVariableRef;
import mousquetaires.languages.ytree.types.YType;
import mousquetaires.utils.exceptions.NotImplementedException;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Map;


class CminToYtreeTransformerVisitor extends CminBaseVisitor<YEntity> {
    private final YSyntaxTreeBuilder builder;
    private final CminToYtreeTransformerTypeHelper typeHelper;

    CminToYtreeTransformerVisitor() {
        builder = new YSyntaxTreeBuilder();
        typeHelper = new CminToYtreeTransformerTypeHelper(this);
    }


    // may receive null argument
    // may return null result

    /** main
     *      :   statement+
     *      ;
     */
    @Override
    public YEntity visitMain(CminParser.MainContext ctx) {
        int childrenCount = ctx.getChildCount();
        for (int i = 0; i < childrenCount; i++) {
            YEntity root = this.visit(ctx.getChild(i));
            builder.addRoot(root);
        }
        return builder.build();
    }

    /** primaryExpression
     *      :   variableName
     *      |   constant
     *      ;
     */
    @Override
    public YEntity visitPrimaryExpression(CminParser.PrimaryExpressionContext ctx) {
        assert ctx != null;
        YVariableRef variableRef = (YVariableRef) visitVariableName(ctx.variableName());
        if (variableRef != null) {
            return variableRef;
        }
        YConstant constant = (YConstant) visitConstant(ctx.constant());
        if (constant != null) {
            return constant;
        }
        throw new IllegalStateException();
    }

    /** constant
     *      :   Constant
     *      |   StringLiteral
     *      ;
     */
    @Override
    public YEntity visitConstant(CminParser.ConstantContext ctx) {
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

    /** postfixExpression
     *      :   primaryExpression
     *      |   postfixExpression '(' functionArgumentExpressionList? ')'
     *      |   postfixExpression (PlusPlus | MinusMinus)
     *      ;
     */
    @Override
    public YEntity visitPostfixExpression(CminParser.PostfixExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** functionArgumentExpressionList
     *      :   functionArgumentExpression
     *      |   functionArgumentExpressionList ',' functionArgumentExpression
     *      ;
     */
    @Override
    public YEntity visitFunctionArgumentExpressionList(CminParser.FunctionArgumentExpressionListContext ctx) {
        throw new IllegalStateException();
    }

    /** functionArgumentExpression
     *      :   unaryOrNullaryExpression
     *      ;
     */
    @Override
    public YEntity visitFunctionArgumentExpression(CminParser.FunctionArgumentExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** unaryOrNullaryExpression
     *      :   postfixExpression
     *      |   unaryOperator unaryOrNullaryExpression
     *      ;
     */
    @Override
    public YEntity visitUnaryOrNullaryExpression(CminParser.UnaryOrNullaryExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** unaryOperator
     *      :   And
     *      |   Asterisk
     *      |   Plus
     *      |   Minus
     *      |   Tilde
     *      |   Not
     *      |   PlusPlus
     *      |   MinusMinus
     *      ;
     */
    @Override
    public YEntity visitUnaryOperator(CminParser.UnaryOperatorContext ctx) {
        throw new IllegalStateException();
    }

    /** binaryOrTernaryExpression
     *      :   multiplicativeExpression
     *      |   additiveExpression
     *      |   shiftExpression
     *      |   relationalExpression
     *      |   equalityExpression
     *      |   andExpression
     *      |   exclusiveOrExpression
     *      |   inclusiveOrExpression
     *      |   logicalAndExpression
     *      |   logicalOrExpression
     *      |   ternaryExpression
     *      ;
     */
    @Override
    public YEntity visitBinaryOrTernaryExpression(CminParser.BinaryOrTernaryExpressionContext ctx) {
        YEntity equalityExpression = visitEqualityExpression(ctx.equalityExpression());
        if (equalityExpression != null) {
            return equalityExpression;
        }

        // todo: others
        throw new IllegalStateException();
    }

    /** multiplicativeExpression
     *      :   unaryOrNullaryExpression
     *      |   multiplicativeExpression (Asterisk | Div | Mod) unaryOrNullaryExpression
     *      ;
     */
    @Override
    public YEntity visitMultiplicativeExpression(CminParser.MultiplicativeExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** additiveExpression
     *      :   multiplicativeExpression
     *      |   additiveExpression AdditiveOperator multiplicativeExpression
     *      ;
     */
    @Override
    public YEntity visitAdditiveExpression(CminParser.AdditiveExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** shiftExpression
     *      :   additiveExpression
     *      |   shiftExpression ShiftOperator additiveExpression
     *      ;
     */
    @Override
    public YEntity visitShiftExpression(CminParser.ShiftExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** relationalExpression
     *      :   shiftExpression
     *      |   relationalExpression RelationalOperator shiftExpression
     *      ;
     */
    @Override
    public YEntity visitRelationalExpression(CminParser.RelationalExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** equalityExpression
     *      :   relationalExpression
     *      |   equalityExpression (Equals | NotEquals) relationalExpression
     *      ;
     */
    @Override
    public YEntity visitEqualityExpression(CminParser.EqualityExpressionContext ctx) {
        if (ctx == null) {
            return null;
        }

        boolean isEquality = ctx.Equals() != null;
        boolean isNonEquality = ctx.NotEquals() != null;
        if (isEquality || isNonEquality) {
            YExpression leftExpression = (YExpression) visitEqualityExpression(ctx.equalityExpression());
            YExpression rightExpression = (YExpression) visitRelationalExpression(ctx.relationalExpression());

            YEqualityExpression equalityExpression = new YEqualityExpression(leftExpression, rightExpression);
            if (isEquality) {
                return equalityExpression;
            } else {
                return new YNotExpression(equalityExpression);
            }
        }

        return visitRelationalExpression(ctx.relationalExpression());
    }

    /** andExpression
     *      :   equalityExpression
     *      |   andExpression And equalityExpression
     *      ;
     */
    @Override
    public YEntity visitAndExpression(CminParser.AndExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** exclusiveOrExpression
     *      :   andExpression
     *      |   exclusiveOrExpression Xor andExpression
     *      ;
     */
    @Override
    public YEntity visitExclusiveOrExpression(CminParser.ExclusiveOrExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** inclusiveOrExpression
     *      :   exclusiveOrExpression
     *      |   inclusiveOrExpression Or exclusiveOrExpression
     *      ;
     */
    @Override
    public YEntity visitInclusiveOrExpression(CminParser.InclusiveOrExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** logicalAndExpression
     *      :   inclusiveOrExpression
     *      |   logicalAndExpression AndAnd inclusiveOrExpression
     *      ;
     */
    @Override
    public YEntity visitLogicalAndExpression(CminParser.LogicalAndExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** logicalOrExpression
     *      :   logicalAndExpression
     *      |   logicalOrExpression OrOr logicalAndExpression
     *      ;
     */
    @Override
    public YEntity visitLogicalOrExpression(CminParser.LogicalOrExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** ternaryExpression
     *      :   logicalOrExpression (Question expression Colon ternaryExpression)?
     *      ;
     */
    @Override
    public YEntity visitTernaryExpression(CminParser.TernaryExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** caseExpression
     *      :   ternaryExpression
     *      ;
     */
    @Override
    public YEntity visitCaseExpression(CminParser.CaseExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** lvalueExpression
     *      :   unaryOrNullaryExpression
     *      ;
     */
    @Override
    public YEntity visitLvalueExpression(CminParser.LvalueExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** rvalueExpression
     *      :   ternaryExpression
     *      ;
     */
    @Override
    public YEntity visitRvalueExpression(CminParser.RvalueExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** assignmentExpression
     *      :   rvalueExpression
     *      |   lvalueExpression assignmentOperator assignmentExpression
     *      ;
     */
    @Override
    public YEntity visitAssignmentExpression(CminParser.AssignmentExpressionContext ctx) {
        YEntity assignmentOperator = visitAssignmentOperator(ctx.assignmentOperator());
        if (assignmentOperator != null) {
            YLvalueExpression leftExpression = (YLvalueExpression) visitLvalueExpression(ctx.lvalueExpression());
            YExpression rightExpression = (YExpression) visitRvalueExpression(ctx.rvalueExpression());
            return new YAssignmentExpression(leftExpression, rightExpression);
        }
        return visitRvalueExpression(ctx.rvalueExpression());
    }

    /** assignmentOperator
     *      :   Assign
     *      |   AsteriskAssign
     *      |   DivAssign
     *      |   ModAssign
     *      |   PlusAssign
     *      |   MinusAssign
     *      |   LeftShiftAssign
     *      |   RightShiftAssign
     *      |   AndAssign
     *      |   XorAssign
     *      |   OrAssign
     *      ;
     */
    @Override
    public YEntity visitAssignmentOperator(CminParser.AssignmentOperatorContext ctx) {
        throw new IllegalStateException();
    }

    /** variableDeclarationStatement
     *      :   typeDeclaration variableInitialisationList ';'
     *      ;
     */
    @Override
    public YEntity visitVariableDeclarationStatement(CminParser.VariableDeclarationStatementContext ctx) {
        YType type = visitTypeDeclaration(ctx.typeDeclaration());
    }

    /** typeDeclaration
     *      :   typeSpecifier* typeDeclarator
     *      ;
     */
    @Override
    public YEntity visitTypeDeclaration(CminParser.TypeDeclarationContext ctx) {
        throw new IllegalStateException();
    }

    /** typeDeclarator
     *      :   LeftParen typeDeclarator RightParen
     *      |   typeDeclarator Asterisk
     *      |   primitiveTypeDeclarator
     *      ;
     */
    @Override
    public YEntity visitTypeDeclarator(CminParser.TypeDeclaratorContext ctx) {
        throw new IllegalStateException();
    }

    /** typeSpecifier
     *      :   storageClassSpecifier
     *      ;
     */
    @Override
    public YEntity visitTypeSpecifier(CminParser.TypeSpecifierContext ctx) {
        throw new IllegalStateException();
    }

    /** variableInitialisationList
     *      :   variableInitialisation
     *      |   variableInitialisationList ',' variableInitialisation
     *      ;
     */
    @Override
    public YEntity visitVariableInitialisationList(CminParser.VariableInitialisationListContext ctx) {
        throw new IllegalStateException();
    }

    /** variableInitialisation
     *      :   variableName
     *      |   variableName '=' rvalueExpression
     *      ;
     */
    @Override
    public YEntity visitVariableInitialisation(CminParser.VariableInitialisationContext ctx) {
        throw new IllegalStateException();
    }

    /** storageClassSpecifier
     *      :   Typedef
     *      |   Extern
     *      |   Static
     *      |   ThreadLocal
     *      |   Auto
     *      |   Register
     *      ;
     */
    @Override
    public YEntity visitStorageClassSpecifier(CminParser.StorageClassSpecifierContext ctx) {
        throw new IllegalStateException();
    }

    /** primitiveTypeDeclarator
     *      :   primitiveTypeSpecifier? primitiveTypeKeyword+
     *      ;
     */
    @Override
    public YEntity visitPrimitiveTypeDeclarator(CminParser.PrimitiveTypeDeclaratorContext ctx) {
        throw new IllegalStateException();
    }

    /** primitiveTypeSpecifier
     *      :   Signed
     *      |   Unsigned
     *      ;
     */
    @Override
    public YEntity visitPrimitiveTypeSpecifier(CminParser.PrimitiveTypeSpecifierContext ctx) {
        throw new IllegalStateException();
    }

    /** primitiveTypeKeyword
     *      :   Void
     *      |   Char
     *      |   Short
     *      |   Int
     *      |   Long
     *      |   Long Long
     *      |   Float
     *      |   Double
     *      |   Bool
     *      |   Auto
     *      ;
     */
    @Override
    public YEntity visitPrimitiveTypeKeyword(CminParser.PrimitiveTypeKeywordContext ctx) {
        throw new IllegalStateException();
    }

    /** variableName
     *      :   Identifier
     *      ;
     */
    @Override
    public YEntity visitVariableName(CminParser.VariableNameContext ctx) {
        if (ctx == null) {
            return null;
        }
        String name = ctx.Identifier().getText(); // todo: get token name correctly
        return new YVariableRef(name);
    }

    /** variableTypeSpecifierQualifierList
     *      :   typeSpecifier         variableTypeSpecifierQualifierList?
     *      ;
     */
    @Override
    public YEntity visitVariableTypeSpecifierQualifierList(CminParser.VariableTypeSpecifierQualifierListContext ctx) {
        throw new IllegalStateException();
    }

    /** statement
     *      :   variableDeclarationStatement
     *      |   statementExpression
     *      |   labeledStatement
     *      |   blockStatement
     *      |   branchingStatement
     *      |   loopStatement
     *      |   jumpStatement
     *      ;
     */
    @Override
    public YEntity visitStatement(CminParser.StatementContext ctx) {
        throw new IllegalStateException();
    }

    /** statementExpression
     *      :   expression? ';'
     *      ;
     */
    @Override
    public YEntity visitStatementExpression(CminParser.StatementExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** labeledStatement
     *      :   Identifier ':' statement
     *      |   Case caseExpression ':' statement
     *      |   Default ':' statement
     *      ;
     */
    @Override
    public YEntity visitLabeledStatement(CminParser.LabeledStatementContext ctx) {
        throw new IllegalStateException();
    }

    /** blockStatement
     *      :   LeftBrace statement* RightBrace
     *      ;
     */
    @Override
    public YEntity visitBlockStatement(CminParser.BlockStatementContext ctx) {
        throw new IllegalStateException();
    }

    /** expression
     *      :   LeftParen expression RightParen
     *      |   unaryOrNullaryExpression
     *      |   binaryOrTernaryExpression
     *      |   assignmentExpression
     *      ;
     */
    @Override
    public YEntity visitExpression(CminParser.ExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** condition
     *      :   expression
     *      ;
     */
    @Override
    public YEntity visitCondition(CminParser.ConditionContext ctx) {
        throw new IllegalStateException();
    }

    /** falseStatement
     *      :   statement
     *      ;
     */
    @Override
    public YEntity visitFalseStatement(CminParser.FalseStatementContext ctx) {
        throw new IllegalStateException();
    }

    /** branchingStatement
     *      :   If '(' condition ')' statement (Else falseStatement)?
     *      |   Switch '(' condition ')' statement
     *      ;
     */
    @Override
    public YEntity visitBranchingStatement(CminParser.BranchingStatementContext ctx) {
        throw new IllegalStateException();
    }

    /** loopStatement
     *      :   While '(' condition ')' statement
     *      |   Do statement While '(' condition ')' ';'
     *      |   For '(' forCondition ')' statement
     *      ;
     */
    @Override
    public YEntity visitLoopStatement(CminParser.LoopStatementContext ctx) {
        throw new IllegalStateException();
    }

    /** forCondition
     *  	:   forDeclaration ';' forExpression? ';' forExpression?
     *  	|   expression? ';' forExpression? ';' forExpression?
     *  	;
     *  forDeclaration
     *      :   typeDeclarator variableInitialisationList
     *      ;
     */
    @Override
    public YEntity visitForCondition(CminParser.ForConditionContext ctx) {
        throw new IllegalStateException();
    }

    /** forExpression
     *      :   expression
     *      |   forExpression ',' expression
     *      ;
     */
    @Override
    public YEntity visitForExpression(CminParser.ForExpressionContext ctx) {
        throw new IllegalStateException();
    }

    /** jumpStatement
     *      :   Goto Identifier ';'
     *      |   Continue ';'
     *      |   Break ';'
     *      |   Return expression? ';'
     *      ;
     */
    @Override
    public YEntity visitJumpStatement(CminParser.JumpStatementContext ctx) {
        throw new IllegalStateException();
    }

}
