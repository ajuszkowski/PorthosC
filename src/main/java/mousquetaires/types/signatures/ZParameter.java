package mousquetaires.types.signatures;

import mousquetaires.types.ZType;


public class ZParameter {

    private final ZType type;
    private final String name;

    public ZParameter(ZType type, String name) {
        this.type = type;
        this.name = name;
    }

    public ZType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}
