package mousquetaires.languages.converters.toxrepr;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.converters.toytree.cmin.temporaries.YTempEntity;
import mousquetaires.languages.syntax.xrepr.XEntity;
import mousquetaires.languages.syntax.xrepr.XProgram;
import mousquetaires.languages.syntax.xrepr.datamodels.DataModel;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XSharedMemoryUnit;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.expressions.*;
import mousquetaires.languages.syntax.ytree.expressions.invocation.YFunctionArgument;
import mousquetaires.languages.syntax.ytree.expressions.invocation.YFunctionInvocationExpression;
import mousquetaires.languages.syntax.ytree.signatures.YFunctionParameter;
import mousquetaires.languages.syntax.ytree.statements.*;
import mousquetaires.languages.syntax.ytree.statements.artificial.YBugonStatement;
import mousquetaires.languages.syntax.ytree.statements.artificial.YProcess;
import mousquetaires.languages.types.YXType;
import mousquetaires.languages.types.YXTypeName;
import mousquetaires.languages.types.YXTypeSpecifier;
import mousquetaires.languages.visitors.YtreeBaseVisitor;
import mousquetaires.utils.exceptions.xrepr.InvalidLvalueException;
import mousquetaires.utils.exceptions.xrepr.InvalidRvalueException;
import mousquetaires.utils.exceptions.xrepr.UnallowedMemoryOperation;


class YtreeToXreprConverterVisitor extends YtreeBaseVisitor<XEntity> {

    //private final XCompiler interpreter;
    private final XProgramBuilder programBuilder;
    private final XMemoryManager memoryManager;

    YtreeToXreprConverterVisitor(ProgramLanguage language, DataModel dataModel) {
        //this.interpreter = interpreter;
        this.programBuilder = new XProgramBuilder();
        this.memoryManager = new XMemoryManager(language, dataModel);
    }

    public XProgram getProgram() {
        return programBuilder.build();
    }

    // TODO: All expressions must return XMemoryUnit instances, tentatively generating write events to them.

    @Override
    public XEntity visit(YSyntaxTree node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YProcess node) {
        programBuilder.startProcessDefinition(node.name);
        visit(node.body);
        programBuilder.endProcessDefinition();
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
        XEntity operator = visit(node.operator);  // TODO: process non-trivial assignment operators here!
        XEntity destinationEntity = visit(node.assignee);
        XMemoryUnit destination;
        try {
            destination = (XMemoryUnit) destinationEntity;
        } catch (ClassCastException e) {
            throw new InvalidLvalueException(destinationEntity);
        }
        XEntity sourceEntity = visit(node.expression);
        XMemoryUnit source;
        try {
            source = (XMemoryUnit) sourceEntity;
        } catch (ClassCastException e) {
            throw new InvalidRvalueException(destinationEntity);
        }

        XLocalMemoryUnit sourceLocal = (source instanceof XLocalMemoryUnit)
                ? (XLocalMemoryUnit) source
                : null;
        XSharedMemoryUnit sourceShared = (source instanceof XSharedMemoryUnit)
                ? (XSharedMemoryUnit) source
                : null;
        XLocalMemoryUnit destinationLocal = (destination instanceof XLocalMemoryUnit)
                ? (XLocalMemoryUnit) destination
                : null;
        XSharedMemoryUnit destinationShared = (destination instanceof XSharedMemoryUnit)
                ? (XSharedMemoryUnit) destination
                : null;

        if (destinationShared != null) {
            if (sourceShared != null) {
                throw new UnallowedMemoryOperation("Writes from shared memories unit to shared memories unit are not allowed");
            }
            if (sourceLocal != null) {
                return programBuilder.processSharedMemoryEvent(destinationShared, sourceLocal);
            }
        }
        if (destinationLocal != null) {
            if (sourceLocal != null) {
                return programBuilder.processLocalMemoryEvent(destinationLocal, sourceLocal);
            }
            if (sourceShared != null) {
                return programBuilder.processSharedMemoryEvent(destinationLocal, sourceShared);
            }
        }

        throw new IllegalStateException();
    }

    @Override
    public XEntity visit(YAssignmentExpression.Operator node) {
        return super.visit(node); // TODO: convert to temp operator x-entity
    }

    @Override
    public XEntity visit(YLinearStatement node) {
        return super.visit(node);
    }

    @Override
    public XEntity visit(YVariableDeclarationStatement node) {
        // TODO: determine type (kind) of memories unit here !!!
        return memoryManager.declareSharedMemoryUnit(node.variable.name, node.type);
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
                return memoryManager.getLocalMemoryUnit(node.name);
            case Global:
                return memoryManager.getSharedMemoryUnit(node.name);
            default:
                throw new IllegalArgumentException(node.kind.name());
        }
    }

    @Override
    public XEntity visit(YBranchingStatement node) {
        XMemoryUnit condition  = (XMemoryUnit) visit(node.condition);
        XLocalMemoryUnit conditionLocal;
        if (condition instanceof XSharedMemoryUnit) {
            conditionLocal = memoryManager.newTempLocalMemoryUnit();
        }
        else if (condition instanceof XLocalMemoryUnit) {
            conditionLocal = (XLocalMemoryUnit) condition;
        }
        else {
            throw new IllegalStateException();
        }

        XEntity thenBranch = visit(node.thenBranch);
        XEntity elseBranch = visit(node.elseBranch);

        // TODO: finish jump events structure

        return programBuilder.processControlFlowEvent();
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
