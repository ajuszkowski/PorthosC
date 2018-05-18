package mousquetaires.tests.unit.languages.common;

import mousquetaires.languages.converters.toxgraph.interpretation.XMemoryManagerImpl;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;


public class XTestMemoryManager extends XMemoryManagerImpl {

    public XTestMemoryManager(XProcessId processId) {
        reset(processId);
    }
}
