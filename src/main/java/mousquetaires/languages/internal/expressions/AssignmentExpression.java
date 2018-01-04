package mousquetaires.languages.internal.expressions;

import mousquetaires.execution.memory.MemoryLocation;


public class AssignmentExpression extends Expression {
    public final MemoryLocation destination;
    public final Expression source;

    public AssignmentExpression(String originalExpression, MemoryLocation destination, Expression source) {
        super(originalExpression);
        this.destination = destination;
        this.source = source;
    }
}
