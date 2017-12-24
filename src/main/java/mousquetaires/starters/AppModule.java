package mousquetaires.starters;

import mousquetaires.app.options.CommandLineOptions;


public abstract class AppModule {
    protected final CommandLineOptions options;

    AppModule(CommandLineOptions options) {
        this.options = options;
    }

    public abstract void start();
}
