package mousquetaires.tests.unit;

import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
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
        //int counter = 0;
        while (actualResultIterator.hasNext() && expectedResultIterator.hasNext()) {
            TElement actual = actualResultIterator.next();
            TElement expected = expectedResultIterator.next();
            //System.out.println("Comparing " + counter++ + " pair ...");
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

    protected <T> void assertMapsEqual(String info,
                                 Map<? extends T, ? extends T> expected,
                                 Map<? extends T, ? extends T> actual) {
        for (Map.Entry<? extends T, ? extends T> entry : actual.entrySet()) {
            T actualKey = entry.getKey();
            //TODO: hack, somehow entry and exit events cannot be found in another immutable map even if equals() works fine
            if (actualKey instanceof XEntryEvent || actualKey instanceof XExitEvent) {
                continue;
            }
            Assert.assertTrue(info + ": key " + StringUtils.wrap(actualKey) + " was not found in expected-map",
                    expected.containsKey(actualKey));
            Assert.assertEquals(info + ": expected value mismatch for the key " + StringUtils.wrap(actualKey),
                    expected.get(actualKey),
                    entry.getValue());
        }
        for (T expectedKey : expected.keySet()) {
            //TODO: hack, somehow entry and exit events cannot be found in another immutable map even if equals() works fine
            if (expectedKey instanceof XEntryEvent || expectedKey instanceof XExitEvent) {
                continue;
            }
            Assert.assertTrue(info + ": actual-map does not contain the key " + StringUtils.wrap(expectedKey),
                    actual.containsKey(expectedKey));
        }
    }
}
