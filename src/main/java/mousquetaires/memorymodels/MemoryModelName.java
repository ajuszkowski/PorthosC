package mousquetaires.memorymodels;

// TODO: replace abbreviations with full names
public enum MemoryModelName {
    SC,
    TSO,
    PSO,
    RMO,
    Alpha,
    Power,
    ARM,
    ;

    // implementation as flags:
    //NONE  (0),
    //SC    (1 << 0),
    //TSO   (1 << 1),
    //PSO   (1 << 2),
    //RMO   (1 << 3),
    //Alpha (1 << 4),
    //Power (1 << 5),
    //ARM   (1 << 6);
    //
    //public final int flags;
    //MemoryModelName(Integer weight) {
    //    this.flags = weight;
    //}
    //public final int flags;
    //MemoryModelName(Integer weight) {
    //    this.flags = weight;
    //}

    public static MemoryModelName parse(String value) {
        switch (value.toLowerCase()) {
            case "sc":
                return MemoryModelName.SC;
            case "tso":
                return MemoryModelName.TSO;
            case "pso":
                return MemoryModelName.PSO;
            case "rmo":
                return MemoryModelName.RMO;
            case "alpha":
                return MemoryModelName.Alpha;
            case "power":
                return MemoryModelName.Power;
            case "arm":
                return MemoryModelName.ARM;
            default:
                return null;
        }
    }

    public boolean is(MemoryModelName other) {
        return this == other;
    }
}
