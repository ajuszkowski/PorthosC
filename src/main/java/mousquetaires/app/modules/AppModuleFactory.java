package mousquetaires.app.modules;


import mousquetaires.app.modules.dartagnan.Dartagnan;
import mousquetaires.app.options.AppOptions;


public class AppModuleFactory {
    public static AppModule newAppModule(AppOptions options) {
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
