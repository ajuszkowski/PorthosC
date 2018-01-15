package mousquetaires.languages.syntax.ytree.expressions;

import mousquetaires.languages.types.YXTypeName;
import mousquetaires.languages.types.YXTypeFactory;


public class YConstantFactory {

    public static YConstant newIntConstant(int value) {
        return new YConstant(value, YXTypeFactory.getPrimitiveType(YXTypeName.Int));
    }

    public static YConstant newBoolConstant(boolean value) {
        return new YConstant(value, YXTypeFactory.getPrimitiveType(YXTypeName.Bool));
    }

    // ... todo: others
}
