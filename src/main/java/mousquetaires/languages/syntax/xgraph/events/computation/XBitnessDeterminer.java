package mousquetaires.languages.syntax.xgraph.events.computation;

import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.common.Bitness;
import mousquetaires.utils.exceptions.NotImplementedException;


class XBitnessDeterminer {

    static Bitness determineBitness(XUnaryOperator operator, XLocalMemoryUnit operand) {
        Bitness operandBitness = operand.getBitness();
        switch (operator) {
            case BitNegation:
            case NoOperation:
                return operandBitness;
            default:
                throw new NotImplementedException(operator.name());
        }
    }

    static Bitness determineBitness(XBinaryOperator operator,
                                    XLocalMemoryUnit firstOperand,
                                    XLocalMemoryUnit secondOperand) {
        Bitness firstBitness = firstOperand.getBitness();
        Bitness secondBitness = secondOperand.getBitness();
        switch (operator) {
            case Addition:
            case Subtraction:
            case Multiplication:
            case Division:
            case Modulo:
            case LeftShift:
            case RightShift: {
                if (firstBitness != secondBitness) {
                    // TODO: do something with type casting
                }
                //todo: Unsure that we need to take max bitness of operands
                int maxBitness = Integer.max(firstBitness.toInt(), secondBitness.toInt());
                return Bitness.parseInt(maxBitness);
            }
            case BitAnd:
            case BitOr:
            case BitXor: {
                if (firstBitness != secondBitness) {
                    //todo: bit alignment?
                    throw new IllegalArgumentException("Attempt to perform bit operation with arguments of different sizes");
                    // todo: throw special type of exception everywhere
                }
                return firstBitness;
            }
            case CompareEquals:
            case CompareNotEquals:
            case CompareLess:
            case CompareLessOrEquals:
            case CompareGreater:
            case CompareGreaterOrEquals: {
                return Bitness.bit1;
            }
            default:
                throw new IllegalArgumentException(operator.name());
        }
    }
}
