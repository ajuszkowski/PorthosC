package mousquetaires.languages.transformers.cmin.temp;

import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.languages.ytree.expressions.YVariableRef;
import mousquetaires.utils.YtreeUtils;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.Iterator;


public class YVariableInitialiserTemp implements YTempEntity {

    public final YVariableRef variable;
    public final YExpression initExpression;

    public YVariableInitialiserTemp(YVariableRef variable, YExpression initExpression) {
        this.variable = variable;
        this.initExpression = initExpression;
    }

    @Override
    public Iterator<YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(variable, initExpression);
    }

    @Override
    public void accept(YtreeVisitor visitor) {
        //if (!(visitor instanceof))
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        return variable + " = " + initExpression;
    }
}
