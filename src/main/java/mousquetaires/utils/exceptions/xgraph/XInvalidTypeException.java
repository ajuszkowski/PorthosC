package mousquetaires.utils.exceptions.xgraph;

import mousquetaires.utils.StringUtils;


public class XInvalidTypeException extends XCompilationException {

    public XInvalidTypeException(Object node, Class<?> castToClass) {
        super(getCastErrorMessage(node, castToClass));
    }

    private static String getCastErrorMessage(Object node, Class<?> castToClass) {
        return "Invalid xgraph node type: " + StringUtils.wrap(node.toString()) +
                ", its type " + StringUtils.wrap(node.getClass().getSimpleName()) +
                " is not coercive to " + StringUtils.wrap(castToClass.getSimpleName());
    }
}
