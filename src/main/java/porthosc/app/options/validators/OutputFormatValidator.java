package porthosc.app.options.validators;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import porthosc.app.modules.verdicts.OutputFormat;

import java.util.Arrays;


public class OutputFormatValidator implements IValueValidator<OutputFormat> {

    @Override
    public void validate(String name, OutputFormat value) throws ParameterException {
        if (value == null) {
            throw new ParameterException("Invalid output format. Available options: " +
                    Arrays.toString(OutputFormat.values()));
        }
    }
}
