package mousquetaires.languages.syntax.xgraph.visitors.iterators.old;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;

import java.util.Iterator;
import java.util.Queue;


public class XgraphLineariserTraverser implements Iterator<XEvent> {

    private final Iterator<XEvent> linearisedOrderIterator;

    //TODO: traverse graph on verifying stage (right after building) and saving this info as min-path labels foreach node
    public XgraphLineariserTraverser(XProcess process) {
        XgraphLineariser lineariser = new XgraphLineariser(process);
        Queue<XEvent> linearsedEventsList = lineariser.build();
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
