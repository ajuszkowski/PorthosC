package mousquetaires.tests.unit.languages.parsers.c11.statements;

import mousquetaires.languages.syntax.ytree.expressions.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.YVariableRef;
import mousquetaires.languages.syntax.ytree.types.YMockType;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.tests.unit.languages.parsers.c11.C11ParseTest;


public abstract class C11ParseStatementTest extends C11ParseTest {
    protected final String statementsDirectory = rootDirectory + "statements/";

    // shortcuts necessary for tests
    protected YVariableRef variableX = YVariableRef.create("x");
    protected YVariableRef variableY = YVariableRef.create("y");
    protected YVariableRef variableZ = YVariableRef.create("z");
    protected YVariableRef variableA = YVariableRef.create("a");
    protected YVariableRef variableB = YVariableRef.create("b");
    protected YVariableRef variableC = YVariableRef.create("c");

    protected YConstant constant1 = YConstant.fromValue(1);
    protected YConstant constant2 = YConstant.fromValue(2);
    protected YConstant constant3 = YConstant.fromValue(3);

    protected YType typeInt = new YMockType();  //YPrimitiveTypeFactory.getPrimitiveType(YTypeName.Int);
    protected YType typeChar = new YMockType();  //YPrimitiveTypeFactory.getPrimitiveType(YTypeName.Char);
    protected YType typeShort = new YMockType();  //YPrimitiveTypeFactory.getPrimitiveType(YTypeName.Short);
    protected YType typeLong = new YMockType();  //YPrimitiveTypeFactory.getPrimitiveType(YTypeName.Long);
    protected YType typeLongLong = new YMockType();  //YPrimitiveTypeFactory.getPrimitiveType(YTypeName.LongLong);
    protected YType typeVoidPointer = new YMockType();  //YPrimitiveTypeFactory.getPrimitiveType(YTypeName.Void, 1);

}
