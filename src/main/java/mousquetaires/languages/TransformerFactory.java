package mousquetaires.languages;

import mousquetaires.languages.cmin.transformer.CminToYtreeTransformer;
import mousquetaires.languages.porthos.PorthosToYtreeTransformer;


public class TransformerFactory {
    public static ProgramToYtreeTransformer getTransformer(ProgramLanguage inputLanguage) {
        switch (inputLanguage) {
            case Cmin:
                return new CminToYtreeTransformer();
            case Porthos:
                return new PorthosToYtreeTransformer();
            case Litmus:
                throw new RuntimeException("not implemented yet");
            default:
                throw new IllegalArgumentException("Unsupported language: " + inputLanguage.toString());
        }
    }
}
