package porthosc.languages.converters.toxgraph;

import porthosc.languages.converters.toxgraph.interpretation.XMemoryManager;
import porthosc.languages.converters.toxgraph.interpretation.XMemoryManagerImpl;
import porthosc.languages.converters.toxgraph.interpretation.XProgramInterpreter;
import porthosc.languages.syntax.xgraph.program.XCyclicProgram;
import porthosc.languages.syntax.ytree.YSyntaxTree;
import porthosc.memorymodels.wmm.MemoryModel;


// Stateless
public class Y2XConverter {

    private final MemoryModel.Kind memoryModel;

    public Y2XConverter(MemoryModel.Kind memoryModelKind) {
        this.memoryModel = memoryModelKind;
    }

    public XCyclicProgram convert(YSyntaxTree internalSyntaxTree) {
        XMemoryManager sharedMemoryManager = new XMemoryManagerImpl();//dataModel
        XProgramInterpreter programInterpreter = new XProgramInterpreter(sharedMemoryManager, memoryModel);
        Y2XConverterVisitor visitor = new Y2XConverterVisitor(programInterpreter, internalSyntaxTree.getJumpsResolver());
        internalSyntaxTree.accept(visitor);
        return visitor.getProgram();
    }
}
