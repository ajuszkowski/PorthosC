package mousquetaires.languages.syntax.wmodel.operators;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.wmodel.WEntity;
import mousquetaires.languages.syntax.wmodel.WOperator;
import mousquetaires.languages.syntax.wmodel.visitors.WmodelVisitor;


public class WOperatorUnary extends WOperatorBase {

    private final WEntity operand;

    private WOperatorUnary(CodeLocation origin,
                           WOperatorUnary.Kind kind,
                           WEntity operand) {
        super(origin, operand.containsRecursion(), kind);
        this.operand = operand;
    }

    public WEntity getOperand() {
        return operand;
    }

    @Override
    public WOperatorUnary.Kind getKind() {
        return (WOperatorUnary.Kind) super.getKind();
    }

    @Override
    public <T> T accept(WmodelVisitor<T> visitor) {
        return visitor.visit(this);
    }

    // --

    public enum Kind implements WOperator.Kind {
        IdentityClosure,
        ReflexiveTransitiveClosure,
        TransitiveClosure,
        Complement,
        Inverse,
        ;


        @Override
        public String toString() {
            switch (this) {
                case IdentityClosure:            return "?";
                case ReflexiveTransitiveClosure: return "*";
                case TransitiveClosure:          return "+";
                case Complement:                 return "~";
                case Inverse:                    return "^-1";
                default:
                    throw new IllegalStateException(this.name());
            }
        }

        public WOperator create(CodeLocation origin, WEntity operand) {
            return new WOperatorUnary(origin, this, operand);
        }
    }
}
