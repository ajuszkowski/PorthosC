package mousquetaires.tests.unit.languages.converters.tozformula.c11.statements;

import mousquetaires.tests.unit.UnitTestPaths;
import mousquetaires.tests.unit.languages.converters.tozformula.AbstractZformulaUnitTest;
import org.junit.Test;


public class C11ToZformula_BranchingStatement_ConversionTest extends AbstractZformulaUnitTest {

    @Test
    public void test() {

        run( UnitTestPaths.c11StatementsDirectory + "branchingStatement.c",
                null);
    }
}
