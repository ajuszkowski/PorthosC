package mousquetaires.interpretation.internalrepr;

import mousquetaires.languages.ProgramLanguage;


public class InternalInterpreter {

    private final TypeManager typeManager;

    public InternalInterpreter(ProgramLanguage language) {
        this.typeManager = new TypeManager(language);
    }

    //public YEntity createMethodInvocation()
}
