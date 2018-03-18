package mousquetaires.languages.syntax.ytree.types.signatures;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.temporaries.YTempEntity;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.patterns.BuilderBase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class YParameterListBuilder extends BuilderBase<YParameter[]> implements YTempEntity {

    private List<YParameter> parameterList;

    public YParameterListBuilder() {
        this.parameterList = new ArrayList<>();
    }

    @Override
    public YParameter[] build() {
        return parameterList.toArray(new YParameter[0]);
    }

    public void add(YParameter parameter) {
        parameterList.add(parameter);
    }

    public void addAll(YParameterListBuilder builder) {
        parameterList.addAll(builder.parameterList);
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public YEntity copy() {
        throw new UnsupportedOperationException();
    }
}
