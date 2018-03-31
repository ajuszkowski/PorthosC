package mousquetaires.languages.syntax.xgraph.events.memory;


import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;


public interface XLocalMemoryEvent extends XMemoryEvent {

    @Override
    XLocalMemoryUnit getSource();

    @Override
    XLocalMemoryUnit getDestination();
}
