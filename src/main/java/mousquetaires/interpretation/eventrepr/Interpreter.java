package mousquetaires.interpretation.eventrepr;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.common.types.YXType;
import mousquetaires.languages.xrepr.events.XWriteEvent;
import mousquetaires.languages.xrepr.memory.XLocalLocation;
import mousquetaires.languages.xrepr.memory.XLocation;
import mousquetaires.languages.xrepr.memory.XSharedLocation;
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

    public XLocation createMemoryLocation(String name, YXType type, XLocation.Kind kind) {
        switch (kind) {
            case Local:
                return memoryManager.defineRegistryLocation(name, type);
            case Shared:
                return memoryManager.defineSharedLocation(name, type);
            default:
                throw new IllegalArgumentException(kind.toString());
        }
    }

    public XLocation tryGetMemoryLocation(String name) {
        XSharedLocation sharedLocation = memoryManager.tryGetSharedLocation(name);
        if (sharedLocation != null) {
            return sharedLocation;
        }
        XLocalLocation registryLocation = memoryManager.tryGetLocalLocation(name);
        if (registryLocation != null) {
            return registryLocation;
        }
        return null;
    }

    public void processBranchingStatement() {
        throw new NotImplementedException();
    }

    public void processWriteEvent(XWriteEvent writeEvent) {
        dataFlowManager.registerWriteEvent(writeEvent);
    }
}
