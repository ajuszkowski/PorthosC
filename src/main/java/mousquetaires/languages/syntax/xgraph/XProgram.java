package mousquetaires.languages.syntax.xgraph;

import com.google.common.collect.ImmutableList;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import dartagnan.program.Thread;
import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.process.XProcess;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;


public final class XProgram extends XProgramBase<XProcess> {

    XProgram(ImmutableList<XProcess> processes) {
        super(processes);
    }

}
