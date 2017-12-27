package mousquetaires.starters;

import mousquetaires.app.options.CommandLineOptions;
import mousquetaires.app.options.RequiredByValidator;


public abstract class AppModule {

    protected final CommandLineOptions options;

    AppModule(CommandLineOptions options) {
        RequiredByValidator.validateOptions(this, options);
        this.options = options;
    }

    public abstract void start();


}
