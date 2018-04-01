package mousquetaires.memorymodels;

// TODO: replace abbreviations with full aliases
public enum MemoryModelName {
    SC,
    TSO,
    PSO,
    RMO,
    Alpha,
    Power,
    ARM,
    ;

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
