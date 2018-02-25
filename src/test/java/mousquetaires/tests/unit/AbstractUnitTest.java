package mousquetaires.tests.unit;

import mousquetaires.tests.AbstractTest;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;


public abstract class AbstractUnitTest<TElement> extends AbstractTest {

    protected abstract Iterator<? extends TElement> parseTestFile(String testFile);

    public void run(String testFile,
                    Iterator<? extends TElement> expectedResultIterator) {
        Iterator<? extends TElement> actualResultIterator = parseTestFile(testFile);
        int counter = 0;
        while (actualResultIterator.hasNext() && expectedResultIterator.hasNext()) {
            TElement actual = actualResultIterator.next();
            TElement expected = expectedResultIterator.next();
            Assert.assertEquals("mismatch in " + counter + "th statement:", expected, actual);
            counter++;
        }
        Assert.assertFalse("actual result has extra elements", actualResultIterator.hasNext());
        Assert.assertFalse("actual result miss some elements", expectedResultIterator.hasNext());
    }

    protected Iterator<? extends TElement> getIterator(TElement... values) {
        return Arrays.stream(values).collect(Collectors.toList()).iterator();
    }
}
