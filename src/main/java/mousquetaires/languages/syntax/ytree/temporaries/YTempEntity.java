package mousquetaires.languages.syntax.ytree.temporaries;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.visitors.YtreeVisitor;


public interface YTempEntity extends YEntity {

    @Override
    default <T> T accept(YtreeVisitor<T> visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    default Origin origin() {
        throw new UnsupportedOperationException();
    }
}
