package mousquetaires.languages.syntax.ytree.definitions;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.CollectionUtils;

import java.util.Iterator;
import java.util.Objects;


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
        return CollectionUtils.createIteratorFrom(body);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YFunctionDefinition copy() {
        return new YFunctionDefinition(body);
    }

    @Override
    public String toString() {
        return "<method_"+hashCode()+"_signature>"  + body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YFunctionDefinition)) return false;
        YFunctionDefinition that = (YFunctionDefinition) o;
        return Objects.equals(getBody(), that.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBody());
    }
}
