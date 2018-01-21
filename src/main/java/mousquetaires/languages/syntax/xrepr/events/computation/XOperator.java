package mousquetaires.languages.syntax.xrepr.events.computation;

public enum XOperator {
    // logical operators:
    LogicalAnd,
    LogicalOr,
    LogicalXor,
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
}
