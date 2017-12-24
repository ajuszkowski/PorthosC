package mousquetaires.options;

import com.beust.jcommander.IStringConverter;
import mousquetaires.AppModule;


public class AppModuleConverter implements IStringConverter<AppModule> {

    @Override
    public AppModule convert(String value) {
        return AppModule.parse(value);
    }
}
