package mousquetaires;

import mousquetaires.app.AppBase;
import mousquetaires.app.modules.AppModule;
import mousquetaires.app.modules.dartagnan.DartagnanModule;
import mousquetaires.app.modules.dartagnan.DartagnanOptions;
import mousquetaires.app.modules.porthos.PorthosModule;
import mousquetaires.app.modules.porthos.PorthosOptions;


public class PorthosApp extends AppBase {

    public static void main(String[] args) {
        PorthosOptions options = parseOptions(args, new PorthosOptions());
        if (options == null) {
            System.exit(1);
        }

        AppModule module = new PorthosModule(options);
        start(module);
    }
}
