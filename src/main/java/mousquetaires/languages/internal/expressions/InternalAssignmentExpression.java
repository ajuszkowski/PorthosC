package mousquetaires.languages.internal.expressions;

import mousquetaires.execution.memory.MemoryLocation;


public class InternalAssignmentExpression extends InternalExpression {

    public final MemoryLocation destination;
    public final InternalExpression source;

    public InternalAssignmentExpression(String originalExpression, MemoryLocation destination, InternalExpression source) {
        super(originalExpression);
        this.destination = destination;
        this.source = source;
    }
}
