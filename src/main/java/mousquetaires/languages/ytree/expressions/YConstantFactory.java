package mousquetaires.languages.ytree.expressions;

import mousquetaires.languages.ytree.types.YPrimitiveTypeName;
import mousquetaires.languages.ytree.types.YTypeFactory;


public class YConstantFactory {

    public static YConstant newIntConstant(int value) {
        return new YConstant(value, YTypeFactory.getPrimitiveType(YPrimitiveTypeName.Int));
    }

    public static YConstant newBoolConstant(boolean value) {
        return new YConstant(value, YTypeFactory.getPrimitiveType(YPrimitiveTypeName.Bool));
    }

    // ... todo: others
}
