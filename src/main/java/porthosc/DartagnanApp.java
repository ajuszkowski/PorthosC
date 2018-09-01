package porthosc;

import porthosc.app.AppBase;
import porthosc.app.modules.AppModule;
import porthosc.app.modules.verdicts.IAppVerdictSerializer;
import porthosc.app.modules.dartagnan.DartagnanModule;
import porthosc.app.modules.dartagnan.DartagnanOptions;
import porthosc.app.modules.verdicts.VerdictSerializerFactory;


public class DartagnanApp extends AppBase {

    public static void main(String[] args) {
        DartagnanOptions options = parseOptions(args, new DartagnanOptions());
        if (options == null) {
            System.exit(1);
        }

        AppModule module = new DartagnanModule(options);
        IAppVerdictSerializer serializer = VerdictSerializerFactory.getSerializer(options.outputFormat);
        start(module, serializer);
    }
}
