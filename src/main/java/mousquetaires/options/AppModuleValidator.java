package mousquetaires.options;


import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import mousquetaires.AppModule;

import java.util.Arrays;


public class AppModuleValidator implements IValueValidator<AppModule> {

    @Override
    public void validate(String name, AppModule value) throws ParameterException {
        if (value == null) {
            throw new ParameterException("invalid format of application module. Available options: " +
                    Arrays.toString(AppModule.values()).toLowerCase());
        }
    }
}
