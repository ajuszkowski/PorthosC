package mousquetaires.languages.syntax.zformula.visitors;

import mousquetaires.languages.syntax.zformula.*;


public interface ZformulaVisitor<T> {

    T visit(ZGlobalVariable formula);

    T visit(ZIndexedVariable formula);

    T visit(ZConstant formula);

    T visit(ZLogicalNegation formula);

    T visit(ZUnaryOperation formula);

    T visit(ZBinaryOperation formula);

    T visit(ZLogicalImplication formula);

    T visit(ZLogicalDisjunction formula);

    T visit(ZLogicalConjunction formula);
}
