package mousquetaires.tests.unit.languages.converters.toytree.c11.statements;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import mousquetaires.languages.syntax.ytree.statements.YVariableDeclarationStatement;
import mousquetaires.languages.syntax.ytree.types.YMockType;
import mousquetaires.languages.syntax.ytree.types.YMethodSignature;
import mousquetaires.languages.syntax.ytree.expressions.atomics.YParameter;
import mousquetaires.tests.unit.UnitTestPaths;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Iterator;


public class C2Ytree_TypeDeclarationStatement_UnitTest extends C2Ytree_Statement_UnitTest {


    @Test
    @Ignore("Yet initializers are not implemented")
    public void test_primitiveTypeDeclaration_initialisation() {
        Iterator<? extends YEntity> expected = getIterator(
                new YFunctionDefinition(location, new YMethodSignature("?", new YMockType(), new YParameter[0]),// TODO: replace this mock signature with real
                        new YCompoundStatement(location, true,
                        new YVariableDeclarationStatement(location, typeInt, variableA), // 'int a'
                        new YLinearStatement(location, new YAssignmentExpression(location, variableA, constant1)), // 'a = 1;'
                        new YVariableDeclarationStatement(location, typeInt, variableA), // 'int a'
                        new YVariableDeclarationStatement(location, typeInt, variableB), // 'int b;'
                        new YLinearStatement(location, new YAssignmentExpression(location, variableB, constant2)), // 'b = 2;'
                        new YVariableDeclarationStatement(location, typeInt, variableC), // 'int c;'
                        new YLinearStatement(location, new YAssignmentExpression(location, variableC, constant3)), // 'c = 3;'
                        new YVariableDeclarationStatement(location, typeInt, variableA), // 'int a'
                        new YLinearStatement(location, new YAssignmentExpression(location, variableA, constant1)), // 'a = 1;'
                        new YVariableDeclarationStatement(location, typeInt, variableC), // 'int c;'
                        new YVariableDeclarationStatement(location, typeInt, variableB), // 'int b;'
                        new YLinearStatement(location, new YAssignmentExpression(location, variableC, constant3)) // 'c = 3;'
                )));
        run(UnitTestPaths.c11PrimitiveTypeDeclarationsDirectory + "/variableDeclarationInitialisation.c",
                expected);
    }

    @Test
    public void test_primitiveTypeDeclaration() {
        Iterator<? extends YEntity> expected = getIterator(
                new YFunctionDefinition(location, new YMethodSignature("?", new YMockType(), new YParameter[0]),// TODO: replace this mock signature with real
                        new YCompoundStatement(location, new YVariableDeclarationStatement(location, typeInt, variableX),
                        //new YVariableDeclarationStatement(typeInt.withPointerLevel(1), variableX),
                        //new YVariableDeclarationStatement(typeInt.withPointerLevel(2), variableX),
                        //new YVariableDeclarationStatement(typeInt.withPointerLevel(2), variableX),
                        //
                        new YVariableDeclarationStatement(location, typeInt/*.asUnsigned()*/, variableX),
                        //new YVariableDeclarationStatement(typeInt/*.asUnsigned()*/.withPointerLevel(1), variableX),
                        //
                        new YVariableDeclarationStatement(location, typeChar, variableX),
                        new YVariableDeclarationStatement(location, typeChar/*.asUnsigned()*/, variableX),
                        //
                        new YVariableDeclarationStatement(location, typeShort, variableX),
                        new YVariableDeclarationStatement(location, typeShort, variableX),
                        new YVariableDeclarationStatement(location, typeShort/*.asUnsigned()*/, variableX),
                        //
                        new YVariableDeclarationStatement(location, typeLong, variableX),
                        new YVariableDeclarationStatement(location, typeLong, variableX),
                        new YVariableDeclarationStatement(location, typeLong/*.asUnsigned()*/, variableX),
                        //
                        new YVariableDeclarationStatement(location, typeLongLong, variableX),
                        new YVariableDeclarationStatement(location, typeLongLong, variableX),
                        new YVariableDeclarationStatement(location, typeLongLong/*.asUnsigned()*/, variableX),
                        //
                        new YVariableDeclarationStatement(location, typeVoidPointer, variableX)
                )));
        run(UnitTestPaths.c11PrimitiveTypeDeclarationsDirectory  + "/primitiveTypeDeclaration.c",
                expected);
    }

}
