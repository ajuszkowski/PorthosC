package mousquetaires.languages.syntax.zformula;

import com.google.common.collect.ImmutableList;
import mousquetaires.utils.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class ZLogicalFormulaHelper {

    public static ZLogicalFormula and(ZLogicalFormula... expressions) {
        return and(Arrays.asList(expressions));
    }

    public static ZLogicalFormula and(Collection<ZLogicalFormula> expressions) {
        switch (expressions.size()) {
            case 0:
                return ZLogicalConstant.True;
            case 1:
                return CollectionUtils.getSingleElement(expressions);
            default:
                return new ZLogicalConjunction(ImmutableList.copyOf(expressions));
        }
    }

    public static ZLogicalFormula or(ZLogicalFormula... expressions) {
        return or(Arrays.asList(expressions));
    }

    public static ZLogicalFormula or(Collection<? extends ZLogicalFormula> expressions) {
        switch (expressions.size()) {
            case 0:
                return ZLogicalConstant.True;
            case 1:
                return CollectionUtils.getSingleElement(expressions);
            default:
                return new ZLogicalDisjunction(ImmutableList.copyOf(expressions));
        }
    }

    public static ZLogicalFormula not(ZLogicalFormula expression) {
        return expression instanceof ZLogicalNegation
                ? ((ZLogicalNegation) expression).getExpression()
                : new ZLogicalNegation(expression);
    }

    public static ZLogicalImplication implication(ZLogicalFormula left, ZLogicalFormula right) {
        return new ZLogicalImplication(left, right);
    }
}
