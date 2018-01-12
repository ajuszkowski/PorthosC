package mousquetaires.languages.common.parsers;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.common.transformers.ProgramToYtreeConverter;
import mousquetaires.languages.common.transformers.ProgramTransformerFactory;
import mousquetaires.languages.ytree.YSyntaxTree;
import org.antlr.v4.runtime.ParserRuleContext;

import java.io.File;
import java.io.IOException;


public class YtreeParser {

    public static YSyntaxTree parse(File inputProgramFile, ProgramLanguage language) throws IOException {

        ProgramToYtreeConverter transformer = ProgramTransformerFactory.getTransformer(language);
        ParserRuleContext parserEntryPoint = ProgramParserFactory.getParser(inputProgramFile, language);
        return transformer.convert(parserEntryPoint);
    }
}
