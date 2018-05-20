package mousquetaires.languages.converters.toxgraph.hooks;

import mousquetaires.languages.converters.toxgraph.interpretation.XProgramInterpreter;


public abstract class InvocationHookBase implements InvocationHook {
    protected final XProgramInterpreter program;

    public InvocationHookBase(XProgramInterpreter program) {
        this.program = program;
    }
}
