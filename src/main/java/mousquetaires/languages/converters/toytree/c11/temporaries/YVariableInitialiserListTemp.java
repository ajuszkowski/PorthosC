package mousquetaires.languages.converters.toytree.c11.temporaries;

import mousquetaires.languages.syntax.ytree.expressions.assignments.YVariableAssignmentExpression;


public class YVariableInitialiserListTemp extends YReversableList<YVariableAssignmentExpression> {

    public YVariableAssignmentExpression[] asArray() {
        return this.toArray(new YVariableAssignmentExpression[0]);
    }
}
