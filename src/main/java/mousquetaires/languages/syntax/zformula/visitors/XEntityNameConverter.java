package mousquetaires.languages.syntax.zformula.visitors;

import mousquetaires.languages.syntax.xgraph.events.computation.XBinaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.computation.XUnaryComputationEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XEntryEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XExitEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XJumpEvent;
import mousquetaires.languages.syntax.xgraph.events.fake.XNopEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XInitialWriteEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XLoadMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XRegisterMemoryEvent;
import mousquetaires.languages.syntax.xgraph.events.memory.XStoreMemoryEvent;
import mousquetaires.languages.syntax.xgraph.memories.XConstant;
import mousquetaires.languages.syntax.xgraph.memories.XLocation;
import mousquetaires.languages.syntax.xgraph.memories.XRegister;
import mousquetaires.languages.syntax.xgraph.visitors.XEventVisitor;
import mousquetaires.languages.syntax.xgraph.visitors.XMemoryUnitVisitor;
import mousquetaires.utils.exceptions.NotImplementedException;


public class XEntityNameConverter implements XEventVisitor<String>, XMemoryUnitVisitor<String> {

    @Override
    public String visit(XEntryEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XExitEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XUnaryComputationEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XInitialWriteEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XBinaryComputationEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XRegisterMemoryEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XStoreMemoryEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XLoadMemoryEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XJumpEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XNopEvent event) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XRegister entity) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XLocation entity) {
        throw new NotImplementedException();
    }

    @Override
    public String visit(XConstant entity) {
        throw new NotImplementedException();
    }
}
