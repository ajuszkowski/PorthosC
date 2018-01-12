package mousquetaires.languages.ytree.statements.artificial;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.statements.YBlockStatement;
import mousquetaires.languages.ytree.statements.YStatement;
import mousquetaires.utils.YtreeUtils;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.Iterator;
import java.util.Objects;


/**
 * Temporary class representing explicitly defined process in c-like code.
 */
public class YProcess extends YStatement {

    public final String name;
    public final YBlockStatement body;

    public YProcess(String name, YBlockStatement body) {
        super(null);
        this.name = name;
        this.body = body;
    }

    @Override
    public YStatement withLabel(String newLabel) {
        throw new NotSupportedException();
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(body);
    }

    @Override
    public void accept(YtreeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "process " + name + " " + body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YProcess)) return false;
        YProcess yProcess = (YProcess) o;
        return Objects.equals(name, yProcess.name) &&
                Objects.equals(body, yProcess.body);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, body);
    }
}
