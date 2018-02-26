//package mousquetaires.languages.syntax.xrepr.events.memory;
//
//import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
//import mousquetaires.languages.syntax.xgraph.memories.XRegister;
//import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
//import mousquetaires.languages.syntax.xgraph.memories.XConstant;
//
//
///** Initial write event to any kind of memoryevents location ({@link XMemoryUnit}) */
//public class XInitialWriteEvent extends XRegisterMemoryEvent {
//
//    public XInitialWriteEvent(XEventInfo info, XRegister destination, XConstant value) {
//        super(info, destination, value);
//    }
//
//    @Override
//    public String toString() {
//        return "initial_write(" + getDestination() + "<- " + getSource() + ")";
//    }
//}
