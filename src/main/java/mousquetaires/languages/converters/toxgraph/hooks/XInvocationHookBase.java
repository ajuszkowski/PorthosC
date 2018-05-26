package mousquetaires.languages.converters.toxgraph.hooks;

import mousquetaires.languages.converters.toxgraph.interpretation.XProgramInterpreter;


public abstract class XInvocationHookBase implements XInvocationHook {
    protected final XProgramInterpreter program;

    public XInvocationHookBase(XProgramInterpreter program) {
        this.program = program;
    }
}
