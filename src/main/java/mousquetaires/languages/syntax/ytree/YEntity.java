package mousquetaires.languages.syntax.ytree;

import mousquetaires.languages.visitors.ytree.YtreeVisitor;

import java.util.Iterator;


public interface YEntity extends Cloneable {

    //void setChild(YEntity child);

    Iterator<? extends YEntity> getChildrenIterator();

    <T> T accept(YtreeVisitor<T> visitor);

    YEntity copy();
}