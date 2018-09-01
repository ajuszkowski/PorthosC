package porthosc.app.options.converters;

import com.beust.jcommander.IStringConverter;
import porthosc.app.modules.verdicts.OutputFormat;


public class OutputFormatConverter implements IStringConverter<OutputFormat> {

    @Override
    public OutputFormat convert(String value) {
        return OutputFormat.parse(value);
    }
}
