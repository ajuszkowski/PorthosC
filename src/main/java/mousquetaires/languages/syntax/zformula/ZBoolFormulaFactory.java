package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;
import mousquetaires.utils.CollectionUtils;

import java.util.Collection;
import java.util.List;


public class ZBoolFormulaFactory {

    public static ZBoolFormula and(ZBoolFormula... expressions) {
        return and(List.of(expressions));
    }

    public static ZBoolFormula and(Collection<ZBoolFormula> expressions) {
        switch (expressions.size()) {
            case 0:
                return ZBoolConstant.True;
            case 1:
                return CollectionUtils.getSingleElement(expressions);
            default:
                return new ZBoolConjunction(ImmutableList.copyOf(expressions));
        }
    }

    public static ZBoolFormula or(ZBoolFormula... expressions) {
        return or(List.of(expressions));
    }

    public static ZBoolFormula or(Collection<? extends ZBoolFormula> expressions) {
        switch (expressions.size()) {
            case 0:
                return ZBoolConstant.True;
            case 1:
                return CollectionUtils.getSingleElement(expressions);
            default:
                return new ZBoolDisjunction(ImmutableList.copyOf(expressions));
        }
    }

    public static ZBoolFormula not(ZBoolFormula expression) {
        return expression instanceof ZBoolNegation
                ? ((ZBoolNegation) expression).getExpression()
                : new ZBoolNegation(expression);
    }

    public static ZBoolImplication implication(ZBoolFormula left, ZBoolFormula right) {
        return new ZBoolImplication(left, right);
    }
}
