package mousquetaires.languages.syntax.zformula.visitors;

import mousquetaires.languages.syntax.zformula.*;


public abstract class ZformulaVisitorBase<T> implements ZformulaVisitor<T> {

    @Override
    public T visit(ZGlobalVariable formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZIndexedVariable formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZConstant formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZLogicalNegation formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZBinaryOperation formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZLogicalImplication formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZLogicalDisjunction formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZLogicalConjunction formula) {
        throw new ZformulaVisitorIllegalStateException();
    }
}
