package mousquetaires.app.modules.porthos;

import mousquetaires.app.modules.AppVerdict;
import mousquetaires.app.options.AppOptions;


public class PorthosVerdict extends AppVerdict {
    public enum Status {
        StatePortable,
        NonStatePortable,
        ExecutionPortable,
        NonExecutionPortable,
        ;
    }

    public PorthosVerdict.Status result;

    public int iterations;

    public PorthosVerdict(AppOptions options) {
        super(options);
    }
}
