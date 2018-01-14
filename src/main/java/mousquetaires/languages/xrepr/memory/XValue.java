package mousquetaires.languages.xrepr.memory;

import mousquetaires.languages.common.types.YXType;


/** Shortcut for working with constant values as with values stored in local memory (registers).
 * For example, the code 'r1 <- 1' is syntactic sugar for 'r1 <- m1' where the memory location 'm1'
 * contains value '1', though it does not need to be considered in weak memory model, as there are
 * no writes to that location, therefore we model it as a local storage. */
public class XValue extends XLocalMemory {

    public XValue(String name, YXType type) {
        super(name, type);
    }
}
