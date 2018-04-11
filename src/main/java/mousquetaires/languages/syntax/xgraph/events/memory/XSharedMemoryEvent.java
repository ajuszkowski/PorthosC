package mousquetaires.languages.syntax.xgraph.events.memory;


import mousquetaires.languages.syntax.xgraph.memories.XLocalLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XSharedLvalueMemoryUnit;


public interface XSharedMemoryEvent extends XMemoryEvent {

    XSharedLvalueMemoryUnit getLoc();

    //@Override
    XLocalLvalueMemoryUnit getReg();
}
