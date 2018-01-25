package mousquetaires.types;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.visitors.YtreeVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;

import java.util.Iterator;


public enum ZTypeName implements YEntity {
    Void,
    Char,
    Short,
    Int,
    Long,
    LongLong,
    Float,
    Double,
    LongDouble,
    Bool,
    ;

    public String getText() {
        final StringBuilder builder = new StringBuilder();
        String space = "";
        for (char c : this.name().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                builder.append(c);
            } else {
                builder.append(space).append(Character.toLowerCase(c));
                space = " ";
            }
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return getText();
    }

    // TODO: refactor type system, it should be separate intsance, with no relations to X and Y

    @Override
    public Iterator<? extends YEntity> getChildrenIterator() {
        throw new NotImplementedException();
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        throw new NotImplementedException();
    }

    @Override
    public YEntity copy() {
        throw new NotImplementedException();
    }
}
