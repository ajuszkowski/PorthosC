package mousquetaires.languages.cmin.transformer.temporaries;

import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.expressions.YExpression;
import mousquetaires.languages.ytree.expressions.YVariableRef;


public class YVariableInitialiser implements YEntity {
    public final YVariableRef variable;
    public final YExpression initExpression;

    public YVariableInitialiser(YVariableRef variable, YExpression initExpression) {
        this.variable = variable;
        this.initExpression = initExpression;
    }

    public boolean hasInitExpression() {
        return initExpression != null;
    }

    @Override
    public String toString() {
        return variable + " = " + initExpression;
    }
}
