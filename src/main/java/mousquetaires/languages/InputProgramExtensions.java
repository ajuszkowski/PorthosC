package mousquetaires.languages;

import java.util.HashMap;


public class InputProgramExtensions {

    private static final HashMap<String, InputProgramLanguage> inputProgramExtensionsMap =
            new HashMap<>() {{
                put("cmin", InputProgramLanguage.CMIN);
                put("pts", InputProgramLanguage.PORTHOS);
                put("litmus", InputProgramLanguage.LITMUS);
            }};

    private static final HashMap<String, InputModelLanguage> inputModelExtensionsMap =
            new HashMap<>() {{
                put("cat", InputModelLanguage.CAT);
            }};

    // result is null if not valid extension
    public static InputProgramLanguage tryParseInputProgramExtension(String fileName) {
        return inputProgramExtensionsMap.get(fileName);
    }

    // result is null if not valid extension
    public static InputModelLanguage tryParseInputModelExtension(String fileName) {
        return inputModelExtensionsMap.get(fileName);
    }
}
