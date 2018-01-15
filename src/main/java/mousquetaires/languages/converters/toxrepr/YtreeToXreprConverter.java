package mousquetaires.languages.converters.toxrepr;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.syntax.xrepr.XProgram;
import mousquetaires.languages.syntax.xrepr.datamodels.DataModel;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;


public class YtreeToXreprConverter {

    private boolean finished = false;
    private final XCompiler interpreter;

    public YtreeToXreprConverter(ProgramLanguage language, DataModel dataModel) {
        this.interpreter = new XCompiler(language, dataModel);
    }

    public XProgram convert(YSyntaxTree internalSyntaxTree) {
        if (finished) {
            throw new IllegalStateException("Cannot re-use " + getClass().getName());
        }
        YtreeToXreprConverterVisitor visitor = new YtreeToXreprConverterVisitor(interpreter);
        XProgram result = (XProgram) internalSyntaxTree.accept(visitor);
        finished = true;
        return result;
    }
}
