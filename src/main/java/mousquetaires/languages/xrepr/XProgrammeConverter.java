package mousquetaires.languages.xrepr;

import mousquetaires.interpretation.eventrepr.Interpreter;
import mousquetaires.languages.transformers.ytree.YtreeToXreprTransformer;
import mousquetaires.languages.ytree.YSyntaxTree;


public class XProgrammeConverter {

    public static XProgram toProgramme(YSyntaxTree syntaxTree, Interpreter interpreter) {
        YtreeToXreprTransformer transformer = new YtreeToXreprTransformer(interpreter);
        return transformer.transform(syntaxTree);
    }
}
