package porthosc.tests.unit;

public final class AssertionSuccess extends Assertion {
    private static AssertionSuccess instance;
    public static AssertionSuccess instance() {
        return instance == null
                ? instance = new AssertionSuccess()
                : instance;
    }

    public AssertionSuccess() {
        super("assertion success");
    }

    @Override
    public boolean checkSuccess() {
        return true;
    }
}
