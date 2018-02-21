package mousquetaires.languages.converters.toytree.c11;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.converters.toytree.c11.helpers.C11ParserHelper;
import mousquetaires.languages.parsers.C11Parser;
import mousquetaires.languages.parsers.C11Visitor;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.YSyntaxTreeBuilder;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariableRef;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YIndexerExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YInvocationExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YMemberAccessExpression;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignee;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YVariableAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YIntegerUnaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YLogicalUnaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YPointerUnaryExpression;
import mousquetaires.languages.syntax.ytree.statements.*;
import mousquetaires.languages.syntax.ytree.statements.jumps.*;
import mousquetaires.languages.syntax.ytree.temporaries.YCompoundStatementBuilder;
import mousquetaires.languages.syntax.ytree.temporaries.YEntityListBuilder;
import mousquetaires.languages.syntax.ytree.temporaries.YExpressionListBuilder;
import mousquetaires.languages.syntax.ytree.temporaries.YUnaryOperatorKindTemp;
import mousquetaires.languages.syntax.ytree.types.YMockType;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.languages.syntax.ytree.types.signatures.YMethodSignature;
import mousquetaires.languages.syntax.ytree.types.signatures.YParameter;
import mousquetaires.languages.syntax.ytree.types.signatures.YParameterListBuilder;
import mousquetaires.utils.StringUtils;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.ytree.YParserException;
import mousquetaires.utils.exceptions.ytree.YParserNotImplementedException;
import mousquetaires.utils.exceptions.ytree.YParserUnintendedStateException;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;


