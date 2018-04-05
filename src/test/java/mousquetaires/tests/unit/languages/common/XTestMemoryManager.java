package mousquetaires.tests.unit.languages.common;

import mousquetaires.languages.converters.toxgraph.interpretation.XMemoryManager;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;


public class XTestMemoryManager extends XMemoryManager {

    public XTestMemoryManager(XProcessId processId) {
        reset(processId);
    }
}
