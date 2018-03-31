package mousquetaires.languages.syntax.xgraph;

public interface XEntity {

    default String uniqueId() {
        return "x_" + hashCode();
    }
}
