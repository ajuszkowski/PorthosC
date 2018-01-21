package mousquetaires.tests.unit.languages.converters.cmin2xrepr;

import org.junit.Test;


public class CminConvertBranchingStatementTest extends CminConverterTest {
    protected final String statementsDirectory = rootDirectory + "statements/";

    @Test
    public void test_branchingStatement() {
        runParserTest( statementsDirectory + "branchingStatement.c", null);
    }
}
