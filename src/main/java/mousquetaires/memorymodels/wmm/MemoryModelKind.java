package mousquetaires.memorymodels.wmm;

// TODO: replace abbreviations with full aliases
public enum MemoryModelKind {
    SC,
    TSO,
    PSO,
    RMO,
    Alpha,
    Power,
    ARM,
    ;

    public static MemoryModelKind parse(String value) {
        switch (value.toLowerCase()) {
            case "sc":
                return MemoryModelKind.SC;
            case "tso":
                return MemoryModelKind.TSO;
            case "pso":
                return MemoryModelKind.PSO;
            case "rmo":
                return MemoryModelKind.RMO;
            case "alpha":
                return MemoryModelKind.Alpha;
            case "power":
                return MemoryModelKind.Power;
            case "arm":
                return MemoryModelKind.ARM;
            default:
                return null;
        }
    }

    public boolean is(MemoryModelKind other) {
        return this == other;
    }
}
