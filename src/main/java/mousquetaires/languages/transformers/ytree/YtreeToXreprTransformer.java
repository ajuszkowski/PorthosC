package mousquetaires.languages.transformers.ytree;

import mousquetaires.interpretation.eventrepr.Interpreter;
import mousquetaires.languages.xrepr.XProgram;
import mousquetaires.languages.ytree.YSyntaxTree;


public class YtreeToXreprTransformer {

    private final Interpreter interpreter;

    public YtreeToXreprTransformer(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public XProgram transform(YSyntaxTree internalSyntaxTree) {
        return null;
    }
}
