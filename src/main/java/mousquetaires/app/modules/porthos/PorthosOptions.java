package mousquetaires.app.modules.porthos;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import mousquetaires.app.options.AppOptions;
import mousquetaires.app.options.converters.MemoryModelNameConverter;
import mousquetaires.app.options.converters.PorthosModeConverter;
import mousquetaires.app.options.validators.FileValidator;
import mousquetaires.app.options.validators.InputProgramExtensionValidator;
import mousquetaires.app.options.validators.MemoryModelNameValidator;
import mousquetaires.app.options.validators.PorthosModeValidator;
import mousquetaires.memorymodels.wmm.MemoryModelKind;

import java.io.File;


public class PorthosOptions extends AppOptions {

    @Parameter(names = {"-i", "--input"},
            arity = 1,
            description = "Input source-code file path",
            converter = FileConverter.class,
            validateValueWith = {FileValidator.class, InputProgramExtensionValidator.class})
    public File inputProgramFile;

    @Parameter(names = {"-s", "--source-model"},
            required = true,
            arity = 1,
            description = "Source weak memory model name",
            // uncomment when we'll be parsing .cat-files
            //converter = FileConverter.class,
            //validateValueWith = {FileValidator.class, InputModelExtensionValidator.class})
            converter = MemoryModelNameConverter.class,
            validateValueWith = MemoryModelNameValidator.class)
    public MemoryModelKind sourceModel;

    @Parameter(names = {"-t", "--target-model"},
            required = true,
            arity = 1,
            description = "Target weak memory model name",
            // uncomment when we'll be parsing .cat-files
            //converter = FileConverter.class,
            //validateValueWith = {FileValidator.class, InputModelExtensionValidator.class})
            converter = MemoryModelNameConverter.class,
            validateValueWith = MemoryModelNameValidator.class)
    public MemoryModelKind targetModel;

    @Parameter(names = {"-m", "--mode"},
            arity = 1,
            description = "Mode of the portability analysis",
            // uncomment when we'll be parsing .cat-files
            //converter = FileConverter.class,
            //validateValueWith = {FileValidator.class, InputModelExtensionValidator.class})
            converter = PorthosModeConverter.class,
            validateValueWith = PorthosModeValidator.class)
    public PorthosMode mode = PorthosMode.StateInclusion;


}
