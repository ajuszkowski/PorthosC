package mousquetaires.app.modules.dartagnan;

import mousquetaires.app.modules.AppVerdict;


public class DartagnanVerdict extends AppVerdict {
    public enum ReachabilityStatus {
        Reachable,
        NotReachable,
    }

    public ReachabilityStatus result;
}
