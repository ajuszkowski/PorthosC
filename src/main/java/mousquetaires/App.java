package mousquetaires;

import com.beust.jcommander.ParameterException;
import mousquetaires.app.options.CommandLineOptions;
import mousquetaires.starters.AppModule;
import mousquetaires.starters.AppModuleFactory;


public class App {

    public static void main(String[] args) {
        CommandLineOptions options;
        AppModule module = null;
        try {
            options = CommandLineOptions.parse(args);
            module = AppModuleFactory.newAppModule(options);
        }
        catch (ParameterException e) {
            // TODO: log
            System.out.println(e.getMessage() + "\n");
            System.out.println(CommandLineOptions.getUsageString());
            System.exit(1);
        }

        module.start();
    }
}
