package afs.semantic_analysis.exceptions;

public class LookupException extends RuntimeException {
    public LookupException() {super();}
    public LookupException(String message) {
        super(message);
    }
}
