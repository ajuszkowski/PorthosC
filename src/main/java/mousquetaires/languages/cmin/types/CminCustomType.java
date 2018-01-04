package mousquetaires.languages.cmin.types;

public class CminCustomType extends CminType {
    private final String name;

    public CminCustomType (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }
}
