package mousquetaires.app.modules;

import mousquetaires.app.options.AppOptions;
import mousquetaires.app.options.RequiredByValidator;


public abstract class AppModule {

    protected final AppOptions options;

    public AppModule(AppOptions options) {
        RequiredByValidator.validateOptions(this, options);
        this.options = options;
    }

    public abstract AppVerdict run();
}
