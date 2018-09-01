package porthosc.app.modules.verdicts;

import porthosc.app.options.AppOptions;


public class VerdictSerializerFactory {

    public static IAppVerdictSerializer getSerializer(OutputFormat outputFormat) {
        switch (outputFormat) {
            case Json:
                return new JsonVerdictSerializer();
            case Yaml:
                return new YamlVerdictSerializer();
            default:
                throw new IllegalArgumentException(outputFormat.toString());
        }
    }
}
