package mousquetaires.languages.converters.toxgraph;

import mousquetaires.languages.InputLanguage;
import mousquetaires.languages.converters.toxgraph.interpretation.XMemoryManager;
import mousquetaires.languages.converters.toxgraph.interpretation.XMemoryManagerImpl;
import mousquetaires.languages.syntax.xgraph.program.XCyclicProgram;
import mousquetaires.languages.converters.toxgraph.interpretation.XProgramInterpreter;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.memorymodels.wmm.MemoryModel;


// Stateless
public class Ytree2XgraphConverter {

    private final MemoryModel.Kind memoryModel;

    public Ytree2XgraphConverter(MemoryModel.Kind memoryModelKind) {
        this.memoryModel = memoryModelKind;
    }

    public XCyclicProgram convert(YSyntaxTree internalSyntaxTree) {
        XMemoryManager sharedMemoryManager = new XMemoryManagerImpl();//dataModel
        XProgramInterpreter programInterpreter = new XProgramInterpreter(sharedMemoryManager, memoryModel);
        Ytree2XgraphConverterVisitor visitor = new Ytree2XgraphConverterVisitor(programInterpreter);
        internalSyntaxTree.accept(visitor);
        return visitor.getProgram();
    }
}
