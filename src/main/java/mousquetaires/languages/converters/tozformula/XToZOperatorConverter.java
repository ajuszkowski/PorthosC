package mousquetaires.languages.converters.tozformula;

import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryOperator;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryOperator;
import mousquetaires.languages.syntax.zformula.ZBinaryOperator;
import mousquetaires.languages.syntax.zformula.ZUnaryOperator;


class XToZOperatorConverter {

    static ZBinaryOperator convert(XBinaryOperator xOperator) {
        switch (xOperator) {
            case Addition:               return ZBinaryOperator.Addition;
            case Subtraction:            return ZBinaryOperator.Subtraction;
            case Multiplication:         return ZBinaryOperator.Multiplication;
            case Division:               return ZBinaryOperator.Division;
            case Modulo:         return ZBinaryOperator.Modulo;
            case LeftShift:              return ZBinaryOperator.LeftShift;
            case RightShift:             return ZBinaryOperator.RightShift;
            case BitAnd:                 return ZBinaryOperator.BitAnd;
            case BitOr:                  return ZBinaryOperator.BitOr;
            case BitXor:                 return ZBinaryOperator.BitXor;
            case CompareEquals:          return ZBinaryOperator.CompareEquals;
            case CompareNotEquals:       return ZBinaryOperator.CompareNotEquals;
            case CompareLess:            return ZBinaryOperator.CompareLess;
            case CompareLessOrEquals:    return ZBinaryOperator.CompareLessOrEquals;
            case CompareGreater:         return ZBinaryOperator.CompareGreater;
            case CompareGreaterOrEquals: return ZBinaryOperator.CompareGreaterOrEquals;
            default:
                throw new IllegalArgumentException(xOperator.name());
        }
    }

    static ZUnaryOperator convert(XUnaryOperator xOperator) {
        switch (xOperator) {
            case BitNegation: return ZUnaryOperator.BitNegation;
            case NoOperation: return ZUnaryOperator.NoOperation;
            default:
                throw new IllegalArgumentException(xOperator.name());
        }
    }

}

