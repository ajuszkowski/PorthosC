package mousquetaires.languages.syntax.ytree.types;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YParameter;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;


public class YMethodSignature implements YEntity {
    private final String name;
    private final YType returnType;
    private final YParameter[] parameters;

    public YMethodSignature(String name, YType returnType, YParameter[] parameters) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public YType getReturnType() {
        return returnType;
    }

    public YParameter[] getParameters() {
        return parameters;
    }

    @Override
    public CodeLocation codeLocation() {
        return CodeLocation.empty; //TODO: method signature must not be a YEntity! this is a common class used by all representations!
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(returnType).append(" ").append(name).append("(");
        for (int i = 0; i < parameters.length; i++) {
            sb.append(parameters[i]);
            if (i < parameters.length - 1) {
                sb.append(",");
            }
        }
        sb.append(")]");
        return sb.toString();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
