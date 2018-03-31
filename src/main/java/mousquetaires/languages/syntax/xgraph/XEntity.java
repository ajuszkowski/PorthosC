package mousquetaires.languages.syntax.xgraph;

import mousquetaires.languages.common.SmtSerialisable;


public interface XEntity extends SmtSerialisable {
    @Override
    default String toSmt() {
        return "x_" + hashCode();
    }
}
