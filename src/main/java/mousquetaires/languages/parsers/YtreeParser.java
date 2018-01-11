package mousquetaires.languages.parsers;

import mousquetaires.languages.ProgramExtensions;
import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.ProgramToYtreeTransformer;
import mousquetaires.languages.TransformerFactory;
import mousquetaires.languages.internalrepr.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;

import java.io.File;
import java.io.IOException;


public class YtreeParser {

    public static YSyntaxTree parse(File inputProgramFile) throws IOException {
        ProgramLanguage language = ProgramExtensions.parseProgramLanguage(inputProgramFile.getName());
        ProgramToYtreeTransformer transformer = TransformerFactory.getTransformer(language);
        ParserRuleContext parserEntryPoint = CustomParserFactory.getParser(inputProgramFile, language);
        return transformer.transform(parserEntryPoint);
    }
}
