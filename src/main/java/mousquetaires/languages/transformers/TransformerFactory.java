package mousquetaires.languages.transformers;

import mousquetaires.languages.ProgramLanguage;


public class TransformerFactory {
    public static ISyntaxTreeToProgramTransformer getTransformer(ProgramLanguage inputLanguage) {
        switch (inputLanguage) {
            case Cmin:
                return new C11ToProgramTransformer();
            case Porthos:
                return new PorthosToProgramTransformer();
            case Litmus:
                throw new RuntimeException("not implemented yet");
            default:
                throw new IllegalArgumentException("Unsupported language: " + inputLanguage.toString());
        }
    }
}
