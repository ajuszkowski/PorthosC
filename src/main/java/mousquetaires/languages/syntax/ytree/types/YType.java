package mousquetaires.languages.syntax.ytree.types;

import mousquetaires.languages.syntax.ytree.YEntity;


public interface YType extends YEntity {

    interface Qualifier {
    }

    interface Specifier {
    }

    YType.Qualifier getQualifier();

    YType.Specifier getSpecifier();
}
