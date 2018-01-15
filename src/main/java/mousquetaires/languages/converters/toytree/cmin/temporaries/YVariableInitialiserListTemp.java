package mousquetaires.languages.converters.toytree.cmin.temporaries;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.utils.YtreeUtils;
import mousquetaires.utils.exceptions.NotSupportedException;
import mousquetaires.utils.structures.ReversableList;

import java.util.ArrayList;
import java.util.Iterator;


public class YVariableInitialiserListTemp extends ReversableList<YVariableInitialiserTemp> implements YTempEntity {

    public YVariableInitialiserListTemp() {
        super(new ArrayList<>());
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(this);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        // todo: or not implemented exc
        throw new NotSupportedException();
    }

    @Override
    public YEntity copy() {
        throw new NotSupportedException();
    }
}
