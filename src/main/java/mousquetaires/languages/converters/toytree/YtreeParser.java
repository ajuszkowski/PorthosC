package mousquetaires.languages.converters.toytree;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.common.citation.CodeCitationService;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;

import java.io.File;
import java.io.IOException;


public class YtreeParser {

    private final File inputFile;
    private final ProgramLanguage language;

    private CodeCitationService citationService;
    private YSyntaxTree result;

    public YtreeParser(File inputFile, ProgramLanguage language) {
        this.inputFile = inputFile;
        this.language = language;
    }

    public YSyntaxTree parseFile() throws IOException {
        if (result == null) {
            CommonTokenStream tokenStream = InputProgramParserFactory.getTokenStream(inputFile, language);
            citationService = new CodeCitationService(inputFile.getAbsolutePath(), tokenStream);
            ParserRuleContext parserEntryPoint = InputProgramParserFactory.getParser(tokenStream, language);
            InputProgramConvertersFactory convertersFactory = new InputProgramConvertersFactory(citationService);
            InputProgram2YtreeConverter transformer = convertersFactory.getConverter(language);
            result = transformer.convert(parserEntryPoint);
        }
        return result;
    }

    public CodeCitationService getCitationService() {
        assert result != null : "'parseFile()' must been already called";
        assert citationService != null;
        return citationService;
    }
}
