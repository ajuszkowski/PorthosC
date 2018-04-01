package mousquetaires.languages.syntax.xgraph.events;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Z3Exception;
import mousquetaires.languages.common.graph.FlowGraphNode;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;


public interface XEvent extends FlowGraphNode<XEventInfo> {

    <T> T accept(XEventVisitor<T> visitor);

    @Override
    XEvent withInfo(XEventInfo newInfo);


    //TODO: old-code method, to be replaced
    default BoolExpr executes(Context ctx) {
        return ctx.mkBoolConst(String.format("ex(%s)", getName()));
    }
}
