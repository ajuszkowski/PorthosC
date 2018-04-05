package mousquetaires.languages.converters.toxgraph;

import mousquetaires.languages.common.Type;
import mousquetaires.languages.converters.toxgraph.interpretation.XMemoryManager;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariableRef;
import mousquetaires.languages.syntax.ytree.expressions.unary.YPointerUnaryExpression;
import mousquetaires.languages.syntax.ytree.statements.YVariableDeclarationStatement;
import mousquetaires.languages.syntax.ytree.types.signatures.YParameter;
import mousquetaires.languages.syntax.ytree.visitors.ytree.YtreeNullVisitorBase;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.xgraph.XInterpretationError;

import static mousquetaires.utils.StringUtils.wrap;


public class YExpressionToXMemoryUnitConverter extends YtreeNullVisitorBase<XMemoryUnit> {

    private final XMemoryManager memoryManager;

    // todo: must be per-process or stateful (processNextProcess() )

    public YExpressionToXMemoryUnitConverter(XMemoryManager memoryManager) {
        this.memoryManager = memoryManager;
    }

    @Override
    public XLvalueMemoryUnit visit(YVariableDeclarationStatement node) {
        YVariableRef variable = node.getVariable();
        String name = variable.getName();
        Type type = YTypeToBitnessConverter.convert(node.getType());
        return variable.isGlobal()
                ? memoryManager.declareLocation(name, type)
                : memoryManager.declareRegister(name, type);
    }

    @Override
    public XSharedMemoryUnit visit(YPointerUnaryExpression node) {
        XMemoryUnit visited = node.getExpression().accept(this);
        if (!(visited instanceof XLvalueMemoryUnit)) {
            throw new XInterpretationError("Pointer argument is not an l-value: " + wrap(visited));
        }
        XLvalueMemoryUnit lvalue = (XLvalueMemoryUnit) visited;
        String name = lvalue.getName();
        return memoryManager.redeclareAsSharedIfNeeded(name);
    }

    @Override
    public XLvalueMemoryUnit visit(YVariableRef variable) {
        return memoryManager.getUnit(variable.getName());
    }

    @Override
    public XConstant visit(YConstant node) {
        Type type = YTypeToBitnessConverter.convert(node.getType());
        return XConstant.create(node.getValue(), type);
    }

    @Override
    public XMemoryUnit visit(YParameter node) {
        throw new NotImplementedException();
    }
}
