package mousquetaires.app.options.converters;

import com.beust.jcommander.IStringConverter;
import mousquetaires.memorymodels.MemoryModelName;


public class MemoryModelNameConverter implements IStringConverter<MemoryModelName> {

    @Override
    public MemoryModelName convert(String value) {
        return MemoryModelName.parse(value);
    }
}
