package afs.semantic_analysis.exceptions;

public class UnreachableCodeException extends RuntimeException {
    public UnreachableCodeException(String message) {
        super(message);
    }
}
