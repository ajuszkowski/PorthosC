package mousquetaires.languages.syntax.ytree.specific;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.statements.YSequenceStatement;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;
import java.util.Objects;


/**
 * Temporary class representing explicitly defined processName in c-like code.
 */
public class YProcessStatement extends YStatement {

    private final int processId;
    private final YSequenceStatement body;

    public YProcessStatement(int processId, YSequenceStatement body) {
        this.processId = processId;
        this.body = body;
    }

    public int getProcessId() {
        return processId;
    }

    public YSequenceStatement getBody() {
        return body;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(body);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YProcessStatement copy() {
        return new YProcessStatement(processId, body);
    }

    @Override
    public String toString() {
        return 'P' + processId + " " + body;
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
