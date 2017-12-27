package mousquetaires.app.modules.dartagnan;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import mousquetaires.app.options.AppOptions;
import mousquetaires.app.options.converters.MemoryModelNameConverter;
import mousquetaires.app.options.validators.FileValidator;
import mousquetaires.app.options.validators.InputProgramExtensionValidator;
import mousquetaires.app.options.validators.MemoryModelNameValidator;
import mousquetaires.models.MemoryModelName;

import java.io.File;


public class DartagnanOptions extends AppOptions {

    @Parameter(names = {"-i", "--input"},
            arity = 1,
            description = "Input source-code file path",
            converter = FileConverter.class,
            validateValueWith = {FileValidator.class, InputProgramExtensionValidator.class})
    public File inputProgramFile;

    @Parameter(names = {"-sm", "--source-model"},
            arity = 1,
            description = "Source weak memory model name",
            // uncomment when we'll be parsing .cat-files
            //converter = FileConverter.class,
            //validateValueWith = {FileValidator.class, InputModelExtensionValidator.class})
            converter = MemoryModelNameConverter.class,
            validateValueWith = MemoryModelNameValidator.class)
    public MemoryModelName sourceModel;
}
