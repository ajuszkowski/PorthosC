package mousquetaires.languages.converters.toytree;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.common.citation.CodeCitationService;
import mousquetaires.languages.converters.toytree.c11.C2YtreeConverter;
import mousquetaires.utils.exceptions.NotImplementedException;


public class InputProgramConvertersFactory {

    private final CodeCitationService citationService;

    public InputProgramConvertersFactory(CodeCitationService citationService) {
        this.citationService = citationService;
    }

    public InputProgram2YtreeConverter getConverter(ProgramLanguage inputLanguage) {
        switch (inputLanguage) {
            case C11:
                return new C2YtreeConverter(citationService);
            //case Porthos:
            //    return new PorthosToYtreeTransformer();
            case Litmus:
                throw new NotImplementedException();
            default:
                throw new UnsupportedOperationException("Unsupported language: " + inputLanguage.toString());
        }
    }
}
