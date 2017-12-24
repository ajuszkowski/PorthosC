package mousquetaires;

public enum AppModule {
    Porthos,   // Portability analysis
    Dartagnan, // Reachability analysis
    Aramis,    // Memory model generation
    ;

    public static AppModule parse(String value) {
        switch (value.toLowerCase()) {
            case "porthos":
                return AppModule.Porthos;
            case "dartagnan":
                return AppModule.Dartagnan;
            case "aramis":
                return AppModule.Aramis;
            default:
                return null;
        }
    }
}
