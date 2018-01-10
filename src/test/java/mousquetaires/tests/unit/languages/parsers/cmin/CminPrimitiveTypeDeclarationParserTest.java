package mousquetaires.tests.unit.languages.parsers.cmin;

import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.ytree.expressions.YAssignmentExpression;
import mousquetaires.languages.ytree.expressions.YConstant;
import mousquetaires.languages.ytree.expressions.YVariableRef;
import mousquetaires.languages.ytree.statements.YBlockStatement;
import mousquetaires.languages.ytree.statements.YLinearStatement;
import mousquetaires.languages.ytree.statements.YVariableDeclarationStatement;
import mousquetaires.languages.ytree.types.YType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class CminPrimitiveTypeDeclarationParserTest extends CminParserTest {

    private final String primitiveDirectory = structuresDirectory + "/type_declaration/primitive";

    @Test
    public void test_primitiveType() {
        YSyntaxTree parsedTree = runTest(primitiveDirectory + "/variableDeclarationStatement.c");
        List<YEntity> actualRoots = parsedTree.getRoots();

        YVariableRef aVariable = YVariableRef.create("a");
        YVariableRef bVariable = YVariableRef.create("b");
        YVariableRef cVariable = YVariableRef.create("c");
        YType intType = YType.getIntType();
        List<YEntity> expectedRoots = new ArrayList<>() {{
            add(new YBlockStatement(
                    // 'int a'
                    new YVariableDeclarationStatement(intType, aVariable),
                    // 'int b;'
                    new YVariableDeclarationStatement(intType, bVariable),
                    // 'b = 1;'
                    new YLinearStatement(new YAssignmentExpression(bVariable, YConstant.createInteger(1))),
                    // 'int c;'
                    new YVariableDeclarationStatement(intType, cVariable),
                    // 'c = 2;'
                    new YLinearStatement(new YAssignmentExpression(cVariable, YConstant.createInteger(2)))
            ));

        }};
        assertEquals(expectedRoots.size(), actualRoots.size());
        for (int i = 0; i < expectedRoots.size(); ++i) {
            assertEquals(i + "th statement:", expectedRoots.get(i), actualRoots.get(i));
        }
    }
}
