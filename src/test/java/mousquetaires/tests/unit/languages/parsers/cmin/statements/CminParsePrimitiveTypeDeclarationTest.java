package mousquetaires.tests.unit.languages.parsers.cmin.statements;

import mousquetaires.languages.internalrepr.YSyntaxTree;
import mousquetaires.languages.internalrepr.expressions.YAssignmentExpression;
import mousquetaires.languages.internalrepr.statements.YBlockStatement;
import mousquetaires.languages.internalrepr.statements.YLinearStatement;
import mousquetaires.languages.internalrepr.statements.YVariableDeclarationStatement;
import org.junit.Test;


public class CminParsePrimitiveTypeDeclarationTest extends CminParseStatementTest {

    private final String primitiveDirectory = statementsDirectory + "/type_declaration/primitive";

    @Test
    public void test_primitiveTypeDeclaration_initialisation() {
        YSyntaxTree expectedTree = new YSyntaxTree(
                new YBlockStatement(
                        new YVariableDeclarationStatement(typeInt, variableA), // 'int a'
                        new YLinearStatement(new YAssignmentExpression(variableA, constant1)) // 'a = 1;'
                ),
                new YBlockStatement(
                        new YVariableDeclarationStatement(typeInt, variableA), // 'int a'
                        new YVariableDeclarationStatement(typeInt, variableB), // 'int b;'
                        new YLinearStatement(new YAssignmentExpression(variableB, constant2)), // 'b = 2;'
                        new YVariableDeclarationStatement(typeInt, variableC), // 'int c;'
                        new YLinearStatement(new YAssignmentExpression(variableC, constant3)) // 'c = 3;'
                ),
                new YBlockStatement(
                        new YVariableDeclarationStatement(typeInt, variableA), // 'int a'
                        new YLinearStatement(new YAssignmentExpression(variableA, constant1)), // 'a = 1;'
                        new YVariableDeclarationStatement(typeInt, variableB), // 'int b;'
                        new YVariableDeclarationStatement(typeInt, variableC), // 'int c;'
                        new YLinearStatement(new YAssignmentExpression(variableC, constant3)) // 'c = 3;'
                ));
        runParserTest(primitiveDirectory + "/variableDeclarationInitialisation.c", expectedTree);
    }

    @Test
    public void test_primitiveTypeDeclaration() {
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
        runParserTest(primitiveDirectory + "/primitiveTypeDeclaration.c", expectedTree);
    }

}
