package mousquetaires.languages.converters.toytree.c11;

import mousquetaires.languages.converters.toytree.c11.temporaries.YExpressionReversableList;
import mousquetaires.languages.parsers.C11Parser;
import mousquetaires.languages.parsers.C11Visitor;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.YSyntaxTreeBuilder;
import mousquetaires.languages.syntax.ytree.expressions.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YVariableRef;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YIndexerExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YInvocationExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YMemberAccessExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YIntegerPostfixUnaryExpression;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.utils.StringUtils;
import mousquetaires.utils.exceptions.ytree.ParserException;
import mousquetaires.utils.exceptions.ytree.ParserIllegalStateException;
import mousquetaires.utils.exceptions.ytree.ParserNotImplementedException;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;


class C11ToYtreeConverterVisitor
        extends AbstractParseTreeVisitor<YEntity>
        implements C11Visitor<YEntity> {

    private final YSyntaxTreeBuilder syntaxTreeBuilder = new YSyntaxTreeBuilder();

    /**
     * main
     * :   statement+
     * |   initialWriteStatement? processStatement+
     * ;
     */
    public YSyntaxTree visitMain(C11Parser.MainContext ctx) {
        int childrenCount = ctx.getChildCount();
        for (int i = 0; i < childrenCount; i++) {
            ParseTree childTree = ctx.getChild(i);
            assert childTree != null;
            YStatement root = (YStatement) this.visit(childTree);
            syntaxTreeBuilder.addRoot(root);
        }
        return syntaxTreeBuilder.build();
    }


    /**
     * primaryExpression
     * :   Identifier
     * |   Constant
     * |   StringLiteral+
     * |   '(' expression ')'
     * |   genericSelection
     * |   '__extension__'? '(' compoundStatement ')'
     * |   '__builtin_va_arg' '(' unaryExpression ',' typeName ')'
     * |   '__builtin_offsetof' '(' typeName ',' unaryExpression ')'
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
                throw new ParserException(ctx, "Could not parse constant: " + StringUtils.wrap(constantNode.getText()));
            }
            return constant;
        }
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * genericSelection
     * :   '_Generic' '(' assignmentExpression ',' genericAssocList ')'
     * ;
     */
    @Override
    public YEntity visitGenericSelection(C11Parser.GenericSelectionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * genericAssocList
     * :   genericAssociation
     * |   genericAssocList ',' genericAssociation
     * ;
     */
    @Override
    public YEntity visitGenericAssocList(C11Parser.GenericAssocListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * genericAssociation
     * :   typeName ':' assignmentExpression
     * |   'default' ':' assignmentExpression
     * ;
     */
    @Override
    public YEntity visitGenericAssociation(C11Parser.GenericAssociationContext ctx) {
        throw new ParserNotImplementedException(ctx);
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
     * |   '__extension__' '(' typeName ')' '{' initializerList '}'
     * |   '__extension__' '(' typeName ')' '{' initializerList ',' '}'
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
                    throw new ParserException(ctx, "Missing indexer expression");
                }
                YExpression indexerExpression = visitExpression(expressionContext);
                return new YIndexerExpression(baseExpression, indexerExpression);
            }
            // case (invocation expression):
            if (ctx.getTokens(C11Parser.LeftParen).size() > 0 && ctx.getTokens(C11Parser.RightParen).size() > 0) {
                C11Parser.ArgumentExpressionListContext argumentExpressionListContextList = ctx.argumentExpressionList();
                YExpressionReversableList arguments = argumentExpressionListContextList != null
                        ? visitArgumentExpressionList(argumentExpressionListContextList)
                        : new YExpressionReversableList();
                return new YInvocationExpression(baseExpression, arguments.asArray());
            }
            // case (member access expression):
            if (ctx.getTokens(C11Parser.Arrow).size() > 0 || ctx.getTokens(C11Parser.Dot).size() > 0) {
                TerminalNode identifierNode = ctx.Identifier();
                if (identifierNode == null) {
                    throw new ParserException(ctx, "Missing member name while parsing member access expression");
                }
                return new YMemberAccessExpression(baseExpression, identifierNode.getText());
            }
            // case (increment):
            if (ctx.getTokens(C11Parser.PlusPlus).size() > 0) {
                return YIntegerPostfixUnaryExpression.Kind.Increment.createExpression(baseExpression);
            }
            // case (decrement):
            if (ctx.getTokens(C11Parser.MinusMinus).size() > 0) {
                return YIntegerPostfixUnaryExpression.Kind.Decrement.createExpression(baseExpression);
            }
        }
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * argumentExpressionList
     * :   assignmentExpression
     * |   argumentExpressionList ',' assignmentExpression
     * ;
     */
    @Override
    public YExpressionReversableList visitArgumentExpressionList(C11Parser.ArgumentExpressionListContext ctx) {
        YExpressionReversableList result = new YExpressionReversableList();
        C11Parser.AssignmentExpressionContext assignmentExpressionContext = ctx.assignmentExpression();
        C11Parser.ArgumentExpressionListContext argumentExpressionListContext = ctx.argumentExpressionList();
        if (assignmentExpressionContext != null) {
            result.add(visitAssignmentExpression(assignmentExpressionContext));
            if (argumentExpressionListContext != null) {
                result.addAll(visitArgumentExpressionList(argumentExpressionListContext));
            }
            return result;
        }
        throw new ParserIllegalStateException(ctx);
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
     * |   '&&' Identifier
     * ;
     */
    @Override
    public YEntity visitUnaryExpression(C11Parser.UnaryExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * unaryOperator
     * :   '&' | '*' | '+' | '-' | '~' | '!'
     * ;
     */
    @Override
    public YEntity visitUnaryOperator(C11Parser.UnaryOperatorContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * castExpression
     * :   unaryExpression
     * |   '(' typeName ')' castExpression
     * |   '__extension__' '(' typeName ')' castExpression
     * |   DigitSequence
     * ;
     */
    @Override
    public YEntity visitCastExpression(C11Parser.CastExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
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
    public YEntity visitMultiplicativeExpression(C11Parser.MultiplicativeExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * additiveExpression
     * :   multiplicativeExpression
     * |   additiveExpression '+' multiplicativeExpression
     * |   additiveExpression '-' multiplicativeExpression
     * ;
     */
    @Override
    public YEntity visitAdditiveExpression(C11Parser.AdditiveExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * shiftExpression
     * :   additiveExpression
     * |   shiftExpression '<<' additiveExpression
     * |   shiftExpression '>>' additiveExpression
     * ;
     */
    @Override
    public YEntity visitShiftExpression(C11Parser.ShiftExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
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
    public YEntity visitRelationalExpression(C11Parser.RelationalExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * equalityExpression
     * :   relationalExpression
     * |   equalityExpression '==' relationalExpression
     * |   equalityExpression '!=' relationalExpression
     * ;
     */
    @Override
    public YEntity visitEqualityExpression(C11Parser.EqualityExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * andExpression
     * :   equalityExpression
     * |   andExpression '&' equalityExpression
     * ;
     */
    @Override
    public YEntity visitAndExpression(C11Parser.AndExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * exclusiveOrExpression
     * :   andExpression
     * |   exclusiveOrExpression '^' andExpression
     * ;
     */
    @Override
    public YEntity visitExclusiveOrExpression(C11Parser.ExclusiveOrExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * inclusiveOrExpression
     * :   exclusiveOrExpression
     * |   inclusiveOrExpression '|' exclusiveOrExpression
     * ;
     */
    @Override
    public YEntity visitInclusiveOrExpression(C11Parser.InclusiveOrExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * logicalAndExpression
     * :   inclusiveOrExpression
     * |   logicalAndExpression '&&' inclusiveOrExpression
     * ;
     */
    @Override
    public YEntity visitLogicalAndExpression(C11Parser.LogicalAndExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * logicalOrExpression
     * :   logicalAndExpression
     * |   logicalOrExpression '||' logicalAndExpression
     * ;
     */
    @Override
    public YEntity visitLogicalOrExpression(C11Parser.LogicalOrExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * conditionalExpression
     * :   logicalOrExpression ('?' expression ':' conditionalExpression)?
     * ;
     */
    @Override
    public YEntity visitConditionalExpression(C11Parser.ConditionalExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * assignmentExpression
     * :   conditionalExpression
     * |   unaryExpression assignmentOperator assignmentExpression
     * |   DigitSequence
     * ;
     */
    @Override
    public YExpression visitAssignmentExpression(C11Parser.AssignmentExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * assignmentOperator
     * :   '=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '&=' | '^=' | '|='
     * ;
     */
    @Override
    public YEntity visitAssignmentOperator(C11Parser.AssignmentOperatorContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * expression
     * :   assignmentExpression
     * |   expression ',' assignmentExpression
     * ;
     */
    @Override
    public YExpression visitExpression(C11Parser.ExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * constantExpression
     * :   conditionalExpression
     * ;
     */
    @Override
    public YEntity visitConstantExpression(C11Parser.ConstantExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * declaration
     * :   declarationSpecifiers initDeclaratorList ';'
     * | 	declarationSpecifiers ';'
     * |   staticAssertDeclaration
     * ;
     */
    @Override
    public YEntity visitDeclaration(C11Parser.DeclarationContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * declarationSpecifiers
     * :   declarationSpecifier+
     * ;
     */
    @Override
    public YEntity visitDeclarationSpecifiers(C11Parser.DeclarationSpecifiersContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * declarationSpecifiers2
     * :   declarationSpecifier+
     * ;
     */
    @Override
    public YEntity visitDeclarationSpecifiers2(C11Parser.DeclarationSpecifiers2Context ctx) {
        throw new ParserNotImplementedException(ctx);
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
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * initDeclaratorList
     * :   initDeclarator
     * |   initDeclaratorList ',' initDeclarator
     * ;
     */
    @Override
    public YEntity visitInitDeclaratorList(C11Parser.InitDeclaratorListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * initDeclarator
     * :   declarator
     * |   declarator '=' initializer
     * ;
     */
    @Override
    public YEntity visitInitDeclarator(C11Parser.InitDeclaratorContext ctx) {
        throw new ParserNotImplementedException(ctx);
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
        throw new ParserNotImplementedException(ctx);
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
     * |   '_Complex'
     * |   '__m128'
     * |   '__m128d'
     * |   '__m128i')
     * |   '__extension__' '(' ('__m128' | '__m128d' | '__m128i') ')'
     * |   atomicTypeSpecifier
     * |   structOrUnionSpecifier
     * |   enumSpecifier
     * |   typedefName
     * |   '__typeof__' '(' constantExpression ')'
     * ;
     */
    @Override
    public YEntity visitTypeSpecifier(C11Parser.TypeSpecifierContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * structOrUnionSpecifier
     * :   structOrUnion Identifier? '{' structDeclarationList '}'
     * |   structOrUnion Identifier
     * ;
     */
    @Override
    public YEntity visitStructOrUnionSpecifier(C11Parser.StructOrUnionSpecifierContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * structOrUnion
     * :   'struct'
     * |   'union'
     * ;
     */
    @Override
    public YEntity visitStructOrUnion(C11Parser.StructOrUnionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * structDeclarationList
     * :   structDeclaration
     * |   structDeclarationList structDeclaration
     * ;
     */
    @Override
    public YEntity visitStructDeclarationList(C11Parser.StructDeclarationListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * structDeclaration
     * :   specifierQualifierList structDeclaratorList? ';'
     * |   staticAssertDeclaration
     * ;
     */
    @Override
    public YEntity visitStructDeclaration(C11Parser.StructDeclarationContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * specifierQualifierList
     * :   typeSpecifier specifierQualifierList?
     * |   typeQualifier specifierQualifierList?
     * ;
     */
    @Override
    public YEntity visitSpecifierQualifierList(C11Parser.SpecifierQualifierListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * structDeclaratorList
     * :   structDeclarator
     * |   structDeclaratorList ',' structDeclarator
     * ;
     */
    @Override
    public YEntity visitStructDeclaratorList(C11Parser.StructDeclaratorListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * structDeclarator
     * :   declarator
     * |   declarator? ':' constantExpression
     * ;
     */
    @Override
    public YEntity visitStructDeclarator(C11Parser.StructDeclaratorContext ctx) {
        throw new ParserNotImplementedException(ctx);
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
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * enumeratorList
     * :   enumerator
     * |   enumeratorList ',' enumerator
     * ;
     */
    @Override
    public YEntity visitEnumeratorList(C11Parser.EnumeratorListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * enumerator
     * :   enumerationConstant
     * |   enumerationConstant '=' constantExpression
     * ;
     */
    @Override
    public YEntity visitEnumerator(C11Parser.EnumeratorContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * enumerationConstant
     * :   Identifier
     * ;
     */
    @Override
    public YEntity visitEnumerationConstant(C11Parser.EnumerationConstantContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * atomicTypeSpecifier
     * :   '_Atomic' '(' typeName ')'
     * ;
     */
    @Override
    public YEntity visitAtomicTypeSpecifier(C11Parser.AtomicTypeSpecifierContext ctx) {
        throw new ParserNotImplementedException(ctx);
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
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * functionSpecifier
     * :   ('inline'
     * |   '_Noreturn'
     * |   '__inline__'
     * |   '__stdcall')
     * |   gccAttributeSpecifier
     * |   '__declspec' '(' Identifier ')'
     * ;
     */
    @Override
    public YEntity visitFunctionSpecifier(C11Parser.FunctionSpecifierContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * alignmentSpecifier
     * :   '_Alignas' '(' typeName ')'
     * |   '_Alignas' '(' constantExpression ')'
     * ;
     */
    @Override
    public YEntity visitAlignmentSpecifier(C11Parser.AlignmentSpecifierContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * declarator
     * :   pointer? directDeclarator gccDeclaratorExtension*
     * ;
     */
    @Override
    public YEntity visitDeclarator(C11Parser.DeclaratorContext ctx) {
        throw new ParserNotImplementedException(ctx);
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
     * |   Identifier ':' DigitSequence
     * ;
     */
    @Override
    public YEntity visitDirectDeclarator(C11Parser.DirectDeclaratorContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * gccDeclaratorExtension
     * :   '__asm' '(' StringLiteral+ ')'
     * |   gccAttributeSpecifier
     * ;
     */
    @Override
    public YEntity visitGccDeclaratorExtension(C11Parser.GccDeclaratorExtensionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * gccAttributeSpecifier
     * :   '__attribute__' '(' '(' gccAttributeList ')' ')'
     * ;
     */
    @Override
    public YEntity visitGccAttributeSpecifier(C11Parser.GccAttributeSpecifierContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * gccAttributeList
     * :   gccAttribute (',' gccAttribute)*
     * |
     * ;
     */
    @Override
    public YEntity visitGccAttributeList(C11Parser.GccAttributeListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * gccAttribute
     * :   ~(',' | '(' | ')')
     * ('(' argumentExpressionList? ')')?
     * |
     * ;
     */
    @Override
    public YEntity visitGccAttribute(C11Parser.GccAttributeContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * nestedParenthesesBlock
     * :   (   ~('(' | ')')
     * |   '(' nestedParenthesesBlock ')'
     * )*
     * ;
     */
    @Override
    public YEntity visitNestedParenthesesBlock(C11Parser.NestedParenthesesBlockContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * pointer
     * :   '*' typeQualifierList?
     * |   '*' typeQualifierList? pointer
     * |   '^' typeQualifierList?
     * |   '^' typeQualifierList? pointer
     * ;
     */
    @Override
    public YEntity visitPointer(C11Parser.PointerContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * typeQualifierList
     * :   typeQualifier
     * |   typeQualifierList typeQualifier
     * ;
     */
    @Override
    public YEntity visitTypeQualifierList(C11Parser.TypeQualifierListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * parameterTypeList
     * :   parameterList
     * |   parameterList ',' '...'
     * ;
     */
    @Override
    public YEntity visitParameterTypeList(C11Parser.ParameterTypeListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * parameterList
     * :   parameterDeclaration
     * |   parameterList ',' parameterDeclaration
     * ;
     */
    @Override
    public YEntity visitParameterList(C11Parser.ParameterListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * parameterDeclaration
     * :   declarationSpecifiers declarator
     * |   declarationSpecifiers2 abstractDeclarator?
     * ;
     */
    @Override
    public YEntity visitParameterDeclaration(C11Parser.ParameterDeclarationContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * identifierList
     * :   Identifier
     * |   identifierList ',' Identifier
     * ;
     */
    @Override
    public YEntity visitIdentifierList(C11Parser.IdentifierListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * typeName
     * :   specifierQualifierList abstractDeclarator?
     * ;
     */
    @Override
    public YEntity visitTypeName(C11Parser.TypeNameContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * abstractDeclarator
     * :   pointer
     * |   pointer? directAbstractDeclarator gccDeclaratorExtension*
     * ;
     */
    @Override
    public YEntity visitAbstractDeclarator(C11Parser.AbstractDeclaratorContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * directAbstractDeclarator
     * :   '(' abstractDeclarator ')' gccDeclaratorExtension*
     * |   '[' typeQualifierList? assignmentExpression? ']'
     * |   '[' 'static' typeQualifierList? assignmentExpression ']'
     * |   '[' typeQualifierList 'static' assignmentExpression ']'
     * |   '[' '*' ']'
     * |   '(' parameterTypeList? ')' gccDeclaratorExtension*
     * |   directAbstractDeclarator '[' typeQualifierList? assignmentExpression? ']'
     * |   directAbstractDeclarator '[' 'static' typeQualifierList? assignmentExpression ']'
     * |   directAbstractDeclarator '[' typeQualifierList 'static' assignmentExpression ']'
     * |   directAbstractDeclarator '[' '*' ']'
     * |   directAbstractDeclarator '(' parameterTypeList? ')' gccDeclaratorExtension*
     * ;
     */
    @Override
    public YEntity visitDirectAbstractDeclarator(C11Parser.DirectAbstractDeclaratorContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * typedefName
     * :   Identifier
     * ;
     */
    @Override
    public YEntity visitTypedefName(C11Parser.TypedefNameContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * initializer
     * :   assignmentExpression
     * |   '{' initializerList '}'
     * |   '{' initializerList ',' '}'
     * ;
     */
    @Override
    public YEntity visitInitializer(C11Parser.InitializerContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * initializerList
     * :   designation? initializer
     * |   initializerList ',' designation? initializer
     * ;
     */
    @Override
    public YEntity visitInitializerList(C11Parser.InitializerListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * designation
     * :   designatorList '='
     * ;
     */
    @Override
    public YEntity visitDesignation(C11Parser.DesignationContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * designatorList
     * :   designator
     * |   designatorList designator
     * ;
     */
    @Override
    public YEntity visitDesignatorList(C11Parser.DesignatorListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * designator
     * :   '[' constantExpression ']'
     * |   '.' Identifier
     * ;
     */
    @Override
    public YEntity visitDesignator(C11Parser.DesignatorContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * staticAssertDeclaration
     * :   '_Static_assert' '(' constantExpression ',' StringLiteral+ ')' ';'
     * ;
     */
    @Override
    public YEntity visitStaticAssertDeclaration(C11Parser.StaticAssertDeclarationContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * statement
     * :   labeledStatement
     * |   compoundStatement
     * |   expressionStatement
     * |   selectionStatement
     * |   iterationStatement
     * |   jumpStatement
     * |   ('__asm' | '__asm__') ('volatile' | '__volatile__') '(' (logicalOrExpression (',' logicalOrExpression)*)? (':' (logicalOrExpression (',' logicalOrExpression)*)?)* ')' ';'
     * ;
     */
    @Override
    public YEntity visitStatement(C11Parser.StatementContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * labeledStatement
     * :   Identifier ':' statement
     * |   'case' constantExpression ':' statement
     * |   'default' ':' statement
     * ;
     */
    @Override
    public YEntity visitLabeledStatement(C11Parser.LabeledStatementContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * compoundStatement
     * :   '{' blockItemList? '}'
     * ;
     */
    @Override
    public YEntity visitCompoundStatement(C11Parser.CompoundStatementContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * blockItemList
     * :   blockItem
     * |   blockItemList blockItem
     * ;
     */
    @Override
    public YEntity visitBlockItemList(C11Parser.BlockItemListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * blockItem
     * :   declaration
     * |   statement
     * ;
     */
    @Override
    public YEntity visitBlockItem(C11Parser.BlockItemContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * expressionStatement
     * :   expression? ';'
     * ;
     */
    @Override
    public YEntity visitExpressionStatement(C11Parser.ExpressionStatementContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * selectionStatement
     * :   'if' '(' expression ')' statement ('else' statement)?
     * |   'switch' '(' expression ')' statement
     * ;
     */
    @Override
    public YEntity visitSelectionStatement(C11Parser.SelectionStatementContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * iterationStatement
     * :   While '(' expression ')' statement
     * |   Do statement While '(' expression ')' ';'
     * |   For '(' forCondition ')' statement
     * ;
     */
    @Override
    public YEntity visitIterationStatement(C11Parser.IterationStatementContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * forCondition
     * :   forDeclaration ';' forExpression? ';' forExpression?
     * |   expression? ';' forExpression? ';' forExpression?
     * ;
     */
    @Override
    public YEntity visitForDeclaration(C11Parser.ForDeclarationContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * forDeclaration
     * :   declarationSpecifiers initDeclaratorList
     * | 	declarationSpecifiers
     * ;
     */
    @Override
    public YEntity visitForCondition(C11Parser.ForConditionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * forExpression
     * :   assignmentExpression
     * |   forExpression ',' assignmentExpression
     * ;
     */
    @Override
    public YEntity visitForExpression(C11Parser.ForExpressionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * jumpStatement
     * :   'goto' Identifier ';'
     * |   'continue' ';'
     * |   'break' ';'
     * |   'return' expression? ';'
     * |   'goto' unaryExpression ';'
     * ;
     */
    @Override
    public YEntity visitJumpStatement(C11Parser.JumpStatementContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * compilationUnit
     * :   translationUnit? EOF
     * ;
     */
    @Override
    public YEntity visitCompilationUnit(C11Parser.CompilationUnitContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * translationUnit
     * :   externalDeclaration
     * |   translationUnit externalDeclaration
     * ;
     */
    @Override
    public YEntity visitTranslationUnit(C11Parser.TranslationUnitContext ctx) {
        return visitChildren(ctx);
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
        return visitChildren(ctx);
    }

    /**
     * functionDefinition
     * :   declarationSpecifiers? declarator declarationList? compoundStatement
     * ;
     */
    @Override
    public YEntity visitFunctionDefinition(C11Parser.FunctionDefinitionContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }

    /**
     * declarationList
     * :   declaration
     * |   declarationList declaration
     * ;
     */
    @Override
    public YEntity visitDeclarationList(C11Parser.DeclarationListContext ctx) {
        throw new ParserNotImplementedException(ctx);
    }
}