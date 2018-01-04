package mousquetaires.languages.internallang_deleteme.elements;

/** Represents variable used in Static Single Assignment (SSA) form. (TODO -- implement SSA) */
public class InternalVariable extends InternalMemoryLocation {
    public final String name;

    public InternalVariable(String name) {
        this.name = name;
    }
}
