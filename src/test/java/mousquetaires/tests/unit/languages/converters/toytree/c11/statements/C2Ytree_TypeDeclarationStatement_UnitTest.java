package mousquetaires.tests.unit.languages.converters.toytree.c11.statements;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import mousquetaires.languages.syntax.ytree.statements.YVariableDeclarationStatement;
import mousquetaires.languages.syntax.ytree.types.YMockType;
import mousquetaires.languages.syntax.ytree.types.signatures.YMethodSignature;
import mousquetaires.languages.syntax.ytree.types.signatures.YParameter;
import mousquetaires.tests.unit.UnitTestPaths;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Iterator;


public class C2Ytree_TypeDeclarationStatement_UnitTest extends C2Ytree_Statement_UnitTest {


    @Test
    @Ignore("Yet initializers are not implemented")
    public void test_primitiveTypeDeclaration_initialisation() {
        Iterator<? extends YEntity> expected = getIterator(
                new YFunctionDefinition(new YMethodSignature("?", new YMockType(), new YParameter[0]),// TODO: replace this mock signature with real
                        new YCompoundStatement(true,
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
        run(UnitTestPaths.c11PrimitiveTypeDeclarationsDirectory + "/variableDeclarationInitialisation.c",
                expected);
    }

    @Test
    public void test_primitiveTypeDeclaration() {
        Iterator<? extends YEntity> expected = getIterator(
                new YFunctionDefinition(new YMethodSignature("?", new YMockType(), new YParameter[0]),// TODO: replace this mock signature with real
                        new YCompoundStatement(
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
        run(UnitTestPaths.c11PrimitiveTypeDeclarationsDirectory  + "/primitiveTypeDeclaration.c",
                expected);
    }

}
