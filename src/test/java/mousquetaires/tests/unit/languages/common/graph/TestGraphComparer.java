//package mousquetaires.tests.unit.languages.common.graph;
//
//import mousquetaires.languages.common.graph.FlowGraph;
//import mousquetaires.languages.common.graph.Node;
//import mousquetaires.tests.unit.Assertion;
//import mousquetaires.tests.unit.AssertionMapContains;
//import mousquetaires.tests.unit.AssertionObjectsEqual;
//import mousquetaires.tests.unit.AssertionSuccess;
//
//import java.util.Map;
//
//
//public class TestGraphComparer {
//    public static <T extends Node> Assertion compareFlowGraphs(FlowGraph<T> expected, FlowGraph<T> actual) {
//        Assertion sourcesAssert = new AssertionObjectsEqual("entry events do not match", expected.source(), actual.source());
//        if (!sourcesAssert.checkSuccess()) {
//            return sourcesAssert;
//        }
//        Assertion sinksAssert = new AssertionObjectsEqual("exit events do not match", expected.sink(), actual.sink());
//        if (!sinksAssert.checkSuccess()) {
//            return sinksAssert;
//        }
//        Assertion thenComparison = compareMaps("comparing then-edges", expected.getEdges(true), actual.getEdges(true));
//        if (!thenComparison.checkSuccess()) {
//            return thenComparison;
//        }
//        Assertion elseComparison = compareMaps("comparing else-edges", expected.getEdges(false), actual.getEdges(false));
//        if (!elseComparison.checkSuccess()) {
//            return elseComparison;
//        }
//        return AssertionSuccess.instance();
//    }
//
//    private static <T extends Node> Assertion compareMaps(String info,
//                                                          Map<? extends T, ? extends T> expected,
//                                                          Map<? extends T, ? extends T> actualMap) {
//        Assertion sizeAssert = new AssertionObjectsEqual(info + ": maps sizes mismatch", expected.size(), actualMap.size());
//        if (!sizeAssert.checkSuccess()) {
//            return sizeAssert;
//        }
//
//        for (Map.Entry<? extends T, ? extends T> entry : expected.entrySet()) {
//            T expectedKey = entry.getKey(), expectedValue = entry.getValue();
//            Assertion containsAssert = new AssertionMapContains<>(info + ": actual map does not contain expected key-value pair",
//                    actualMap, expectedKey, expectedValue);
//            if (!containsAssert.checkSuccess()) {
//                return containsAssert;
//            }
//        }
//        return AssertionSuccess.instance();
//    }
//}
