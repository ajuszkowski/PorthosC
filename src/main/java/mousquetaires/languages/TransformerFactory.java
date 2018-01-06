package mousquetaires.languages;

import mousquetaires.interpretation.eventrepr.Interpreter;
import mousquetaires.languages.porthos.PorthosToInternalTransformer;
import mousquetaires.languages.cmin.transformer.CminToInternalTransformer;


public class TransformerFactory {
    public static SyntaxTreeToInternalTransformer getTransformer(ProgramLanguage inputLanguage, Interpreter interpreter) {
        switch (inputLanguage) {
            case Cmin:
                return new CminToInternalTransformer(interpreter);
            case Porthos:
                return new PorthosToInternalTransformer(interpreter);
            case Litmus:
                throw new RuntimeException("not implemented yet");
            default:
                throw new IllegalArgumentException("Unsupported language: " + inputLanguage.toString());
        }
    }
}
