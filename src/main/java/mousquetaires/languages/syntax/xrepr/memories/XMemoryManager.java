package mousquetaires.languages.syntax.xrepr.memories;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.syntax.xrepr.datamodels.DataModel;
import mousquetaires.utils.exceptions.xrepr.UndeclaredMemoryUnitException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for virtual memoryevents adresation (by variable names) and allocation.
 */
public final class XMemoryManager {

    private final Map<String, XLocalMemoryUnit> localLocations;
    private final Map<String, XSharedMemoryUnit> sharedLocations;

    public XMemoryManager(ProgramLanguage language, DataModel dataModel) {
        // todo: use parameters for better memoryevents allocation
        this.localLocations = new HashMap<>();
        this.sharedLocations = new HashMap<>();
    }


    //public XConstant getConstantValue(Object value, XType type) {
    //    return new XConstant(value, type);
    //}

    public XLocalMemoryUnit newLocalMemoryUnit() {
        String name = newTempLocalName();
        while (localLocations.containsKey(name)) {
            name = newTempLocalName();
        }
        //return declareLocalMemoryUnit(name, null);
        return new XLocalMemoryUnit(name, XMemoryUnit.Bitness.bit16);// TODO: process bitness!!
    }

    public XConstant getConstant(Object value, XMemoryUnit.Bitness bitness) {
        return new XConstant(value, bitness);
    }

    public XLocalMemoryUnit getLocalMemoryUnit(String name) {
        XLocalMemoryUnit result = localLocations.get(name);
        if (result == null) {
            // todo: probably, do not need to declare local memoryevents (registers)
            //throw new UndeclaredMemoryUnitException(name);
            return newLocalMemoryUnit(name);//todo: type
        }
        return result;
    }

    public XSharedMemoryUnit getSharedMemoryUnit(String name) {
        XSharedMemoryUnit result = sharedLocations.get(name);
        if (result == null) {
            // todo: probably processName undeclared shared memoryevents units as well
            throw new UndeclaredMemoryUnitException(name);
        }
        return result;
    }

    //// TO-DO: do we really need to declare local memory units? or we have infinitely many registers in our model?
    //public XLocalMemoryUnit declareLocalMemoryUnit(String name, XType type) {
    //    if (isLocalMemoryDeclared(name)) {
    //        throw new MemoryUnitDoubleDeclarationException(name, true);
    //    }
    //    return newLocalMemoryUnit(name, type);
    //}
    //
    //public XLocalMemoryUnit declareSharedMemoryUnit(String name, XType type) {
    //    if (isSharedMemoryDeclared(name)) {
    //        throw new MemoryUnitDoubleDeclarationException(name, false);
    //    }
    //    return newSharedMemory(name, type);
    //}

    //private String defineMemory(String postProcessId, XType returnType, boolean isLocal) {
    //    String baseName = postProcessId;
    //    int count = 1;
    //    XMemoryUnit location = null;
    //    while (location == null) {
    //        location = isLocal ? getLocalMemory(postProcessId) : tryFindSharedMemory(postProcessId);
    //        postProcessId = getNewName(baseName, count++);
    //    }
    //    return postProcessId;
    //}


    //private boolean isLocalMemoryDeclared(String name) {
    //    return localLocations.containsKey(name);
    //}
    //
    //private boolean isSharedMemoryDeclared(String name) {
    //    return sharedLocations.containsKey(name);
    //}

    public XLocalMemoryUnit newLocalMemoryUnit(String name) {// type) {
        XLocalMemoryUnit location = new XLocalMemoryUnit(name, XMemoryUnit.Bitness.bit16); //todo:bitness
        localLocations.put(name, location);
        return location;
    }

    public XSharedMemoryUnit newSharedMemory(String name) {
        XSharedMemoryUnit location = new XSharedMemoryUnit(name, XMemoryUnit.Bitness.bit16); //todo:bitness
        sharedLocations.put(name, location);
        return location;
    }



    private int tempNamesCounter = 1;
    private String newTempLocalName() {
        return getNewName("reg", tempNamesCounter++);
    }

    private String getNewName(String baseName, int count) {
        return baseName + '_' + count;
    }
}
