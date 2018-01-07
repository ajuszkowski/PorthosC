package mousquetaires.languages.parsers;

import mousquetaires.interpretation.eventrepr.Interpreter;
import mousquetaires.languages.ProgramExtensions;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.SyntaxTreeToInternalTransformer;
import mousquetaires.languages.TransformerFactory;
import mousquetaires.languages.internalrepr.InternalSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;

import java.io.File;
import java.io.IOException;


public class InternalLanguageParser {

    public static InternalSyntaxTree parse(File inputProgramFile) throws IOException {
        ProgramLanguage language = ProgramExtensions.parseProgramLanguage(inputProgramFile.getName());
        Interpreter interpreter = new Interpreter(language);
        SyntaxTreeToInternalTransformer transformer = TransformerFactory.getTransformer(language);

        ParserRuleContext parserEntryPoint = CustomParserFactory.getParser(inputProgramFile, language);

        return transformer.transform(parserEntryPoint);
    }
}
