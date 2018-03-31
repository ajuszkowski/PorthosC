package mousquetaires.languages.syntax.zformula;

import mousquetaires.languages.syntax.zformula.visitors.ZformulaVisitor;


public interface ZEntity {
    <T> T accept(ZformulaVisitor<T> visitor);
}
