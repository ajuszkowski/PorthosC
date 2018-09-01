package porthosc.app.modules.verdicts;

public interface IAppVerdictSerializer {

    void setPrettyPrinting(boolean prettyPrinting);

    String stringify(AppVerdict verdict);
}
