package mousquetaires.languages.converters.toxgraph;

import mousquetaires.languages.syntax.xgraph.XProgramInterpretationBuilder;
import mousquetaires.languages.syntax.xgraph.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryManager;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.languages.syntax.ytree.expressions.YExpression;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariableRef;
import mousquetaires.languages.syntax.ytree.expressions.unary.YPointerUnaryExpression;
import mousquetaires.languages.syntax.ytree.statements.YVariableDeclarationStatement;
import mousquetaires.languages.syntax.ytree.types.signatures.YParameter;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeVisitorStrictBase;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.ytree.YtreeVisitorIllegalStateException;


class YExpressionToXMemoryUnitConverter extends YtreeVisitorStrictBase<XMemoryUnit> {

    private final XMemoryManager memoryManager;
    private final XProgramInterpretationBuilder program;

    public YExpressionToXMemoryUnitConverter(XMemoryManager memoryManager,
                                             XProgramInterpretationBuilder program) {
        this.memoryManager = memoryManager;
        this.program = program;
    }

    public XMemoryUnit tryConvertOrThrow(YExpression expression) {
        return expression.accept(this);
    }

    public XMemoryUnit tryConvertOrNull(YExpression expression) {
        try {
            return tryConvertOrThrow(expression);
        }
        catch (YtreeVisitorIllegalStateException e) {
            return null;
        }
    }

    public XLocalMemoryUnit tryConvertToLocalOrThrow(YExpression expression) {
        XMemoryUnit memoryUnit = tryConvertOrThrow(expression);
        return program.currentProcess.copyToLocalMemoryIfNecessary(memoryUnit);
    }

    public XLocalMemoryUnit tryConvertToLocalOrNull(YExpression expression) {
        try {
            return tryConvertToLocalOrThrow(expression);
        }
        catch (YtreeVisitorIllegalStateException e) {
            return null;
        }
    }

    @Override
    public XMemoryUnit visit(YVariableDeclarationStatement node) {
        // TODO: test this
        YVariableRef variable = node.getVariable();
        return variable.isGlobal()
                ? memoryManager.newSharedMemoryUnit(variable.getName())
                : memoryManager.newLocalMemoryUnit(variable.getName());
    }

    @Override
    public XMemoryUnit visit(YVariableRef variable) {
        return variable.isGlobal()
                ? memoryManager.getSharedMemoryUnit(variable.getName())
                : memoryManager.getLocalMemoryUnit(variable.getName());
    }

    @Override
    public XMemoryUnit visit(YConstant node) {
        return memoryManager.getConstant(node.getValue()); //todo: type
    }

    @Override
    public XMemoryUnit visit(YParameter node) {
        throw new NotImplementedException();
    }

    @Override
    public XMemoryUnit visit(YPointerUnaryExpression node) {
        YExpression expression = node.getExpression();
        // TODO: do not cast, visit!
        if (!(expression instanceof YVariableRef)) {
            throw new NotImplementedException("yet only one-level pointers supported");
        }
        String name = ((YVariableRef) expression).getName();
        return memoryManager.getSharedMemoryUnit(name);
    }
}
