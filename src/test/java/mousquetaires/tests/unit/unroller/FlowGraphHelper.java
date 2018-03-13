package mousquetaires.tests.unit.unroller;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.XEventRef;


class FlowGraphHelper {
    static XEventRef ref(XEvent event, int id) {
        return new XEventRef(event, id);
    }

}
