package mousquetaires.languages.syntax.ytree;

import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

import java.util.Iterator;


public interface YEntity extends Cloneable {

    //void setChild(YEntity child);

    Iterator<? extends YEntity> getChildrenIterator();

    YEntity copy();

    <T> T accept(YtreeVisitor<T> visitor);
}