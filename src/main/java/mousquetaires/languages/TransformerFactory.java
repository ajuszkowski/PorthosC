package mousquetaires.languages;

import mousquetaires.languages.porthos.PorthosToInternalTransformer;
import mousquetaires.languages.cmin.transformer.CminToInternalTransformer;


public class TransformerFactory {
    public static SyntaxTreeToInternalTransformer getTransformer(ProgramLanguage inputLanguage) {
        switch (inputLanguage) {
            case Cmin:
                return new CminToInternalTransformer();
            case Porthos:
                return new PorthosToInternalTransformer();
            case Litmus:
                throw new RuntimeException("not implemented yet");
            default:
                throw new IllegalArgumentException("Unsupported language: " + inputLanguage.toString());
        }
    }
}
