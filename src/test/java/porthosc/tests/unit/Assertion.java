package porthosc.tests.unit;

public abstract class Assertion {
    private String errorMessage;

    public Assertion(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public abstract boolean checkSuccess();

    protected void addErrorMessage(String message) {
        errorMessage += ": " + message;
    }

    public String getErrorMessage() {
        return errorMessage != null
                ? errorMessage
                : "";
    }
}
