package mousquetaires.languages.transformers;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.transformers.cmin.CminToYtreeTransformer;
import mousquetaires.utils.exceptions.NotImplementedException;


public class ProgramTransformerFactory {

    public static ProgramToYtreeTransformer getTransformer(ProgramLanguage inputLanguage) {
        switch (inputLanguage) {
            case Cmin:
                return new CminToYtreeTransformer();
            //case Porthos:
            //    return new PorthosToYtreeTransformer();
            case Litmus:
                throw new NotImplementedException();
            default:
                throw new IllegalArgumentException("Unsupported language: " + inputLanguage.toString());
        }
    }
}
