package mousquetaires.languages.xrepr.events;

import mousquetaires.languages.xrepr.memory.XLocation;


/** Initial write event to any kind of memory location ({@link XLocation}) */
public class XInitialWriteEvent extends XMemoryEvent {

    public final XLocation source;

    // todo: value - ?

    public XInitialWriteEvent(XEventInfo info, XLocation source) {
        super(info);
        this.source = source;
    }

    @Override
    public String toString() {
        return "iw" + source + ')';
    }
}
