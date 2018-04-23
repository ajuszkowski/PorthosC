package mousquetaires.languages.converters.tozformula.process;

import com.microsoft.z3.BoolExpr;
import mousquetaires.languages.syntax.xgraph.process.XProcess;

import java.util.List;


public interface XProcessEncoder {

    List<BoolExpr> encodeProcess(XProcess process);

    List<BoolExpr> encodeProcessRFRelation(XProcess process);
}
