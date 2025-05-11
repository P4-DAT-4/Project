<<<<<<<< HEAD:src/main/java/afs/semantic_analysis/exceptions/IllegalStatementException.java
package afs.semantic_analysis.exceptions;
========
package afs.checker.exceptions;
>>>>>>>> origin/DPP-142-Type-checker:src/main/java/afs/checker/exceptions/IllegalStatementException.java

public class IllegalStatementException extends RuntimeException {
    public IllegalStatementException(String message) {
        
    }

    public IllegalStatementException(String message, Throwable cause) {
        super(message, cause);
    }
  
}
