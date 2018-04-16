package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

import java.util.Objects;


public class YParameter extends YVariable {

    private final YType type;

    public YParameter(String name, YType type) {
        super(Kind.Global, name);
        this.type = type;
    }

    public YType getType() {
        return type;
    }


    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof YParameter)) { return false; }
        if (!super.equals(o)) { return false; }
        YParameter that = (YParameter) o;
        return Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType());
    }
}
