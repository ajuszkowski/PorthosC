package mousquetaires.tests.unit;

import mousquetaires.tests.AbstractTest;
import org.junit.Assert;

import java.util.Iterator;


public abstract class AbstractUnitTest<T> extends AbstractTest {

    protected void compareResults(Iterator<? extends T> expectedIterator,
                                  Iterator<? extends T> actualIterator) {
        //int counter = 0;
        while (actualIterator.hasNext() && expectedIterator.hasNext()) {
            T actual = actualIterator.next();
            T expected = expectedIterator.next();
            //System.out.println("Comparing " + counter++ + " pair ...");
            assertObjectsEqual(expected, actual);
        }
        Assert.assertFalse("actual result has extra elements", actualIterator.hasNext());
        Assert.assertFalse("actual result miss some elements", expectedIterator.hasNext());
    }


    protected abstract void assertObjectsEqual(T expected, T actual);

    protected String getErrorString(int counter) {
        return "mismatch in " + counter + "th statement:";
    }

}
