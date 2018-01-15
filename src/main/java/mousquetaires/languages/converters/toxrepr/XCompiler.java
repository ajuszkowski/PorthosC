package mousquetaires.languages.converters.toxrepr;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.types.YXType;
import mousquetaires.languages.syntax.xrepr.memory.XMemoryUnit;
import mousquetaires.languages.syntax.xrepr.datamodels.DataModel;


public class XCompiler {
    private final XProgramBuilder programBuilder;
    private final XMemoryManager memoryManager;

    public XCompiler(ProgramLanguage language, DataModel dataModel) {
        this.programBuilder = new XProgramBuilder(language, dataModel);
        this.memoryManager = new XMemoryManager();
    }

    public void startProcessDefinition(String name) {
        programBuilder.startProcessDefinition(name);
    }

    public void endProcessDefinition() {
        programBuilder.endProcessDefinition();
    }

    public XMemoryUnit declareLocalMemoryUnit(String name, YXType type) {
        return memoryManager.declareLocalMemoryUnit(name, type);
    }

    public XMemoryUnit declareSharedMemoryUnit(String name, YXType type) {
        return memoryManager.declareSharedMemoryUnit(name, type);
    }

    public XMemoryUnit getLocalMemoryUnit(String name) {
        return memoryManager.tryFindLocalMemory(name);
    }

    public XMemoryUnit getSharedMemoryUnit(String name) {
        return memoryManager.tryFindSharedMemory(name);
    }


}
