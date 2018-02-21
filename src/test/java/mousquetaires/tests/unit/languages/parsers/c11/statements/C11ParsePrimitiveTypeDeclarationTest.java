package mousquetaires.tests.unit.languages.parsers.c11.statements;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import mousquetaires.languages.syntax.ytree.statements.YVariableDeclarationStatement;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;


public class C11ParsePrimitiveTypeDeclarationTest extends C11ParseStatementTest {

    private final String primitiveDirectory = statementsDirectory + "/type_declaration/primitive";

    @Test
    @Ignore("Yet initializers are not implemented")
    public void test_primitiveTypeDeclaration_initialisation() {
        List<YEntity> expected = buildResultList(
                new YFunctionDefinition(new YCompoundStatement(true,
                        new YVariableDeclarationStatement(typeInt, variableA), // 'int a'
                        new YLinearStatement(new YAssignmentExpression(variableA, constant1)), // 'a = 1;'
                        new YVariableDeclarationStatement(typeInt, variableA), // 'int a'
                        new YVariableDeclarationStatement(typeInt, variableB), // 'int b;'
                        new YLinearStatement(new YAssignmentExpression(variableB, constant2)), // 'b = 2;'
                        new YVariableDeclarationStatement(typeInt, variableC), // 'int c;'
                        new YLinearStatement(new YAssignmentExpression(variableC, constant3)), // 'c = 3;'
                        new YVariableDeclarationStatement(typeInt, variableA), // 'int a'
                        new YLinearStatement(new YAssignmentExpression(variableA, constant1)), // 'a = 1;'
                        new YVariableDeclarationStatement(typeInt, variableB), // 'int b;'
                        new YVariableDeclarationStatement(typeInt, variableC), // 'int c;'
                        new YLinearStatement(new YAssignmentExpression(variableC, constant3)) // 'c = 3;'
                )));
        run(primitiveDirectory + "/variableDeclarationInitialisation.c", expected);
    }

    @Test
    public void test_primitiveTypeDeclaration() {
        List<YEntity> expected = buildResultList(
                new YFunctionDefinition(new YCompoundStatement(
                        new YVariableDeclarationStatement(typeInt, variableX),
                        //new YVariableDeclarationStatement(typeInt.withPointerLevel(1), variableX),
                        //new YVariableDeclarationStatement(typeInt.withPointerLevel(2), variableX),
                        //new YVariableDeclarationStatement(typeInt.withPointerLevel(2), variableX),
                        //
                        new YVariableDeclarationStatement(typeInt/*.asUnsigned()*/, variableX),
                        //new YVariableDeclarationStatement(typeInt/*.asUnsigned()*/.withPointerLevel(1), variableX),
                        //
                        new YVariableDeclarationStatement(typeChar, variableX),
                        new YVariableDeclarationStatement(typeChar/*.asUnsigned()*/, variableX),
                        //
                        new YVariableDeclarationStatement(typeShort, variableX),
                        new YVariableDeclarationStatement(typeShort, variableX),
                        new YVariableDeclarationStatement(typeShort/*.asUnsigned()*/, variableX),
                        //
                        new YVariableDeclarationStatement(typeLong, variableX),
                        new YVariableDeclarationStatement(typeLong, variableX),
                        new YVariableDeclarationStatement(typeLong/*.asUnsigned()*/, variableX),
                        //
                        new YVariableDeclarationStatement(typeLongLong, variableX),
                        new YVariableDeclarationStatement(typeLongLong, variableX),
                        new YVariableDeclarationStatement(typeLongLong/*.asUnsigned()*/, variableX),
                        //
                        new YVariableDeclarationStatement(typeVoidPointer, variableX)
                )));
        run(primitiveDirectory + "/primitiveTypeDeclaration.c", expected);
    }

}
