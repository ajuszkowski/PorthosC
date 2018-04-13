package mousquetaires.languages.converters.toxgraph.hooks;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.converters.toxgraph.interpretation.XProgramInterpreter;

import java.util.HashSet;


public class HookManager implements InvocationHook {

    private final ImmutableSet<InvocationHook> registeredHooks;

    // TODO: accept the settings structure as argument for DISABLING unnecessary type processors (without doing this, may be unwanted errors)
    public HookManager(XProgramInterpreter program) {
        this.registeredHooks = ImmutableSet.copyOf(
                new HashSet<InvocationHook>() {{
                    add(new HardwareInvocationHook(program));
                    add(new KernelInvocationHook());
                }});
    }

    @Override
    public InterceptionAction tryInterceptInvocation(String methodName) {
        for (InvocationHook hook : registeredHooks) {
            InterceptionAction result = hook.tryInterceptInvocation(methodName);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
