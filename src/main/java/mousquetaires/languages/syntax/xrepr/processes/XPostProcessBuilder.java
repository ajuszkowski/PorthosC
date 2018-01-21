package mousquetaires.languages.syntax.xrepr.processes;

import mousquetaires.languages.converters.toxrepr.XMemoryManager;


public class XPostProcessBuilder extends XProcessBuilder {

    public static final int postProcessId = -1;

    public XPostProcessBuilder(XMemoryManager memoryManager) {
        super(postProcessId, memoryManager);
    }

    @Override
    public XPostProcess build() {
        finish();
        return new XPostProcess(this);
    }
}
