package porthosc.app.modules;

public enum AppModuleName {
    Porthos,   // Portability analysis
    Dartagnan, // Reachability analysis
    Aramis,    // Memory model generation
    ;

    public static AppModuleName parse(String value) {
        switch (value.toLowerCase()) {
            case "old/porthos":
                return AppModuleName.Porthos;
            case "old/dartagnan":
                return AppModuleName.Dartagnan;
            case "old/aramis":
                return AppModuleName.Aramis;
            default:
                return null;
        }
    }
}
