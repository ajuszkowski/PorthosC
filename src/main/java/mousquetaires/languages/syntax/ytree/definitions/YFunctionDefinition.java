package mousquetaires.languages.syntax.ytree.definitions;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.types.YFunctionSignature;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;

import java.util.Objects;



public class YFunctionDefinition implements YDefinition {
    private final Origin origin;
    private final YFunctionSignature signature;
    private final YCompoundStatement body;

    public YFunctionDefinition(Origin origin, YFunctionSignature signature, YCompoundStatement body) {
        this.origin = origin;
        this.signature = signature;
        this.body = body;
    }

    public YFunctionSignature getSignature() {
        return signature;
    }

    public YCompoundStatement getBody() {
        return body;
    }

    @Override
    public Origin origin() {
        return origin;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
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
