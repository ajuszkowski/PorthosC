package mousquetaires.languages.common.transformers.ytree;

import mousquetaires.interpretation.Interpreter;
import mousquetaires.interpretation.exceptions.InvalidLvalueException;
import mousquetaires.interpretation.exceptions.InvalidRvalueException;
import mousquetaires.interpretation.exceptions.UndeclaredMemoryUnitException;
import mousquetaires.languages.common.transformers.cmin.temp.YTempEntity;
import mousquetaires.languages.common.types.YXType;
import mousquetaires.languages.common.types.YXTypeName;
import mousquetaires.languages.common.types.YXTypeSpecifier;
import mousquetaires.languages.common.visitors.YtreeBaseVisitor;
import mousquetaires.languages.xrepr.XEntity;
import mousquetaires.languages.xrepr.XValue;
import mousquetaires.languages.xrepr.events.memory.XStoreEvent;
import mousquetaires.languages.xrepr.memory.XMemoryUnit;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.ytree.expressions.*;
import mousquetaires.languages.ytree.expressions.invocation.YFunctionArgument;
import mousquetaires.languages.ytree.expressions.invocation.YFunctionInvocationExpression;
import mousquetaires.languages.ytree.signatures.YFunctionParameter;
import mousquetaires.languages.ytree.statements.*;
import mousquetaires.languages.ytree.statements.artificial.YBugonStatement;
import mousquetaires.languages.ytree.statements.artificial.YProcess;
import mousquetaires.utils.exceptions.NotImplementedException;


public class YtreeToXreprConverterVisitor extends YtreeBaseVisitor<XEntity> {

    private final Interpreter interpreter;

    public YtreeToXreprConverterVisitor(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public XEntity visit(YSyntaxTree node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YProcess node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YBugonStatement node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YConstant node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YMemberAccess node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YFunctionArgument node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YFunctionInvocationExpression node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YFunctionParameter node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YFunctionDefinitionStatement node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YUnaryExpression node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YUnaryExpression.Operator node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YBinaryExpression node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YBinaryExpression.Operator node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YTernaryExpression node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YAssignmentExpression node) {
        // todo: dont forget to process operator
        XEntity destinationEntity = visit(node.assignee);
        XMemoryUnit destination;
        try {
            destination = (XMemoryUnit) destinationEntity;
        } catch (ClassCastException e) {
            throw new InvalidLvalueException(destinationEntity);
        }
        XEntity sourceEntity = visit(node.expression);
        // todo: instanceof : value / other location / ..?
        // todo: if pure value, firstly write it to registry?
        XValue source;
        try {
            source = (XValue) sourceEntity;
        } catch (ClassCastException e) {
            throw new InvalidRvalueException(destinationEntity);
        }
        return new XStoreEvent(null, source, destination);
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YAssignmentExpression.Operator node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YLinearStatement node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YVariableDeclarationStatement node) {
        // TODO: determine type of memory here
        XMemoryUnit.Kind memoryKind = XMemoryUnit.Kind.Shared;
        return interpreter.createMemoryLocation(node.variable.name, node.type, memoryKind);
    }

    @Override
    public XEntity visit(YXType node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YXTypeName node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YXTypeSpecifier node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YVariableRef node) {
        String name = node.name;
        XMemoryUnit location = interpreter.tryGetMemoryLocation(name);
        if (location == null) {
            throw new UndeclaredMemoryUnitException(name);
        }
        return location;
    }

    @Override
    public XEntity visit(YVariableRef.Kind node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YBlockStatement node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YBranchingStatement node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YLoopStatement node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YJumpStatement node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YTempEntity node) {
        return super.visit(node);
    }
}
