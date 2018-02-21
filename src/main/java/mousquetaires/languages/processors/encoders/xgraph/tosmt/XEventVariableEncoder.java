package mousquetaires.languages.processors.encoders.xgraph.tosmt;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import mousquetaires.languages.syntax.xgraph.events.XEvent;


public class XEventVariableEncoder {
    private final Context ctx;

    public XEventVariableEncoder(Context ctx) {
        this.ctx = ctx;
    }

    public BoolExpr encode(XEvent event) {
        return ctx.mkBoolConst(event.getUniqueId());
    }

}
