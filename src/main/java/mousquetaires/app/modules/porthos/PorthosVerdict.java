package mousquetaires.app.modules.porthos;

import mousquetaires.app.modules.AppVerdict;


public class PorthosVerdict extends AppVerdict {
    public enum Status {
        StatePortable,
        NonStatePortable,
    }

    public PorthosVerdict.Status result;

    public int iterations;
}
