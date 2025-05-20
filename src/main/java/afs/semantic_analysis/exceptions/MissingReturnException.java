package afs.semantic_analysis.exceptions;

public class MissingReturnException extends RuntimeException {
    public MissingReturnException(String message) {
        super(message);
    }
}
