package mousquetaires.options;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import java.io.File;


public abstract class FileValidator implements IValueValidator<File> {

    @Override
    public void validate(String name, File value) throws ParameterException {
        if (!value.isFile()) {
            throw new ParameterException("could not find file '" + value.getAbsolutePath() + "'");
        }
    }
}
