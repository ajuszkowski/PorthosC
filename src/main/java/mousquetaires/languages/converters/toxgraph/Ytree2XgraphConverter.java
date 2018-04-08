package mousquetaires.languages.converters.toxgraph;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.converters.toxgraph.interpretation.XMemoryManager;
import mousquetaires.languages.syntax.xgraph.XProgram;
import mousquetaires.languages.converters.toxgraph.interpretation.XProgramInterpreter;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;


// Stateless
public class Ytree2XgraphConverter {

    private final ProgramLanguage language;
    private final DataModel dataModel;

    public Ytree2XgraphConverter(ProgramLanguage language, DataModel dataModel) {
        this.language = language;
        this.dataModel = dataModel;
    }

    public XProgram convert(YSyntaxTree internalSyntaxTree) {
        XMemoryManager sharedMemoryManager = new XMemoryManager();//dataModel
        XProgramInterpreter programInterpreter = new XProgramInterpreter(sharedMemoryManager);
        Ytree2XgraphConverterVisitor visitor = new Ytree2XgraphConverterVisitor(programInterpreter);
        internalSyntaxTree.accept(visitor);
        return visitor.getProgram();
    }
}
