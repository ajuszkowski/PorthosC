package mousquetaires.languages.common.transformers.cmin.temp;

import mousquetaires.languages.common.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.languages.ytree.expressions.YVariableRef;
import mousquetaires.utils.YtreeUtils;
import mousquetaires.utils.exceptions.NotSupportedException;

import java.util.Iterator;


public class YVariableInitialiserTemp implements YTempEntity {

    public final YVariableRef variable;
    public final YExpression initExpression;

    public YVariableInitialiserTemp(YVariableRef variable, YExpression initExpression) {
        this.variable = variable;
        this.initExpression = initExpression;
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(variable, initExpression);
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        //if (!(visitor instanceof))
        throw new NotSupportedException();
    }

    @Override
    public YEntity copy() {
        return new YVariableInitialiserTemp(variable, initExpression);
    }

    @Override
    public String toString() {
        return variable + " = " + initExpression;
    }
}
