package porthosc;

import porthosc.app.AppBase;
import porthosc.app.modules.AppModule;
import porthosc.app.modules.porthos.PorthosModule;
import porthosc.app.modules.porthos.PorthosOptions;
import porthosc.app.modules.verdicts.IAppVerdictSerializer;
import porthosc.app.modules.verdicts.VerdictSerializerFactory;


public class PorthosApp extends AppBase {

    public static void main(String[] args) {
        PorthosOptions options = parseOptions(args, new PorthosOptions());
        if (options == null) {
            System.exit(1);
        }

        AppModule module = new PorthosModule(options);
        IAppVerdictSerializer serializer = VerdictSerializerFactory.getSerializer(options.outputFormat);
        start(module, serializer);
    }
}
