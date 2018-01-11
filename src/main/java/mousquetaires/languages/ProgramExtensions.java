package mousquetaires.languages;

import org.apache.commons.io.FilenameUtils;

import java.util.HashMap;


public class ProgramExtensions {
    private static final HashMap<String, ProgramLanguage> inputProgramExtensionsMap =
            new HashMap<>() {{
                put("cmin", ProgramLanguage.Cmin);
                put("c", ProgramLanguage.Cmin);
                //put("pts", ProgramLanguage.Porthos);
                put("litmus", ProgramLanguage.Litmus);
            }};

    // Uncomment when being implementing the cat file parser
    //private static final HashMap<String, InputModelLanguage> inputModelExtensionsMap =
    //        new HashMap<>() {{
    //            put("cat", InputModelLanguage.CAT);
    //        }};

    // result is null if not valid extension
    public static ProgramLanguage tryParseProgramLanguage(String fileName) {
        return inputProgramExtensionsMap.get(FilenameUtils.getExtension(fileName));
    }

    public static ProgramLanguage parseProgramLanguage(String fileName) {
        ProgramLanguage result = inputProgramExtensionsMap.get(FilenameUtils.getExtension(fileName));
        if (result == null) {
            throw new IllegalArgumentException(fileName);
        }
        return result;
    }

    // Uncomment when being implementing the cat file parser
    //// result is null if not valid extension
    //public static InputModelLanguage tryParseInputModelExtension(String fileName) {
    //    return inputModelExtensionsMap.get(fileName);
    //}
}
