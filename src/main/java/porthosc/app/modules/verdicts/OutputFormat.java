package porthosc.app.modules.verdicts;

public enum OutputFormat {
    Json,
    Yaml,
    //...
    ;

    public static OutputFormat parse(String value) {
        switch (value.toLowerCase()) {
            case "json":
                return Json;
            case "yaml":
                return Yaml;
            default:
                return null;
        }
    }
}
