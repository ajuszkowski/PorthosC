package mousquetaires.app.options.validators;


import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import mousquetaires.app.modules.AppModuleName;

import java.util.Arrays;


public class AppModuleValidator implements IValueValidator<AppModuleName> {

    @Override
    public void validate(String name, AppModuleName value) throws ParameterException {
        if (value == null) {
            throw new ParameterException("Invalid format of application moduleName. Available options: " +
                    Arrays.toString(AppModuleName.values()).toLowerCase());
        }
    }
}
