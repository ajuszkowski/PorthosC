package mousquetaires.app.modules;

import mousquetaires.app.options.AppOptions;
import mousquetaires.app.options.RequiredByValidator;

import java.io.IOException;


public abstract class AppModule {

    protected final AppOptions options;

    public AppModule(AppOptions options) {
        RequiredByValidator.validateOptions(this, options);
        this.options = options;
    }

    public abstract AppVerdict run() throws IOException;
}
