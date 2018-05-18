package mousquetaires.languages.converters.toxgraph.interpretation;

import mousquetaires.languages.common.XType;
import mousquetaires.languages.syntax.xgraph.memories.XLocation;
import mousquetaires.languages.syntax.xgraph.memories.XLvalueMemoryUnit;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.syntax.xgraph.process.XProcessId;


public interface XMemoryManager {

    void reset(XProcessId processId);

    XLocation declareLocation(String name, XType type);

    XRegister declareRegister(String name, XType type);

    XRegister declareTempRegister(XType type);

    XLvalueMemoryUnit declareUnresolvedUnit(String name, boolean isGlobal);

    XLvalueMemoryUnit getDeclaredUnitOrNull(String name);

    XRegister getDeclaredRegister(String name, XProcessId processId);
}
