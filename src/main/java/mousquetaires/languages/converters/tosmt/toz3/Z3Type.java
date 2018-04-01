package mousquetaires.languages.converters.tosmt.toz3;

import mousquetaires.languages.common.Bitness;
import mousquetaires.utils.exceptions.NotImplementedException;

// TODO: consider data model here
public enum Z3Type {
    Bool,
    Int,
    Real,
    BitVector,
    ;

    public static Z3Type convert(Bitness bitness) {
        switch (bitness) {
            case bit1:
                return Bool;
            case bit16:
                throw new NotImplementedException();
            case bit32:
                return Int;
            case bit64:
                throw new NotImplementedException();
            default:
                throw new IllegalArgumentException(bitness.name());
        }
    }

}
