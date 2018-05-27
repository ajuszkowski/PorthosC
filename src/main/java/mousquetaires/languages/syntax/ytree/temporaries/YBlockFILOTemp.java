package mousquetaires.languages.syntax.ytree.temporaries;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YStatement;
import mousquetaires.utils.patterns.Builder;


public class YBlockFILOTemp
        extends YQueueFILOTemp<YStatement>
        implements Builder<YCompoundStatement>, YTempEntity {

    private final Origin blockLocation;
    private boolean hasBraces;

    public YBlockFILOTemp(Origin blockLocation) {
        this.blockLocation = blockLocation;
    }

    @Override
    public YCompoundStatement build() {
        return new YCompoundStatement(blockLocation, hasBraces, buildValues());
    }

    public void markHasBraces() {
        hasBraces = true;
    }
}
