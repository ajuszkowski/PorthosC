package mousquetaires.languages.converters.toxgraph.hooks;


interface InvocationHook {

    // TODO: work with signature
    XInvocationHookAction tryInterceptInvocation(String methodName);
}
