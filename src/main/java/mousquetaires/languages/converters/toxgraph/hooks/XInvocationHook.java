package mousquetaires.languages.converters.toxgraph.hooks;


interface XInvocationHook {

    // TODO: work with signature
    XInvocationHookAction tryInterceptInvocation(String methodName);
}
