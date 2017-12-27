package mousquetaires.app.options;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;
import mousquetaires.App;
import mousquetaires.app.options.converters.AppModuleConverter;
import mousquetaires.app.options.converters.LogLevelConverter;
import mousquetaires.app.options.converters.MemoryModelNameConverter;
import mousquetaires.app.options.validators.*;
import mousquetaires.models.MemoryModelName;
import mousquetaires.starters.AppModuleName;
import mousquetaires.starters.Dartagnan;
import mousquetaires.utils.logging.LogLevel;

import java.io.File;


@Parameters(separators=" =")
public class CommandLineOptions {

    public static CommandLineOptions parse(String[] args) {
        CommandLineOptions options = new CommandLineOptions();
        JCommander.newBuilder().addObject(options).build().parse(args);
        return options;
    }

    public static String getUsageString() {
        CommandLineOptions options = new CommandLineOptions();
        JCommander jCommander = new JCommander(options);
        jCommander.setProgramName(App.class.getName());
        StringBuilder builder = new StringBuilder();
        jCommander.usage(builder);
        return builder.toString();
    }

    @Parameter(names = {"-m", "--module"},
            required = true,
            arity = 1,
            description = "module name",
            converter = AppModuleConverter.class,
            validateValueWith = AppModuleValidator.class)
    public AppModuleName moduleName;

    @RequiredBy(modules = {Dartagnan.class, })
    @Parameter(names = {"-i", "--input"},
            arity = 1,
            description = "Input source-code file path",
            converter = FileConverter.class,
            validateValueWith = {FileValidator.class, InputProgramExtensionValidator.class})
    public File inputProgramFile;

    @RequiredBy(modules = {Dartagnan.class, })
    @Parameter(names = {"-sm", "--source-model"},
            arity = 1,
            description = "Source weak memory model name",
            // uncomment when we'll be parsing .cat-files
            //converter = FileConverter.class,
            //validateValueWith = {FileValidator.class, InputModelExtensionValidator.class})
            converter = MemoryModelNameConverter.class,
            validateValueWith = MemoryModelNameValidator.class)
    public MemoryModelName sourceModel;

    //@RequiredBy(modules = {Porthos.class, })
    @Parameter(names = {"-tm", "--target-model"},
            arity = 1,
            description = "Target weak memory model name",
            // uncomment when we'll be parsing .cat-files
            //converter = FileConverter.class,
            //validateValueWith = {FileValidator.class, InputModelExtensionValidator.class})
            converter = MemoryModelNameConverter.class,
            validateValueWith = MemoryModelNameValidator.class)
    public MemoryModelName targetModel;

    @Parameter(names = {"-log", "--log"},
            converter = LogLevelConverter.class,
            validateValueWith = LogLevelValidator.class)
    public LogLevel logLevel;

    @Parameter(names = { "-h", "-?", "--help" },
            descriptionKey = "Print help message",
            help = true)
    private boolean help;
}
