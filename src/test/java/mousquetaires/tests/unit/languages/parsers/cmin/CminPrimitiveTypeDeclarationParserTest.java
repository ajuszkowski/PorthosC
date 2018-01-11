package mousquetaires.tests.unit.languages.parsers.cmin;

import mousquetaires.languages.ytree.YSyntaxTree;
import mousquetaires.languages.ytree.expressions.YAssignmentExpression;
import mousquetaires.languages.ytree.statements.YBlockStatement;
import mousquetaires.languages.ytree.statements.YLinearStatement;
import mousquetaires.languages.ytree.statements.YVariableDeclarationStatement;
import org.junit.Test;


public class CminPrimitiveTypeDeclarationParserTest extends CminParserTest {

    private final String primitiveDirectory = structuresDirectory + "/type_declaration/primitive";


    @Test
    public void test_primitive_type_declaration() {
        YSyntaxTree expectedTree = new YSyntaxTree(
                new YVariableDeclarationStatement(typeInt, variableX),
                new YVariableDeclarationStatement(typeInt.withPointerLevel(1), variableX),
                new YVariableDeclarationStatement(typeInt.withPointerLevel(2), variableX),
                new YVariableDeclarationStatement(typeInt.withPointerLevel(2), variableX),
                //
                new YVariableDeclarationStatement(typeInt.asUnsigned(), variableX),
                new YVariableDeclarationStatement(typeInt.asUnsigned().withPointerLevel(1), variableX),
                //
                new YVariableDeclarationStatement(typeChar, variableX),
                new YVariableDeclarationStatement(typeChar.asUnsigned(), variableX),
                //
                new YVariableDeclarationStatement(typeShort, variableX),
                new YVariableDeclarationStatement(typeShort, variableX),
                new YVariableDeclarationStatement(typeShort.asUnsigned(), variableX),
                //
                new YVariableDeclarationStatement(typeLong, variableX),
                new YVariableDeclarationStatement(typeLong, variableX),
                new YVariableDeclarationStatement(typeLong.asUnsigned(), variableX),
                //
                new YVariableDeclarationStatement(typeLongLong, variableX),
                new YVariableDeclarationStatement(typeLongLong, variableX),
                new YVariableDeclarationStatement(typeLongLong.asUnsigned(), variableX),
                //
                new YVariableDeclarationStatement(typeVoidPointer, variableX)
        );
        runParserTest(primitiveDirectory + "/primitive_type_declaration.c", expectedTree);
    }

    @Test
    public void test_primitive_type_declaration_initialisation() {
        YSyntaxTree expectedTree = new YSyntaxTree(
                new YBlockStatement(
                        // 'int a'
                        new YVariableDeclarationStatement(typeInt, variableA),
                        // 'a = 1;'
                        new YLinearStatement(new YAssignmentExpression(variableA, constant1))
                ),
                new YBlockStatement(
                        // 'int a'
                        new YVariableDeclarationStatement(typeInt, variableA),
                        // 'int b;'
                        new YVariableDeclarationStatement(typeInt, variableB),
                        // 'b = 2;'
                        new YLinearStatement(new YAssignmentExpression(variableB, constant2)),
                        // 'int c;'
                        new YVariableDeclarationStatement(typeInt, variableC),
                        // 'c = 3;'
                        new YLinearStatement(new YAssignmentExpression(variableC, constant3))
                ),
                new YBlockStatement(
                        // 'int a'
                        new YVariableDeclarationStatement(typeInt, variableA),
                        // 'a = 1;'
                        new YLinearStatement(new YAssignmentExpression(variableA, constant1)),
                        // 'int b;'
                        new YVariableDeclarationStatement(typeInt, variableB),
                        // 'int c;'
                        new YVariableDeclarationStatement(typeInt, variableC),
                        // 'c = 3;'
                        new YLinearStatement(new YAssignmentExpression(variableC, constant3))
                ));
        runParserTest(primitiveDirectory + "/variable_declaration_initialisation.c", expectedTree);
    }
}
