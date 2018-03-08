//package mousquetaires.languages.syntax.xgraph.events.fakes;
//
//import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
//import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
//import mousquetaires.languages.syntax.xgraph.events.XEventInfo;
//import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
//import mousquetaires.utils.exceptions.NotSupportedException;
//
//
//public class XFakeComputationEvent extends XFakeEvent implements XComputationEvent {
//
//    public XFakeComputationEvent(XEventInfo info) {
//        super(info);
//    }
//
//    @Override
//    public String getName() {
//        return "fakecomp_" + getUniqueId();
//    }
//
//    @Override
//    public <T> T accept(XEventVisitor<T> visitor) {
//        throw new NotSupportedException();
//    }
//
//    @Override
//    public XMemoryUnit.Bitness getBitness() {
//        throw new NotSupportedException();
//    }
//}
