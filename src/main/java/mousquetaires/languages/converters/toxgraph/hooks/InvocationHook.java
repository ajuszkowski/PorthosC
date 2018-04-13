package mousquetaires.languages.converters.toxgraph.hooks;


interface InvocationHook {

    // TODO: work with signature
    InterceptionAction tryInterceptInvocation(String methodName);
}
