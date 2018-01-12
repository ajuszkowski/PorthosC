package mousquetaires.languages.visitors;

import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.ytree.expressions.*;
import mousquetaires.languages.ytree.expressions.invocation.YFunctionArgument;
import mousquetaires.languages.ytree.expressions.invocation.YFunctionInvocationExpression;
import mousquetaires.languages.ytree.signatures.YFunctionParameter;
import mousquetaires.languages.ytree.statements.*;
import mousquetaires.languages.ytree.statements.artificial.YBugonStatement;
import mousquetaires.languages.ytree.statements.artificial.YProcess;
import mousquetaires.languages.ytree.types.YPrimitiveTypeName;
import mousquetaires.languages.ytree.types.YPrimitiveTypeSpecifier;
import mousquetaires.languages.ytree.types.YType;

import java.util.Iterator;


public class YtreeBaseVisitor implements YtreeVisitor {

    public void visitChildren(YEntity node) {
        Iterator<YEntity> iterator = node.getChildrenIterator();
        while (iterator.hasNext()) {
            YEntity child = iterator.next();
            child.accept(this);
        }
    }

    @Override
    public void visit(YSyntaxTree node) {
        visitChildren(node);
    }

    @Override
    public void visit(YProcess node) {
        visitChildren(node);
    }

    @Override
    public void visit(YBugonStatement node) {
        visitChildren(node);
    }

    @Override
    public void visit(YConstant node) {
        visitChildren(node);
    }

    @Override
    public void visit(YMemberAccess node) {
        visitChildren(node);
    }

    @Override
    public void visit(YFunctionArgument node) {
        visitChildren(node);
    }

    @Override
    public void visit(YFunctionInvocationExpression node) {
        visitChildren(node);
    }

    @Override
    public void visit(YFunctionParameter node) {
        visitChildren(node);
    }

    @Override
    public void visit(YFunctionDefinitionStatement node) {
        visitChildren(node);
    }

    @Override
    public void visit(YUnaryExpression node) {
        visitChildren(node);
    }

    @Override
    public void visit(YUnaryExpression.Operator node) {
        visitChildren(node);
    }

    @Override
    public void visit(YBinaryExpression node) {
        visitChildren(node);
    }

    @Override
    public void visit(YBinaryExpression.Operator node) {
        visitChildren(node);
    }

    @Override
    public void visit(YTernaryExpression node) {
        visitChildren(node);
    }

    @Override
    public void visit(YAssignmentExpression node) {
        visitChildren(node);
    }

    @Override
    public void visit(YAssignmentExpression.Operator node) {
        visitChildren(node);
    }

    @Override
    public void visit(YLinearStatement node) {
        visitChildren(node);
    }

    @Override
    public void visit(YVariableDeclarationStatement node) {
        visitChildren(node);
    }

    @Override
    public void visit(YType node) {
        visitChildren(node);
    }

    @Override
    public void visit(YPrimitiveTypeName node) {
        visitChildren(node);
    }

    @Override
    public void visit(YPrimitiveTypeSpecifier node) {
        visitChildren(node);
    }

    @Override
    public void visit(YVariableRef node) {
        visitChildren(node);
    }

    @Override
    public void visit(YVariableRef.Kind node) {
        visitChildren(node);
    }

    @Override
    public void visit(YBlockStatement node) {
        visitChildren(node);
    }

    @Override
    public void visit(YBranchingStatement node) {
        visitChildren(node);
    }

    @Override
    public void visit(YLoopStatement node) {
        visitChildren(node);
    }

    @Override
    public void visit(YJumpStatement node) {
        visitChildren(node);
    }
}
