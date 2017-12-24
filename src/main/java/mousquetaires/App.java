package mousquetaires;

import com.beust.jcommander.ParameterException;
import mousquetaires.options.CommandLineOptions;
import mousquetaires.starters.AppModule;
import mousquetaires.starters.AppModuleFactory;


public class App {

    public static void main(String[] args) {
        CommandLineOptions options = null;

        try {
            options = CommandLineOptions.parse(args);
        }
        catch (ParameterException e) {
            // TODO: log
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());

            System.out.println(CommandLineOptions.getUsageString());
            System.exit(1);
        }

        AppModule module = AppModuleFactory.newAppModule(options);
        module.start();
    }
}
