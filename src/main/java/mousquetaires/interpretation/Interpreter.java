package mousquetaires.interpretation;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.common.types.YXType;
import mousquetaires.languages.xrepr.XProcess;
import mousquetaires.languages.xrepr.events.memory.XStoreEvent;
import mousquetaires.languages.xrepr.memory.XMemoryUnit;
import mousquetaires.languages.xrepr.memory.XLocalMemory;
import mousquetaires.languages.xrepr.memory.XSharedMemory;
import mousquetaires.languages.xrepr.memory.datamodels.DataModel;
import mousquetaires.utils.exceptions.NotImplementedException;


public class Interpreter {

    private final MemoryManager memoryManager;
    private final DataFlowManager dataFlowManager;
    //private final TypeManager typeManager;

    public Interpreter(ProgramLanguage language, DataModel dataModel) {
        this.memoryManager = new MemoryManager();
        this.dataFlowManager = new DataFlowManager();
        //this.typeManager   = new TypeManager(language, dataModel);
    }

    public XProcess declareProcess(String name) {

    }

    public XMemoryUnit declareLocalMemoryUnit(String name, YXType type) {
        return memoryManager.declareLocalMemoryUnit(name, type);
    }

    public XMemoryUnit declareSharedMemoryUnit(String name, YXType type) {
        return memoryManager.declareSharedMemoryUnit(name, type);
    }

    public XMemoryUnit tryGetMemoryLocation(String name) {
        XSharedMemory sharedLocation = memoryManager.tryFindSharedMemory(name);
        if (sharedLocation != null) {
            return sharedLocation;
        }
        XLocalMemory registryLocation = memoryManager.tryFindLocalMemory(name);
        if (registryLocation != null) {
            return registryLocation;
        }
        return null;
    }

    public void processBranchingStatement() {
        throw new NotImplementedException();
    }

    public void processWriteEvent(XStoreEvent writeEvent) {
        dataFlowManager.registerWriteEvent(writeEvent);
    }
}
