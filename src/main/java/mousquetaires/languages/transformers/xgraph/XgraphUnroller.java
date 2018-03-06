package mousquetaires.languages.transformers.xgraph;

import mousquetaires.languages.syntax.xgraph.processes.XProcess;
import mousquetaires.languages.syntax.xgraph.visitors.iterators.XProcessIterator;
import mousquetaires.languages.syntax.xgraph.visitors.traversers.XProcessSimpleTraverser;


public class XgraphUnroller extends XProcessIterator {

    public XgraphUnroller(XProcess cyclicProcess) {
        super(new XProcessSimpleTraverser());
    }
}
