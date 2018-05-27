package mousquetaires.tests.unit.languages.converters.toytree.c11.statements;

import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.definitions.YFunctionDefinition;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignmentExpression;
import mousquetaires.languages.syntax.ytree.statements.YCompoundStatement;
import mousquetaires.languages.syntax.ytree.statements.YLinearStatement;
import mousquetaires.languages.syntax.ytree.statements.YVariableDeclarationStatement;
import mousquetaires.languages.syntax.ytree.types.YMethodSignature;
import mousquetaires.languages.syntax.ytree.types.YMockType;
import mousquetaires.tests.unit.UnitTestPaths;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Iterator;


public class C2Ytree_TypeDeclarationStatement_UnitTest extends C2Ytree_Statement_UnitTest {


    @Test
    @Ignore("Yet initializers are not implemented")
    public void test_primitiveTypeDeclaration_initialisation() {
        Iterator<? extends YEntity> expected = getIterator(
                new YFunctionDefinition(origin, new YMethodSignature("?", new YMockType()),// TODO: replace this mock signature with real
                                        new YCompoundStatement(origin, true,
                                                               new YVariableDeclarationStatement(origin, typeInt, variableA),// 'int a'
                                                               new YLinearStatement(new YAssignmentExpression(origin, variableA, constant1)),// 'a = 1;'
                                                               new YVariableDeclarationStatement(origin, typeInt, variableA),// 'int a'
                                                               new YVariableDeclarationStatement(origin, typeInt, variableB),// 'int b;'
                                                               new YLinearStatement(new YAssignmentExpression(origin, variableB, constant2)),// 'b = 2;'
                                                               new YVariableDeclarationStatement(origin, typeInt, variableC),// 'int c;'
                                                               new YLinearStatement(new YAssignmentExpression(origin, variableC, constant3)),// 'c = 3;'
                                                               new YVariableDeclarationStatement(origin, typeInt, variableA),// 'int a'
                                                               new YLinearStatement(new YAssignmentExpression(origin, variableA, constant1)),// 'a = 1;'
                                                               new YVariableDeclarationStatement(origin, typeInt, variableC),// 'int c;'
                                                               new YVariableDeclarationStatement(origin, typeInt, variableB),// 'int b;'
                                                               new YLinearStatement(new YAssignmentExpression(origin, variableC, constant3))// 'c = 3;'
                )));
        run(UnitTestPaths.c11PrimitiveTypeDeclarationsDirectory + "/variableDeclarationInitialisation.c",
                expected);
    }

    @Test
    public void test_primitiveTypeDeclaration() {
        Iterator<? extends YEntity> expected = getIterator(
                new YFunctionDefinition(origin, new YMethodSignature("?", new YMockType()),// TODO: replace this mock signature with real
                                        new YCompoundStatement(origin, new YVariableDeclarationStatement(origin, typeInt, variableX),
                                                               //new YVariableDeclarationStatement(typeInt.withPointerLevel(1), variableX),
                                                               //new YVariableDeclarationStatement(typeInt.withPointerLevel(2), variableX),
                                                               //new YVariableDeclarationStatement(typeInt.withPointerLevel(2), variableX),
                                                               //
                                                               new YVariableDeclarationStatement(origin, typeInt/*.asUnsigned()*/, variableX),
                                                               //new YVariableDeclarationStatement(typeInt/*.asUnsigned()*/.withPointerLevel(1), variableX),
                                                               //
                                                               new YVariableDeclarationStatement(origin, typeChar, variableX),
                                                               new YVariableDeclarationStatement(origin, typeChar/*.asUnsigned()*/, variableX),
                                                               //
                                                               new YVariableDeclarationStatement(origin, typeShort, variableX),
                                                               new YVariableDeclarationStatement(origin, typeShort, variableX),
                                                               new YVariableDeclarationStatement(origin, typeShort/*.asUnsigned()*/, variableX),
                                                               //
                                                               new YVariableDeclarationStatement(origin, typeLong, variableX),
                                                               new YVariableDeclarationStatement(origin, typeLong, variableX),
                                                               new YVariableDeclarationStatement(origin, typeLong/*.asUnsigned()*/, variableX),
                                                               //
                                                               new YVariableDeclarationStatement(origin, typeLongLong, variableX),
                                                               new YVariableDeclarationStatement(origin, typeLongLong, variableX),
                                                               new YVariableDeclarationStatement(origin, typeLongLong/*.asUnsigned()*/, variableX),
                                                               //
                                                               new YVariableDeclarationStatement(origin, typeVoidPointer, variableX)
                )));
        run(UnitTestPaths.c11PrimitiveTypeDeclarationsDirectory  + "/primitiveTypeDeclaration.c",
                expected);
    }

}
