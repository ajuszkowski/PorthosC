package mousquetaires.tests.unit.languages.converters.toytree.c11.statements;

import mousquetaires.languages.syntax.ytree.expressions.atomics.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariable;
import mousquetaires.languages.syntax.ytree.types.YMockType;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.tests.unit.languages.converters.toytree.C2Ytree_UnitTestBase;


public abstract class C2Ytree_Statement_UnitTest extends C2Ytree_UnitTestBase {

    // shortcuts necessary for tests
    protected YVariable variableX = new YVariable("x");
    protected YVariable variableY = new YVariable("y");
    protected YVariable variableZ = new YVariable("z");
    protected YVariable variableA = new YVariable("a");
    protected YVariable variableB = new YVariable("b");
    protected YVariable variableC = new YVariable("c");

    protected YConstant constant1 = YConstant.fromValue(1);
    protected YConstant constant2 = YConstant.fromValue(2);
    protected YConstant constant3 = YConstant.fromValue(3);
    protected YConstant constant4 = YConstant.fromValue(4);

    protected YType typeInt = new YMockType();  //YPrimitiveTypeFactory.getPrimitiveType(YTypeName.Int);
    protected YType typeChar = new YMockType();  //YPrimitiveTypeFactory.getPrimitiveType(YTypeName.Char);
    protected YType typeShort = new YMockType();  //YPrimitiveTypeFactory.getPrimitiveType(YTypeName.Short);
    protected YType typeLong = new YMockType();  //YPrimitiveTypeFactory.getPrimitiveType(YTypeName.Long);
    protected YType typeLongLong = new YMockType();  //YPrimitiveTypeFactory.getPrimitiveType(YTypeName.LongLong);
    protected YType typeVoidPointer = new YMockType();  //YPrimitiveTypeFactory.getPrimitiveType(YTypeName.Void, 1);

}
