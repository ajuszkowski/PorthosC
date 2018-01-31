package mousquetaires.languages.syntax.xrepr.processes.notyet;


import mousquetaires.languages.syntax.xrepr.processes.XProcessBuilder;


public class XPostProcessBuilder extends XProcessBuilder {

    public static final String postProcessId = "__post_process";

    public XPostProcessBuilder() {
        super(postProcessId);
    }

    @Override
    public XPostProcess build() {
        finishBuilding();
        return new XPostProcess(this);
    }
}
