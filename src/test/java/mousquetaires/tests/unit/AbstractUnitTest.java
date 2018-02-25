package mousquetaires.tests.unit;

import mousquetaires.tests.AbstractTest;
import mousquetaires.utils.CollectionUtils;
import mousquetaires.utils.StringUtils;
import org.junit.Assert;

import java.util.Iterator;
import java.util.Map;


public abstract class AbstractUnitTest<TElement> extends AbstractTest {

    protected abstract Iterator<? extends TElement> parseTestFile(String testFile);

    public void run(String testFile,
                    Iterator<? extends TElement> expectedResultIterator) {
        Iterator<? extends TElement> actualResultIterator = parseTestFile(testFile);
        int counter = 0;
        while (actualResultIterator.hasNext() && expectedResultIterator.hasNext()) {
            TElement actual = actualResultIterator.next();
            TElement expected = expectedResultIterator.next();
            // todo: compare processes in a more clever way
            System.out.println("Comparing " + counter++ + " objects...");
            assertObjectsEqual(expected, actual);
        }
        Assert.assertFalse("actual result has extra elements", actualResultIterator.hasNext());
        Assert.assertFalse("actual result miss some elements", expectedResultIterator.hasNext());
    }

    protected Iterator<? extends TElement> getIterator(TElement... values) {
        return CollectionUtils.createIteratorFrom(values);
    }

    protected abstract void assertObjectsEqual(TElement expected, TElement actual);

    protected String getErrorString(int counter) {
        return "mismatch in " + counter + "th statement:";
    }

    protected void assertMapsEqual(String info,
                                 Map<?, ?> expected,
                                 Map<?, ?> actual) {
        for (Map.Entry<?, ?> entry : actual.entrySet()) {
            Object actualKey = entry.getKey();
            Assert.assertTrue(info + ": element " + StringUtils.wrap(actualKey) + " was not found in expected-map",
                    expected.containsKey(actualKey));
            Assert.assertEquals(info + ": expected value ",
                    expected.get(actualKey),
                    entry.getValue());
        }
        for (Object expectedKey : expected.keySet()) {
            Assert.assertTrue(info + ": actual-map does not contain the key " + StringUtils.wrap(expectedKey),
                    actual.containsKey(expectedKey));
        }
    }
}
