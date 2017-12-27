package mousquetaires.languages.parsers;

import mousquetaires.languages.ProgramExtensions;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.program.Program;
import mousquetaires.utils.io.FileUtils;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;


public class ProgramParserFactory {

    // TODO: Return Parser
    public static Program getParser(File programFile) throws IOException {
        ProgramLanguage language = ProgramExtensions.tryParseProgramLanguage(programFile.getName());
        if (language == null) {
            throw new IllegalArgumentException(programFile.getName());
        }

        CharStream charStream = FileUtils.getFileCharStream(programFile);

        switch (language) {
            case Cmin:
                throw new RuntimeException(); // Not implemented yet
            case Porthos: {
                PorthosLexer lexer = new PorthosLexer(charStream);
                CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                return new PorthosParser(tokenStream)    .program(programFile.getName()).p;
            }
            case Litmus: {
                LitmusLexer lexer = new LitmusLexer(charStream);
                CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                return new LitmusParser(tokenStream)    .program(programFile.getName()).p;
            }
            default:
                throw new IllegalArgumentException(language.toString());
        }
    }

    // TODO: remove this method
    public static Program getProgram(File programFile) throws IOException {
        return getParser(programFile);
    }

}
