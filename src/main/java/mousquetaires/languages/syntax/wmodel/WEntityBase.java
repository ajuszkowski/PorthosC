package mousquetaires.languages.syntax.wmodel;

import mousquetaires.languages.common.citation.CodeLocation;


public abstract class WEntityBase implements WEntity {

    private final CodeLocation origin;
    private final boolean containsRecursion;

    public WEntityBase(CodeLocation origin, boolean containsRecursion) {
        this.origin = origin;
        this.containsRecursion = containsRecursion;
    }

    @Override
    public boolean containsRecursion() {
        return containsRecursion;
    }

    @Override
    public CodeLocation origin() {
        return origin;
    }
}
