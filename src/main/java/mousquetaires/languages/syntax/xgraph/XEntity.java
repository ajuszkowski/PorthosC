package mousquetaires.languages.syntax.xgraph;

import mousquetaires.languages.visitors.xgraph.XgraphVisitor;


public interface XEntity {  //extends Cloneable {
    //XEntity copy(); // todo <--

    // TODO: unique identifier

    <T> T accept(XgraphVisitor<T> visitor);
}
