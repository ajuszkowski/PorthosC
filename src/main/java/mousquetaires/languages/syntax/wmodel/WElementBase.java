package mousquetaires.languages.syntax.wmodel;

import mousquetaires.languages.common.citation.CodeLocation;


public abstract class WElementBase extends WEntityBase implements WElement {

    public WElementBase(CodeLocation origin, boolean containsRecursion) {
        super(origin, containsRecursion);
    }
}
