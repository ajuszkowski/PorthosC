package mousquetaires.options;

import com.beust.jcommander.IStringConverter;
import mousquetaires.starters.AppModuleName;


public class AppModuleConverter implements IStringConverter<AppModuleName> {

    @Override
    public AppModuleName convert(String value) {
        return AppModuleName.parse(value);
    }
}
