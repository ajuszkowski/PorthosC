package mousquetaires.app.options;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import mousquetaires.app.App;
import mousquetaires.app.options.converters.LogLevelConverter;
import mousquetaires.app.options.validators.LogLevelValidator;
import mousquetaires.utils.logging.LogLevel;


@Parameters(separators = " =")
public abstract class AppOptions {

    @Parameter(names = {"-log", "--log"},
            converter = LogLevelConverter.class,
            validateValueWith = LogLevelValidator.class)
    public LogLevel logLevel;

    @Parameter(names = {"-h", "-?", "--help"},
            descriptionKey = "Print help message",
            help = true)
    public boolean help;

    //@YFunctionParameter(names = {"-m", "--module"},
    //        required = true,
    //        arity = 1,
    //        description = "module name",
    //        converter = AppModuleConverter.class,
    //        validateValueWith = AppModuleValidator.class)
    //public AppModuleName moduleName;

    public void parse(String[] args) {
        JCommander.newBuilder().addObject(this).build().parse(args);
    }

    public String getUsageString() {
        JCommander jCommander = new JCommander(this);
        jCommander.setProgramName(App.class.getName());
        StringBuilder builder = new StringBuilder();
        jCommander.usage(builder);
        return builder.toString();
    }
}
