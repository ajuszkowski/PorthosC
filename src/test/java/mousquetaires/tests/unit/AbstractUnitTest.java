package mousquetaires.tests.unit;

import mousquetaires.tests.AbstractTest;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public abstract class AbstractUnitTest<TElement> extends AbstractTest {

    protected abstract Iterator<? extends TElement> parseTestFile(String testFile);

    public void run(String testFile, Iterator<? extends TElement> expectedResultIterator) {
        Iterator<? extends TElement> actualResultIterator = parseTestFile(testFile);
        int counter = 0;
        while (actualResultIterator.hasNext() && expectedResultIterator.hasNext()) {
            TElement actual = actualResultIterator.next();
            TElement expected = expectedResultIterator.next();
            assertEquals("mismatch in " + counter + "th statement:", expected, actual);
            counter++;
        }
        assertFalse("actual result has extra elements", actualResultIterator.hasNext());
        assertFalse("actual result miss some elements", expectedResultIterator.hasNext());
    }

    protected Iterator<? extends TElement> getIterator(TElement... values) {
        return Arrays.stream(values).collect(Collectors.toList()).iterator();
    }
}
