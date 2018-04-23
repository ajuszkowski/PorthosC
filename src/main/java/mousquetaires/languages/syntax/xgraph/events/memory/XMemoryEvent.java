package mousquetaires.languages.syntax.xgraph.events.memory;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;


public interface XMemoryEvent extends XEvent {

    XMemoryUnit getSource();

    XMemoryUnit getDestination();

    //XLocalMemoryUnit getReg();
}
