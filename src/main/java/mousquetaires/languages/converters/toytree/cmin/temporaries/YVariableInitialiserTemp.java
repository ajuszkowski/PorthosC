package mousquetaires.languages.converters.toytree.cmin.temporaries;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YVariableRef;
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
