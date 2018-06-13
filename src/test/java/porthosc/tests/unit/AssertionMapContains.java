package porthosc.tests.unit;

import java.util.Map;


public class AssertionMapContains<K, V> extends Assertion {
    private final Map<? extends K, ? extends V> map;
    private final K expectedElement;
    private final V expectedValue;

    public AssertionMapContains(String errorMessage, Map<? extends K, ? extends V> map, K expectedElement, V expectedValue) {
        super(errorMessage);
        this.map = map;
        this.expectedElement = expectedElement;
        this.expectedValue = expectedValue;
    }

    public boolean checkSuccess() {
        return map.containsKey(expectedElement) &&
                map.get(expectedElement).equals(expectedValue);
    }
}
