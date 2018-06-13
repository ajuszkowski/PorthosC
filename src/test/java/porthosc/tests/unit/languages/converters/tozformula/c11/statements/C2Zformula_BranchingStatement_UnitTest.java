package porthosc.tests.unit.languages.converters.tozformula.c11.statements;

import porthosc.memorymodels.wmm.MemoryModel;
import porthosc.tests.unit.UnitTestPaths;
import porthosc.tests.unit.languages.converters.tozformula.C2Zformula_UnitTestBase;
import org.junit.Test;


public class C2Zformula_BranchingStatement_UnitTest extends C2Zformula_UnitTestBase {

    @Override
    protected MemoryModel.Kind memoryModel() {
        return MemoryModel.Kind.TSO;//temporary
    }

    @Test
    public void test() {
        run( UnitTestPaths.c11StatementsDirectory + "branchingStatement.c",
                null);
    }
}
