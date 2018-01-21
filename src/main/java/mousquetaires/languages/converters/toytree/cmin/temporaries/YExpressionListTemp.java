package mousquetaires.languages.converters.toytree.cmin.temporaries;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;


public class YExpressionListTemp extends YTempList<YExpression> {

    public YExpression[] asArray() {
        return this.toArray(new YExpression[0]);
    }
}
