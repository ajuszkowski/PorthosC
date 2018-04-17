package mousquetaires.languages.syntax.ytree.expressions.atomics;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.temporaries.YTempEntity;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;
import mousquetaires.utils.exceptions.NotSupportedException;
import mousquetaires.utils.patterns.BuilderBase;

import java.util.ArrayList;
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
    public <T> T accept(YtreeVisitor<T> visitor) {
        throw new UnsupportedOperationException();
    }
}
