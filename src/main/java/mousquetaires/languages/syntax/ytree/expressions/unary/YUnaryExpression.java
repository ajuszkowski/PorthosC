package mousquetaires.languages.syntax.ytree.expressions.unary;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.YMultiExpression;
import mousquetaires.utils.YtreeUtils;

import java.util.Iterator;


public abstract class YUnaryExpression extends YMultiExpression {
    protected interface Kind {
        YUnaryExpression createExpression(YExpression baseExpression);
    }

    //public interface Kind  {
    //    //Not,                // !x
    //    //IncrementPrefix,    // ++x
    //    //IncrementPostfix,   // x++
    //    //DecrementPrefix,    // --x
    //    //DecrementPostfix,   // x--
    //    //
    //    //;
    //
    //}

    private final YUnaryExpression.Kind kind;

    YUnaryExpression(YUnaryExpression.Kind kind, YExpression baseExpression) {
        super(baseExpression);
        this.kind = kind;
    }

    public YUnaryExpression.Kind getKind() {
        return kind;
    }

    public YExpression getExpression() {
        return getElements()[0];
    }

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        return YtreeUtils.createIteratorFrom(getExpression());
    }

    //@Override
    //public String toString() {
    //    switch (operator) {
    //        case Not:
    //            return "!" + expression;
    //        case IncrementPrefix:
    //            return "++" + expression;
    //        case IncrementPostfix:
    //            return expression + "++";
    //        case DecrementPrefix:
    //            return "--" + expression;
    //        case DecrementPostfix:
    //            return expression + "--";
    //        case PointerDereference:
    //            return "*" + expression;
    //        case PointerReference:
    //            return "&" + expression;
    //        default:
    //            throw new IllegalArgumentException();
    //    }
    //}
}
