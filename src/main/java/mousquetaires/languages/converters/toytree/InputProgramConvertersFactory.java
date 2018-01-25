package mousquetaires.languages.converters.toytree;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.converters.toytree.c11.C11ToYtreeConverter;
import mousquetaires.utils.exceptions.NotImplementedException;


public class InputProgramConvertersFactory {

    public static InputProgramToYtreeConverter getYtreeConverter(ProgramLanguage inputLanguage) {
        switch (inputLanguage) {
            case C11:
                return new C11ToYtreeConverter();
            //case Porthos:
            //    return new PorthosToYtreeTransformer();
            case Litmus:
                throw new NotImplementedException();
            default:
                throw new UnsupportedOperationException("Unsupported language: " + inputLanguage.toString());
        }
    }
}
