package porthosc.app.modules.verdicts;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;


public class YamlVerdictSerializer implements IAppVerdictSerializer {

    private boolean prettyPrinting = true;

    public void setPrettyPrinting(boolean prettyPrinting) {
        this.prettyPrinting = prettyPrinting;
    }

    @Override
    public String stringify(AppVerdict verdict) {
        DumperOptions options = new DumperOptions();
        if (prettyPrinting) {
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            options.setPrettyFlow(true);
        }

        Yaml yaml = new Yaml(options);
        yaml.setBeanAccess(BeanAccess.FIELD);
        return yaml.dump(verdict);
    }
}
