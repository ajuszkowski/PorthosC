package mousquetaires.app;

import com.beust.jcommander.ParameterException;
import mousquetaires.app.errors.AppError;
import mousquetaires.app.modules.AppVerdict;
import mousquetaires.app.modules.AppModule;
import mousquetaires.app.modules.IAppVerdictStringifier;
import mousquetaires.app.modules.JsonVerdictStringifier;
import mousquetaires.app.options.AppOptions;

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
            System.out.println(options.getUsageString());
        }
        return null;
    }

    protected static void start(AppModule module) {
        IAppVerdictStringifier stringifier = new JsonVerdictStringifier();
        AppVerdict verdict = module.run();
        if (verdict.hasErrors()) {
            for (AppError error : verdict.getErrors()) {
                // todo: log error:
                System.out.println(error.getMessage());
                // todo: log info:
                System.out.println(error.getAdditionalMessage());
                System.out.println("");
            }
            System.exit(1);
        }
        System.out.println(stringifier.stringify(verdict));
    }
}
