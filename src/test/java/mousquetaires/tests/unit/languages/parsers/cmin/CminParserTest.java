package mousquetaires.tests.unit.languages.parsers.cmin;

import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.ytree.expressions.YConstant;
import mousquetaires.languages.ytree.expressions.YConstantFactory;
import mousquetaires.languages.ytree.expressions.YVariableRef;
import mousquetaires.languages.ytree.types.YPrimitiveTypeName;
import mousquetaires.languages.ytree.types.YPrimitiveTypeSpecifier;
import mousquetaires.languages.ytree.types.YType;
import mousquetaires.languages.ytree.types.YTypeFactory;
import mousquetaires.tests.unit.languages.parsers.AbstractParserUnitTest;

import java.util.List;

import static org.junit.Assert.assertEquals;


public abstract class CminParserTest extends AbstractParserUnitTest {

    protected final String rootDirectory = parsersDirectory + "cmin/";
    protected final String structuresDirectory = rootDirectory + "structures/";

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


    protected void runParserTest(String file, YSyntaxTree expectedTree) {
        YSyntaxTree actualTree = runTest(file);
        List<YEntity> actualRoots = actualTree.getRoots();
        List<YEntity> expectedRoots = expectedTree.getRoots();
        assertEquals("roots number does not match", expectedRoots.size(), actualRoots.size());
        for (int i = 0; i < expectedRoots.size(); ++i) {
            assertEquals("mismatch in " + i + "th statement:", expectedRoots.get(i), actualRoots.get(i));
        }
    }


    //@Test
    //public void test_variableDeclarationStatement() {
    //    YSyntaxTree parsed = runTest(structuresDirectory + "variableDeclarationStatement.c");
    //    //assertEquals(programme)
    //}
    //
    //@Test
    //public void test_postfixExpression_call() {
    //    YSyntaxTree parsed = runTest(structuresDirectory + "postfixExpression_call.c");
    //    //assertEquals(programme)
    //}

}
