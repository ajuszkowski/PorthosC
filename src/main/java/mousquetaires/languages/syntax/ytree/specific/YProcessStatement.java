package mousquetaires.languages.syntax.ytree.specific;

import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YUnlabeledStatement;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;
import java.util.Objects;


/**
 * Temporary class representing explicitly defined processName in c-like code.
 */
public class YProcessStatement extends YUnlabeledStatement {

    private final XProcessId processId;
    private final YCompoundStatement body;

    public YProcessStatement(XProcessId processId, YCompoundStatement body) {
        this.processId = processId;
        this.body = body;
    }

    public XProcessId getProcessId() {
        return processId;
    }

    public YCompoundStatement getBody() {
        return body;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom(body);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return getProcessId() + " " + getBody();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YProcessStatement)) return false;
        YProcessStatement yProcess = (YProcessStatement) o;
        return Objects.equals(processId, yProcess.processId) &&
                Objects.equals(body, yProcess.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processId, body);
    }
}
