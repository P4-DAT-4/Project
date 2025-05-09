package afs.astbuilder.checker.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import afs.astbuilder.checker.TypeValidator;

// Uses Junit5 for unit testing TypeChecker
public class TypeCheckerTest {
  @Test
  public void validateDeclaration() {
    @Nested
    class Declaration {
      @Test
    }
    @Nested
    class Function {
      @Test
    }
    class Visualize {
      @Test
    }
  }
  @Test
  public void validateEvent() {
    @Nested
    class Composition {
      @Test
    }
    class Declaration {
      @Test
    }

  }
  @Test
  public void validateStatement() {
    @Nested
    class While {
      @Test
    }
    class Assignment {
      @Test
    }
    class Block {
      @Test
    }
    class Composition {
      @Test
    }
    class Declaration {
      @Test
    }
    class FunctionCall {
      @Test
    }
    class Return {
      @Test
    }
    class Skip {
      @Test
    }
  }
  @Test
  public void validateExpression() {
    @Nested
    class Binop {
      @Nested
      class AddSubMulDiv {
        @Test
      }
      @Nested
      class Cat {
        @Test
      }
      @Nested
      class LtEq {
        @Test
      }
      @Nested
      class And {
        @Test
      }
    }
    @Nested
    class Unop {
      @Test
      public void validateUnop() {
        @Nested
        class Not {
          @Test
        }
        @Nested
        class Neg {
          @Test
        }
      }
    }
    
  } 
  @Test
  public void validateTypeType() {

  }
}
