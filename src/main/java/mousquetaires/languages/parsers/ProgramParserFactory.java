package mousquetaires.languages.parsers;

import mousquetaires.languages.ProgramExtensions;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.transformers.C11ToProgramTransformer;
import mousquetaires.languages.transformers.ISyntaxTreeToProgramTransformer;
import mousquetaires.languages.transformers.PorthosToProgramTransformer;
import mousquetaires.languages.transformers.TransformerFactory;
import mousquetaires.program.Program;
import mousquetaires.utils.io.FileUtils;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.IOException;


public class ProgramParserFactory {

    // TODO: Return Parser
    // TODO: input: already parsed language
    public static Program getParser(File programFile) throws IOException {
        ProgramLanguage language = ProgramExtensions.tryParseProgramLanguage(programFile.getName());
        if (language == null) {
            throw new IllegalArgumentException(programFile.getName());
        }

        CharStream charStream = FileUtils.getFileCharStream(programFile);
        ISyntaxTreeToProgramTransformer transformer = TransformerFactory.getTransformer(language);

        switch (language) {
            case Cmin: {
                C11Lexer lexer = new C11Lexer(charStream);
                CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                C11Parser parser = new C11Parser(tokenStream);
                return transformer.transform(parser.primaryExpression());

            }
            case Porthos: {
                PorthosLexer lexer = new PorthosLexer(charStream);
                CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                PorthosParser parser = new PorthosParser(tokenStream);
                return transformer.transform(parser.program());
            }
            //case Litmus: {
            //    LitmusLexer lexer = new LitmusLexer(charStream);
            //    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            //    return new LitmusParser(tokenStream)    .program(programFile.getName()).p;
            //}
            default:
                throw new IllegalArgumentException(language.toString());
        }
    }

    // TODO: remove this method
    public static Program getProgram(File programFile) throws IOException {
        return getParser(programFile);
    }

}
