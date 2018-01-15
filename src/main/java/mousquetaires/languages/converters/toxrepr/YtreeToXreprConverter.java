package mousquetaires.languages.converters.toxrepr;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.syntax.xrepr.XProgram;
import mousquetaires.languages.syntax.xrepr.datamodels.DataModel;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;


public class YtreeToXreprConverter {

    private final ProgramLanguage language;
    private final DataModel dataModel;

    public YtreeToXreprConverter(ProgramLanguage language, DataModel dataModel) {
        this.language = language;
        this.dataModel = dataModel;
    }

    public XProgram convert(YSyntaxTree internalSyntaxTree) {
        YtreeToXreprConverterVisitor visitor = new YtreeToXreprConverterVisitor(language, dataModel);
        internalSyntaxTree.accept(visitor);
        return visitor.getProgram();
    }
}
