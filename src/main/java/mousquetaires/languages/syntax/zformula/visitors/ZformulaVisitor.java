package mousquetaires.languages.syntax.zformula.visitors;

import mousquetaires.languages.syntax.zformula.*;


public interface ZformulaVisitor<T> {

    T visit(ZVariable formula);

    T visit(ZVariableReference formula);

    T visit(ZConstant formula);

    T visit(ZBoolConstant formula);

    T visit(ZBoolNegation formula);

    T visit(ZBoolOperation formula);

    T visit(ZBoolImplication formula);

    T visit(ZBoolDisjunction formula);

    T visit(ZBoolConjunction formula);
}
