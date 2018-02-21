package mousquetaires.languages.visitors.xgraph;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;

import java.util.Iterator;
import java.util.Queue;


public class XgraphLinearisedTraverser implements Iterator<XEvent> {

    private final Iterator<XEvent> linearisedOrderIterator;

    public XgraphLinearisedTraverser(XProcess process) {
        XgraphLinearisator linearisator = new XgraphLinearisator(process);
        Queue<XEvent> linearsedEventsList = linearisator.build();
        linearisedOrderIterator = linearsedEventsList.iterator();
    }

    @Override
    public boolean hasNext() {
        return linearisedOrderIterator.hasNext();
    }

    @Override
    public XEvent next() {
        return linearisedOrderIterator.next();
    }

}
