package porthosc.tests.unit;

import porthosc.utils.StringUtils;

import java.util.Objects;


public class AssertionObjectsEqual extends Assertion {
    private final Object expected;
    private final Object actual;

    public AssertionObjectsEqual(Object expected, Object actual) {
        this("objects are supposed to be equal", expected, actual);
    }

    public AssertionObjectsEqual(String errorMessage, Object expected, Object actual) {
        super(errorMessage);
        this.expected = expected;
        this.actual = actual;
    }

    public Object getExpected() {
        return expected;
    }

    public Object getActual() {
        return actual;
    }

    @Override
    public String getErrorMessage() {
        return super.getErrorMessage() + ":\n" +
                "Expected=" + StringUtils.wrap(getExpected()) + "\n" +
                "Actual=" + StringUtils.wrap(actual);
    }

    @Override
    public boolean checkSuccess() {
        return Objects.equals(expected, actual);
    }
}
