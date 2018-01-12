package mousquetaires.languages.common.parsers;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.utils.io.FileUtils;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;

import java.io.File;
import java.io.IOException;


public class ProgramParserFactory {

    public static ParserRuleContext getParser(File programFile, ProgramLanguage language) throws IOException {
        CharStream charStream = FileUtils.getFileCharStream(programFile);

        switch (language) {
            case Cmin: {
                CminLexer lexer = new CminLexer(charStream);
                CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                CminParser parser = new CminParser(tokenStream);
                return parser.main();
            }
            //case Porthos: {
            //    PorthosLexer lexer = new PorthosLexer(charStream);
            //    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            //    PorthosParser parser = new PorthosParser(tokenStream);
            //    return parser.main();
            //}
            //case Litmus: {
            //    LitmusLexer lexer = new LitmusLexer(charStream);
            //    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            //    return new LitmusParser(tokenStream)    .program(programFile.getName()).p;
            //}
            default:
                throw new IllegalArgumentException(language.name());
        }
    }
}
