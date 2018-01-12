package mousquetaires.languages.ytree.signatures;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.types.YType;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public class YFunctionParameter implements YEntity {

    public final YType type;
    public final String name;

    public YFunctionParameter(YType type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(type);
    }

    @Override
    public void accept(YtreeVisitor visitor) {
        visitor.visit(this);
    }
}