class C11ToYtreeConverterVisitor
        extends AbstractParseTreeVisitor<YEntity>
        implements C11Visitor<YEntity> {

    private final YSyntaxTreeBuilder syntaxTreeBuilder = new YSyntaxTreeBuilder();

    /**
     * main
     * :   compilationUnit
     * ;
     */
    public YSyntaxTree visitMain(C11Parser.MainContext ctx) {
        C11Parser.CompilationUnitContext compilationUnitContext = ctx.compilationUnit();
        if (compilationUnitContext != null) {
            YEntityListBuilder compilationUnitBuilder = visitCompilationUnit(compilationUnitContext);
            ImmutableList<YEntity> rootsList = compilationUnitBuilder.build();
            syntaxTreeBuilder.addAll(rootsList);
            return syntaxTreeBuilder.build();
        }
        throw new YParserException(ctx, "Missing compilation unit");
    }


    /**
     * primaryExpression
     * :   Identifier
     * |   Constant
     * |   StringLiteral+
     * |   '(' expression ')'
     * |   genericSelection
     * ;
     */
    @Override
    public YExpression visitPrimaryExpression(C11Parser.PrimaryExpressionContext ctx) {
        TerminalNode identifier = ctx.Identifier();
        if (identifier != null) {
            return new YVariableRef(identifier.getText());
        }
        TerminalNode constantNode = ctx.Constant();
        if (constantNode != null) {
            YConstant constant = YConstant.tryParse(constantNode.getText());
            if (constant == null) {
                throw new YParserException(ctx, "Could not parse constant: " + StringUtils.wrap(constantNode.getText()));
            }
            return constant;
        }
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * genericSelection
     * :   '_Generic' '(' assignmentExpression ',' genericAssocList ')'
     * ;
     */
    @Override
    public YEntity visitGenericSelection(C11Parser.GenericSelectionContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * genericAssocList
     * :   genericAssociation
     * |   genericAssocList ',' genericAssociation
     * ;
     */
    @Override
    public YEntity visitGenericAssocList(C11Parser.GenericAssocListContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * genericAssociation
     * :   typeName ':' assignmentExpression
     * |   'default' ':' assignmentExpression
     * ;
     */
    @Override
    public YEntity visitGenericAssociation(C11Parser.GenericAssociationContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * postfixExpression
     * :   primaryExpression
     * |   postfixExpression '[' expression ']'
     * |   postfixExpression '(' argumentExpressionList? ')'
     * |   postfixExpression '.' Identifier
     * |   postfixExpression '->' Identifier
     * |   postfixExpression '++'
     * |   postfixExpression '--'
     * |   '(' typeName ')' '{' initializerList '}'
     * |   '(' typeName ')' '{' initializerList ',' '}'
     * ;
     */
    @Override
    public YExpression visitPostfixExpression(C11Parser.PostfixExpressionContext ctx) {
        C11Parser.PrimaryExpressionContext primaryExpressionContext = ctx.primaryExpression();
        if (primaryExpressionContext != null) {
            return visitPrimaryExpression(primaryExpressionContext);
        }
        C11Parser.PostfixExpressionContext postfixExpressionContext = ctx.postfixExpression();
        if (postfixExpressionContext != null) {
            YExpression baseExpression = visitPostfixExpression(postfixExpressionContext);
            // case (indexer expression):
            if (ctx.getTokens(C11Parser.LeftBracket).size() > 0 && ctx.getTokens(C11Parser.RightBracket).size() > 0) {
                C11Parser.ExpressionContext expressionContext = ctx.expression();
                if (expressionContext == null) {
                    throw new YParserException(ctx, "Missing indexer expression");
                }
                YExpression indexerExpression = visitExpression(expressionContext);
                return new YIndexerExpression(baseExpression, indexerExpression);
            }
            // case (invocation expression):
            if (ctx.getTokens(C11Parser.LeftParen).size() > 0 && ctx.getTokens(C11Parser.RightParen).size() > 0) {
                C11Parser.ArgumentExpressionListContext argumentExpressionListContextList = ctx.argumentExpressionList();
                YExpressionListBuilder argumentsBuilder = argumentExpressionListContextList != null
                        ? visitArgumentExpressionList(argumentExpressionListContextList)
                        : new YExpressionListBuilder();
                ImmutableList<YExpression> arguments = argumentsBuilder.build();
                return new YInvocationExpression(baseExpression, arguments);
            }
            // case (member access expression):
            if (ctx.getTokens(C11Parser.Arrow).size() > 0 || ctx.getTokens(C11Parser.Dot).size() > 0) {
                TerminalNode identifierNode = ctx.Identifier();
                if (identifierNode == null) {
                    throw new YParserException(ctx, "Missing member name while parsing member access expression");
                }
                return new YMemberAccessExpression(baseExpression, identifierNode.getText());
            }
            // case (increment):
            if (ctx.getTokens(C11Parser.PlusPlus).size() > 0) {
                return YIntegerUnaryExpression.Kind.PrefixIncrement.createExpression(baseExpression);
            }
            // case (decrement):
            if (ctx.getTokens(C11Parser.MinusMinus).size() > 0) {
                return YIntegerUnaryExpression.Kind.PrefixDecrement.createExpression(baseExpression);
            }
        }
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * argumentExpressionList
     * :   assignmentExpression
     * |   argumentExpressionList ',' assignmentExpression
     * ;
     */
    @Override
    public YExpressionListBuilder visitArgumentExpressionList(C11Parser.ArgumentExpressionListContext ctx) {
        YExpressionListBuilder result = new YExpressionListBuilder();
        C11Parser.AssignmentExpressionContext assignmentExpressionContext = ctx.assignmentExpression();
        C11Parser.ArgumentExpressionListContext argumentExpressionListContext = ctx.argumentExpressionList();
        if (assignmentExpressionContext != null) {
            result.add(visitAssignmentExpression(assignmentExpressionContext));
            if (argumentExpressionListContext != null) {
                result.addAll(visitArgumentExpressionList(argumentExpressionListContext));
            }
            return result;
        }
        throw new YParserException(ctx);
    }

    /**
     * unaryExpression
     * :   postfixExpression
     * |   '++' unaryExpression
     * |   '--' unaryExpression
     * |   unaryOperator castExpression
     * |   'sizeof' unaryExpression
     * |   'sizeof' '(' typeName ')'
     * |   '_Alignof' '(' typeName ')'
     * ;
     */
    @Override
    public YExpression visitUnaryExpression(C11Parser.UnaryExpressionContext ctx) {
        // postfixExpression:
        C11Parser.PostfixExpressionContext postfixExpressionContext = ctx.postfixExpression();
        if (postfixExpressionContext != null) {
            return visitPostfixExpression(postfixExpressionContext);
        }
        C11Parser.UnaryExpressionContext unaryExpressionContext = ctx.unaryExpression();
        if (unaryExpressionContext != null) {
            YExpression baseExpression = visitUnaryExpression(unaryExpressionContext);
            // '++' unaryExpression:
            if (C11ParserHelper.hasToken(ctx, C11Parser.PlusPlus)) {
                return YIntegerUnaryExpression.Kind.PrefixIncrement.createExpression(baseExpression);
            }
            // '--' unaryExpression:
            if (C11ParserHelper.hasToken(ctx, C11Parser.MinusMinus)) {
                return YIntegerUnaryExpression.Kind.PrefixDecrement.createExpression(baseExpression);
            }
            // 'sizeof' unaryExpression:
            if (C11ParserHelper.hasToken(ctx, C11Parser.Sizeof)) {
                throw new YParserNotImplementedException(ctx);
            }
        }
        C11Parser.UnaryOperatorContext unaryOperatorContext = ctx.unaryOperator();
        C11Parser.CastExpressionContext castExpressionContext = ctx.castExpression();
        if (unaryOperatorContext != null) {
            if (castExpressionContext == null) {
                throw new YParserException(ctx, "Missing unary operand");
            }
            YUnaryOperatorKindTemp operator = visitUnaryOperator(unaryOperatorContext);
            YExpression operand = visitCastExpression(castExpressionContext);
            switch (operator) {
                case Ampersand:
                    return YPointerUnaryExpression.Kind.Reference.createExpression(operand);
                case Asterisk:
                    return YPointerUnaryExpression.Kind.Dereference.createExpression(operand);
                case Plus:
                    return operand; // TODO: check whether expression '+2' really equals to '2' in C
                case Minus:
                    return YIntegerUnaryExpression.Kind.IntegerNegation.createExpression(operand);
                case Tilde:
                    return YIntegerUnaryExpression.Kind.BitwiseComplement.createExpression(operand);
                case Exclamation:
                    return YLogicalUnaryExpression.Kind.Negation.createExpression(operand);
                default:
                    throw new YParserNotImplementedException(ctx, "Unsupported unary operator: " + operator.name());
            }
        }
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * unaryOperator
     * :   '&' | '*' | '+' | '-' | '~' | '!'
     * ;
     */
    @Override
    public YUnaryOperatorKindTemp visitUnaryOperator(C11Parser.UnaryOperatorContext ctx) {
        assert ctx.getChildCount() == 1: ctx.getChildCount();
        String tokenText = ctx.getChild(0).getText();
        YUnaryOperatorKindTemp operator = YUnaryOperatorKindTemp.tryParse(tokenText);
        if (operator == null) {
            throw new YParserException(ctx, "Could not parse unary operator " + StringUtils.wrap(tokenText));
        }
        return operator;
    }

    /**
     * castExpression
     * :   unaryExpression
     * |   '(' typeName ')' castExpression
     * ;
     */
    @Override
    public YExpression visitCastExpression(C11Parser.CastExpressionContext ctx) {
        C11Parser.UnaryExpressionContext unaryExpressionContext = ctx.unaryExpression();
        if (unaryExpressionContext != null) {
            return visitUnaryExpression(unaryExpressionContext);
        }
        // TODO: type cast!
        C11Parser.TypeNameContext typeNameContext = ctx.typeName();
        C11Parser.CastExpressionContext castExpressionContext = ctx.castExpression();
        throw new YParserNotImplementedException(ctx, "Type cast is not implemented yet");
    }

    /**
     * multiplicativeExpression
     * :   castExpression
     * |   multiplicativeExpression '*' castExpression
     * |   multiplicativeExpression '/' castExpression
     * |   multiplicativeExpression '%' castExpression
     * ;
     */
    @Override
    public YExpression visitMultiplicativeExpression(C11Parser.MultiplicativeExpressionContext ctx) {
        C11Parser.CastExpressionContext castExpressionContext = ctx.castExpression();
        C11Parser.MultiplicativeExpressionContext multiplicativeExpressionContext = ctx.multiplicativeExpression();
        if (castExpressionContext != null) {
            // todo: others
            return visitCastExpression(castExpressionContext);
        }
        throw new YParserUnintendedStateException(ctx);
    }

    /**
     * additiveExpression
     * :   multiplicativeExpression
     * |   additiveExpression '+' multiplicativeExpression
     * |   additiveExpression '-' multiplicativeExpression
     * ;
     */
    @Override
    public YExpression visitAdditiveExpression(C11Parser.AdditiveExpressionContext ctx) {
        C11Parser.MultiplicativeExpressionContext multiplicativeExpressionContext = ctx.multiplicativeExpression();
        C11Parser.AdditiveExpressionContext additiveExpressionContext = ctx.additiveExpression();
        if (multiplicativeExpressionContext != null) {
            // todo: others
            return visitMultiplicativeExpression(multiplicativeExpressionContext);
        }
        throw new YParserUnintendedStateException(ctx);
    }

    /**
     * shiftExpression
     * :   additiveExpression
     * |   shiftExpression '<<' additiveExpression
     * |   shiftExpression '>>' additiveExpression
     * ;
     */
    @Override
    public YExpression visitShiftExpression(C11Parser.ShiftExpressionContext ctx) {
        C11Parser.AdditiveExpressionContext additiveExpressionContext = ctx.additiveExpression();
        C11Parser.ShiftExpressionContext shiftExpressionContext = ctx.shiftExpression();
        if (additiveExpressionContext != null) {
            // todo: others
            return visitAdditiveExpression(additiveExpressionContext);
        }
        throw new YParserUnintendedStateException(ctx);
    }

    /**
     * relationalExpression
     * :   shiftExpression
     * |   relationalExpression '<' shiftExpression
     * |   relationalExpression '>' shiftExpression
     * |   relationalExpression '<=' shiftExpression
     * |   relationalExpression '>=' shiftExpression
     * ;
     */
    @Override
    public YExpression visitRelationalExpression(C11Parser.RelationalExpressionContext ctx) {
        C11Parser.RelationalExpressionContext relationalExpressionContext = ctx.relationalExpression();
        C11Parser.ShiftExpressionContext shiftExpressionContext = ctx.shiftExpression();
        boolean isLess = C11ParserHelper.hasToken(ctx, C11Parser.Less);
        boolean isGreater = C11ParserHelper.hasToken(ctx, C11Parser.Greater);
        boolean isLessEqual = C11ParserHelper.hasToken(ctx, C11Parser.LessEqual);
        boolean isGreaterEqual = C11ParserHelper.hasToken(ctx, C11Parser.GreaterEqual);
        if (isLess || isLessEqual || isGreater || isGreaterEqual) {
            if (relationalExpressionContext == null) {
                throw new YParserException(ctx, "Missing left part of inequality");
            }
            if (shiftExpressionContext == null) {
                throw new YParserException(ctx, "Missing right part of inequality");
            }
            YExpression leftPart = visitRelationalExpression(relationalExpressionContext);
            YExpression rightPart = visitShiftExpression(shiftExpressionContext);
            YBinaryExpression.Kind operator =
                    isLess ? YRelativeBinaryExpression.Kind.Less :
                            (isLessEqual ? YRelativeBinaryExpression.Kind.LessOrEquals :
                                    (isGreater ? YRelativeBinaryExpression.Kind.Greater :
                                            YRelativeBinaryExpression.Kind.GreaterOrEquals));
            return operator.createExpression(leftPart, rightPart);
        }
        if (shiftExpressionContext != null) {
            return visitShiftExpression(shiftExpressionContext);
        }
        throw new YParserUnintendedStateException(ctx);
    }

    /**
     * equalityExpression
     * :   relationalExpression
     * |   equalityExpression '==' relationalExpression
     * |   equalityExpression '!=' relationalExpression
     * ;
     */
    @Override
    public YExpression visitEqualityExpression(C11Parser.EqualityExpressionContext ctx) {
        C11Parser.RelationalExpressionContext relationalExpressionContext = ctx.relationalExpression();
        C11Parser.EqualityExpressionContext equalityExpressionContext = ctx.equalityExpression();
        boolean isEquality = C11ParserHelper.hasToken(ctx, C11Parser.Equal);
        boolean isInequality = C11ParserHelper.hasToken(ctx, C11Parser.NotEqual);
        if (isEquality || isInequality) {
            if (equalityExpressionContext == null) {
                throw new YParserException(ctx, "Missing left part of equality");
            }
            if (relationalExpressionContext == null) {
                throw new YParserException(ctx, "Missing right part of equality");
            }
            YExpression left = visitEqualityExpression(equalityExpressionContext);
            YExpression right = visitRelationalExpression(relationalExpressionContext);
            YRelativeBinaryExpression.Kind operator = isEquality
                    ? YRelativeBinaryExpression.Kind.Equals
                    : YRelativeBinaryExpression.Kind.NotEquals;
            return operator.createExpression(left, right);
        }
        if (relationalExpressionContext != null) {
            return visitRelationalExpression(relationalExpressionContext);
        }
        throw new YParserUnintendedStateException(ctx);
    }

    /**
     * andExpression
     * :   equalityExpression
     * |   andExpression '&' equalityExpression
     * ;
     */
    @Override
    public YExpression visitAndExpression(C11Parser.AndExpressionContext ctx) {
        C11Parser.EqualityExpressionContext equalityExpressionContext = ctx.equalityExpression();
        C11Parser.AndExpressionContext andExpressionContext = ctx.andExpression();
        if (equalityExpressionContext != null) {
            // todo: others
            return visitEqualityExpression(equalityExpressionContext);
        }
        throw new YParserUnintendedStateException(ctx);
    }

    /**
     * exclusiveOrExpression
     * :   andExpression
     * |   exclusiveOrExpression '^' andExpression
     * ;
     */
    @Override
    public YExpression visitExclusiveOrExpression(C11Parser.ExclusiveOrExpressionContext ctx) {
        C11Parser.AndExpressionContext andExpressionContext = ctx.andExpression();
        C11Parser.ExclusiveOrExpressionContext exclusiveOrExpressionContext = ctx.exclusiveOrExpression();
        if (andExpressionContext != null) {
            // todo: others
            return visitAndExpression(andExpressionContext);
        }
        throw new YParserUnintendedStateException(ctx);
    }

    /**
     * inclusiveOrExpression
     * :   exclusiveOrExpression
     * |   inclusiveOrExpression '|' exclusiveOrExpression
     * ;
     */
    @Override
    public YExpression visitInclusiveOrExpression(C11Parser.InclusiveOrExpressionContext ctx) {
        C11Parser.ExclusiveOrExpressionContext exclusiveOrExpressionContext = ctx.exclusiveOrExpression();
        C11Parser.InclusiveOrExpressionContext inclusiveOrExpressionContext = ctx.inclusiveOrExpression();
        if (exclusiveOrExpressionContext != null) {
            // todo: others
            return visitExclusiveOrExpression(exclusiveOrExpressionContext);
        }
        throw new YParserUnintendedStateException(ctx);
    }

    /**
     * logicalAndExpression
     * :   inclusiveOrExpression
     * |   logicalAndExpression '&&' inclusiveOrExpression
     * ;
     */
    @Override
    public YExpression visitLogicalAndExpression(C11Parser.LogicalAndExpressionContext ctx) {
        C11Parser.InclusiveOrExpressionContext inclusiveOrExpressionContext = ctx.inclusiveOrExpression();
        C11Parser.LogicalAndExpressionContext logicalAndExpressionContext = ctx.logicalAndExpression();
        if (inclusiveOrExpressionContext != null) {
            // todo: others
            return visitInclusiveOrExpression(inclusiveOrExpressionContext);
        }
        throw new YParserUnintendedStateException(ctx);
    }

    /**
     * logicalOrExpression
     * :   logicalAndExpression
     * |   logicalOrExpression '||' logicalAndExpression
     * ;
     */
    @Override
    public YExpression visitLogicalOrExpression(C11Parser.LogicalOrExpressionContext ctx) {
        C11Parser.LogicalAndExpressionContext logicalAndExpressionContext = ctx.logicalAndExpression();
        C11Parser.LogicalOrExpressionContext logicalOrExpressionContext = ctx.logicalOrExpression();
        if (logicalAndExpressionContext != null) {
            // todo: others
            return visitLogicalAndExpression(logicalAndExpressionContext);
        }
        throw new YParserUnintendedStateException(ctx);
    }

    /**
     * conditionalExpression
     * :   logicalOrExpression ('?' expression ':' conditionalExpression)?
     * ;
     */
    @Override
    public YExpression visitConditionalExpression(C11Parser.ConditionalExpressionContext ctx) {
        C11Parser.LogicalOrExpressionContext logicalOrExpressionContext = ctx.logicalOrExpression();
        if (logicalOrExpressionContext != null) {
            return visitLogicalOrExpression(logicalOrExpressionContext);
            // todo: others
        }
        throw new YParserUnintendedStateException(ctx);
    }

    /**
     * assignmentExpression
     * :   conditionalExpression
     * |   unaryExpression assignmentOperator assignmentExpression
     * ;
     */
    @Override
    public YExpression visitAssignmentExpression(C11Parser.AssignmentExpressionContext ctx) {
        C11Parser.ConditionalExpressionContext conditionalExpressionContext = ctx.conditionalExpression();
        C11Parser.UnaryExpressionContext unaryExpressionContext = ctx.unaryExpression();
        C11Parser.AssignmentOperatorContext assignmentOperatorContext = ctx.assignmentOperator();
        C11Parser.AssignmentExpressionContext assignmentExpressionContext = ctx.assignmentExpression();
        if (conditionalExpressionContext != null) {
            return visitConditionalExpression(conditionalExpressionContext);
        }
        if (assignmentOperatorContext != null) {
            // TODO: process non-trivial assignments
            //YEntity operator = visitAssignmentOperator(assignmentOperatorContext);
            if (unaryExpressionContext == null) {
                throw new YParserException(ctx, "Missing assignee expression");
            }
            if (assignmentExpressionContext == null) {
                throw new YParserException(ctx, "Missing assigner expression");
            }
            YExpression assigneeEntity = visitUnaryExpression(unaryExpressionContext);
            if (!(assigneeEntity instanceof YAssignee)) {
                throw new YParserException(ctx, "Invalid assignee " + StringUtils.wrap(assigneeEntity.toString())
                        + " of type " + assigneeEntity.getClass().getSimpleName());
            }
            YAssignee assignee = (YAssignee) assigneeEntity;
            YExpression expression = visitAssignmentExpression(assignmentExpressionContext);
            return new YAssignmentExpression(assignee, expression);

        }
        throw new YParserUnintendedStateException(ctx);
    }

    /**
     * assignmentOperator
     * :   '=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '&=' | '^=' | '|='
     * ;
     */
    @Override
    public YEntity visitAssignmentOperator(C11Parser.AssignmentOperatorContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * expression
     * :   assignmentExpression
     * |   expression ',' assignmentExpression
     * ;
     */
    @Override
    public YExpression visitExpression(C11Parser.ExpressionContext ctx) {
        C11Parser.ExpressionContext expressionContext = ctx.expression();
        if (expressionContext != null) {
            // evaluate, discard return value
            throw new YParserNotImplementedException(ctx, "Comma-operator is not supported yet");
        }
        C11Parser.AssignmentExpressionContext assignmentExpressionContext = ctx.assignmentExpression();
        if (assignmentExpressionContext != null) {
            return visitAssignmentExpression(assignmentExpressionContext);
        }
        throw new YParserUnintendedStateException(ctx);
    }

    /**
     * constantExpression
     * :   conditionalExpression
     * ;
     */
    @Override
    public YEntity visitConstantExpression(C11Parser.ConstantExpressionContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * declaration
     * :   declarationSpecifiers initDeclaratorList ';'
     * |   staticAssertDeclaration
     * ;
     */
    @Override
    public YStatement visitDeclaration(C11Parser.DeclarationContext ctx) {
        C11Parser.DeclarationSpecifiersContext declarationSpecifiersContext = ctx.declarationSpecifiers();
        if (declarationSpecifiersContext != null) {
            //A declaration other than a static_assert declaration shall declare at least a declarator
            //(other than the parameters of a function or the members of a structure or union), a tag, or
            //the members of an enumeration.
            YCompoundStatementBuilder builder = new YCompoundStatementBuilder();

            YType type = new YMockType();
            C11Parser.InitDeclaratorListContext initDeclaratorListContext = ctx.initDeclaratorList();
            if (initDeclaratorListContext == null) {
                throw new YParserNotImplementedException(ctx, "Missing variable declarator");
            }
            YExpressionListBuilder declarationListBuilder = visitInitDeclaratorList(initDeclaratorListContext);
            ImmutableList<YExpression> declarationList = declarationListBuilder.build();
            for (YExpression declarationExpression : declarationList) {
                if (declarationExpression instanceof YVariableRef) {
                    YVariableRef variable = (YVariableRef) declarationExpression;
                    builder.addStatement(new YVariableDeclarationStatement(type, variable));
                }
                else if (declarationExpression instanceof YVariableAssignmentExpression) {
                    YVariableAssignmentExpression assignment = (YVariableAssignmentExpression) declarationExpression;
                    YVariableRef variable = assignment.getAssignee();
                    YExpression value = assignment.getExpression();
                    builder.addStatement(new YVariableDeclarationStatement(type, variable));
                    builder.addLinearStatement(new YVariableAssignmentExpression(variable, value));
                }
                else { //if (declarationExpression instanceof )
                    throw new YParserNotImplementedException(ctx, "Not supported variable declaration " +
                            StringUtils.wrap(declarationExpression.toString()) + " of type " +
                            declarationExpression.getClass().getSimpleName());
                }
            }
            return builder.build();
        }
        C11Parser.StaticAssertDeclarationContext staticAssertDeclarationContext = ctx.staticAssertDeclaration();
        if (staticAssertDeclarationContext != null) {
            throw new YParserNotImplementedException(ctx);
        }
        throw new YParserException(ctx);
    }

    /**
     * declarationSpecifiers
     * :   declarationSpecifier+
     * ;
     */
    @Override
    public YEntity visitDeclarationSpecifiers(C11Parser.DeclarationSpecifiersContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * declarationSpecifiers2
     * :   declarationSpecifier+
     * ;
     */
    @Override
    public YEntity visitDeclarationSpecifiers2(C11Parser.DeclarationSpecifiers2Context ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * declarationSpecifier
     * :   storageClassSpecifier
     * |   typeSpecifier
     * |   typeQualifier
     * |   functionSpecifier
     * |   alignmentSpecifier
     * ;
     */
    @Override
    public YEntity visitDeclarationSpecifier(C11Parser.DeclarationSpecifierContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * initDeclaratorList
     * :   initDeclarator
     * |   initDeclaratorList ',' initDeclarator
     * ;
     */
    @Override
    public YExpressionListBuilder visitInitDeclaratorList(C11Parser.InitDeclaratorListContext ctx) {
        YExpressionListBuilder result = new YExpressionListBuilder();
        C11Parser.InitDeclaratorListContext recursiveListContext = ctx.initDeclaratorList();
        if (recursiveListContext != null) {
            result.addAll(visitInitDeclaratorList(recursiveListContext));
        }
        C11Parser.InitDeclaratorContext initDeclaratorContext = ctx.initDeclarator();
        if (initDeclaratorContext != null) {
            result.add(visitInitDeclarator(initDeclaratorContext));
        }
        return result;
    }

    /**
     * initDeclarator
     * :   declarator
     * |   declarator '=' initializer
     * ;
     */
    @Override
    public YExpression visitInitDeclarator(C11Parser.InitDeclaratorContext ctx) {
        C11Parser.DeclaratorContext declaratorContext = ctx.declarator();
        if (declaratorContext != null) {
            YEntity declaratorEntity = visitDeclarator(declaratorContext);
            if (!(declaratorEntity instanceof YExpression)) {
                throw new YParserException(ctx, "Initializer may be only an expression");
            }
            YExpression declarator = (YExpression) declaratorEntity;
            C11Parser.InitializerContext initializerContext = ctx.initializer();
            if (initializerContext == null) {
                return declarator;
            }
            YExpression initializer = visitInitializer(initializerContext);
            if (declarator instanceof YVariableRef) {
                return new YVariableAssignmentExpression((YVariableRef) declarator, initializer);
            }
            //if (declarator instanceof ...)
            throw new YParserNotImplementedException(ctx);
        }
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * storageClassSpecifier
     * :   'typedef'
     * |   'extern'
     * |   'static'
     * |   '_Thread_local'
     * |   'auto'
     * |   'register'
     * ;
     */
    @Override
    public YEntity visitStorageClassSpecifier(C11Parser.StorageClassSpecifierContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * typeSpecifier
     * :   ('void'
     * |   'char'
     * |   'short'
     * |   'int'
     * |   'long'
     * |   'float'
     * |   'double'
     * |   'signed'
     * |   'unsigned'
     * |   '_Bool'
     * |   '_Complex')
     * |   atomicTypeSpecifier
     * |   structOrUnionSpecifier
     * |   enumSpecifier
     * |   typedefName
     * ;
     */
    @Override
    public YEntity visitTypeSpecifier(C11Parser.TypeSpecifierContext ctx) {
        // atomicTypeSpecifier:
        C11Parser.AtomicTypeSpecifierContext atomicTypeSpecifierContext = ctx.atomicTypeSpecifier();
        if (atomicTypeSpecifierContext != null) {
            throw new YParserNotImplementedException(ctx);
        }
        // structOrUnionSpecifier:
        C11Parser.StructOrUnionSpecifierContext structOrUnionSpecifierContext = ctx.structOrUnionSpecifier();
        if (structOrUnionSpecifierContext != null) {
            throw new YParserNotImplementedException(ctx);
        }
        // enumSpecifier:
        C11Parser.EnumSpecifierContext enumSpecifierContext = ctx.enumSpecifier();
        if (enumSpecifierContext != null) {
            throw new YParserNotImplementedException(ctx);
        }
        // typedefName
        C11Parser.TypedefNameContext typedefNameContext = ctx.typedefName();
        if (typedefNameContext != null) {
            throw new YParserNotImplementedException(ctx);
        }
        // Token:
        //throw new YParserNotImplementedException(ctx);
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * structOrUnionSpecifier
     * :   structOrUnion Identifier? '{' structDeclarationList '}'
     * |   structOrUnion Identifier
     * ;
     */
    @Override
    public YEntity visitStructOrUnionSpecifier(C11Parser.StructOrUnionSpecifierContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * structOrUnion
     * :   'struct'
     * |   'union'
     * ;
     */
    @Override
    public YEntity visitStructOrUnion(C11Parser.StructOrUnionContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * structDeclarationList
     * :   structDeclaration
     * |   structDeclarationList structDeclaration
     * ;
     */
    @Override
    public YEntity visitStructDeclarationList(C11Parser.StructDeclarationListContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * structDeclaration
     * :   specifierQualifierList structDeclaratorList? ';'
     * |   staticAssertDeclaration
     * ;
     */
    @Override
    public YEntity visitStructDeclaration(C11Parser.StructDeclarationContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * specifierQualifierList
     * :   typeSpecifier specifierQualifierList?
     * |   typeQualifier specifierQualifierList?
     * ;
     */
    @Override
    public YEntity visitSpecifierQualifierList(C11Parser.SpecifierQualifierListContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * structDeclaratorList
     * :   structDeclarator
     * |   structDeclaratorList ',' structDeclarator
     * ;
     */
    @Override
    public YEntity visitStructDeclaratorList(C11Parser.StructDeclaratorListContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * structDeclarator
     * :   declarator
     * |   declarator? ':' constantExpression
     * ;
     */
    @Override
    public YEntity visitStructDeclarator(C11Parser.StructDeclaratorContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * enumSpecifier
     * :   'enum' Identifier? '{' enumeratorList '}'
     * |   'enum' Identifier? '{' enumeratorList ',' '}'
     * |   'enum' Identifier
     * ;
     */
    @Override
    public YEntity visitEnumSpecifier(C11Parser.EnumSpecifierContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * enumeratorList
     * :   enumerator
     * |   enumeratorList ',' enumerator
     * ;
     */
    @Override
    public YEntity visitEnumeratorList(C11Parser.EnumeratorListContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * enumerator
     * :   enumerationConstant
     * |   enumerationConstant '=' constantExpression
     * ;
     */
    @Override
    public YEntity visitEnumerator(C11Parser.EnumeratorContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * enumerationConstant
     * :   Identifier
     * ;
     */
    @Override
    public YEntity visitEnumerationConstant(C11Parser.EnumerationConstantContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * atomicTypeSpecifier
     * :   '_Atomic' '(' typeName ')'
     * ;
     */
    @Override
    public YEntity visitAtomicTypeSpecifier(C11Parser.AtomicTypeSpecifierContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * typeQualifier
     * :   'const'
     * |   'restrict'
     * |   'volatile'
     * |   '_Atomic'
     * ;
     */
    @Override
    public YEntity visitTypeQualifier(C11Parser.TypeQualifierContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * functionSpecifier
     * :   'inline'
     * |   '_Noreturn'
     * ;
     */
    @Override
    public YEntity visitFunctionSpecifier(C11Parser.FunctionSpecifierContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * alignmentSpecifier
     * :   '_Alignas' '(' typeName ')'
     * |   '_Alignas' '(' constantExpression ')'
     * ;
     */
    @Override
    public YEntity visitAlignmentSpecifier(C11Parser.AlignmentSpecifierContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * declarator
     * :   pointer? directDeclarator
     * ;
     */
    @Override
    public YEntity visitDeclarator(C11Parser.DeclaratorContext ctx) {
        // TODO: process pointer
        C11Parser.DirectDeclaratorContext directDeclaratorContext = ctx.directDeclarator();
        if (directDeclaratorContext != null) {
            return visitDirectDeclarator(directDeclaratorContext);
        }
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * directDeclarator
     * :   Identifier
     * |   '(' declarator ')'
     * |   directDeclarator '[' typeQualifierList? assignmentExpression? ']'
     * |   directDeclarator '[' 'static' typeQualifierList? assignmentExpression ']'
     * |   directDeclarator '[' typeQualifierList 'static' assignmentExpression ']'
     * |   directDeclarator '[' typeQualifierList? '*' ']'
     * |   directDeclarator '(' parameterTypeList ')'
     * |   directDeclarator '(' identifierList? ')'
     * ;
     */
    @Override
    public YEntity visitDirectDeclarator(C11Parser.DirectDeclaratorContext ctx) {
        TerminalNode identifier;
        C11Parser.DeclaratorContext declaratorContext;
        C11Parser.DirectDeclaratorContext directDeclaratorContext;
        C11Parser.TypeQualifierListContext typeQualifierListContext;
        C11Parser.AssignmentExpressionContext assignmentExpressionContext;
        C11Parser.ParameterTypeListContext parameterTypeListContext;
        C11Parser.IdentifierListContext identifierListContext;

        if ((identifier = ctx.Identifier()) != null) {
            return new YVariableRef(identifier.getText());
        }
        boolean hasParentheses = C11ParserHelper.hasParentheses(ctx);
        if (hasParentheses) {
            if ((declaratorContext = ctx.declarator()) != null) {
                return visitDeclarator(declaratorContext);
            }
        }
        if ((directDeclaratorContext = ctx.directDeclarator()) != null) {
            YEntity directDeclarator = visitDirectDeclarator(directDeclaratorContext);
            // '['
            // ...
            if (hasParentheses) {
                if ((parameterTypeListContext = ctx.parameterTypeList()) != null) {
                    YParameterListBuilder parameterTypeList = visitParameterTypeList(parameterTypeListContext);
                    YParameter[] parameters = parameterTypeList.build();
                    if (directDeclarator instanceof YVariableRef) {
                        YVariableRef methodVariable = (YVariableRef) directDeclarator;
                        // todo: parse return type
                        return new YMethodSignature(methodVariable.getName(), new YMockType(), parameters);
                    }
                    throw new YParserException(ctx, "For now, only simple name-based method definitions are supported");
                }
                throw new YParserException(ctx);
            }
        }
        // todo: some others
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * pointer
     * :   ('*' | '&') typeQualifierList?
     * |   ('*' | '&') typeQualifierList? pointer
     * ;
     */
    @Override
    public YEntity visitPointer(C11Parser.PointerContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * typeQualifierList
     * :   typeQualifier
     * |   typeQualifierList typeQualifier
     * ;
     */
    @Override
    public YEntity visitTypeQualifierList(C11Parser.TypeQualifierListContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * parameterTypeList
     * :   parameterList
     * |   parameterList ',' '...'
     * ;
     */
    @Override
    public YParameterListBuilder visitParameterTypeList(C11Parser.ParameterTypeListContext ctx) {
        C11Parser.ParameterListContext parameterListContext = ctx.parameterList();
        if (parameterListContext != null) {
            return visitParameterList(parameterListContext);
        }
        throw new YParserException(ctx);
    }

    /**
     * parameterList
     * :   parameterDeclaration
     * |   parameterList ',' parameterDeclaration
     * ;
     */
    @Override
    public YParameterListBuilder visitParameterList(C11Parser.ParameterListContext ctx) {
        C11Parser.ParameterDeclarationContext parameterDeclarationContext;
        C11Parser.ParameterListContext parameterListContext;
        YParameterListBuilder builder = new YParameterListBuilder();
        if ((parameterDeclarationContext = ctx.parameterDeclaration()) != null) {
            builder.add(visitParameterDeclaration(parameterDeclarationContext));
        }
        if ((parameterListContext = ctx.parameterList()) != null) {
            builder.addAll(visitParameterList(parameterListContext));
        }
        return builder;
    }

    /**
     * parameterDeclaration
     * :   declarationSpecifiers declarator
     * |   declarationSpecifiers2 abstractDeclarator?
     * ;
     */
    @Override
    public YParameter visitParameterDeclaration(C11Parser.ParameterDeclarationContext ctx) {
        C11Parser.DeclarationSpecifiersContext declarationSpecifiersContext;
        C11Parser.DeclarationSpecifiers2Context declarationSpecifiers2Context;
        C11Parser.DeclaratorContext declaratorContext;
        C11Parser.AbstractDeclaratorContext abstractDeclaratorContext;
        if ((declaratorContext = ctx.declarator()) != null) {
            if ((declarationSpecifiersContext = ctx.declarationSpecifiers()) != null) {
                // todo: parse type in specifiers
                YEntity declarator = visitDeclarator(declaratorContext);
                if (!(declarator instanceof YVariableRef)) {
                    throw new YParserException(ctx, "Only identifier is allowed as function parameter name");
                }
                YVariableRef parameterEntity = (YVariableRef) declarator;
                return new YParameter(new YMockType(), parameterEntity.getName());
            }
            throw new YParserException(ctx, "Could not find parameter type specifiers");
        }

        throw new YParserNotImplementedException(ctx);
    }

    /**
     * identifierList
     * :   Identifier
     * |   identifierList ',' Identifier
     * ;
     */
    @Override
    public YEntity visitIdentifierList(C11Parser.IdentifierListContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * typeName
     * :   specifierQualifierList abstractDeclarator?
     * ;
     */
    @Override
    public YEntity visitTypeName(C11Parser.TypeNameContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * abstractDeclarator
     * :   pointer
     * |   pointer? directAbstractDeclarator
     * ;
     */
    @Override
    public YEntity visitAbstractDeclarator(C11Parser.AbstractDeclaratorContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * directAbstractDeclarator
     * :   '(' abstractDeclarator ')'
     * |   '[' typeQualifierList? assignmentExpression? ']'
     * |   '[' 'static' typeQualifierList? assignmentExpression ']'
     * |   '[' typeQualifierList 'static' assignmentExpression ']'
     * |   '[' '*' ']'
     * |   '(' parameterTypeList? ')'
     * |   directAbstractDeclarator '[' typeQualifierList? assignmentExpression? ']'
     * |   directAbstractDeclarator '[' 'static' typeQualifierList? assignmentExpression ']'
     * |   directAbstractDeclarator '[' typeQualifierList 'static' assignmentExpression ']'
     * |   directAbstractDeclarator '[' '*' ']'
     * |   directAbstractDeclarator '(' parameterTypeList? ')'
     * ;
     */
    @Override
    public YEntity visitDirectAbstractDeclarator(C11Parser.DirectAbstractDeclaratorContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * typedefName
     * :   Identifier
     * ;
     */
    @Override
    public YEntity visitTypedefName(C11Parser.TypedefNameContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * initializer
     * :   assignmentExpression
     * |   '{' initializerList '}'
     * |   '{' initializerList ',' '}'
     * ;
     */
    @Override
    public YExpression visitInitializer(C11Parser.InitializerContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * initializerList
     * :   designation? initializer
     * |   initializerList ',' designation? initializer
     * ;
     */
    @Override
    public YEntity visitInitializerList(C11Parser.InitializerListContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * designation
     * :   designatorList '='
     * ;
     */
    @Override
    public YEntity visitDesignation(C11Parser.DesignationContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * designatorList
     * :   designator
     * |   designatorList designator
     * ;
     */
    @Override
    public YEntity visitDesignatorList(C11Parser.DesignatorListContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * designator
     * :   '[' constantExpression ']'
     * |   '.' Identifier
     * ;
     */
    @Override
    public YEntity visitDesignator(C11Parser.DesignatorContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * staticAssertDeclaration
     * :   '_Static_assert' '(' constantExpression ',' StringLiteral+ ')' ';'
     * ;
     */
    @Override
    public YEntity visitStaticAssertDeclaration(C11Parser.StaticAssertDeclarationContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * statement
     * :   labeledStatement
     * |   compoundStatement
     * |   expressionStatement
     * |   selectionStatement
     * |   iterationStatement
     * |   jumpStatement
     * ;
     */
    @Override
    public YStatement visitStatement(C11Parser.StatementContext ctx) {
        // todo: switch context?
        C11Parser.LabeledStatementContext labeledStatementContext = ctx.labeledStatement();
        if (labeledStatementContext != null) {
            return visitLabeledStatement(labeledStatementContext);
        }
        C11Parser.CompoundStatementContext compoundStatementContext = ctx.compoundStatement();
        if (compoundStatementContext != null) {
            return visitCompoundStatement(compoundStatementContext);
        }
        C11Parser.ExpressionStatementContext expressionStatementContext = ctx.expressionStatement();
        if (expressionStatementContext != null) {
            return visitExpressionStatement(expressionStatementContext);
        }
        C11Parser.SelectionStatementContext selectionStatementContext = ctx.selectionStatement();
        if (selectionStatementContext != null) {
            return visitSelectionStatement(selectionStatementContext);
        }
        C11Parser.IterationStatementContext iterationStatementContext = ctx.iterationStatement();
        if (iterationStatementContext != null) {
            return visitIterationStatement(iterationStatementContext);
        }
        C11Parser.JumpStatementContext jumpStatementContext = ctx.jumpStatement();
        if (jumpStatementContext != null) {
            return visitJumpStatement(jumpStatementContext);
        }
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * labeledStatement
     * :   Identifier ':' statement
     * |   'case' constantExpression ':' statement
     * |   'default' ':' statement
     * ;
     */
    @Override
    public YStatement visitLabeledStatement(C11Parser.LabeledStatementContext ctx) {
        // TODO
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * compoundStatement
     * :   '{' blockItemList? '}'
     * ;
     */
    @Override
    public YCompoundStatement visitCompoundStatement(C11Parser.CompoundStatementContext ctx) {
        C11Parser.BlockItemListContext blockItemListContext = ctx.blockItemList();
        if (blockItemListContext != null) {
            YCompoundStatementBuilder builder = visitBlockItemList(blockItemListContext);
            builder.markHasBraces();
            return builder.build();
        }
        return new YCompoundStatement(true);
    }

    /**
     * blockItemList
     * :   blockItem
     * |   blockItemList blockItem
     * ;
     */
    @Override
    public YCompoundStatementBuilder visitBlockItemList(C11Parser.BlockItemListContext ctx) {
        YCompoundStatementBuilder builder = new YCompoundStatementBuilder();
        C11Parser.BlockItemListContext blockItemListContext = ctx.blockItemList();
        if (blockItemListContext != null) {
            builder.addFrom(visitBlockItemList(blockItemListContext));
        }
        C11Parser.BlockItemContext blockItemContext = ctx.blockItem();
        if (blockItemContext != null) {
            builder.addStatement(visitBlockItem(blockItemContext));
        }
        return builder;
    }

    /**
     * blockItem
     * :   declaration
     * |   statement
     * ;
     */
    @Override
    public YStatement visitBlockItem(C11Parser.BlockItemContext ctx) {
        C11Parser.DeclarationContext declarationContext = ctx.declaration();
        if (declarationContext != null) {
            return visitDeclaration(declarationContext);
        }
        C11Parser.StatementContext statementContext = ctx.statement();
        if (statementContext != null) {
            return visitStatement(statementContext);
        }
        throw new YParserException(ctx);
    }

    /**
     * expressionStatement
     * :   expression? ';'
     * ;
     */
    @Override
    public YLinearStatement visitExpressionStatement(C11Parser.ExpressionStatementContext ctx) {
        C11Parser.ExpressionContext expressionContext = ctx.expression();
        return expressionContext != null
                ? new YLinearStatement(visitExpression(expressionContext))
                : YLinearStatement.createEmptyStatement();
    }

    /**
     * selectionStatement
     * :   'if' '(' expression ')' statement ('else' statement)?
     * |   'switch' '(' expression ')' statement
     * ;
     */
    @Override
    public YStatement visitSelectionStatement(C11Parser.SelectionStatementContext ctx) {
        C11Parser.ExpressionContext expressionContext = ctx.expression();
        C11Parser.StatementContext statement1Context = ctx.statement(0);
        C11Parser.StatementContext statement2Context = ctx.statement(1);

        // todo: rewrite linearly
        if (expressionContext != null) {
            YExpression expression = visitExpression(expressionContext);
            if (statement1Context != null) {
                YStatement statement1 = visitStatement(statement1Context);
                if (C11ParserHelper.hasToken(ctx, C11Parser.If)) {
                    YStatement statement2 = null;
                    if (C11ParserHelper.hasToken(ctx, C11Parser.Else)) {
                        if (statement2Context == null) {
                            throw new YParserException(ctx, "Empty 'else' statement");
                        }
                        statement2 = visitStatement(statement2Context);
                    }
                    return new YBranchingStatement(expression, statement1, statement2);
                }
                if (C11ParserHelper.hasToken(ctx, C11Parser.Switch)) {
                    throw new YParserNotImplementedException(ctx, "Switch is not implemented yet");
                }
            }
        }
        throw new YParserUnintendedStateException(ctx);
    }

    /**
     * iterationStatement
     * :   While '(' expression ')' statement
     * |   Do statement While '(' expression ')' ';'
     * |   For '(' forCondition ')' statement
     * ;
     */
    @Override
    public YStatement visitIterationStatement(C11Parser.IterationStatementContext ctx) {
        C11Parser.ExpressionContext expressionContext = ctx.expression();
        C11Parser.StatementContext statementContext = ctx.statement();
        C11Parser.ForConditionContext forConditionContext = ctx.forCondition();

        YStatement statement = statementContext != null
                ? visitStatement(statementContext)
                : YLinearStatement.createEmptyStatement();

        boolean isDoWhile = C11ParserHelper.hasToken(ctx, C11Parser.Do);
        boolean isWhile = !isDoWhile && C11ParserHelper.hasToken(ctx, C11Parser.While);
        boolean isFor = C11ParserHelper.hasToken(ctx, C11Parser.For);

        if (isDoWhile || isWhile) {
            if (expressionContext == null) {
                throw new YParserException(ctx, "Missing loop expression");
            }
            YExpression expression = visitExpression(expressionContext);
            YStatement loopStatement = new YWhileLoopStatement(expression, statement);
            if (isDoWhile) {
                YCompoundStatementBuilder builder = new YCompoundStatementBuilder();
                builder.addStatement(statement); //first iteration
                builder.addStatement(loopStatement);
                return builder.build();
            }
            return loopStatement;
        }
        if (isFor) {
            throw new YParserNotImplementedException(ctx);
        }
        throw new YParserException(ctx);
    }

    /**
     * forCondition
     * :   forDeclaration ';' forExpression? ';' forExpression?
     * |   expression? ';' forExpression? ';' forExpression?
     * ;
     */
    @Override
    public YEntity visitForDeclaration(C11Parser.ForDeclarationContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * forDeclaration
     * :   declarationSpecifiers initDeclaratorList
     * | 	declarationSpecifiers
     * ;
     */
    @Override
    public YEntity visitForCondition(C11Parser.ForConditionContext ctx) {
        throw new YParserNotImplementedException(ctx);
        //throw new YParserNotImplementedException(ctx);
    }

    /**
     * forExpression
     * :   assignmentExpression
     * |   forExpression ',' assignmentExpression
     * ;
     */
    @Override
    public YEntity visitForExpression(C11Parser.ForExpressionContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * jumpStatement
     * :   'goto' Identifier ';'
     * |   'continue' ';'
     * |   'break' ';'
     * |   'return' expression? ';'
     * ;
     */
    @Override
    public YJumpStatement visitJumpStatement(C11Parser.JumpStatementContext ctx) {
        if (C11ParserHelper.hasToken(ctx, C11Parser.Goto)) {
            TerminalNode identifier = ctx.Identifier();
            if (identifier == null) {
                throw new YParserException(ctx, "Missing goto label in jump statement");
            }
            YJumpLabel gotoLabel = new YJumpLabel(identifier.getText());
            return YJumpStatement.Kind.Goto.createJumpStatement(gotoLabel);
        }
        if (C11ParserHelper.hasToken(ctx, C11Parser.Continue)) {
            return YJumpStatement.Kind.Continue.createJumpStatement();
        }
        if (C11ParserHelper.hasToken(ctx, C11Parser.Break)) {
            return YJumpStatement.Kind.Break.createJumpStatement();
        }
        if (C11ParserHelper.hasToken(ctx, C11Parser.Return)) {
            C11Parser.ExpressionContext expressionContext = ctx.expression();
            if (expressionContext != null) {
                // TODO: process return value
                throw new NotImplementedException("For now, return values are not supported");
            }
            return YJumpStatement.Kind.Return.createJumpStatement();
        }
        throw new YParserNotImplementedException(ctx);
    }

    /**
     * compilationUnit
     * :   translationUnit? EOF
     * ;
     */
    @Override
    public YEntityListBuilder visitCompilationUnit(C11Parser.CompilationUnitContext ctx) {
        C11Parser.TranslationUnitContext translationUnitContext = ctx.translationUnit();
        if (translationUnitContext == null) {
            throw new YParserException(ctx, "Empty input");
        }
        return visitTranslationUnit(translationUnitContext);
    }

    /**
     * translationUnit
     * :   externalDeclaration
     * |   translationUnit externalDeclaration
     * ;
     */
    @Override
    public YEntityListBuilder visitTranslationUnit(C11Parser.TranslationUnitContext ctx) {
        YEntityListBuilder result = new YEntityListBuilder();
        C11Parser.TranslationUnitContext translationUnitContext = ctx.translationUnit();
        if (translationUnitContext != null) {
            result.addAll(visitTranslationUnit(translationUnitContext));
        }
        C11Parser.ExternalDeclarationContext externalDeclarationContext = ctx.externalDeclaration();
        if (externalDeclarationContext != null) {
            result.add(visitExternalDeclaration(externalDeclarationContext));
        }
        return result;
    }

    /**
     * externalDeclaration
     * :   functionDefinition
     * |   declaration
     * |   ';'
     * ;
     */
    @Override
    public YEntity visitExternalDeclaration(C11Parser.ExternalDeclarationContext ctx) {
        C11Parser.FunctionDefinitionContext functionDefinitionContext = ctx.functionDefinition();
        if (functionDefinitionContext != null) {
            return visitFunctionDefinition(functionDefinitionContext);
        }
        C11Parser.DeclarationContext declarationContext = ctx.declaration();
        if (declarationContext != null) {
            return visitDeclaration(declarationContext);
        }
        return YLinearStatement.createEmptyStatement();
    }

    /**
     * functionDefinition
     * :   declarationSpecifiers? declarator declarationList? compoundStatement
     * ;
     */
    @Override
    public YFunctionDefinition visitFunctionDefinition(C11Parser.FunctionDefinitionContext ctx) {
        // TODO: process declaration specifiers
        // TODO: process declarator
        // TODO: process declarationList
        C11Parser.CompoundStatementContext compoundStatementContext = ctx.compoundStatement();
        if (compoundStatementContext == null) {
            throw new YParserException(ctx, "Missing function body");
        }
        YCompoundStatement body = visitCompoundStatement(compoundStatementContext);
        // TODO: parse and set signature
        return new YFunctionDefinition(body);
    }

    /**
     * declarationList
     * :   declaration
     * |   declarationList declaration
     * ;
     */
    @Override
    public YEntity visitDeclarationList(C11Parser.DeclarationListContext ctx) {
        throw new YParserNotImplementedException(ctx);
    }

}