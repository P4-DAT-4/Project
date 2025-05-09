package afs.semantic_analysis.exceptions;

public class TypeCheckException extends RuntimeException {
    public TypeCheckException() {
        super();
    }

    public TypeCheckException(String message) {
        super(message);
    }
}
