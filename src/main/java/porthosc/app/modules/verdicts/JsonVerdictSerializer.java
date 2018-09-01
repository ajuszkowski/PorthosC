package porthosc.app.modules.verdicts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonVerdictSerializer implements IAppVerdictSerializer {
    private final Gson gson;

    private boolean prettyPrinting = true;

    public void setPrettyPrinting(boolean prettyPrinting) {
        this.prettyPrinting = prettyPrinting;
    }

    public JsonVerdictSerializer() {
        GsonBuilder builder = new GsonBuilder();
        if (prettyPrinting) {
            builder.setPrettyPrinting();
        }

        gson = builder.create();
    }

    @Override
    public String stringify(AppVerdict verdict) {
        return gson.toJson(verdict);
    }
}
