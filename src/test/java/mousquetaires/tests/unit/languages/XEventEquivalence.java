package mousquetaires.tests.unit.languages;

import com.google.common.base.Equivalence;
import mousquetaires.languages.syntax.xgraph.events.XEvent;


public class XEventEquivalence extends Equivalence<XEvent> {

    @Override
    protected boolean doEquivalent(XEvent a, XEvent b) {
        return a.equals(b);
    }

    @Override
    protected int doHash(XEvent event) {
        return event.hashCode();
    }
}
