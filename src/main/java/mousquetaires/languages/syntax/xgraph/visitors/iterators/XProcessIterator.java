package mousquetaires.languages.syntax.xgraph.visitors.iterators;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.visitors.traversers.XProcessTraverser;

import java.util.Iterator;


public abstract class XProcessIterator implements Iterator<XEvent> {
    private final XProcessTraverser traverser;
    private XEvent current;

    public XProcessIterator(XProcessTraverser traverser) {
        this.traverser = traverser;
    }

    @Override
    public boolean hasNext() {
        return traverser.hasNextEvent(current);
    }
}
