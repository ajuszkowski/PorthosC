package mousquetaires.options.converters;

import com.beust.jcommander.IStringConverter;
import mousquetaires.models.MemoryModelName;


public class MemoryModelNameConverter implements IStringConverter<MemoryModelName> {

    @Override
    public MemoryModelName convert(String value) {
        return MemoryModelName.parse(value);
    }
}
