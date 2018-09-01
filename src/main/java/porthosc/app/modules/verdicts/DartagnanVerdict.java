package porthosc.app.modules.verdicts;

import porthosc.app.options.AppOptions;


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
