package mousquetaires.app.options.converters;

import com.beust.jcommander.IStringConverter;
import mousquetaires.memorymodels.wmm.MemoryModel;


public class MemoryModelNameConverter implements IStringConverter<MemoryModel.Kind> {

    @Override
    public MemoryModel.Kind convert(String value) {
        return MemoryModel.Kind.parse(value);
    }
}
