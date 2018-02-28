package mousquetaires.languages.syntax.ytree.temporaries;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

import java.util.Iterator;


/**
 * unaryOperator
 * :   '&' | '*' | '+' | '-' | '~' | '!'
 * ;
 */
public enum YUnaryOperatorKindTemp implements YTempEntity {
    Ampersand,
    Asterisk,
    Plus,
    Minus,
    Tilde,
    Exclamation,
    ;


    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public YEntity copy() {
        throw new UnsupportedOperationException();
    }

    public static YUnaryOperatorKindTemp tryParse(String value) {
        switch (value) {
            case "&": return Ampersand;
            case "*": return Asterisk;
            case "+": return Plus;
            case "-": return Minus;
            case "~": return Tilde;
            case "!": return Exclamation;
            default:
                return null;
        }
    }
}
