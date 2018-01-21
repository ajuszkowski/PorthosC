package mousquetaires.languages.syntax.xrepr.processes;

import mousquetaires.languages.converters.toxrepr.XMemoryManager;


public class XPreProcessBuilder extends XProcessBuilder {

    public static final int preProcessId = 0;

    public XPreProcessBuilder(XMemoryManager memoryManager) {
        super(preProcessId, memoryManager);
    }

    @Override
    public XPreProcess build() {
        finish();
        return new XPreProcess(this);
    }
}
