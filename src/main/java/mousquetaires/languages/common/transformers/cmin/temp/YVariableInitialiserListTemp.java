package mousquetaires.languages.common.transformers.cmin.temp;

import mousquetaires.languages.common.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
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
