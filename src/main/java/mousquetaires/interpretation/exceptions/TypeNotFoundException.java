package mousquetaires.interpretation.exceptions;


public class TypeNotFoundException extends InterpreterException {

    public TypeNotFoundException(String typeNane) {
        super(typeNane);
    }
}
