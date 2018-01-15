package mousquetaires.languages.converters.toytree;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.converters.toytree.cmin.CminToYtreeConverter;
import mousquetaires.utils.exceptions.NotImplementedException;


public class InputProgramConvertersFactory {

    public static InputProgramToYtreeConverter getYtreeConverter(ProgramLanguage inputLanguage) {
        switch (inputLanguage) {
            case Cmin:
                return new CminToYtreeConverter();
            //case Porthos:
            //    return new PorthosToYtreeTransformer();
            case Litmus:
                throw new NotImplementedException();
            default:
                throw new UnsupportedOperationException("Unsupported language: " + inputLanguage.toString());
        }
    }
}
