package mousquetaires.languages.converters.toxgraph;

import mousquetaires.languages.common.XType;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YConstant;
import mousquetaires.languages.syntax.ytree.types.YType;


public class Y2XTypeConverter {

    public static XType determineType(YConstant constant) {
        //assert constant.getValue() instanceof Integer : "for now, only ints are supported; found constant of type: "
        //        + constant.getValue().getClass().getSimpleName();
        return XType.int32;//todo: there's no typing yet
    }

    public static XType convert(YType yType) {
        return XType.int32;//todo: there's no typing yet
    }
}
