package mousquetaires.program.barriers;

import java.util.Collections;

public class Mfence extends Barrier {

    public Mfence() {
        this.condLevel = 0;
    }

    public String toString() {
        return String.format("%smfence", String.join("", Collections.nCopies(condLevel, "  ")));
    }

    public Mfence clone() {
        Mfence newMfence = new Mfence();
        newMfence.condLevel = condLevel;
        return newMfence;
    }

    public static class Sync extends Barrier {

        public Sync() {
            this.condLevel = 0;
        }

        public String toString() {
            return String.format("%ssync", String.join("", Collections.nCopies(condLevel, "  ")));
        }

        public Sync clone() {
            Sync newSync = new Sync();
            newSync.condLevel = condLevel;
            return newSync;
        }
    }
}
