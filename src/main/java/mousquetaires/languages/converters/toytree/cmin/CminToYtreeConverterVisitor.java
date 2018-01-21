package mousquetaires.languages.converters.toytree.cmin;

import mousquetaires.languages.converters.toytree.cmin.helpers.YSequenceStatementBuilder;
import mousquetaires.languages.converters.toytree.cmin.temporaries.YExpressionListTemp;
import mousquetaires.languages.converters.toytree.cmin.temporaries.YVariableInitialiserListTemp;
import mousquetaires.languages.parsers.CminParser;
import mousquetaires.languages.parsers.CminVisitor;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.YSyntaxTreeBuilder;
import mousquetaires.languages.syntax.ytree.expressions.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YTernaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.YVariableRef;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YIndexerExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YInvocationExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YMemberAccessExpression;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignee;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YVariableAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YIntegerBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YLogicalBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YIntegerPostfixUnaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YLogicalUnaryExpression;
import mousquetaires.languages.syntax.ytree.specific.YAssertionStatement;
import mousquetaires.languages.syntax.ytree.specific.YPreludeStatement;
import mousquetaires.languages.syntax.ytree.specific.YProcessStatement;
import mousquetaires.languages.syntax.ytree.specific.YVariableAssertion;
import mousquetaires.languages.syntax.ytree.statements.YSequenceStatement;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.languages.syntax.ytree.statements.labeled.*;
import mousquetaires.types.ZType;
import mousquetaires.types.ZTypeFactory;
import mousquetaires.types.ZTypeName;
import mousquetaires.types.ZTypeSpecifier;
import mousquetaires.utils.CastHelper;
import mousquetaires.utils.exceptions.ArgumentNullException;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.ytree.ParserException;
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
     * |   initialWriteStatement? processStatement+
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

    // -- Litmus-specific syntax ---------------------------------------------------------------------------------------

    /**
     * litmusSpecificSyntax
     * :   initialWriteStatement? processStatement+ assertionStatement
     * ;
     */
    public YEntity visitLitmusSpecificSyntax(CminParser.LitmusSpecificSyntaxContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * initialWriteStatement
     * :   '{' variableDeclarationStatement* '}'
     * ;
     */
    public YPreludeStatement visitInitialWriteStatement(CminParser.InitialWriteStatementContext ctx) {
        YSequenceStatementBuilder builder = new YSequenceStatementBuilder();
        for (CminParser.VariableDeclarationStatementContext declarationCtx : ctx.variableDeclarationStatement()) {
            YStatement declaration = visitVariableDeclarationStatement(declarationCtx);
            // caution: bad design
            if (declaration instanceof YSequenceStatement) {
                builder.addStatements((YSequenceStatement) declaration);
            } else {
                builder.add(declaration);
            }
        }
        return new YPreludeStatement(builder.build());
    }

    /**
     * processStatement
     * :   'P' ProcessId blockStatement
     * ;
     */
    public YEntity visitProcessStatement(CminParser.ProcessStatementContext ctx) {
        CminParser.BlockStatementContext blockStatementCtx = ctx.blockStatement();
        if (blockStatementCtx != null) {
            int processId = parseProcessId(ctx.ProcessId());
            YSequenceStatement blockStatement = visitBlockStatement(blockStatementCtx);
            return new YProcessStatement(processId, blockStatement);
        }

        throw new IllegalStateException();
    }

    private static int parseProcessId(TerminalNode processIdNode) {
        String processIdText = processIdNode.getSymbol().getText();
        try {
            return Integer.parseInt(processIdText);
        } catch (NumberFormatException e) {
            throw new ParserException("Invalid process postProcessId '" + processIdText + "'");
        }
    }

    /**
     * blockStatement
     * :   LeftBrace statement* RightBrace
     * ;
     */
    public YSequenceStatement visitBlockStatement(CminParser.BlockStatementContext ctx) {
        YSequenceStatementBuilder builder = new YSequenceStatementBuilder(true);
        for (CminParser.StatementContext statementContext : ctx.statement()) {
            YStatement statement = visitStatement(statementContext);
            builder.add(statement);
        }
        return builder.build();
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
        if (childrenCount == 0) {
            return YLinearStatement.createEmptyStatement();
        }
        assert childrenCount == 1 : childrenCount;
        return (YStatement) visit(ctx.getChild(0));
    }

    /**
     * assertionStatement
     * :   'exists' '(' assertionExpression ')' ';'
     * ;
     */
    public YEntity visitAssertionStatement(CminParser.AssertionStatementContext ctx) {
        CminParser.AssertionExpressionContext assertionExpressionCtx = ctx.assertionExpression();
        if (assertionExpressionCtx != null) {
            YExpression assertionExpression = visitAssertionExpression(assertionExpressionCtx);
            return new YAssertionStatement(assertionExpression);
        }
        throw new IllegalStateException();
    }

    /**
     * assertionExpression
     * :   '(' assertionOrExpression ')'
     * |   assertionOrExpression
     * ;
     */
    public YExpression visitAssertionExpression(CminParser.AssertionExpressionContext ctx) {
        CminParser.AssertionOrExpressionContext assertionOrExpressionCtx = ctx.assertionOrExpression();
        if (assertionOrExpressionCtx != null) {
            return visitAssertionOrExpression(assertionOrExpressionCtx);
        }
        throw new IllegalStateException();
    }

    // -- END OF Litmus-specific syntax --------------------------------------------------------------------------------

    /**
     * assertionOrExpression
     * :   assertionAndExpression
     * |   assertionOrExpression (OrOr|DisjunctionOperator) assertionAndExpression
     * ;
     */
    public YExpression visitAssertionOrExpression(CminParser.AssertionOrExpressionContext ctx) {
        CminParser.AssertionAndExpressionContext assertionAndExpressionCtx = ctx.assertionAndExpression();
        if (assertionAndExpressionCtx != null) {
            YExpression rightExpression = visitAssertionAndExpression(assertionAndExpressionCtx);
            CminParser.AssertionOrExpressionContext assertionOrExpressionCtx = ctx.assertionOrExpression();
            if (assertionOrExpressionCtx != null) {
                YExpression leftExpression = visitAssertionAndExpression(assertionAndExpressionCtx);
                return YLogicalBinaryExpression.Kind.Disjunction.createExpression(leftExpression, rightExpression);
            }
            return rightExpression;
        }
        throw new IllegalStateException();
    }

    /**
     * assertionAndExpression
     * :   assertion
     * |   assertionAndExpression (AndAnd|ConjunctionOperator) assertion
     * ;
     */
    public YExpression visitAssertionAndExpression(CminParser.AssertionAndExpressionContext ctx) {
        CminParser.AssertionContext assertionCtx = ctx.assertion();
        if (assertionCtx != null) {
            YRelativeBinaryExpression rightExpression = visitAssertion(assertionCtx);
            CminParser.AssertionAndExpressionContext assertionAndExpressionCtx = ctx.assertionAndExpression();
            if (assertionAndExpressionCtx != null) {
                YExpression leftExpression = visitAssertionAndExpression(assertionAndExpressionCtx);
                return YLogicalBinaryExpression.Kind.Conjunction.createExpression(leftExpression, rightExpression);
            }
            return rightExpression;
        }
        throw new IllegalStateException();
    }

    /**
     * assertion
     * :   (ProcessId ':')? variableName Equals constant
     * ;
     */
    public YRelativeBinaryExpression visitAssertion(CminParser.AssertionContext ctx) {
        Integer processId = null;
        TerminalNode processIdNode = ctx.ProcessId();
        if (processIdNode != null) {
            processId = parseProcessId(processIdNode);
        }
        YVariableRef variableRef = visitVariableName(ctx.variableName());
        YConstant constant = visitConstant(ctx.constant());
        return new YVariableAssertion(processId, variableRef, constant);
    }

    /**
     * primaryExpression
     * :   variableName
     * |   constant
     * ;
     */
    public YExpression visitPrimaryExpression(CminParser.PrimaryExpressionContext ctx) {
        CminParser.VariableNameContext variableNameCtx = ctx.variableName();
        if (variableNameCtx != null) {
            return visitVariableName(variableNameCtx);
        }
        CminParser.ConstantContext constantCtx = ctx.constant();
        if (constantCtx != null) {
            return visitConstant(constantCtx);
        }
        throw new IllegalStateException();
    }

    /**
     * variableName
     * :   Identifier
     * ;
     */
    public YVariableRef visitVariableName(CminParser.VariableNameContext ctx) {
        String name = ctx.Identifier().getText(); // todo: get token postProcessId correctly
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
                throw new ParserException(ctx, "Could not parse constant " + constantText);
            }
            return parsedConstant;
        }
        throw new IllegalStateException();
    }

    /** postfixExpression
     * :   postfixExpression
     * |   postfixExpression LeftBracket expression RightBracket
     * |   postfixExpression LeftParen argumentExpressionList? RightParen
     * |   postfixExpression (Dot | Arrow) Identifier
     * |   postfixExpression (PlusPlus | MinusMinus)
     * ;
     */
    @Override
    public YExpression visitPostfixExpression(CminParser.PostfixExpressionContext ctx) {
        CminParser.PrimaryExpressionContext primaryExpressionCtx = ctx.primaryExpression();
        if (primaryExpressionCtx != null) {
            return visitPrimaryExpression(primaryExpressionCtx);
        }
        CminParser.PostfixExpressionContext postfixExpressionCtx = ctx.postfixExpression();
        if (postfixExpressionCtx != null) {
            YExpression baseExpression = visitPostfixExpression(postfixExpressionCtx);
            // indexer:
            if (ctx.LeftBracket() != null) {
                YExpression indexExpression = visitExpression(ctx.expression());
                return new YIndexerExpression(baseExpression, indexExpression);
            }
            // function invocation:
            if (ctx.LeftParen() != null) {
                YExpressionListTemp argumentsList = visitArgumentExpressionList(ctx.argumentExpressionList());
                return new YInvocationExpression(baseExpression, argumentsList.asArray());
            }
            // member access:
            if (ctx.Dot() != null || ctx.Arrow() != null) {
                String memberName = ctx.Identifier().getSymbol().getText();
                return new YMemberAccessExpression(baseExpression, memberName);
            }
            // increment/decrement:
            if (ctx.PlusPlus() != null) {
                return YIntegerPostfixUnaryExpression.Kind.Increment.createExpression(baseExpression);
            }
            if (ctx.MinusMinus() != null) {
                return YIntegerPostfixUnaryExpression.Kind.Decrement.createExpression(baseExpression);
            }
        }
        throw new IllegalStateException();
    }

    /** argumentExpressionList
     *      :   assignmentExpression
     *      |   argumentExpressionList ',' assignmentExpression
     *      ;
     */
    @Override
    public YExpressionListTemp visitArgumentExpressionList(CminParser.ArgumentExpressionListContext ctx) {
        YExpressionListTemp argumentList = new YExpressionListTemp();
        CminParser.AssignmentExpressionContext assignmentExpressionCtx = ctx.assignmentExpression();
        if (assignmentExpressionCtx != null) {
            argumentList.add(visitAssignmentExpression(assignmentExpressionCtx));
        }
        CminParser.ArgumentExpressionListContext recursiveListCtx = ctx.argumentExpressionList();
        if (recursiveListCtx != null) {
            argumentList.addAll(visitArgumentExpressionList(recursiveListCtx));
        }
        return argumentList;
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
        if (ctx.And() != null ||
                ctx.Asterisk() != null ||
                ctx.Plus() != null ||
                ctx.Minus() != null ||
                ctx.Tilde() != null) {
            // todo; support these cases
            throw new NotImplementedException();
        }
        if (ctx.Not() != null) {
            return YLogicalUnaryExpression.Kind.Negation.createExpression(expression);
        }

        // Increment / Decrement:
        YAssignee assignee = CastHelper.castOrThrow(expression);
        if (ctx.PlusPlus() != null) {
            YIntegerBinaryExpression incremented = YIntegerBinaryExpression.createIncrementExpression(assignee);
            return new YAssignmentExpression(assignee, incremented);
        }
        if (ctx.MinusMinus() != null) {
            YIntegerBinaryExpression incremented = YIntegerBinaryExpression.createDecrementExpression(assignee);
            return new YAssignmentExpression(assignee, incremented);
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
        CminParser.RelationalExpressionContext relationalExpressionCtx = ctx.relationalExpression();
        if (relationalExpressionCtx != null) {
            return visitRelationalExpression(relationalExpressionCtx);
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
     * |   relationalExpression (Less | LessEqual | Greater | GreaterEqual | Equals | NotEquals) shiftExpression
     * ;
     */
    public YExpression visitRelationalExpression(CminParser.RelationalExpressionContext ctx) {
        YExpression rightExpression = visitShiftExpression(ctx.shiftExpression());

        CminParser.RelationalExpressionContext relationalExpressionCtx = ctx.relationalExpression();
        if (relationalExpressionCtx != null) {
            YExpression leftExpression = visitRelationalExpression(relationalExpressionCtx);
            YRelativeBinaryExpression.Kind operator;
            if (ctx.Less() != null) {
                operator = YRelativeBinaryExpression.Kind.Less;
            }
            else if (ctx.LessEqual() != null) {
                operator = YRelativeBinaryExpression.Kind.LessOrEquals;
            }
            else if (ctx.Greater() != null) {
                operator = YRelativeBinaryExpression.Kind.Greater;
            }
            else if (ctx.GreaterEqual() != null) {
                operator = YRelativeBinaryExpression.Kind.GreaterOrEquals;
            }
            else if (ctx.Equals() != null) {
                operator = YRelativeBinaryExpression.Kind.Equals;
            }
            else if (ctx.NotEquals() != null) {
                operator = YRelativeBinaryExpression.Kind.NotEquals;
            }
            else {
                throw new IllegalStateException();
            }
            return operator.createExpression(leftExpression, rightExpression);
        }
        else {
            return rightExpression;
        }
    }

    /**
     * andExpression
     * :   relationalExpression
     * |   andExpression And relationalExpression
     * ;
     */
    public YExpression visitAndExpression(CminParser.AndExpressionContext ctx) {
        YExpression rightExpression = visitRelationalExpression(ctx.relationalExpression());
        CminParser.AndExpressionContext andExpressionCtx = ctx.andExpression();
        if (andExpressionCtx != null) {
            YExpression leftExpression = visitAndExpression(andExpressionCtx);
            return YLogicalBinaryExpression.Kind.Conjunction.createExpression(leftExpression, rightExpression);
        }
        return rightExpression;
    }

    /**
     * exclusiveOrExpression
     * :   andExpression
     * |   exclusiveOrExpression Xor andExpression
     * ;
     */
    public YExpression visitExclusiveOrExpression(CminParser.ExclusiveOrExpressionContext ctx) {
        YExpression rightExpression = visitAndExpression(ctx.andExpression());
        CminParser.ExclusiveOrExpressionContext exclusiveOrCtx = ctx.exclusiveOrExpression();
        if (exclusiveOrCtx != null) {
            YExpression leftExpression = visitExclusiveOrExpression(exclusiveOrCtx);
            return YIntegerBinaryExpression.Kind.BitXor.createExpression(leftExpression, rightExpression);
        }
        return rightExpression;
    }

    /**
     * inclusiveOrExpression
     * :   exclusiveOrExpression
     * |   inclusiveOrExpression Or exclusiveOrExpression
     * ;
     */
    public YExpression visitInclusiveOrExpression(CminParser.InclusiveOrExpressionContext ctx) {
        YExpression rightExpression = visitExclusiveOrExpression(ctx.exclusiveOrExpression());
        CminParser.InclusiveOrExpressionContext inclusiveOrCtx = ctx.inclusiveOrExpression();
        if (inclusiveOrCtx != null) {
            YExpression leftExpression = visitInclusiveOrExpression(inclusiveOrCtx);
            return YIntegerBinaryExpression.Kind.BitOr.createExpression(rightExpression, leftExpression);
        }
        return rightExpression;
    }

    /**
     * logicalAndExpression
     * :   inclusiveOrExpression
     * |   logicalAndExpression AndAnd inclusiveOrExpression
     * ;
     */
    public YExpression visitLogicalAndExpression(CminParser.LogicalAndExpressionContext ctx) {
        YExpression rightExpression = visitInclusiveOrExpression(ctx.inclusiveOrExpression());
        CminParser.LogicalAndExpressionContext logicalAndCtx = ctx.logicalAndExpression();
        if (logicalAndCtx != null) {
            YExpression leftExpression = visitLogicalAndExpression(logicalAndCtx);
            return YLogicalBinaryExpression.Kind.Conjunction.createExpression(rightExpression, leftExpression);
        }
        return rightExpression;
    }

    /**
     * logicalOrExpression
     * :   logicalAndExpression
     * |   logicalOrExpression OrOr logicalAndExpression
     * ;
     */
    public YExpression visitLogicalOrExpression(CminParser.LogicalOrExpressionContext ctx) {
        YExpression rightExpression = visitLogicalAndExpression(ctx.logicalAndExpression());
        CminParser.LogicalOrExpressionContext logicalOrCtx = ctx.logicalOrExpression();
        if (logicalOrCtx != null) {
            YExpression leftExpression = visitLogicalOrExpression(logicalOrCtx);
            return YLogicalBinaryExpression.Kind.Disjunction.createExpression(leftExpression, rightExpression);
        }
        return rightExpression;
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
     * |   lvalueExpression
     *     (   Assign
     *     |   MultiplyAssign
     *     |   DivideAssign
     *     |   ModuloAssign
     *     |   PlusAssign
     *     |   MinusAssign
     *     |   LeftShiftAssign
     *     |   RightShiftAssign
     *     |   AndAssign
     *     |   XorAssign
     *     |   OrAssign
     *     )
     *     assignmentExpression
     * ;
     */
    public YExpression visitAssignmentExpression(CminParser.AssignmentExpressionContext ctx) {
        CminParser.RvalueExpressionContext rvalueExpressionCtx = ctx.rvalueExpression();
        if (rvalueExpressionCtx != null) {
            return visitRvalueExpression(rvalueExpressionCtx);
        }
        CminParser.LvalueExpressionContext lvalueExpressionCtx = ctx.lvalueExpression();
        CminParser.AssignmentExpressionContext assignmentExpressionCtx = ctx.assignmentExpression();
        if (lvalueExpressionCtx != null && assignmentExpressionCtx != null) {
            YVariableRef assignee = visitLvalueExpression(lvalueExpressionCtx);
            YExpression expressionOriginal = visitAssignmentExpression(assignmentExpressionCtx);
            YExpression expression;
            if (ctx.Assign() != null) {
                expression = expressionOriginal;
            }
            else {
                YIntegerBinaryExpression.Kind operator;
                if (ctx.MultiplyAssign() != null) {
                    operator = YIntegerBinaryExpression.Kind.Multiply;
                }
                else if (ctx.DivideAssign() != null) {
                    operator = YIntegerBinaryExpression.Kind.Divide;
                }
                else if (ctx.ModuloAssign() != null) {
                    operator = YIntegerBinaryExpression.Kind.Modulo;
                }
                else if (ctx.PlusAssign() != null) {
                    operator = YIntegerBinaryExpression.Kind.Plus;
                }
                else if (ctx.MinusAssign() != null) {
                    operator = YIntegerBinaryExpression.Kind.Minus;
                }
                else if (ctx.LeftShiftAssign() != null) {
                    operator = YIntegerBinaryExpression.Kind.LeftShift;
                }
                else if (ctx.RightShiftAssign() != null) {
                    operator = YIntegerBinaryExpression.Kind.RightShift;
                }
                else if (ctx.AndAssign() != null) {
                    operator = YIntegerBinaryExpression.Kind.BitAnd;
                }
                else if (ctx.OrAssign() != null) {
                    operator = YIntegerBinaryExpression.Kind.BitOr;
                }
                else if (ctx.XorAssign() != null) {
                    operator = YIntegerBinaryExpression.Kind.BitXor;
                }
                else {
                    throw new IllegalStateException();
                }
                expression = operator.createExpression(assignee, expressionOriginal);
            }
            return new YVariableAssignmentExpression(assignee, expression);
        }
        throw new IllegalStateException();
    }

    /**
     * lvalueExpression
     * :   unaryOrNullaryExpression
     * ;
     */
    public YVariableRef visitLvalueExpression(CminParser.LvalueExpressionContext ctx) {
        YExpression lvalue = visitUnaryOrNullaryExpression(ctx.unaryOrNullaryExpression());
        try {
            return (YVariableRef) lvalue;
        }
        catch (ClassCastException e) {
            // TODO: support Assignments to indexers and member accesses etc...
            throw new NotImplementedException("Assignments to indexers and member accesses are not supported yet");
        }
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
    public YVariableAssignmentExpression visitVariableInitialisation(CminParser.VariableInitialisationContext ctx) {
        //if (ctx == null) {
        //    return null;
        //}
        CminParser.VariableNameContext variableNameCtx = ctx.variableName();
        if (variableNameCtx == null) {
            throw new ParserException(ctx, "Missing variable postProcessId in variable declaration");
        }
        YVariableRef variable = visitVariableName(variableNameCtx);
        CminParser.RvalueExpressionContext expressionCtx = ctx.rvalueExpression();
        YExpression expression = expressionCtx != null
                ? visitRvalueExpression(expressionCtx)
                : null;

        return new YVariableAssignmentExpression(variable, expression);
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
    public ZType visitPrimitiveTypeDeclarator(CminParser.PrimitiveTypeDeclaratorContext ctx) {
        ZTypeSpecifier typeSpecifier = visitPrimitiveTypeSpecifier(ctx.primitiveTypeSpecifier());
        ZTypeName typeName = visitPrimitiveTypeKeyword(ctx.primitiveTypeKeyword());
        return ZTypeFactory.getPrimitiveType(typeName, typeSpecifier);
    }

    /**
     * primitiveTypeSpecifier
     * :   Signed
     * |   Unsigned
     * ;
     */
    public ZTypeSpecifier visitPrimitiveTypeSpecifier(CminParser.PrimitiveTypeSpecifierContext ctx) {
        if (ctx == null) {
            return null;
        }
        int childCount = ctx.getChildCount();
        assert childCount > 0 : childCount;
        String keyword = ctx.getChild(0).getText(); // todo: get token characters correctly, something like: `ctx.getToken(...)`

        switch (keyword) {
            case "specifier":
                return ZTypeSpecifier.Signed;
            case "unsigned":
                return ZTypeSpecifier.Unsigned;
            default:
                throw new ParserException(ctx, "Unexpected primitive returnType specifier: " + keyword);
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
    public ZTypeName visitPrimitiveTypeKeyword(CminParser.PrimitiveTypeKeywordContext ctx) {
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
                return ZTypeName.Short;
            case "long":
            case "long int":
                return ZTypeName.Long;
            case "long long":
            case "long long int":
                return ZTypeName.LongLong;
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
                return ZTypeName.Int;
            case "char":
                return ZTypeName.Char;
            case "float":
                return ZTypeName.Float;
            case "double":
                return ZTypeName.Double;
            case "long double":
                return ZTypeName.LongDouble;
            case "void":
                return ZTypeName.Void;
            default:
                throw new ParserException(ctx, "Unexpected primitive returnType keyword: " + keywordText);
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
            ZType type = visitTypeDeclaration(typeDeclarationCtx);

            YVariableInitialiserListTemp initList = visitVariableInitialisationList(ctx.variableInitialisationList());
            initList.reverse(); // initialisers are visited in reversed order
            YSequenceStatementBuilder builder = new YSequenceStatementBuilder();
            for (YVariableAssignmentExpression initialiser : initList) {
                YVariableRef variable = initialiser.getAssignee();
                YExpression initExpression = initialiser.getExpression();
                builder.add(new YVariableDeclarationStatement(type, variable));
                if (initExpression != null) {
                    YAssignmentExpression assignmentExpression = new YAssignmentExpression(variable, initExpression);
                    builder.add(new YLinearStatement(assignmentExpression));
                }
            }
            return builder.buildAndOptimise();
        }
        throw new IllegalStateException();
    }

    /**
     * typeDeclaration
     * :   typeSpecifier* typeDeclarator
     * ;
     */
    public ZType visitTypeDeclaration(CminParser.TypeDeclarationContext ctx) {
        CminParser.TypeDeclaratorContext typeDeclaratorCtx = ctx.typeDeclarator();
        if (typeDeclaratorCtx != null) {
            ZType type = visitTypeDeclarator(typeDeclaratorCtx);
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
        YVariableInitialiserListTemp initialiserList = new YVariableInitialiserListTemp();
        CminParser.VariableInitialisationContext variableInitialisationCtx = ctx.variableInitialisation();
        if (variableInitialisationCtx != null) {
            initialiserList.add(visitVariableInitialisation(variableInitialisationCtx));
        }
        CminParser.VariableInitialisationListContext recursiveListCtx = ctx.variableInitialisationList();
        if (recursiveListCtx != null) {
            initialiserList.addAll(visitVariableInitialisationList(recursiveListCtx));
        }
        return initialiserList;
    }

    /**
     * typeDeclarator
     * :   LeftParen typeDeclarator RightParen
     * |   typeDeclarator Asterisk
     * |   primitiveTypeDeclarator
     * ;
     */
    // TODO: Check this method's efficiency
    public ZType visitTypeDeclarator(CminParser.TypeDeclaratorContext ctx) {
        CminParser.TypeDeclaratorContext typeDeclaratorCtx = ctx.typeDeclarator();
        if (typeDeclaratorCtx != null) {
            boolean isPointer = ctx.Asterisk() != null;
            ZType result = visitTypeDeclarator(ctx.typeDeclarator());
            if (isPointer) {
                return result.withPointerLevel(result.pointerLevel + 1);
            }
            return result;
        }
        ZType primitiveTypeDeclarator = visitPrimitiveTypeDeclarator(ctx.primitiveTypeDeclarator());
        if (primitiveTypeDeclarator != null) {
            return primitiveTypeDeclarator;
        }
        throw new ParserException(ctx, "Could not parse returnType declarator");
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
    public YLabeledStatement visitLabeledStatement(CminParser.LabeledStatementContext ctx) {
        //TODO: implement
        //CminParser.StatementContext statementCtx = ctx.statement();
        //assert statementCtx != null;
        //YStatement statement = visitStatement(statementCtx);
        //// todo: rest
        //TerminalNode identifierNode = ctx.Identifier();
        //if (identifierNode != null) {
        //    String identifier = identifierNode.getSymbol().getText();  // todo: get terminal node text correctly
        //    return statement.withLabel(identifier);
        //}

        throw new NotImplementedException();
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
    public YStatement visitElseStatement(CminParser.ElseStatementContext ctx) {
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
            YStatement thenStatement = visitStatement(ctx.statement());
            CminParser.ElseStatementContext elseStatementCtx = ctx.elseStatement();
            YStatement elseStatement = elseStatementCtx != null
                    ? visitElseStatement(elseStatementCtx)
                    : null;
            return new YBranchingStatement(condition, thenStatement, elseStatement);
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
