package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.types.signatures.ZMethodSignature;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public class YFunctionDefinitionStatement extends YStatement {

    private final ZMethodSignature signature;
    private final YSequenceStatement body;

    public YFunctionDefinitionStatement(ZMethodSignature signature, YSequenceStatement body) {
        this.signature = signature;
        this.body = body;
    }

    public ZMethodSignature getSignature() {
        return signature;
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
    public YFunctionDefinitionStatement copy() {
        return new YFunctionDefinitionStatement(signature, body);
    }
}
