package mousquetaires;

import mousquetaires.app.App;
import mousquetaires.app.modules.IAppModule;
import mousquetaires.app.modules.dartagnan.DartagnanModule;
import mousquetaires.app.modules.dartagnan.DartagnanOptions;


public class Dartagnan extends App {

    public static void main(String[] args) {
        DartagnanOptions options = (DartagnanOptions) parseOptions(args, DartagnanOptions::new);
        if (options == null) {
            System.exit(1);
        }

        IAppModule module = new DartagnanModule(options);
        start(module);
    }
}
