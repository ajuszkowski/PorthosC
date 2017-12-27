package mousquetaires.app.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonVerdictStringifier implements IAppVerdictStringifier {
    private final Gson gson = new GsonBuilder().create();

    @Override
    public String stringify(AppVerdict verdict) {
        return gson.toJson(verdict);
    }
}
