package afs.semantic_analysis.exceptions;

public class IllegalStatementException extends RuntimeException {
    public IllegalStatementException(String message) {
        
    }

    public IllegalStatementException(String message, Throwable cause) {
        super(message, cause);
    }
  
}
