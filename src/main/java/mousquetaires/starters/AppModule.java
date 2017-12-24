package mousquetaires.starters;

import mousquetaires.app.options.CommandLineOptions;
import mousquetaires.app.options.validators.OptionsRequiredByValidator;


public abstract class AppModule {

    protected final CommandLineOptions options;

    AppModule(CommandLineOptions options) {
        OptionsRequiredByValidator.validateOptions(this, options);
        this.options = options;
    }

    public abstract void start();


}
