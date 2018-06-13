package porthosc.languages.converters.toxgraph.hooks;

import porthosc.languages.converters.toxgraph.interpretation.XProgramInterpreter;


public abstract class XInvocationHookBase implements XInvocationHook {
    protected final XProgramInterpreter program;

    public XInvocationHookBase(XProgramInterpreter program) {
        this.program = program;
    }
}
