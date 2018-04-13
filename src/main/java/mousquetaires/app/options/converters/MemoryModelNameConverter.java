package mousquetaires.app.options.converters;

import com.beust.jcommander.IStringConverter;
import mousquetaires.memorymodels.wmm.MemoryModelKind;


public class MemoryModelNameConverter implements IStringConverter<MemoryModelKind> {

    @Override
    public MemoryModelKind convert(String value) {
        return MemoryModelKind.parse(value);
    }
}
