package mousquetaires.tests.unit.languages.converters.toytree.c11.statements;

import mousquetaires.languages.syntax.ytree.expressions.atomics.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YVariableRef;
import mousquetaires.languages.syntax.ytree.types.YMockType;
import mousquetaires.languages.syntax.ytree.types.YType;
import mousquetaires.tests.unit.languages.converters.toytree.C11ToYtree_UnitTestBase;


public abstract class C11ToYtree_Statement_UnitTest extends C11ToYtree_UnitTestBase {

    // shortcuts necessary for tests
    protected YVariableRef variableX = YVariableRef.Kind.Local.createVariable("x");
    protected YVariableRef variableY = YVariableRef.Kind.Local.createVariable("y");
    protected YVariableRef variableZ = YVariableRef.Kind.Local.createVariable("z");
    protected YVariableRef variableA = YVariableRef.Kind.Local.createVariable("a");
    protected YVariableRef variableB = YVariableRef.Kind.Local.createVariable("b");
    protected YVariableRef variableC = YVariableRef.Kind.Local.createVariable("c");

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
