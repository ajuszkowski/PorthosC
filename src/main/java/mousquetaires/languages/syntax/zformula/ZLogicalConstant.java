package mousquetaires.languages.syntax.zformula;

import mousquetaires.languages.common.Bitness;


public class ZLogicalConstant extends ZConstant implements ZAtom, ZLogicalFormula {

    public static final ZLogicalConstant True = new ZLogicalConstant(true);

    public static final ZLogicalConstant False = new ZLogicalConstant(false);

    private ZLogicalConstant(boolean value) {
        super(value, Bitness.bit1);
    }
}
