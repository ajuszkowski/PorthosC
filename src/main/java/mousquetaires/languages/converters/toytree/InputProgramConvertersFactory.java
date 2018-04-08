package mousquetaires.languages.converters.toytree;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.converters.toytree.c11.C2YtreeConverter;
import mousquetaires.utils.exceptions.NotImplementedException;


public class InputProgramConvertersFactory {

    public static InputProgram2YtreeConverter getYtreeConverter(ProgramLanguage inputLanguage) {
        switch (inputLanguage) {
            case C11:
                return new C2YtreeConverter();
            //case Porthos:
            //    return new PorthosToYtreeTransformer();
            case Litmus:
                throw new NotImplementedException();
            default:
                throw new UnsupportedOperationException("Unsupported language: " + inputLanguage.toString());
        }
    }
}
