<<<<<<<< HEAD:src/main/java/afs/semantic_analysis/exceptions/IllegalExpressionException.java
package afs.semantic_analysis.exceptions;
========
package afs.checker.exceptions;
>>>>>>>> origin/DPP-142-Type-checker:src/main/java/afs/checker/exceptions/IllegalExpressionException.java

public class IllegalExpressionException extends RuntimeException {
  public IllegalExpressionException() {
    
  }
  
  public IllegalExpressionException(String message) {
    super(message);
  }
}
