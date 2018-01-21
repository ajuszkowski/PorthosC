package mousquetaires.languages.syntax.ytree.expressions.binary;

import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.visitors.YtreeVisitor;


public class YLogicalBinaryExpression extends YBinaryExpression {
    public enum Kind implements YBinaryExpression.Kind {
        Conjunction,
        Disjunction,
        ;

        @Override
        public String toString() {
            switch (this) {
                case Conjunction:     return "&&";
                case Disjunction:     return "||";
                default: throw new IllegalArgumentException(this.name());
            }
        }

        public YLogicalBinaryExpression createExpression(YExpression leftExpression, YExpression rightExpression) {
            return new YLogicalBinaryExpression(this, leftExpression, rightExpression);
        }
    }

    //public static YLogicalBinaryExpression createConjunction() {
    //    return new YLogicalBinaryExpression(Kind.Conjunction, leftExpression, rightExpression);
    //}
    //
    //public static YLogicalBinaryExpression createDisjunction(YExpression leftExpression, YExpression rightExpression) {
    //    return new YLogicalBinaryExpression(Kind.Disjunction, leftExpression, rightExpression);
    //}

    private YLogicalBinaryExpression(Kind kind, YExpression leftExpression, YExpression rightExpression) {
        super(kind, leftExpression, rightExpression);
    }

    @Override
    public YLogicalBinaryExpression.Kind getKind() {
        return (YLogicalBinaryExpression.Kind) super.getKind();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public YLogicalBinaryExpression copy() {
        return new YLogicalBinaryExpression(getKind(), getLeftExpression(), getRightExpression());
    }

    @Override
    public String toString() {
        return getLeftExpression() + " " + getKind() + " " + getRightExpression();
    }
}
