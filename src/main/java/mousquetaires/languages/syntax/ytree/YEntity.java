package mousquetaires.languages.syntax.ytree;

import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitor;

import java.util.Iterator;

// TODO: add 'Origin origin()' method where Origin contains text citation, line number(s), etc...
public interface YEntity extends Cloneable {

    //void setChild(YEntity child);

    Iterator<? extends YEntity> getChildrenIterator();

    <T> T accept(YtreeVisitor<T> visitor);
}