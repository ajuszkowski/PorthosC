package mousquetaires.languages.syntax.zformula;


public enum ZBinaryOperator implements ZOperator {
    Addition,
    Subtraction,
    Multiplication,
    Division,        // todo //?
    Modulo,  // todo //?
    LeftShift,
    RightShift, //unsigned
    BitAnd,
    BitOr,
    BitXor,
    CompareEquals,
    CompareNotEquals,
    CompareLess,
    CompareLessOrEquals,
    CompareGreater,
    CompareGreaterOrEquals,
    ;

    public ZBinaryOperation create(ZAtom leftOperand, ZAtom rightOperand) {
        return new ZBinaryOperation(this, leftOperand, rightOperand);
    }

    @Override
    // for now, for debug only
    public String toString() {
        switch (this) {
            case Addition:               return "+";
            case Subtraction:            return "-";
            case Multiplication:         return "*";
            case Division:               return "/";
            case Modulo:                 return "%";
            case LeftShift:              return "<<";
            case RightShift:             return ">>";
            case BitAnd:                 return "&";
            case BitOr:                  return "|";
            case BitXor:                 return "^";
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
