package mousquetaires.languages.converters.toytree;

import mousquetaires.languages.InputLanguage;
import mousquetaires.languages.converters.InputParserBase;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;

import java.io.File;


public class YtreeParser extends InputParserBase {

    public YtreeParser(File inputFile, InputLanguage language) {
        super(inputFile, language);
    }
}
