package mousquetaires.languages.syntax.xrepr.events.controlflow;

import mousquetaires.languages.converters.toxrepr.XEventInfo;


public class XUnconditionalJumpEvent extends XControlFlowEvent {

    public final String jumpToLabel;  //TODO: abstrairez le label et gérez leurs en XProgramBuilder

    public XUnconditionalJumpEvent(XEventInfo info, String jumpToLabel) {
        super(info);
        this.jumpToLabel = jumpToLabel;
    }
}
