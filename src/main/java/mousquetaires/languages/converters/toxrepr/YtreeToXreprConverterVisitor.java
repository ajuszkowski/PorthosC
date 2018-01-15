package mousquetaires.languages.converters.toxrepr;

import mousquetaires.languages.converters.toytree.cmin.temporaries.YTempEntity;
import mousquetaires.languages.types.YXType;
import mousquetaires.languages.types.YXTypeName;
import mousquetaires.languages.types.YXTypeSpecifier;
import mousquetaires.languages.visitors.YtreeBaseVisitor;
import mousquetaires.languages.syntax.xrepr.XEntity;
import mousquetaires.languages.syntax.xrepr.XValue;
import mousquetaires.languages.syntax.xrepr.memory.XMemoryUnit;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.expressions.*;
import mousquetaires.languages.syntax.ytree.expressions.invocation.YFunctionArgument;
import mousquetaires.languages.syntax.ytree.expressions.invocation.YFunctionInvocationExpression;
import mousquetaires.languages.syntax.ytree.signatures.YFunctionParameter;
import mousquetaires.languages.syntax.ytree.statements.*;
import mousquetaires.languages.syntax.ytree.statements.artificial.YBugonStatement;
import mousquetaires.languages.syntax.ytree.statements.artificial.YProcess;
import mousquetaires.utils.exceptions.NotImplementedException;
import mousquetaires.utils.exceptions.xrepr.InvalidLvalueException;
import mousquetaires.utils.exceptions.xrepr.InvalidRvalueException;


class YtreeToXreprConverterVisitor extends YtreeBaseVisitor<XEntity> {

    private final XCompiler interpreter;

    YtreeToXreprConverterVisitor(XCompiler interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public XEntity visit(YSyntaxTree node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YProcess node) {
        interpreter.startProcessDefinition(node.name);
        visit(node.body);
        interpreter.endProcessDefinition();
        return null;
    }

    @Override
    public XEntity visit(YBlockStatement node) {
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
        //return programBuilder;
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
        // TODO: determine type of memory here !!!
        return interpreter.declareSharedMemoryUnit(node.variable.name, node.type);
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
        switch (node.kind) {
            case Local:
                return interpreter.getLocalMemoryUnit(node.name);
            case Global:
                return interpreter.getSharedMemoryUnit(node.name);
            default:
                throw new IllegalArgumentException(node.kind.name());
        }
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
