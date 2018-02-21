package mousquetaires.tests.unit.languages.converters.toxgraph.c11;

import mousquetaires.tests.unit.UnitTestPaths;
import mousquetaires.tests.unit.languages.converters.toxgraph.AbstractXgraphUnitTest;
import org.junit.Test;


public class C11ConvertAssertionStatementTest extends AbstractXgraphUnitTest {

    @Test
    public void test_assertionStatement() {
        //YSyntaxTree expected = new YSyntaxTree(
        //        new YAssertionStatement(
        //
        //        )
        //);
        run( UnitTestPaths.c11StatementsDirectory + "assertionStatement.c", null);
    }
}
