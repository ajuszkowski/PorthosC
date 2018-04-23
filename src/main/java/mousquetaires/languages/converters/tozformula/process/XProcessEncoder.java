package mousquetaires.languages.converters.tozformula.process;

import com.microsoft.z3.BoolExpr;
import mousquetaires.languages.syntax.xgraph.process.XProcess;
import mousquetaires.languages.syntax.zformula.ZFormulaBuilder;

import java.util.List;


public interface XProcessEncoder {

    void encodeProcess(XProcess process, ZFormulaBuilder formulaBuilder);
    
    void encodeProcessRFRelation(XProcess process, ZFormulaBuilder formulaBuilder);
}
