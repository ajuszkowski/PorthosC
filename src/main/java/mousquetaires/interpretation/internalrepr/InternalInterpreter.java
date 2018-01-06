package mousquetaires.interpretation.internalrepr;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.internalrepr.InternalEntity;


public class InternalInterpreter {

    private final TypeManager typeManager;

    public InternalInterpreter(ProgramLanguage language) {
        this.typeManager = new TypeManager(language);
    }

    //public InternalEntity createMethodInvocation()
}
