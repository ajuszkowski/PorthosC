package mousquetaires.languages.converters.toxgraph.hooks;

import mousquetaires.utils.exceptions.NotImplementedException;


class KernelInvocationHook implements InvocationHook {

    @Override
    public InterceptionAction tryInterceptInvocation(String methodName) {
        switch (methodName) {
            case "WRITE_ONCE":
                throw new NotImplementedException();
            case "READ_ONCE":
                throw new NotImplementedException();
            case "rcu_read_lock":
                throw new NotImplementedException();
            case "rcu_read_unlock":
                throw new NotImplementedException();
            case "synchronize_rcu":
                throw new NotImplementedException();
            default:
                return null;
        }
    }
}
