package mousquetaires.languages.cmin.transformer;

import mousquetaires.interpretation.internalrepr.exceptions.ParserException;
import mousquetaires.languages.ProgramToYtreeTransformer;
import mousquetaires.languages.cmin.transformer.tokens.CminKeyword;
import mousquetaires.languages.cmin.types.CminPrimitiveTypeBuilder;
import mousquetaires.languages.parsers.CminBaseVisitor;
import mousquetaires.languages.parsers.CminParser;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.languages.ytree.expressions.lvalue.YVariableRef;
import mousquetaires.languages.ytree.types.YType;
import mousquetaires.utils.Pair;
import mousquetaires.utils.exceptions.ArgumentNullException;
import mousquetaires.utils.exceptions.NotImplementedException;
import org.antlr.v4.misc.OrderedHashMap;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Map;


final class CminToYtreeTransformerTypeHelper {

    private final CminToYtreeTransformerVisitor patronVisitor;

    CminToYtreeTransformerTypeHelper(CminToYtreeTransformerVisitor patronVisitor) {
        this.patronVisitor = patronVisitor;
    }


    // May not receive null argument
    // May not return null result

    public Map<YVariableRef, YExpression> convertVariableInitialisationList(CminParser.VariableInitialisationListContext ctx) {
        if (ctx == null) {
            throw new ArgumentNullException();
        }
        Map<YVariableRef, YExpression> result = new OrderedHashMap<>();
        fillVariableInitialisationList(ctx, result);
        return result;
    }

    /**
     * variableInitialisationList
     *     :   variableInitialisation
     *     |   variableInitialisationList ',' variableInitialisation
     *     ;
     */
    private void fillVariableInitialisationList(CminParser.VariableInitialisationListContext ctx,
                                                Map<YVariableRef, YExpression> result) {
        CminParser.VariableInitialisationListContext initialisationListCtx = ctx.variableInitialisationList();
        if (initialisationListCtx != null) {
            fillVariableInitialisationList(ctx.variableInitialisationList(), result);
        }

        CminParser.VariableInitialisationContext initialisationCtx = ctx.variableInitialisation();
        if (initialisationCtx == null) {
            throw new ParserException(ctx, "Missing variable name in variable declaration");
        }

        //YVariableRef variableName = (YVariableRef) patronVisitor.visitVariableName(initialisationCtx.variableName());
        //YExpression initialisator = (YExpression) patronVisitor.visitRvalueExpression(initialisationCtx.rvalueExpression());
        Pair<YVariableRef, YExpression> pair = convertVariableInitialisation(initialisationCtx);

        result.put(pair.first(), pair.second());
    }

    /** variableInitialisation
     *      :   variableName
     *      |   variableName '=' rvalueExpression
     *      ;
     */
    public Pair<YVariableRef, YExpression> convertVariableInitialisation(CminParser.VariableInitialisationContext ctx) {
        if (ctx == null) {
            throw new ArgumentNullException();
        }

        CminParser.VariableNameContext variableNameCtx = ctx.variableName();
        if (variableNameCtx == null) {
            throw new ParserException(ctx, "Missing variable name in variable declaration");
        }
        YVariableRef variable = (YVariableRef) patronVisitor.visitVariableName(variableNameCtx);

        CminParser.RvalueExpressionContext expressionCtx = ctx.rvalueExpression();
        YExpression expression = expressionCtx != null
                ? (YExpression) patronVisitor.visitRvalueExpression(expressionCtx)
                : null;

        return new Pair<>(variable, expression);
    }

    /** typeDeclarator
     *      :   LeftParen typeDeclarator RightParen
     *      |   typeDeclarator Asterisk
     *      |   primitiveTypeDeclarator
     *      ;
     */
    public YType convertTypeDeclarator(CminParser.TypeDeclaratorContext ctx) {
        if (ctx == null) {
            throw new ArgumentNullException();
        }

        CminParser.TypeDeclaratorContext typeDeclaratorCtx = ctx.typeDeclarator();
        if (typeDeclaratorCtx != null) {
            boolean isPointer = ctx.Asterisk() != null;
            YType result = convertTypeDeclarator(ctx.typeDeclarator());
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

    YType visitPrimitiveTypeDeclarator(CminParser.PrimitiveTypeDeclaratorContext ctx) {
        if (ctx == null) {
            throw new ArgumentNullException();
        }
        CminPrimitiveTypeBuilder builder = new CminPrimitiveTypeBuilder();
        for (CminParser.PrimitiveTypeKeywordContext typeKeywordContext : ctx.primitiveTypeKeyword()) {
            CminKeyword typeKeyword = visitPrimitiveTypeKeyword(typeKeywordContext);
            builder.addModifier(typeKeyword);

            CminKeyword specifierKeyword = visitPrimitiveTypeSpecifier(ctx.primitiveTypeSpecifier());
            if (specifierKeyword != null) {
                builder.addModifier(specifierKeyword);
            }
        }
        return builder.build();
    }

    CminKeyword visitPrimitiveTypeSpecifier(CminParser.PrimitiveTypeSpecifierContext ctx) {
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

    public CminKeyword visitPrimitiveTypeKeyword(CminParser.PrimitiveTypeKeywordContext ctx) {
        if (ctx == null) {
            throw new ArgumentNullException();
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
            default:
                throw new ParserException(ctx, "Unexpected primitive type keyword: " + keyword);
        }
    }

}
