package mousquetaires.languages.syntax.zformula;

import mousquetaires.languages.syntax.zformula.visitors.ZBoolAtom;


public class ZBoolVariable extends ZVariable implements ZBoolAtom {

    ZBoolVariable(String name) {
        super(name);
    }
}
