package mousquetaires.tests.unit.languages.parsers.cmin.statements;

import mousquetaires.languages.types.YXType;
import mousquetaires.languages.syntax.ytree.expressions.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.YConstantFactory;
import mousquetaires.languages.syntax.ytree.expressions.YVariableRef;
import mousquetaires.languages.types.YXTypeName;
import mousquetaires.languages.types.YXTypeFactory;
import mousquetaires.tests.unit.languages.parsers.cmin.CminParseTest;


public abstract class CminParseStatementTest extends CminParseTest {
    protected final String statementsDirectory = rootDirectory + "statements/";

    // shortcuts necessary for tests
    protected YVariableRef variableX = YVariableRef.create("x");
    protected YVariableRef variableY = YVariableRef.create("y");
    protected YVariableRef variableZ = YVariableRef.create("z");
    protected YVariableRef variableA = YVariableRef.create("a");
    protected YVariableRef variableB = YVariableRef.create("b");
    protected YVariableRef variableC = YVariableRef.create("c");

    protected YConstant constant1 = YConstantFactory.newIntConstant(1);
    protected YConstant constant2 = YConstantFactory.newIntConstant(2);
    protected YConstant constant3 = YConstantFactory.newIntConstant(3);

    protected YXType typeInt = YXTypeFactory.getPrimitiveType(YXTypeName.Int);
    protected YXType typeChar = YXTypeFactory.getPrimitiveType(YXTypeName.Char);
    protected YXType typeShort = YXTypeFactory.getPrimitiveType(YXTypeName.Short);
    protected YXType typeLong = YXTypeFactory.getPrimitiveType(YXTypeName.Long);
    protected YXType typeLongLong = YXTypeFactory.getPrimitiveType(YXTypeName.LongLong);
    protected YXType typeVoidPointer = YXTypeFactory.getPrimitiveType(YXTypeName.Void, 1);

}
