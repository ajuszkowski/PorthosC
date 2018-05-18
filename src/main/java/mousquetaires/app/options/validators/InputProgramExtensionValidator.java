package mousquetaires.app.options.validators;


import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import mousquetaires.languages.InputExtensions;
import mousquetaires.languages.InputLanguage;

import java.io.File;


public class InputProgramExtensionValidator implements IValueValidator<File> {

    @Override
    public void validate(String name, File value) throws ParameterException {
        InputLanguage language = InputExtensions.tryParseProgramLanguage(value.getName());
        if (language == null) {
            throw new ParameterException("The extension of the file '" + value.getAbsolutePath() +
                    "' cannot be recognised as for an input program");
        }
    }
}
