package mousquetaires.languages.transformers;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.transformers.cmin.CminToYtreeConverter;
import mousquetaires.utils.exceptions.NotImplementedException;


public class ProgramTransformerFactory {

    public static ProgramToYtreeConverter getTransformer(ProgramLanguage inputLanguage) {
        switch (inputLanguage) {
            case Cmin:
                return new CminToYtreeConverter();
            //case Porthos:
            //    return new PorthosToYtreeTransformer();
            case Litmus:
                throw new NotImplementedException();
            default:
                throw new IllegalArgumentException("Unsupported language: " + inputLanguage.toString());
        }
    }
}
