package mousquetaires.starters;


import mousquetaires.app.options.CommandLineOptions;


public class AppModuleFactory {

    public static AppModule newAppModule(CommandLineOptions options) {
        AppModuleName moduleName = options.moduleName;
        switch (moduleName) {
            //case Porthos:
            //    return new Porthos(options);
            case Dartagnan:
                return new Dartagnan(options);
            //case Aramis:
            //    return nwe Aramis(options);
            default:
                throw new UnsupportedOperationException(moduleName.toString());
        }
    }
}
