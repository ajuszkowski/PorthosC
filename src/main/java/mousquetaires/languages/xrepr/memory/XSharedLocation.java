package mousquetaires.languages.xrepr.memory;

import mousquetaires.languages.common.types.YXType;


public class XSharedLocation extends XLocation {
    public XSharedLocation(String name, YXType type) {
        super(name, type, Kind.Shared);
    }
}
