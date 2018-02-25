package mousquetaires.languages.syntax.xgraph.processes;

import mousquetaires.languages.syntax.xgraph.events.XEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.auxilaries.XExitEvent;

import java.util.HashSet;
import java.util.Set;


public class XProcessVerifier {

    public static boolean verify(XProcess process) {
        // assert events also
        for (XEvent event : process.events) {
            if (process.thenBranchingJumpsMap.containsKey(event)) {
                if (!process.elseBranchingJumpsMap.containsKey(event)) {
                    assert false: "then without else, src: " + event;
                }
                assert !process.nextEventMap.containsKey(event) : "too many srcs " + event + ": then and eps";
            }
            else if (process.elseBranchingJumpsMap.containsKey(event)) {
                if (!process.thenBranchingJumpsMap.containsKey(event)) {
                    assert false: "else without then, src: " + event + ": else and eps";
                }
            }
            else {
                if (!(event instanceof XExitEvent)) {
                    assert process.nextEventMap.containsKey(event) : "lack src " + event;
                }
            }
            if (!(event instanceof XEntryEvent)
                    && !process.nextEventMap.containsValue(event)
                    && !process.thenBranchingJumpsMap.containsValue(event)
                    && !process.elseBranchingJumpsMap.containsValue(event) ) {
                System.out.println("dead code: " + event);
            }
        }

        Set<XEvent> allEventsFromTransitions = new HashSet<>();
        allEventsFromTransitions.addAll(process.nextEventMap.keySet());
        allEventsFromTransitions.addAll(process.thenBranchingJumpsMap.keySet());
        allEventsFromTransitions.addAll(process.elseBranchingJumpsMap.keySet());
        allEventsFromTransitions.addAll(process.nextEventMap.values());
        allEventsFromTransitions.addAll(process.thenBranchingJumpsMap.values());
        allEventsFromTransitions.addAll(process.elseBranchingJumpsMap.values());
        Set<XEvent> allEventsRegistered = new HashSet<>(process.events);

        for (XEvent eventFromTransition : allEventsFromTransitions) {
            assert allEventsRegistered.contains(eventFromTransition) : "not registered event: " + eventFromTransition;
        }
        for (XEvent eventRegistered : allEventsRegistered) {
            assert allEventsFromTransitions.contains(eventRegistered) : "event without a transition: " + eventRegistered;
        }

        return true;
    }
}
