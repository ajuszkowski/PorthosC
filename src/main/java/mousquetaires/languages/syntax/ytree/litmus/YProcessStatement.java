package mousquetaires.languages.syntax.ytree.litmus;

import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.types.YMethodSignature;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;


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
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return getProcessId() + " " + getBody();
    }
}
