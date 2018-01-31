package mousquetaires.languages.syntax.xrepr.events.controlflow;

import mousquetaires.languages.syntax.xrepr.events.XEvent;


public interface XControlFlowEvent extends XEvent {

    XEvent getFromEvent();

    XEvent getToEvent();
}
