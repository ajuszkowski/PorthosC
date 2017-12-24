package mousquetaires.app.options.converters;

import com.beust.jcommander.IStringConverter;
import mousquetaires.utils.logging.LogLevel;


public class LogLevelConverter implements IStringConverter<LogLevel> {

    @Override
    public LogLevel convert(String value) {
        return LogLevel.parse(value);
    }
}
