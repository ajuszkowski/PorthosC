package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import dartagnan.program.Register;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.memories.XLocalLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.languages.syntax.xgraph.process.XUnrolledProcess;

import java.util.ArrayList;
import java.util.Set;

import static mousquetaires.utils.StringUtils.wrap;


public final class XUnrolledProgram extends XProgramBase<XUnrolledProcess> {

    XUnrolledProgram(ImmutableList<XUnrolledProcess> processes) {
        super(processes);
    }

    public XUnrolledProcess getProcess(XProcessId processId) {
        for (XUnrolledProcess process : getProcesses()) {
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
        XUnrolledProcess process = getProcess(getProcessId(one, two));
        return process.compareTopologically(one, two);
    }

    public int compareTopologicallyAndCondLevel(XEvent one, XEvent two) {
        XUnrolledProcess process = getProcess(getProcessId(one, two));
        return process.compareTopologicallyAndCondLevel(one, two);
    }

    public Set<XLocalLvalueMemoryUnit> getCondRegs(XEvent event) {
        return getProcess(event.getProcessId()).getCondRegs(event);
    }
}
