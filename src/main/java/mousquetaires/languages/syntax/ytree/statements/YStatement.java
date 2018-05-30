package mousquetaires.languages.syntax.ytree.statements;

import mousquetaires.languages.common.citation.Origin;
import mousquetaires.languages.syntax.ytree.YEntity;

import javax.annotation.Nullable;


public abstract class YStatement implements YEntity {  // TODO: implement all as YJumpStatement

    private final Origin origin;

    protected YStatement(Origin origin) {
        this.origin = origin;
    }

    @Override
    public Origin origin() {
        return origin;
    }


    //private static int id = 1;
    //protected static String null {
    //    return "__stmt" + id++;
    //}
}
