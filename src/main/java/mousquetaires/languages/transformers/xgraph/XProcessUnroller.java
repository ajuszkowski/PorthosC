package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.syntax.xgraph.processes.XProcess;
import mousquetaires.utils.patterns.Transformer;


public class XProcessUnroller implements Transformer<XProcess> {


    @Override
    public XProcess transform(XProcess process) {
        XgraphUnroller visitor = new XgraphUnroller();
        return process.
    }
}
