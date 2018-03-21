package mousquetaires.languages.converters.toxgraph;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.syntax.xgraph.XProgramInterpretationBuilder;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryManager;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitorBase;


// Stateless
public class YtreeToXgraphConverter {

    private final ProgramLanguage language;
    private final DataModel dataModel;

    public YtreeToXgraphConverter(ProgramLanguage language, DataModel dataModel) {
        this.language = language;
        this.dataModel = dataModel;
    }

    public XProgram convert(YSyntaxTree internalSyntaxTree) {
        YtreeToXgraphConverterVisitor visitor = new YtreeToXgraphConverterVisitor(language, dataModel);
        internalSyntaxTree.accept(visitor);
        return visitor.getProgram();
    }
}
