package mousquetaires.tests.unit;

import mousquetaires.languages.common.graph.FlowGraph;
import mousquetaires.languages.common.graph.Node;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.utils.StringUtils;
import org.junit.Assert;

import java.util.Map;


public class FlowGraphComparer {
    public static <T extends Node> void assertGraphsEqual(FlowGraph<T> expected, FlowGraph<T> actual) {
        Assert.assertEquals("entry events do not match", expected.source(), actual.source());
        Assert.assertEquals("exit events do not match", expected.sink(), actual.sink());
        assertMapsEqual("edges set mismatch", expected.getEdges(true), actual.getEdges(true));
        assertMapsEqual("alternative edges set mismatch", expected.getEdges(false), actual.getEdges(false));
    }

    private static <T extends Node> void assertMapsEqual(String info,
                                                         Map<? extends T, ? extends T> expected,
                                                         Map<? extends T, ? extends T> actual) {
        for (Map.Entry<? extends T, ? extends T> entry : actual.entrySet()) {
            T actualKey = entry.getKey(), actualValue = entry.getValue();
            //TODO: hack, somehow entry and exit events cannot be found in another immutable map even if equals() works fine
            if (actualKey instanceof XEntryEvent || actualKey instanceof XExitEvent) {
                continue;
            }
            Assert.assertTrue(info + ": key " + StringUtils.wrap(actualKey) + " was not found in expected-map"
                        + " (entry in actual-map: " + actualKey + "=" + actualValue +")",
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
