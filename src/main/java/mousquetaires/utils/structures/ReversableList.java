package mousquetaires.utils.structures;


import java.util.Collections;
import java.util.List;


public class ReversableList<T> extends ListWrapperBase<T> {

    public ReversableList(List<T> value) {
        super(value);
    }

    private boolean reversed = false;

    public boolean reverse() {
        if (!reversed) {
            Collections.reverse(value);
            reversed = true;
            return true;
        }
        return false;
    }

    public boolean isReversed() {
        return reversed;
    }
}
