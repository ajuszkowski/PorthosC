package mousquetaires.app.modules.dartagnan;

import mousquetaires.app.modules.AppVerdict;
import mousquetaires.app.options.AppOptions;


public class DartagnanVerdict extends AppVerdict {
    public enum Status {
        Reachable,
        NonReachable,
    }

    public Status result;

    public DartagnanVerdict(AppOptions options) {
        super(options);
    }
}
