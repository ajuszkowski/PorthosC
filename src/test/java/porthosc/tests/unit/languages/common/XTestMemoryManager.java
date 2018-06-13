package porthosc.tests.unit.languages.common;

import porthosc.languages.converters.toxgraph.interpretation.XMemoryManagerImpl;
import porthosc.languages.syntax.xgraph.process.XProcessId;


public class XTestMemoryManager extends XMemoryManagerImpl {

    public XTestMemoryManager(XProcessId processId) {
        reset(processId);
    }
}
