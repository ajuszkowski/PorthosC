package mousquetaires.languages.syntax.ytree.expressions;

import java.util.Objects;

public class YProcessVariableRef extends YVariableRef {

    private final int processId;

    private YProcessVariableRef(String name, int processId) {
        super(name);
        this.processId = processId;
    }

    public static YProcessVariableRef create(YVariableRef variable, int processId) {
        return new YProcessVariableRef(variable.getName(), processId);
    }

    @Override
    public String toString() {
        return processId + ":" + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YProcessVariableRef)) return false;
        if (!super.equals(o)) return false;
        YProcessVariableRef that = (YProcessVariableRef) o;
        return processId == that.processId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), processId);
    }
}
