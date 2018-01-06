package mousquetaires.languages.cmin.transformer;


import mousquetaires.languages.internalrepr.InternalEntity;


public class CminAssignmentOperator implements InternalEntity {

    public enum Kind {
        // '=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '&=' | '^=' | '|='
        Equals,
        StarEquals,
        DivEquals,
        ModEquals,
        PlusEquals,
        MinusEquals,
        LeftShiftEquals,
        RightShiftEquals,
        AndEquals,
        XorEquals,
        OrEquals,
    }

    private final Kind kind;

    public CminAssignmentOperator(Kind kind) {
        this.kind = kind;
    }
}
