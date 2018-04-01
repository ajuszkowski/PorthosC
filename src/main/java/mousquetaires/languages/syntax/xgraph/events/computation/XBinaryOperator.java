package mousquetaires.languages.syntax.xgraph.events.computation;


public enum XBinaryOperator implements XOperator {
    Addition,
    Subtraction,
    Multiplication,
    Division,        // todo //?
    Modulo,  // todo //?
    LeftShift,
    RightShift,
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

}
