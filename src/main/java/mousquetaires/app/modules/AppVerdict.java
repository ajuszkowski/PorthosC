package mousquetaires.app.modules;

import com.google.common.collect.ImmutableMap;
import mousquetaires.app.errors.AppError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public abstract class AppVerdict {

    public enum ProgramStage {
        Interpretation,
        Unrolling,
        ProgramEncoding,
        ProgramDomainEncoding,
        MemoryModelEncoding,
        Solving,
        ;

        private String separatedByCapitals() {
            String name = this.toString();
            StringBuilder res = new StringBuilder();
            res.append(name.charAt(0));
            for(int i = 1; i < name.length(); i++) {
                Character ch = name.charAt(i);
                if(Character.isUpperCase(ch))
                    res.append(" ").append(Character.toLowerCase(ch));
                else
                    res.append(ch);
            }
            return res.toString();
        }
    }

    private final Timer mainTimer;
    private final ImmutableMap<ProgramStage, Timer> timers;

    private final List<AppError> errors;

    public AppVerdict() {
        this.mainTimer = new Timer();
        HashMap<ProgramStage, Timer> timersValues = new HashMap<>();
        for (ProgramStage programStage : ProgramStage.values()) {
            timersValues.put(programStage, new Timer());
        }
        this.timers = ImmutableMap.copyOf(timersValues);
        this.errors = new ArrayList<>();
    }

    public void startAll() {
        mainTimer.start();
    }

    public void onStart(ProgramStage stage) {
        String currentTime = String.format("%.3f: ", ((System.currentTimeMillis() - mainTimer.getStartTime()) / 1000));
        System.out.println( currentTime + stage.separatedByCapitals() + "...");
        timers.get(stage).start();
    }

    public void onFinish(ProgramStage stage) {
        timers.get(stage).finish();
    }

    public void finishAll() {
        mainTimer.finish();
    }


    public void addError(AppError error) {
        errors.add(error);
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public List<AppError> getErrors() {
        return errors;
    }

    // TODO: store original SMT formula here + num of iterations

}
