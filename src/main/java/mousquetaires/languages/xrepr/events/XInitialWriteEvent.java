package mousquetaires.languages.xrepr.events;

import mousquetaires.languages.xrepr.memory.XMemoryLocation;


/** Initial write event to any kind of memory location ({@link XMemoryLocation}) */
public class XInitialWriteEvent extends XMemoryEvent {

    public final XMemoryLocation source;

    // todo: value - ?

    public XInitialWriteEvent(XEventInfo info, XMemoryLocation source) {
        super(info);
        this.source = source;
    }

    @Override
    public String toString() {
        return "iw" + source + ')';
    }
}
