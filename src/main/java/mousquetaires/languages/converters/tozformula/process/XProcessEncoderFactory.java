package mousquetaires.languages.converters.tozformula.process;

import com.microsoft.z3.Context;
import mousquetaires.languages.converters.tozformula.StaticSingleAssignmentMap;
import mousquetaires.languages.converters.tozformula.XDataflowEncoder;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;


public class XProcessEncoderFactory {

    private final Context ctx;
    private final StaticSingleAssignmentMap ssaMap;
    private final XDataflowEncoder dataFlowEncoder;

    public XProcessEncoderFactory(Context ctx, StaticSingleAssignmentMap ssaMap, XDataflowEncoder dataFlowEncoder) {
        this.ctx = ctx;
        this.ssaMap = ssaMap;
        this.dataFlowEncoder = dataFlowEncoder;
    }

    public XProcessEncoder getEncoder(XProcess process) {
        // TODO: switch not by process-id, by but type
        XProcessId processId = process.getId();
        if (processId == XProcessId.PreludeProcessId) {
            return new XPreludeEncoder(ctx, ssaMap, dataFlowEncoder);
        }
        if (processId == XProcessId.PostludeProcessId) {
            return new XPostludeEncoder(ctx, ssaMap, dataFlowEncoder);
        }
        return new XThreadEncoder(ctx, ssaMap, dataFlowEncoder);
    }
}
