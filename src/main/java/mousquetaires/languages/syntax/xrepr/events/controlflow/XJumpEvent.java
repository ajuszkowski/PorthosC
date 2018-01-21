package mousquetaires.languages.syntax.xrepr.events.controlflow;

import mousquetaires.languages.syntax.xrepr.processes.XEventInfo;


public class XJumpEvent extends XControlFlowEvent {

    public final String jumpToLabel;  //TODO: abstrairez le label et gérez leurs en XProgramBuilder

    public XJumpEvent(XEventInfo info, String jumpToLabel) {
        super(info);
        this.jumpToLabel = jumpToLabel;
    }
}
