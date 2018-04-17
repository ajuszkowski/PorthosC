package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLocalLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.languages.syntax.xgraph.process.XProcess;

import java.util.Set;

import static mousquetaires.utils.StringUtils.wrap;


public final class XUnrolledProgram extends XProgramBase<XProcess> {

    XUnrolledProgram(ImmutableList<XProcess> processes) {
        super(processes);
    }

    public XProcess getProcess(XProcessId processId) {
        for (XProcess process : getProcesses()) {
            if (process.getId() == processId) {
                return process;
            }
        }
        throw new IllegalArgumentException("Process not found: " + processId);
    }

    private XProcessId getProcessId(XEvent one, XEvent two) {
        XProcessId processId = one.getProcessId();
        if (processId != two.getProcessId()) {
            throw new IllegalArgumentException("Comparing events must belong to the same process");
        }
        return processId;
    }


    public int compareTopologically(XEvent one, XEvent two) {
        XProcess process = getProcess(getProcessId(one, two));
        return process.compareTopologically(one, two);
    }

    public int compareTopologicallyAndCondLevel(XEvent one, XEvent two) {
        XProcess process = getProcess(getProcessId(one, two));
        return process.compareTopologicallyAndCondLevel(one, two);
    }

    public Set<XLocalLvalueMemoryUnit> getCondRegs(XEvent event) {
        return getProcess(event.getProcessId()).getCondRegs(event);
    }
}
