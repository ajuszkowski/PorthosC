package mousquetaires.languages.syntax.ytree.definitions;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.syntax.ytree.types.signatures.YMethodSignature;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public class YFunctionDefinition implements YDefinition {

    private final YMethodSignature signature;
    private final YCompoundStatement body;
    private final YLinearStatement exitStatement;  // statement to which all 'return' values will be connected

    public YFunctionDefinition(YMethodSignature signature, YCompoundStatement body, YLinearStatement exitStatement) {
        this.signature = signature;
        this.body = body;
        this.exitStatement = exitStatement;
    }

    public YMethodSignature getSignature() {
        return signature;
    }

    public YCompoundStatement getBody() {
        return body;
    }

    public YLinearStatement getExitStatement() {
        return exitStatement;
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
    public YFunctionDefinition copy() {
        return new YFunctionDefinition(signature, body, exitStatement);
    }
}
