package mousquetaires.languages.syntax.smt;

abstract class ZVariableGlobal {
    private final String name;

    ZVariableGlobal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
