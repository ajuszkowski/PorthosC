package mousquetaires.languages.converters.toytree.cmin.temporaries;

import mousquetaires.languages.syntax.ytree.expressions.assignments.YVariableAssignmentExpression;


public class YVariableInitialiserListTemp extends YTempList<YVariableAssignmentExpression> {

    public YVariableAssignmentExpression[] asArray() {
        return this.toArray(new YVariableAssignmentExpression[0]);
    }
}
