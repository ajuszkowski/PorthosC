package mousquetaires.tests.unit;

import mousquetaires.tests.AbstractTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


public abstract class AbstractUnitTest<TElement> extends AbstractTest {

    protected abstract List<TElement> parseTestFile(String testFile);

    public void run(String testFile, List<TElement> expectedResult) {
        List<TElement> actualResult = parseTestFile(testFile);
        assertEquals("roots number does not match", expectedResult.size(), actualResult.size());
        for (int i = 0; i < expectedResult.size(); ++i) {
            assertEquals("mismatch in " + i + "th statement:", expectedResult.get(i), actualResult.get(i));
        }
    }

    protected List<TElement> buildResultList(TElement... values) {
        return Arrays.stream(values).collect(Collectors.toList());
    }

}
