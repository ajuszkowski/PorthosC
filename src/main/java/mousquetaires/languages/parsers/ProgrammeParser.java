package mousquetaires.languages.parsers;

import mousquetaires.execution.Programme;
import mousquetaires.interpretation.Interpreter;
import mousquetaires.languages.ProgramExtensions;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.SyntaxTreeToInternalTransformer;
import mousquetaires.languages.TransformerFactory;
import org.antlr.v4.runtime.ParserRuleContext;

import java.io.File;
import java.io.IOException;


public class ProgrammeParser {

    public static Programme parse(File inputProgramFile) throws IOException {
        ProgramLanguage language = ProgramExtensions.parseProgramLanguage(inputProgramFile.getName());
        Interpreter interpreter = new Interpreter(language);
        SyntaxTreeToInternalTransformer transformer = TransformerFactory.getTransformer(language, interpreter);

        ParserRuleContext parserEntryPoint = ProgramParserFactory.getParser(inputProgramFile, language);

        return transformer.transform(parserEntryPoint);
    }
}
