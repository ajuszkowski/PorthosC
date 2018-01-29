package mousquetaires.languages.syntax.ytree.types;

import mousquetaires.languages.syntax.ytree.YEntity;


public interface YType extends YEntity {

    public interface Qualifier {
    }

    public interface Specifier {
    }

    YType.Qualifier getQualifier();
    YType.Specifier getSpecifier();

    int getPointerLevel();

    YType withPointerLevel(int newPointerLevel);

}
