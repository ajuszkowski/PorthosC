package mousquetaires.languages.internallang_deleteme.expressions;

import mousquetaires.languages.internallang_deleteme.elements.InternalMemoryLocation;


public class InternalAssignmentExpression extends InternalExpression {
    public final InternalMemoryLocation destination;
    public final InternalMemoryLocation source;

    public InternalAssignmentExpression(InternalMemoryLocation destination, InternalMemoryLocation source) {
        this.destination = destination;
        this.source = source;
    }
}
