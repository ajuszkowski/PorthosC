package mousquetaires.languages.syntax.ytree.types;

import mousquetaires.languages.common.citation.CodeLocation;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;


public class YMockType implements YType {

    @Override
    public Qualifier getQualifier() {
        return null;
    }

    @Override
    public Specifier getSpecifier() {
        return null;
    }

    @Override
    public <T> T accept(YtreeVisitor<T> visitor) {
        throw new NotImplementedException();
    }

    @Override
    public CodeLocation codeLocation() {
        return CodeLocation.empty;
    }

    @Override
    public String toString() {
        return "mock_type";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof YMockType;
    }
}
