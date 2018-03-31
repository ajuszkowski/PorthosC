package mousquetaires.languages.syntax.zformula.visitors;

import mousquetaires.languages.syntax.zformula.*;


public abstract class ZformulaVisitorBase<T> implements ZformulaVisitor<T> {

    @Override
    public T visit(ZVariable formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZVariableReference formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZConstant formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZBoolConstant formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZBoolNegation formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZBoolOperation formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZBoolImplication formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZBoolDisjunction formula) {
        throw new ZformulaVisitorIllegalStateException();
    }

    @Override
    public T visit(ZBoolConjunction formula) {
        throw new ZformulaVisitorIllegalStateException();
    }
}
