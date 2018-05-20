package mousquetaires.languages.converters.tozformula.process;

import com.microsoft.z3.BoolExpr;
import mousquetaires.languages.syntax.xgraph.process.XProcess;


public interface XProcessEncoder {

    BoolExpr encodeProcess(XProcess process);

    BoolExpr encodeProcessRFRelation(XProcess process);
}
