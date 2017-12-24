package mousquetaires.options;


import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import mousquetaires.languages.InputProgramExtensions;
import mousquetaires.languages.InputProgramLanguage;

import java.io.File;


public class InputProgramExtensionValidator implements IValueValidator<File> {

    @Override
    public void validate(String name, File value) throws ParameterException {
        InputProgramLanguage language = InputProgramExtensions.tryParseInputProgramExtension(value.getName());
        if (language == null) {
            throw new ParameterException("extension of the file '" + value.getAbsolutePath() +
                    "' cannot be recognised as for an input program");
        }
    }
}
