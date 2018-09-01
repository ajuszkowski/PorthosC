package porthosc.app.options;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;
import com.beust.jcommander.converters.IntegerConverter;
import porthosc.app.AppBase;
import porthosc.app.modules.verdicts.OutputFormat;
import porthosc.app.options.converters.LogLevelConverter;
import porthosc.app.options.converters.OutputFormatConverter;
import porthosc.app.options.validators.FileValidator;
import porthosc.app.options.validators.LogLevelValidator;
import porthosc.app.options.validators.OutputFormatValidator;
import porthosc.utils.logging.LogLevel;

import java.io.File;


@Parameters(separators = " =")
public abstract class AppOptions {

    @Parameter(names = {"-b", "--bound"},
            converter = IntegerConverter.class)
    public int unrollingBound = 20;

    @Parameter(names = {"-log", "--log"},
            converter = LogLevelConverter.class,
            validateValueWith = LogLevelValidator.class)
    public transient LogLevel logLevel;

    @Parameter(names = {"-h", "-?", "--help"},
            descriptionKey = "Print help message",
            help = true)
    public transient boolean help;

    @Parameter(names = {"-d", "--dump-directory"},
            descriptionKey = "Directory for dumping graphs",
            converter = FileConverter.class)
    public transient File dumpDirectory;

    @Parameter(names = {"-f", "--format"},
            description = "Format of the output",
            converter = OutputFormatConverter.class,
            validateValueWith = OutputFormatValidator.class)
    public transient OutputFormat outputFormat = OutputFormat.Json;



    public void parse(String[] args) {
        JCommander.newBuilder().addObject(this).build().parse(args);
    }

    public String getUsageString() {
        JCommander jCommander = new JCommander(this);
        jCommander.setProgramName(AppBase.class.getName());
        StringBuilder builder = new StringBuilder();
        jCommander.usage(builder);
        return builder.toString();
    }
}
