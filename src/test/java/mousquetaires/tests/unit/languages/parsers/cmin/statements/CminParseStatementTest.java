package mousquetaires.tests.unit.languages.parsers.cmin.statements;

import mousquetaires.languages.ytree.expressions.YConstant;
import mousquetaires.languages.ytree.expressions.YConstantFactory;
import mousquetaires.languages.ytree.expressions.YVariableRef;
import mousquetaires.languages.ytree.types.YPrimitiveTypeName;
import mousquetaires.languages.ytree.types.YType;
import mousquetaires.languages.ytree.types.YTypeFactory;
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

    protected YType typeInt = YTypeFactory.getPrimitiveType(YPrimitiveTypeName.Int);
    protected YType typeChar = YTypeFactory.getPrimitiveType(YPrimitiveTypeName.Char);
    protected YType typeShort = YTypeFactory.getPrimitiveType(YPrimitiveTypeName.Short);
    protected YType typeLong = YTypeFactory.getPrimitiveType(YPrimitiveTypeName.Long);
    protected YType typeLongLong = YTypeFactory.getPrimitiveType(YPrimitiveTypeName.LongLong);
    protected YType typeVoidPointer = YTypeFactory.getPrimitiveType(YPrimitiveTypeName.Void, 1);

}
