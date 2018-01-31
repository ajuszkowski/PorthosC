package mousquetaires.languages.syntax.xrepr.processes.notyet;


import mousquetaires.languages.syntax.xrepr.processes.XProcess;
import mousquetaires.languages.syntax.xrepr.processes.XProcessBuilder;


public class XPostProcess extends XProcess {

    public XPostProcess(XProcessBuilder builder) {
        super(builder);
        if (getSharedMemoryEvents().size() > 0) {
            throw new IllegalArgumentException(getClass().getName() + " should not contain assignments to shared variables");
        }
    }
}
