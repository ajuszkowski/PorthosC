package mousquetaires.languages.syntax.xgraph.processes.interpretation;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;


class XProcessVerifier {

    public static boolean verify(XProcess process) {
        for (XComputationEvent event : process.thenBranchingJumpsMap.keySet()) {
            assert process.elseBranchingJumpsMap.containsKey(event) : "then without else: " + event;
        }
        for (XComputationEvent event : process.elseBranchingJumpsMap.keySet()) {
            assert process.thenBranchingJumpsMap.containsKey(event) : "else without then: " + event;
            assert !process.nextEventMap.containsKey(event) : "both conditional-jump and next-jump for " + event;
        }
        for (XEvent event : process.nextEventMap.keySet()) {
            if (event instanceof XComputationEvent) {
                XComputationEvent computEvent = (XComputationEvent) event;
                assert !process.thenBranchingJumpsMap.containsKey(computEvent) : "both next-jump and conditional-jump for condition: " + computEvent;
                assert !process.elseBranchingJumpsMap.containsKey(computEvent) : "both next-jump and conditional-jump for condition: " + computEvent;
            }
            else if (event instanceof XExitEvent) {
                assert !process.nextEventMap.containsValue(event) : "next-jump out of exit event";
                assert !process.thenBranchingJumpsMap.containsValue(event) : "then-jump out of exit event";
                assert !process.elseBranchingJumpsMap.containsValue(event) : "else-jump out of exit event";
            }
        }

        return true;
    }
}
