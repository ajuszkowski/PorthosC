package mousquetaires.utils;

public class Triplet<T1, T2, T3> extends Pair<T1, T2> {

    private final T3 third;

    public Triplet(T1 first, T2 second, T3 third) {
        super(first, second);
        this.third = third;
    }

    public T3 third() {
        return third;
    }

    @Override
    public String toString() {
        return "(" + first() + ", " + second() + ", " + third() + ")";
    }
}