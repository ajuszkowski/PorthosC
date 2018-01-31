package mousquetaires.languages.syntax.xrepr.events.computation;

import mousquetaires.languages.syntax.xrepr.memories.XMemoryUnit;


public enum XOperator {
    // logical operators:
    LogicalAnd,
    LogicalOr,
    LogicalXor,
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

    public XMemoryUnit.Bitness getBitness() {
        switch (this) {
            case LogicalAnd:
            case LogicalOr:
            case LogicalXor:
            case LogicalNot:
            case CompareEquals:
            case CompareNotEquals:
            case CompareLess:
            case CompareLessOrEquals:
            case CompareGreater:
            case CompareGreaterOrEquals:
                return XMemoryUnit.Bitness.bit1;
            case IntegerPlus:
            case IntegerMinus:
            case IntegerMultiply:
            case IntegerDivide:
            case IntegerModulo:
            case IntegerLeftShift:
            case IntegerRightShift:
            case BitAnd:
            case BitOr:
            case BitXor:
            case BitNot:
                return XMemoryUnit.Bitness.bit32;//todo: other integers
            default:
                throw new IllegalArgumentException(this.name());
        }
    }
}
