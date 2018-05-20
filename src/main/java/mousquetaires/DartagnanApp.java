package mousquetaires;

import mousquetaires.app.AppBase;
import mousquetaires.app.modules.AppModule;
import mousquetaires.app.modules.dartagnan.DartagnanModule;
import mousquetaires.app.modules.dartagnan.DartagnanOptions;


public class DartagnanApp extends AppBase {

    public static void main(String[] args) {
        DartagnanOptions options = parseOptions(args, new DartagnanOptions());
        if (options == null) {
            System.exit(1);
        }

        AppModule module = new DartagnanModule(options);
        start(module);
    }
}
