package afs.astbuilder.checker.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// Uses Junit5 for unit testing TypeChecker
public class TypeCheckerTest {
  @Test
  public void validateDeclaration() {
  
  }
  @Test
  public void validateEvent() {

  }
  @Test
  public void validateStatement() {

  }
  @Test
  public void validateExpression() {
    @Nested
    class Binop {
      @Test
      public void validateBinop() {
        TypeChecker typeChecker = new TypeChecker();
        String input = "validInput"

        String result = typeChecker.check(input);

        assertEquals("ExpectedOutput", result);
      }
    }
    @Nested
    class Unop {
      @Test
      public void validateUnop() {
        TypeChecker typeChecker = new TypeChecker();
        String input = "validInput"

        String result = typeChecker.check(input);

        assertEquals("ExpectedOutput", result);
      }
    }
    
  } 
  @Test
  public void validateTypeType() {

  }
}
