package mousquetaires.languages.converters.toxrepr;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.syntax.xrepr.XEntity;
import mousquetaires.languages.syntax.xrepr.XProgram;
import mousquetaires.languages.syntax.xrepr.XProgramBuilder;
import mousquetaires.languages.syntax.xrepr.datamodels.DataModel;
import mousquetaires.languages.syntax.xrepr.events.XEvent;
import mousquetaires.languages.syntax.xrepr.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xrepr.events.computation.XOperator;
import mousquetaires.languages.syntax.xrepr.memories.XLocalMemoryUnit;
import mousquetaires.languages.syntax.xrepr.memories.XMemoryManager;
import mousquetaires.languages.syntax.xrepr.memories.XMemoryUnit;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.YSyntaxTree;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.YTernaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.YVariableRef;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YIndexerExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YInvocationExpression;
import mousquetaires.languages.syntax.ytree.expressions.accesses.YMemberAccessExpression;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YBinaryExpression;
import mousquetaires.languages.syntax.ytree.expressions.unary.YUnaryExpression;
import mousquetaires.languages.syntax.ytree.specific.YProcessStatement;
import mousquetaires.languages.syntax.ytree.statements.*;
import mousquetaires.languages.syntax.ytree.statements.jumps.YJumpStatement;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.languages.syntax.ytree.types.signatures.YMethodSignature;
import mousquetaires.languages.syntax.ytree.types.signatures.YParameter;
import mousquetaires.languages.visitors.ytree.YtreeBaseVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;


class YtreeToXreprConverterVisitor extends YtreeBaseVisitor<XEntity> {

    //private final XCompiler interpreter;
    private final XProgramBuilder program;

    public XProgram getProgram() {
        return program.build();
    }

    YtreeToXreprConverterVisitor(ProgramLanguage language, DataModel dataModel) {
        //this.interpreter = interpreter;
        this.program = new XProgramBuilder(new XMemoryManager(language, dataModel));
    }

    @Override
    public XEntity visit(YSyntaxTree node) {
        XEntity firstEvent = null;
        for (YEntity root : node.getRoots()) {
            XEntity event = visit(root);
            if (firstEvent == null) {
                firstEvent = event;
            }
        }
        return firstEvent;
    }

    //@Override
    //public XEntity visit(YPreludeStatement node) {
    //    throw new NotImplementedException();
    //}

    @Override
    public XEntity visit(YProcessStatement node) {
        program.startProcessDefinition(node.getProcessId());
        return visit(node.getBody());
    }

    //@Override
    //public XEntity visit(YPostludeStatement node) {
    //    throw new NotImplementedException();
    //}
    //
    //@Override
    //public XEntity visit(YVariableAssertion node) {
    //    throw new NotImplementedException();
    //}

    //@Override
    //public XMemoryUnit visit(YExpression node) {
    //    // todo: strange OOP, find out why method with argument of type subtype still gets there instead of overloads
    //    if (node instanceof YUnaryExpression) {
    //        return visit((YUnaryExpression) node);
    //    }
    //    if (node instanceof YBinaryExpression) {
    //        return visit((YBinaryExpression) node);
    //    }
    //    throw new NotImplementedException();
    //}

    @Override
    public XEntity visit(YConstant node) {
        // todo: convert type 'node.getType()'
        return program.memoryManager.getConstant(node.getValue(), XMemoryUnit.Bitness.bit16);
    }

    @Override
    public XEntity visit(YIndexerExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YMemberAccessExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YInvocationExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YFunctionDefinition node) {
        program.startProcessDefinition("function_name"); // todo: function name
        XEntity result = visit(node.getBody());
        program.finishProcessDefinition();
        return result;
    }

    //@Override
    //public XEntity visit(YRelativeBinaryExpression node) {
    //    throw new NotImplementedException();
    //}
    //
    //@Override
    //public XEntity visit(YLogicalBinaryExpression node) {
    //    throw new NotImplementedException();
    //}
    //
    //@Override
    //public XEntity visit(YIntegerBinaryExpression node) {
    //    throw new NotImplementedException();
    //}
    //
    //@Override
    //public XEntity visit(YLogicalUnaryExpression node) {
    //    throw new NotImplementedException();
    //}
    //
    //@Override
    //public XEntity visit(YPointerUnaryExpression node) {
    //    throw new NotImplementedException();
    //}
    //
    //@Override
    //public XEntity visit(YIntegerUnaryExpression node) {
    //    throw new NotImplementedException();
    //}

    @Override
    public XComputationEvent visit(YUnaryExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XComputationEvent visit(YBinaryExpression node) {
        XMemoryUnit leftUnit = (XMemoryUnit) visit(node.getLeftExpression());
        XLocalMemoryUnit left = program.copyToLocalMemoryIfNecessary(leftUnit);
        XMemoryUnit rightUnit = (XMemoryUnit) visit(node.getRightExpression());
        XLocalMemoryUnit right = program.copyToLocalMemoryIfNecessary(rightUnit);
        // TODO: transform operator!
        return program.currentProcess.emitComputationEvent(XOperator.CompareEquals, left, right);
    }

    @Override
    public XEntity visit(YTernaryExpression node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YAssignmentExpression node) {
        XLocalMemoryUnit assignee = (XLocalMemoryUnit) visit(node.getAssignee());
        XMemoryUnit expressionUnit = (XMemoryUnit) visit(node.getExpression());
        XLocalMemoryUnit expression = program.copyToLocalMemoryIfNecessary(expressionUnit);
        return program.currentProcess.emitMemoryEvent(assignee, expression);
    }

    @Override
    public XEntity visit(YLinearStatement node) {
        return visit(node.getExpression());
    }

    @Override
    public XEntity visit(YCompoundStatement node) {
        XEntity firstEvent = null;
        for (YStatement statement : node.getStatements()) {
            XEntity event = visit(statement);
            if (firstEvent == null) {
                firstEvent = event;
            }
        }
        return firstEvent;
    }

    @Override
    public XEntity visit(YVariableDeclarationStatement node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YType node) {
        throw new NotImplementedException();
    }

    @Override
    public XMemoryUnit visit(YVariableRef node) {
        // TODO: switch (node.getType() )
        return program.memoryManager.newLocalMemoryUnit(node.getName());
    }

    //@Override
    //public XEvent visit(YStatement node) {
    //    throw new NotImplementedException();
    //}
    //
    //@Override
    //public XEntity visit(YAssignee node) {
    //    throw new NotImplementedException();
    //}

    @Override
    public XEntity visit(YBranchingStatement node) {
        XMemoryUnit conditionShared = (XMemoryUnit) visit(node.getCondition());

        XComputationEvent conditionEvent;
        if (conditionShared instanceof XComputationEvent) {
            conditionEvent = (XComputationEvent) conditionShared;
        }
        else {
             XLocalMemoryUnit conditionLocal = program.copyToLocalMemoryIfNecessary(conditionShared);
             conditionEvent = program.currentProcess.emitComputationEvent(conditionLocal); //evaluate
        }

        XEvent thenEvent = (XEvent) visit(node.getThenBranch());
        XEvent elseEvent = (XEvent) visit(node.getElseBranch());
        program.currentProcess.emitConditionalJumpEvent(conditionEvent, thenEvent, elseEvent);

        return conditionEvent; // RETURNING ENTRY
    }

    @Override
    public XEntity visit(YLoopStatement node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YJumpStatement node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YMethodSignature node) {
        throw new NotImplementedException();
    }

    @Override
    public XEntity visit(YParameter node) {
        throw new NotImplementedException();
    }
}
