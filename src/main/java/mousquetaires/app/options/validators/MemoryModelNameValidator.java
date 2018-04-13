package mousquetaires.app.options.validators;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import mousquetaires.memorymodels.wmm.MemoryModelKind;

import java.util.Arrays;


public class MemoryModelNameValidator implements IValueValidator<MemoryModelKind> {

    @Override
    public void validate(String name, MemoryModelKind value) throws ParameterException {
        if (value == null) {
            throw new ParameterException("Invalid memory model name. Available options: " +
                    Arrays.toString(MemoryModelKind.values()).toLowerCase());
        }
    }
}
