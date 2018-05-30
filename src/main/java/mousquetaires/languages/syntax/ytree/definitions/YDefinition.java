package mousquetaires.languages.syntax.ytree.definitions;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.YEntity;


public abstract class YDefinition implements YEntity {

    private final Origin origin;

    protected YDefinition(Origin origin) {
        this.origin = origin;
    }

    @Override
    public Origin origin() {
        return origin;
    }

}
