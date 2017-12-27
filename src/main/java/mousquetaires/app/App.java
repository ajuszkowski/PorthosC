package mousquetaires.app;

import com.beust.jcommander.ParameterException;
import mousquetaires.app.modules.IAppModule;
import mousquetaires.app.modules.AppVerdict;
import mousquetaires.app.modules.IAppVerdictStringifier;
import mousquetaires.app.modules.JsonVerdictStringifier;
import mousquetaires.app.options.AppOptions;

import java.io.IOException;
import java.util.function.Supplier;


public abstract class App {

    protected static AppOptions parseOptions(String[] args, Supplier<AppOptions> createOptionsAction) {
        AppOptions options = createOptionsAction.get();
        try {
            options.parse(args);
            if (options.help) {
                System.out.println(options.getUsageString());
                System.exit(0);
            }
            return options;
        } catch (ParameterException e) {
            // TODO: log
            System.out.println(e.getMessage() + "\n");
        }
        return null;
    }

    protected static void start(IAppModule module) {
        IAppVerdictStringifier stringifier = new JsonVerdictStringifier();
        AppVerdict verdict = null;
        try {
            verdict = module.run();
        } catch (IOException e) {
            // TODO: log
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println(stringifier.stringify(verdict));
    }
}
