package mousquetaires.languages.syntax.ytree.expressions;

import mousquetaires.languages.syntax.ytree.YEntity;


// TODO: add 'Origin origin()' method where Origin contains text citation, line number(s), etc...
public interface YExpression extends YEntity {

    // -1: address (ampersand &)
    // 0: value itself (no asterisks and ampersands)
    // 1,2,3...: pointer (number of asterisks *)
    int getPointerLevel();

    YExpression withPointerLevel(int level);
}
