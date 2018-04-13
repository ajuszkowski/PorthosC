package mousquetaires.tests.unit.languages.converters.toytree.c11.statements;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.expressions.binary.YRelativeBinaryExpression;
import mousquetaires.languages.syntax.ytree.statements.YBranchingStatement;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import mousquetaires.languages.syntax.ytree.types.YMockType;
import mousquetaires.languages.syntax.ytree.types.signatures.YMethodSignature;
import mousquetaires.languages.syntax.ytree.types.signatures.YParameter;
import mousquetaires.tests.unit.UnitTestPaths;
import org.junit.Test;

import java.util.Iterator;


public class C2Ytree_BranchingStatement_UnitTest extends C2Ytree_Statement_UnitTest {

    @Test
    public void test() {
        Iterator<? extends YEntity> expected = getIterator(new YFunctionDefinition(
                new YMethodSignature("?", new YMockType(), new YParameter[0]),// TODO: replace this mock signature with real
                new YCompoundStatement(true,
                        new YBranchingStatement(
                            YRelativeBinaryExpression.Kind.Equals.createExpression(variableX, constant1),
                            new YCompoundStatement(true,
                                    new YLinearStatement(new YAssignmentExpression(variableY, constant2)),
                                    new YLinearStatement(new YAssignmentExpression(variableX, variableY))
                            ),
                            new YBranchingStatement(
                                    YRelativeBinaryExpression.Kind.Greater.createExpression(variableX, constant2),
                                    new YCompoundStatement(true, new YLinearStatement(new YAssignmentExpression(variableY, constant3))),
                                    new YCompoundStatement(true)
                            )
                        ),
                        new YLinearStatement(new YAssignmentExpression(variableX, constant4))
                    )
                )
        );
        run(UnitTestPaths.c11StatementsDirectory + "branchingStatement.c", expected);
    }
}
