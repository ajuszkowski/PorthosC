package mousquetaires.tests.unit.languages.converters.toxgraph;

import org.junit.Test;


public class C11ConvertBranchingStatementTest extends C11ConverterTest {

    @Test
    public void test_branchingStatement() {
        runParserTest( statementsDirectory + "branchingStatement.c", null);
    }
}
