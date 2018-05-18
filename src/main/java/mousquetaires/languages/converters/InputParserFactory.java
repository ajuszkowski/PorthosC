package mousquetaires.languages.converters;

import dartagnan.ModelLexer;
import mousquetaires.languages.InputLanguage;
import mousquetaires.languages.parsers.*;
import mousquetaires.utils.exceptions.ytree.YParserException;
import mousquetaires.utils.io.FileUtils;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;

import java.io.File;
import java.io.IOException;

import static mousquetaires.utils.StringUtils.wrap;


public class InputParserFactory {

    public static CommonTokenStream getTokenStream(File programFile, InputLanguage language) throws IOException {
        CharStream charStream = FileUtils.getFileCharStream(programFile);
        Lexer lexer;

        switch (language) {
            case C11:
                lexer = new C11Lexer(charStream);
                break;
            case Cat:
                lexer = new ModelLexer(charStream);
                break;
            default:
                throw new IllegalArgumentException(language.name());
        }

        return new CommonTokenStream(lexer);
    }

    public static ParserRuleContext getParser(CommonTokenStream tokenStream, InputLanguage language) {
        SyntaxErrorListener errorListener = new SyntaxErrorListener();
        ParserRuleContext result;
        switch (language) {
            case C11: {
                C11Parser parser = new C11Parser(tokenStream);
                parser.addErrorListener(errorListener);
                result = parser.main();
                break;
            }
            case Cat: {
                CatParser parser = new CatParser(tokenStream);
                parser.addErrorListener(errorListener);
                result = parser.main();
                break;
            }
            default:
                throw new IllegalArgumentException(language.name());
        }
        if (errorListener.hasSyntaxErrors()) {
            throw new YParserException(result, "Syntax errors while parsing: " + wrap(errorListener));
        }
        return result;
    }
}
