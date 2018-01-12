package mousquetaires.languages.ytree;

import mousquetaires.languages.common.visitors.YtreeVisitor;

import java.util.Iterator;


public interface YEntity extends Cloneable {

    //void setChild(YEntity child);

    Iterator<? extends YEntity> getChildrenIterator();

    <T> T accept(YtreeVisitor<T> visitor);

    YEntity copy();
}