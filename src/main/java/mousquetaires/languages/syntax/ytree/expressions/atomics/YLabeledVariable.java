package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.Objects;


public class YLabeledVariable extends YVariable {

    private final String label;

    public YLabeledVariable(CodeLocation location, String label, String name) {
        this(location, label, name, 0);
    }

    private YLabeledVariable(CodeLocation location, String label, String name, int pointerLevel) {
        super(location, Kind.Local, name, pointerLevel);
        this.label = label;
    }


    public String getLabel() {
        return label;
    }

    @Override
    public YVariable asGlobal() {
        throw new NotSupportedException("Labeled variables may be only local");
    }

    @Override
    public YLabeledVariable withPointerLevel(int level) {
        return new YLabeledVariable(codeLocation(), getLabel(), getName(), level);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return getLabel() + ':' + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof YLabeledVariable)) { return false; }
        if (!super.equals(o)) { return false; }
        YLabeledVariable that = (YLabeledVariable) o;
        return Objects.equals(getLabel(), that.getLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLabel());
    }
}
