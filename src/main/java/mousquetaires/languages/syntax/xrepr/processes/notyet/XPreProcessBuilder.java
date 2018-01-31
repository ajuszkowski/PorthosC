package mousquetaires.languages.syntax.xrepr.processes.notyet;


import mousquetaires.languages.syntax.xrepr.processes.XProcessBuilder;


public class XPreProcessBuilder extends XProcessBuilder {

    public static final String preProcessId = "__pre_process";

    public XPreProcessBuilder() {
        super(preProcessId);
    }

    @Override
    public XPreProcess build() {
        finishBuilding();
        return new XPreProcess(this);
    }
}
