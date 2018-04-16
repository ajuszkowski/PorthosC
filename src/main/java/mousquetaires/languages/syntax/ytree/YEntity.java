package mousquetaires.languages.syntax.ytree;

import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

import java.util.Iterator;

public interface YEntity extends Cloneable {

    Iterator<? extends YEntity> getChildrenIterator();

    <T> T accept(YtreeVisitor<T> visitor);
}