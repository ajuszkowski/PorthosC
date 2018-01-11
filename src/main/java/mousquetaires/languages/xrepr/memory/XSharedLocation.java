package mousquetaires.languages.xrepr.memory;

public class XSharedLocation extends XMemoryLocation {
    public XSharedLocation(String name) {
        super(name, Kind.SharedMemory);
    }
}
