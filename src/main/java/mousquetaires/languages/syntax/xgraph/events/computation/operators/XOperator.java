package mousquetaires.languages.syntax.xgraph.events.computation.operators;

public enum XOperator {
    // logical operators:
    LogicalAnd,
    LogicalOr,
    LogicalNot,
    // integer operators:
    IntegerPlus,
    IntegerMinus,
    IntegerMultiply,
    IntegerDivide,  // todo
    IntegerModulo,  // todo
    IntegerLeftShift,
    IntegerRightShift,
    BitAnd,
    BitOr,
    BitXor,
    BitNot,
    // relative operators:
    CompareEquals,
    CompareNotEquals,
    CompareLess,
    CompareLessOrEquals,
    CompareGreater,
    CompareGreaterOrEquals,
    // todo: more
    ;

    //public XMemoryUnitBase.Bitness getBitness() {
    //    switch (this) {
    //        case LogicalAnd:
    //        case LogicalOr:
    //        case LogicalNot:
    //        case CompareEquals:
    //        case CompareNotEquals:
    //        case CompareLess:
    //        case CompareLessOrEquals:
    //        case CompareGreater:
    //        case CompareGreaterOrEquals:
    //            return XMemoryUnitBase.Bitness.bit1;
    //        case IntegerPlus:
    //        case IntegerMinus:
    //        case IntegerMultiply:
    //        case IntegerDivide:
    //        case IntegerModulo:
    //        case IntegerLeftShift:
    //        case IntegerRightShift:
    //        case BitAnd:
    //        case BitOr:
    //        case BitXor:
    //        case BitNot:
    //            return XMemoryUnitBase.Bitness.bit32;//todo: other integers
    //        default:
    //            throw new IllegalArgumentException(this.name());
    //    }
    //}

    @Override
    public String toString() {
        switch (this) {
            case LogicalAnd:             return "&&";
            case LogicalOr:              return "||";
            case LogicalNot:             return "!";
            case IntegerPlus:            return "+";
            case IntegerMinus:           return "-";
            case IntegerMultiply:        return "*";
            case IntegerDivide:          return "/";
            case IntegerModulo:          return "%";
            case IntegerLeftShift:       return "<<";
            case IntegerRightShift:      return ">>";
            case BitAnd:                 return "^";
            case BitOr:                  return "|";
            case BitXor:                 return "^";
            case BitNot:                 return "~";
            case CompareEquals:          return "==";
            case CompareNotEquals:       return "!=";
            case CompareLess:            return "<";
            case CompareLessOrEquals:    return "<=";
            case CompareGreater:         return ">";
            case CompareGreaterOrEquals: return ">=";
            default:
                throw new IllegalArgumentException(this.name());
        }
    }
}
