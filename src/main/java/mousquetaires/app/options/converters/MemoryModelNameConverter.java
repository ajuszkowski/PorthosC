package mousquetaires.app.options.converters;

import com.beust.jcommander.IStringConverter;
import mousquetaires.languages.InputExtensions;
import mousquetaires.languages.InputLanguage;
import mousquetaires.memorymodels.wmm.MemoryModel;
import mousquetaires.utils.exceptions.NotImplementedException;


public class MemoryModelNameConverter implements IStringConverter<MemoryModel.Kind> {

    @Override
    public MemoryModel.Kind convert(String value) {
        //MemoryModel.Kind result = MemoryModel.Kind.parse(value);
        //if (result != null) {
        //    return result;
        //}
        //// try to interpret as path to file with user-defined model
        //InputLanguage language = InputExtensions.tryParseProgramLanguage(value);
        //if (language == InputLanguage.Cat) {
        //    MemoryModel.Kind wmm = MemoryModel.Kind.UserDefined;
        //    wmm.setPathToUserDefinedModel(value);
        //    return wmm;
        //}
        return MemoryModel.Kind.parse(value);
    }
}
