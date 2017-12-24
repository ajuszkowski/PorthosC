package mousquetaires.languages;

import mousquetaires.languages.parsers.*;
import mousquetaires.utils.io.FileUtils;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;


public class InputProgramParserFactory {

    public static Parser getParser(File codeFile) throws IOException {
        CharStream charStream = FileUtils.getFileCharStream(codeFile);

        switch (FilenameUtils.getExtension(codeFile.getName())) {
            case "pts":
                PorthosLexer lexer = new PorthosLexer(charStream);
                CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                return new PorthosParser(tokenStream);
            default:
                return null;
        }
    }
}
