package afs.semantic_analysis.exceptions;

public class IllegalExpressionException extends RuntimeException {
  public IllegalExpressionException() {
    
  }
  
  public IllegalExpressionException(String message) {
    super(message);
  }
}
