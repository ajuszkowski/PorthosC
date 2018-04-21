package mousquetaires.languages.converters.toxgraph;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.converters.toxgraph.interpretation.XMemoryManager;
import mousquetaires.languages.syntax.xgraph.XCyclicProgram;
import mousquetaires.languages.converters.toxgraph.interpretation.XProgramInterpreter;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.memorymodels.wmm.MemoryModel;


// Stateless
public class Ytree2XgraphConverter {

    private final ProgramLanguage language;
    private final MemoryModel.Kind memoryModel;
    private final DataModel dataModel;

    public Ytree2XgraphConverter(ProgramLanguage language, MemoryModel.Kind memoryModel, DataModel dataModel) {
        this.language = language;
        this.memoryModel = memoryModel;
        this.dataModel = dataModel;
    }

    public XCyclicProgram convert(YSyntaxTree internalSyntaxTree) {
        XMemoryManager sharedMemoryManager = new XMemoryManager();//dataModel
        XProgramInterpreter programInterpreter = new XProgramInterpreter(sharedMemoryManager, memoryModel);
        Ytree2XgraphConverterVisitor visitor = new Ytree2XgraphConverterVisitor(programInterpreter);
        internalSyntaxTree.accept(visitor);
        return visitor.getProgram();
    }
}
