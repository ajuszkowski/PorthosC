package porthosc.tests.unit.languages.converters.toytree.c11.statements;

import porthosc.languages.common.citation.Origin;
import porthosc.languages.syntax.ytree.expressions.atomics.YConstant;
import porthosc.languages.syntax.ytree.expressions.atomics.YVariableRef;
import porthosc.languages.syntax.ytree.types.YMockType;
import porthosc.languages.syntax.ytree.types.YType;
import porthosc.tests.unit.languages.converters.toytree.C2Ytree_UnitTestBase;


public abstract class C2Ytree_Statement_UnitTest extends C2Ytree_UnitTestBase {
    protected final Origin origin = Origin.empty;

    // shortcuts necessary for tests
    protected YVariableRef variableX = createVariable("x");
    protected YVariableRef variableY = createVariable("y");
    protected YVariableRef variableZ = createVariable("z");
    protected YVariableRef variableA = createVariable("a");
    protected YVariableRef variableB = createVariable("b");
    protected YVariableRef variableC = createVariable("c");

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

    private YVariableRef createVariable(String name) {
        return new YVariableRef(origin, name);
    }
}
