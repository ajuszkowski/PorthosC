package mousquetaires.languages.syntax.wmodel;

import mousquetaires.languages.common.citation.Origin;


public abstract class WElementBase extends WEntityBase implements WElement {

    public WElementBase(Origin origin, boolean containsRecursion) {
        super(origin, containsRecursion);
    }
}
