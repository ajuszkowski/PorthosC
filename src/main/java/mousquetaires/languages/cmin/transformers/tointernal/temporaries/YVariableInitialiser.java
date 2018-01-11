package mousquetaires.languages.cmin.transformers.tointernal.temporaries;

import mousquetaires.languages.internalrepr.YEntity;
import mousquetaires.languages.internalrepr.expressions.YExpression;
import mousquetaires.languages.internalrepr.expressions.YVariableRef;


public class YVariableInitialiser implements YEntity {
    public final YVariableRef variable;
    public final YExpression initExpression;

    public YVariableInitialiser(YVariableRef variable, YExpression initExpression) {
        this.variable = variable;
        this.initExpression = initExpression;
    }

    @Override
    public String toString() {
        return variable + " = " + initExpression;
    }
}
