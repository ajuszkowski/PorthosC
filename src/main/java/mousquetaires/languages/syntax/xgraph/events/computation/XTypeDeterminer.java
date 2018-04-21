package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.common.Type;
import mousquetaires.utils.exceptions.NotImplementedException;


class XTypeDeterminer {

    static Type determineType(XUnaryOperator operator, XLocalMemoryUnit operand) {
        Type operandType = operand.getType();
        switch (operator) {
            case BitNegation:
            case NoOperation:
                return operandType;
            default:
                throw new NotImplementedException(operator.name());
        }
    }

    static Type determineType(XBinaryOperator operator,
                              XLocalMemoryUnit firstOperand,
                              XLocalMemoryUnit secondOperand) {
        Type firstType = firstOperand.getType();
        Type secondType = secondOperand.getType();
        switch (operator) {
            case Addition:
            case Subtraction:
            case Multiplication:
            case Division:
            case Modulo:
            case LeftShift:
            case RightShift: {
                if (firstType != secondType) {
                    throw new IllegalArgumentException("Attempt to perform an operation with arguments of different types: " +
                            firstType + ", " + secondType);
                }
                //todo: Unsure that we need to take max bitness of operands
                return firstType;
            }
            case BitAnd:
            case BitOr:
            case BitXor: {
                if (firstType != secondType) {
                    //todo: bit alignment?
                    throw new IllegalArgumentException("Attempt to perform bit operation with arguments of different sizes");
                    // todo: throw special type of exception everywhere
                }
                return firstType;
            }
            case Conjunction:
            case Disjunction:
            case CompareEquals:
            case CompareNotEquals:
            case CompareLess:
            case CompareLessOrEquals:
            case CompareGreater:
            case CompareGreaterOrEquals: {
                return Type.bit1;
            }
            default:
                throw new IllegalArgumentException(operator.name());
        }
    }
}
