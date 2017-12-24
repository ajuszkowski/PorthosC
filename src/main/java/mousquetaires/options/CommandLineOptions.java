package mousquetaires.options;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;
import mousquetaires.AppModule;
import mousquetaires.models.MemoryModelName;
import mousquetaires.utils.logging.LogLevel;

import java.io.File;


@Parameters(separators=" =")
public class CommandLineOptions {

    public static CommandLineOptions parse(String[] args) {
        CommandLineOptions options = new CommandLineOptions();
        JCommander.newBuilder().addObject(options).build().parse(args);
        return options;
    }

    @Parameter(names = {"-m", "--module"},
            required = true,
            arity = 1,
            description = "module name",
            converter = AppModuleConverter.class,
            validateValueWith = AppModuleValidator.class)
    public AppModule module;

    @Parameter(names = {"-i", "--input"},
            required = true,
            arity = 1,
            description = "input file path",
            converter = FileConverter.class,
            validateValueWith = {FileValidator.class, InputProgramExtensionValidator.class})
    public File inputProgramFile;

    @Parameter(names = {"-sm", "--source-model"},
            required = true,
            arity = 1,
            description = "...",
            // uncomment when we'll be parsing .cat-files
            //converter = FileConverter.class,
            //validateValueWith = {FileValidator.class, InputModelExtensionValidator.class})
            converter = MemoryModelNameConverter.class,
            validateValueWith = MemoryModelNameValidator.class)
    public MemoryModelName sourceModel;

    @Parameter(names = {"-tm", "--target-model"},
            required = false,
            arity = 1,
            description = "...",
            // uncomment when we'll be parsing .cat-files
            //converter = FileConverter.class,
            //validateValueWith = {FileValidator.class, InputModelExtensionValidator.class})
            converter = MemoryModelNameConverter.class,
            validateValueWith = MemoryModelNameValidator.class)
    public MemoryModelName targetModel;

    @Parameter(names = {"-log", "--log"},
            required = false,
            converter = LogLevelConverter.class,
            validateValueWith = LogLevelValidator.class)
    public LogLevel logLevel;
}
