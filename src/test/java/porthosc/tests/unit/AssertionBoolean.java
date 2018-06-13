package porthosc.tests.unit;

public class AssertionBoolean extends Assertion {

    private final boolean argument;

    public AssertionBoolean(String errorMessage, boolean argument) {
        super(errorMessage);
        this.argument = argument;
    }

    @Override
    public boolean checkSuccess() {
        return argument;
    }
}
