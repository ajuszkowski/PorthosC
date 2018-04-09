package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.process.XUnrolledProcess;

import java.util.function.Predicate;


public final class XUnrolledProgram extends XProgramBase<XUnrolledProcess> {

    XUnrolledProgram(ImmutableList<XUnrolledProcess> processes) {
        super(processes);
    }

    public ImmutableSet<XEvent> getEvents(Predicate<XEvent> filter) {
        return getNodes(filter);
    }
}
