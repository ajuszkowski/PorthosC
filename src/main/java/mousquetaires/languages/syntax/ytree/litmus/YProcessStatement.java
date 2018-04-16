package mousquetaires.languages.syntax.ytree.litmus;

import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.types.YMethodSignature;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;


/**
 * Temporary class representing explicitly defined processName in c-like code.
 */
public class YProcessStatement extends YFunctionDefinition {

    private final XProcessId processId;

    public YProcessStatement(YMethodSignature signature, YCompoundStatement body) {
        super(signature, body);
        this.processId = new XProcessId(signature.getName());
    }

    public XProcessId getProcessId() {
        return processId;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return CollectionUtils.createIteratorFrom(getBody());
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return getProcessId() + " " + getBody();
    }
}
