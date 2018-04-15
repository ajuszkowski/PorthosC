package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;
import java.util.Objects;


// TODO: NOTE!!! IN FUNCTION INVOCATION FUNC. NAME IS YVariableRef!
public class YLabeledVariable extends YVariable {

    private final String processId;

    public YLabeledVariable(String processId, String name) {
        super(Kind.Local, name);
        this.processId = processId;
    }

    public YLabeledVariable withKind(Kind kind) {
        throw new IllegalStateException("Not applicable: labeled variables are always local");
    }

    public String getProcessId() {
        return processId;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return getProcessId() + ':' + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof YLabeledVariable)) { return false; }
        if (!super.equals(o)) { return false; }
        YLabeledVariable that = (YLabeledVariable) o;
        return Objects.equals(getProcessId(), that.getProcessId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProcessId());
    }
}
