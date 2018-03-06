package mousquetaires.languages.syntax.xgraph.processes.interpretation;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XComputationEvent;
import mousquetaires.languages.syntax.xgraph.processes.XProcess;


class XProcessVerifier {

    public static boolean verify(XProcess process) {
        for (XComputationEvent event : process.condTrueJumps.keySet()) {
            assert process.condFalseJumps.containsKey(event) : "then without else: " + event;
        }
        for (XComputationEvent event : process.condFalseJumps.keySet()) {
            assert process.condTrueJumps.containsKey(event) : "else without then: " + event;
            assert !process.epsilonJumps.containsKey(event) : "both conditional-jump and next-jump for " + event;
        }
        for (XEvent event : process.epsilonJumps.keySet()) {
            if (event instanceof XComputationEvent) {
                XComputationEvent computEvent = (XComputationEvent) event;
                assert !process.condTrueJumps.containsKey(computEvent) : "both next-jump and conditional-jump for condition: " + computEvent;
                assert !process.condFalseJumps.containsKey(computEvent) : "both next-jump and conditional-jump for condition: " + computEvent;
            }
            else if (event instanceof XExitEvent) {
                assert !process.epsilonJumps.containsValue(event) : "next-jump out of exit event";
                assert !process.condTrueJumps.containsValue(event) : "then-jump out of exit event";
                assert !process.condFalseJumps.containsValue(event) : "else-jump out of exit event";
            }
        }

        return true;
    }
}
