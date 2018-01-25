package mousquetaires.languages.converters.toytree.c11.temporaries;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;


public class YExpressionReversableList extends YReversableList<YExpression> {

    public YExpression[] asArray() {
        return this.toArray(new YExpression[0]);
    }
}
