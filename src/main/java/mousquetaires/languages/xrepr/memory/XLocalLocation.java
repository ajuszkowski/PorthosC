package mousquetaires.languages.xrepr.memory;

import mousquetaires.languages.common.types.YXType;


public class XLocalLocation extends XLocation {
    public XLocalLocation(String name, YXType type) {
        super(name, type, Kind.Local);
    }
}
