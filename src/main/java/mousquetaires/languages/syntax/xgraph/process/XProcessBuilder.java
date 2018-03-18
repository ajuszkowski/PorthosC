package mousquetaires.languages.syntax.xgraph.process;

import com.google.common.collect.ImmutableMap;


public class XProcessBuilder extends XProcessBuilderBase<XProcess> {

    public XProcessBuilder(String processId) {
        super(processId);
    }

    @Override
    public XProcess build() {
        return new XProcess(getProcessId(), getSource(), getSink(),
                ImmutableMap.copyOf(getEdges(true)),
                ImmutableMap.copyOf(getEdges(false)));
    }
}
