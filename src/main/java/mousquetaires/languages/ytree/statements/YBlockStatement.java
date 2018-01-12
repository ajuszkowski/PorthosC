package mousquetaires.languages.ytree.statements;

import com.google.common.collect.ImmutableList;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;


public class YBlockStatement extends YStatement {

    public final ImmutableList<YStatement> statements;

    public YBlockStatement(YStatement... statements) {
        this(null, ImmutableList.copyOf(statements));
    }

    private YBlockStatement(String label, ImmutableList<YStatement> statements) {
        super(label);
        this.statements = statements;
    }

    public YBlockStatement(ImmutableList<YStatement> statements) {
        this(null, statements);
    }

    @Override
    public YBlockStatement withLabel(String newLabel) {
        return new YBlockStatement(newLabel, this.statements);
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(statements);
    }

    @Override
    public void accept(YtreeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder('{');
        for (YStatement statement : statements) {
            builder.append(statement.toString()).append(" ");
        }
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YBlockStatement)) return false;
        YBlockStatement that = (YBlockStatement) o;
        return Objects.equals(statements, that.statements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statements);
    }
}
