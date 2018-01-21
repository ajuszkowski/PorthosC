package mousquetaires.utils;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignee;
import mousquetaires.utils.exceptions.ytree.InvalidLvalueException;


public class CastHelper {

    //public static <T extends S, S extends YEntity, E extends RuntimeException>
    //S castOrThrow(S expression, Class<T> clazz, E onError) {
    //    try {
    //        return clazz.cast(expression);
    //    }
    //    catch (ClassCastException e) {
    //        throw onError;
    //    }
    //}

    public static YAssignee castOrThrow(YEntity expression) {
        try {
            return (YAssignee) expression;
        }
        catch (ClassCastException e) {
            throw new InvalidLvalueException(expression);
        }
    }
}
