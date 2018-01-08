package mousquetaires.languages.cmin.transformer;

import javafx.scene.control.Skin;
import mousquetaires.interpretation.internalrepr.exceptions.ParserException;
import mousquetaires.languages.cmin.tokens.CminKeyword;
import mousquetaires.languages.cmin.types.CminPrimitiveTypeBuilder;
import mousquetaires.languages.internalrepr.InternalEntity;
import mousquetaires.languages.internalrepr.expressions.InternalConstant;
import mousquetaires.languages.internalrepr.expressions.lvalue.InternalVariableRef;
import mousquetaires.languages.internalrepr.statements.InternalBlockStatement;
import mousquetaires.languages.internalrepr.statements.InternalStatement;
import mousquetaires.languages.internalrepr.temporaries.InternalSequenceStatementBuilder;
import mousquetaires.languages.internalrepr.types.InternalType;
import mousquetaires.languages.parsers.CminParser;
import mousquetaires.utils.exceptions.NotImplementedException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;


class CminToInternalTransformerInterior {

    InternalVariableRef visitVariableName(CminParser.VariableNameContext ctx) {
        if (ctx == null) {
            return null;
        }
        int childCount = ctx.getChildCount();
        assert childCount > 0 : childCount;
        String identifier = ctx.getChild(0).getText(); // todo: get token characters correctly, something like: `ctx.getToken(...)`
        return new InternalVariableRef(identifier);
    }

    InternalConstant visitConstant(CminParser.ConstantContext ctx) {
        if (ctx == null) {
            return null;
        }
        TerminalNode constant = ctx.Constant();
        if (constant != null) {
            String constantText = constant.getText();  // todo: get token symbol text properly here
            InternalConstant parsedConstant = InternalConstant.tryParse(constantText);
            if (parsedConstant == null) {
                throw new ParserException(ctx, "Could not determine type of constant " + constantText);
            }
            return parsedConstant;
        }
        TerminalNode stringLiteral = ctx.StringLiteral();
        if (stringLiteral != null) {
            String constantText = stringLiteral.getText();  // todo: get token symbol text properly here
            InternalConstant parsedConstant = InternalConstant.tryParse(constantText);
            if (parsedConstant == null) {
                throw new ParserException(ctx, "Could not parse string constant " + constantText);
            }
            return parsedConstant;
        }
        return null;
    }

    InternalConstant tryParseInternalStringConstant(TerminalNode stringConstant, ParserRuleContext ctx) {
        if (stringConstant == null) {
            return null;
        }
        String constantText = stringConstant.getText();  // todo: get token symbol text properly here
        InternalConstant parsedConstant = InternalConstant.tryParse(constantText);

        return parsedConstant;
    }

    InternalEntity visitVariableDeclarationStatement(CminParser.VariableDeclarationStatementContext ctx) {
        InternalType type = (InternalType) visitTypeDeclarator(ctx.typeDeclarator());
        // for now ignore type specifiers ('extern', 'static', etc)
        // todo: set specifier to the 'type'
        //for (CminParser.TypeSpecifierContext typeSpecifierContext : ctx.typeSpecifier()) {
        //    CminKeyword typeSpecifier = (CminKeyword) visitTypeSpecifier(typeSpecifierContext);
        //}

        InternalBlockStatement declaratorList =
                (InternalBlockStatement) visitVariableInitialisationList(ctx.variableInitialisationList());

        InternalSequenceStatementBuilder builder = new InternalSequenceStatementBuilder();
        for (InternalStatement statement : declaratorList.statements) {
            assignStatement = ()
            declarationStatement = new InternalVariableInitialisationList(assignStatement.)
        }


        return result;
    }

    InternalEntity visitTypeDeclarator(CminParser.TypeDeclaratorContext ctx) {
        CminParser.TypeDeclaratorContext typeDeclaratorContext = ctx.typeDeclarator();
        if (typeDeclaratorContext != null) {
            if (ctx.Asterisk() != null) {
                // TODO: do not ignore pointers here!
                //return visitTypeDeclarator(ctx.typeDeclarator());
                throw new NotImplementedException();
            }
        }

        InternalEntity primitiveTypeDeclarator = visitPrimitiveTypeDeclarator(ctx.primitiveTypeDeclarator());
        if (primitiveTypeDeclarator != null) {
            return primitiveTypeDeclarator;
        }

        // todo: atomicTypeDeclarator

        return visitTypeDeclarator(ctx);
    }

    InternalType visitPrimitiveTypeDeclarator(CminParser.PrimitiveTypeDeclaratorContext ctx) {
        if (ctx == null) {
            return null;
        }
        CminPrimitiveTypeBuilder builder = new CminPrimitiveTypeBuilder();
        for (CminParser.PrimitiveTypeKeywordContext typeKeywordContext : ctx.primitiveTypeKeyword()) {
            CminKeyword typeKeyword = (CminKeyword) visitPrimitiveTypeKeyword(typeKeywordContext);
            builder.addModifier(typeKeyword);

            CminKeyword specifierKeyword = (CminKeyword) visitPrimitiveTypeSpecifier(ctx.primitiveTypeSpecifier());
            if (specifierKeyword != null) {
                builder.addModifier(specifierKeyword);
            }
        }
        return builder.build();
    }

    CminKeyword visitPrimitiveTypeKeyword(CminParser.PrimitiveTypeKeywordContext ctx) {
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
}
