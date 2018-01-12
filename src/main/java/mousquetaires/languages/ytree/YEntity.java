package mousquetaires.languages.ytree;

import mousquetaires.languages.visitors.YtreeVisitor;

import java.util.Iterator;


public interface YEntity {

    //void setChild(YEntity child);

    Iterator<YEntity> getChildrenIterator();

    void accept(YtreeVisitor visitor);
}