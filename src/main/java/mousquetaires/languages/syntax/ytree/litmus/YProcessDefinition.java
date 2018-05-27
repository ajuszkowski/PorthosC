package mousquetaires.languages.syntax.ytree.litmus;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.types.YFunctionSignature;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;


/**
 * Temporary class representing explicitly defined processName in c-like code.
 */
public final class YProcessDefinition extends YFunctionDefinition {

    private final XProcessId processId;

    public YProcessDefinition(Origin origin, YFunctionSignature signature, YCompoundStatement body) {
        super(origin, signature, body);
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
