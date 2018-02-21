package mousquetaires.tests.unit.languages.converters.toxgraph.c11;

import mousquetaires.tests.unit.UnitTestPaths;
import mousquetaires.tests.unit.languages.converters.toxgraph.AbstractXgraphUnitTest;
import org.junit.Test;


public class C11ConvertBranchingStatementTest extends AbstractXgraphUnitTest {

    @Test
    public void test_branchingStatement() {
        run( UnitTestPaths.c11StatementsDirectory + "branchingStatement.c", null);
    }
}
