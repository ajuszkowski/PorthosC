package mousquetaires.tests.unit.languages.converters.toxrepr;

import org.junit.Test;


public class C11ConvertAssertionStatementTest extends C11ConverterTest {

    @Test
    public void test_assertionStatement() {
        //YSyntaxTree expected = new YSyntaxTree(
        //        new YAssertionStatement(
        //
        //        )
        //);
        runParserTest( statementsDirectory + "assertionStatement.c", null);
    }
}
