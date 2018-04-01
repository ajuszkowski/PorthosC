package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.process.XProcess;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;


public final class XProgram extends XProgramBase<XProcess> {

    XProgram(ImmutableList<XProcess> processes) {
        super(processes);
    }


    //TODO: old-code method, to be replaced
    public Set<XEvent> getEvents() {
        Set<XEvent> ret = new HashSet<>();
        for (XProcess process : getAllProcesses()) {
            ret.addAll(process.getAllEvents());
        }
        return ret;
    }
}
