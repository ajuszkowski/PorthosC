//package mousquetaires.languages.syntax.xrepr.events.memory;
//
//import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;
//import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
//import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
//import mousquetaires.languages.syntax.xgraph.memories.XConstant;
//
//
///** Initial write event to any kind of memoryevents location ({@link XMemoryUnit}) */
//public class XInitialWriteEvent extends XLocalMemoryEvent {
//
//    public XInitialWriteEvent(XEventInfo info, XLocalMemoryUnit destination, XConstant value) {
//        super(info, destination, value);
//    }
//
//    @Override
//    public String toString() {
//        return "initial_write(" + getDestination() + "<- " + getSource() + ")";
//    }
//}
