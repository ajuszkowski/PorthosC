package mousquetaires.languages.syntax.xgraph.events;

import mousquetaires.languages.syntax.xgraph.processes.XEventInfo;


public abstract class XEventBase implements XEvent {
    //private XEvent nextEvent;
    private final XEventInfo info;

    public XEventBase(XEventInfo info) {
        this.info = info;
    }

    @Override
    public XEventInfo getInfo() {
        return info;
    }

    //@Override
    //public void setNextEvent(XEvent next) {
    //    if (nextEvent != null) {
    //        throw new IllegalStateException("Next event has already been assigned");
    //    }
    //    nextEvent = next;
    //}
    //
    //@Override
    //public XEvent getNextEvent() {
    //    return nextEvent;
    //}

    @Override
    public String getUniqueId() {
        return getInfo().getProcessId() + "evt+ " + hashCode();
    }
}
