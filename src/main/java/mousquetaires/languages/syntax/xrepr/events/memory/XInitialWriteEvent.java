package mousquetaires.languages.syntax.xrepr.events.memory;

import mousquetaires.languages.syntax.xrepr.XValue;
import mousquetaires.languages.syntax.xrepr.events.XEventInfo;
import mousquetaires.languages.syntax.xrepr.memory.XMemoryUnit;


/** Initial write event to any kind of memory location ({@link XMemoryUnit}) */
public class XInitialWriteEvent extends XMemoryEvent {

    public final XMemoryUnit memoryUnit;
    public final XValue value;


    public XInitialWriteEvent(XEventInfo info, XMemoryUnit memoryUnit, XValue value) {
        super(info);
        this.memoryUnit = memoryUnit;
        this.value = value;
    }

    @Override
    public String toString() {
        return "initial_write(" + memoryUnit + "<- " + value + ")";
    }
}
