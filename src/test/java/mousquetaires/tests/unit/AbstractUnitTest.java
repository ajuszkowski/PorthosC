package mousquetaires.tests.unit;

import mousquetaires.tests.AbstractTest;
import org.junit.Assert;

import java.util.Iterator;


public abstract class AbstractUnitTest<T> extends AbstractTest {

    protected void assertIteratorsEqual(Iterator<? extends T> expectedIterator,
                                             Iterator<? extends T> actualIterator) {
        Assertion assertion = compareMultipleResults(expectedIterator, actualIterator);
        Assert.assertTrue(assertion.getErrorMessage(), assertion.checkSuccess());
    }

    protected Assertion compareMultipleResults(Iterator<? extends T> expectedIterator,
                                               Iterator<? extends T> actualIterator) {
        int counter = 0;
        while (actualIterator.hasNext() && expectedIterator.hasNext()) {
            T actual = actualIterator.next();
            T expected = expectedIterator.next();
            Assertion assertion = compareResults(expected, actual);
            if (!assertion.checkSuccess()) {
                if (dumpExpected(expected)) {
                    System.out.println("expected-object is dumped");
                }
                if (dumpActual(expected)) {
                    System.out.println("actual-object is dumped");
                }
                System.out.println(getErrorString(counter));
                return assertion;
            }
            counter++;
        }
        AssertionBoolean actualHasElements = new AssertionBoolean("actual result has extra elements", actualIterator.hasNext());
        if (!actualHasElements.checkSuccess()) {
            return actualHasElements;
        }
        AssertionBoolean expectedHasElements = new AssertionBoolean("actual result miss some elements", expectedIterator.hasNext());
        if (!expectedHasElements.checkSuccess()) {
            return expectedHasElements;
        }
        return AssertionSuccess.instance();
    }

    protected abstract Assertion compareResults(T expected, T actual);

    protected boolean dumpExpected(T result) {
        return false;
    }

    protected boolean dumpActual(T result) {
        return false;
    }


    private String getErrorString(int counter) {
        return "mismatch in " + counter + "th statement";
    }
}
