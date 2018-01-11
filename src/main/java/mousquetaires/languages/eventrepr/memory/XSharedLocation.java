package mousquetaires.languages.eventrepr.memory;

public class XSharedLocation extends XMemoryLocation {
    public XSharedLocation(String name) {
        super(name, Kind.SharedMemory);
    }
}
