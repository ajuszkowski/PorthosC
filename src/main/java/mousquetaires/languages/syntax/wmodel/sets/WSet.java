package mousquetaires.languages.syntax.wmodel.sets;

import com.google.common.collect.ImmutableSet;
import mousquetaires.languages.syntax.wmodel.WElement;
import mousquetaires.languages.syntax.xgraph.events.XEvent;


public interface WSet<T extends XEvent> extends WElement {

    ImmutableSet<T> getValues();
}
