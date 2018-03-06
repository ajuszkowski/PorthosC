package mousquetaires.utils.patterns;

public interface Encoder<TSrc, TDst> {
    TDst encode(TSrc entity);
}
