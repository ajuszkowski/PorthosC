package mousquetaires.types.signatures;

import mousquetaires.types.ZType;


public class ZMethodSignature {
    private final String name;
    private final ZType returnType;
    private final ZParameter[] parameters;

    public ZMethodSignature(String name, ZType returnType, ZParameter[] parameters) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public ZType getReturnType() {
        return returnType;
    }

    public ZParameter[] getParameters() {
        return parameters;
    }
}
