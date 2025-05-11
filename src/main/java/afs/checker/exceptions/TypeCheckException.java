<<<<<<<< HEAD:src/main/java/afs/semantic_analysis/exceptions/TypeCheckException.java
package afs.semantic_analysis.exceptions;
========
package afs.checker.exceptions;
>>>>>>>> origin/DPP-142-Type-checker:src/main/java/afs/checker/exceptions/TypeCheckException.java

public class TypeCheckException extends RuntimeException {
    public TypeCheckException() {
        super();
    }

    public TypeCheckException(String message) {
        super(message);
    }
}
