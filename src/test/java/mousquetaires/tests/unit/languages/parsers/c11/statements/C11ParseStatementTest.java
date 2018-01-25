package mousquetaires.tests.unit.languages.parsers.c11.statements;

import mousquetaires.types.ZType;
import mousquetaires.languages.syntax.ytree.expressions.YConstant;
import mousquetaires.languages.syntax.ytree.expressions.YVariableRef;
import mousquetaires.types.ZTypeName;
import mousquetaires.types.ZTypeFactory;
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

    protected ZType typeInt = ZTypeFactory.getPrimitiveType(ZTypeName.Int);
    protected ZType typeChar = ZTypeFactory.getPrimitiveType(ZTypeName.Char);
    protected ZType typeShort = ZTypeFactory.getPrimitiveType(ZTypeName.Short);
    protected ZType typeLong = ZTypeFactory.getPrimitiveType(ZTypeName.Long);
    protected ZType typeLongLong = ZTypeFactory.getPrimitiveType(ZTypeName.LongLong);
    protected ZType typeVoidPointer = ZTypeFactory.getPrimitiveType(ZTypeName.Void, 1);

}
