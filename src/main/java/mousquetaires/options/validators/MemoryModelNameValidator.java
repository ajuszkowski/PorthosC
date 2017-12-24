package mousquetaires.options.validators;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import mousquetaires.models.MemoryModelName;

import java.util.Arrays;


public class MemoryModelNameValidator implements IValueValidator<MemoryModelName> {

    @Override
    public void validate(String name, MemoryModelName value) throws ParameterException {
        if (value == null) {
            throw new ParameterException("invalid memory model name. Available options: " +
                    Arrays.toString(MemoryModelName.values()).toLowerCase());
        }
    }
}
