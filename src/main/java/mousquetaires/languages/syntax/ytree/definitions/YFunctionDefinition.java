package mousquetaires.languages.syntax.ytree.definitions;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public class YFunctionDefinition implements YDefinition {

    //private final YMethodSignature signature;
    private final YCompoundStatement body;

    //public YFunctionDefinition(YMethodSignature signature, YCompoundStatement body) {
    public YFunctionDefinition(YCompoundStatement body) {
        //this.signature = signature;
        // TODO: Signature: necessary for binding!
        this.body = body;
    }

    //public YMethodSignature getSignature() {
    //    return signature;
    //}

    public YCompoundStatement getBody() {
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
    public YFunctionDefinition copy() {
        return new YFunctionDefinition(body);
    }
}
