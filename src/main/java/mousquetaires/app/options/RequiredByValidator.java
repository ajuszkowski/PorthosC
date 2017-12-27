package mousquetaires.app.options;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import mousquetaires.starters.AppModule;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class RequiredByValidator {

    public static void validateOptions(AppModule module, CommandLineOptions options) {
        List<String> notSetFields = new ArrayList<>(8);
        boolean needThrow = false;
        for (Field field : options.getClass().getFields()) {
            final Parameter parameterAnnotation = field.getAnnotation(Parameter.class);
            if (parameterAnnotation != null) {
                if (!parameterAnnotation.required()) {
                    final RequiredBy requiredByAnnotation = field.getAnnotation(RequiredBy.class);
                    if (requiredByAnnotation == null) {
                        continue;
                    }
                    for (Class requiredBy : requiredByAnnotation.modules()) {
                        try {
                            if (requiredBy.equals(module.getClass())) {
                                notSetFields.add("[" + String.join(",", parameterAnnotation.names()) + "]");
                                if (field.get(options) == null) {
                                    needThrow = true;
                                    break;
                                }
                            }
                        } catch (IllegalAccessException e) {
                            // TODO: log
                            System.err.println("error : " + e.getMessage() + "\n" + e.getStackTrace());
                        }
                    }
                }
            }
        }
        if (needThrow) {
            throw new ParameterException("Command line options parsing error:\nThe module " +
                    module.getClass().getSimpleName() + " requires the following command line options to be set: " +
                    String.join(" | ", notSetFields));
        }
    }
}
