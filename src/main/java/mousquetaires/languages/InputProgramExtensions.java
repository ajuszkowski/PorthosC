package mousquetaires.languages;

import java.util.HashMap;


public class InputProgramExtensions {

    private static final HashMap<String, InputProgramLanguage> inputProgramExtensionsMap =
            new HashMap<>() {{
                put("cmin", InputProgramLanguage.Cmin);
                put("pts", InputProgramLanguage.Porthos);
                put("litmus", InputProgramLanguage.Litmus);
            }};

    // Uncomment when being implementing the cat file parser
    //private static final HashMap<String, InputModelLanguage> inputModelExtensionsMap =
    //        new HashMap<>() {{
    //            put("cat", InputModelLanguage.CAT);
    //        }};

    // result is null if not valid extension
    public static InputProgramLanguage tryParseInputProgramExtension(String fileName) {
        return inputProgramExtensionsMap.get(fileName);
    }

    // Uncomment when being implementing the cat file parser
    //// result is null if not valid extension
    //public static InputModelLanguage tryParseInputModelExtension(String fileName) {
    //    return inputModelExtensionsMap.get(fileName);
    //}
}
