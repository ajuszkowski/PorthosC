package mousquetaires.languages.syntax.xrepr.processes;


public class XPostProcess extends XProcess {

    public XPostProcess(XProcessBuilder builder) {
        super(builder);
        if (getSharedMemoryEvents().size() > 0) {
            throw new IllegalArgumentException(getClass().getName() + " should not contain assignments to shared variables");
        }
    }
}
