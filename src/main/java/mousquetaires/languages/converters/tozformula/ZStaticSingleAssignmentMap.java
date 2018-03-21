package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.events.XEvent;

import java.util.HashMap;


public class ZStaticSingleAssignmentMap extends HashMap<XEvent, ZVariableReferenceMap> {

    public void copyValues(XEvent parent, XEvent child) {
        assert !containsKey(child) : child + ", " + this;
        assert containsKey(parent) : parent + ", " + this;
        ZVariableReferenceMap childMap  = new ZVariableReferenceMap(get(parent));
        put(child, childMap);
    }
}
