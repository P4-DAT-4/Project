package afs.astbuilder.checker;

import java.util.Set;
import afs.astbuilder.checker.exceptions.TypeCheckException;
import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.checker.types.SimpleType;

public class TypeValidator {
  private static final Set<SimpleType> PRIMITIVE_TYPES = Set.of(
    SimpleType.INT,
    SimpleType.DOUBLE,
    SimpleType.BOOL,
    SimpleType.STRING
  );

  public static void validatePrimitiveType(AFSType type) {
    if(!(type instanceof SimpleType) || !PRIMITIVE_TYPES.contains(type)) {
      throw new TypeCheckException("Invalid type '" + type + "': expected int, double, string, or bool");
    }
  }

  public static void validateIdentifierType(AFSType type) {
    if (!type.equals(SimpleType.STRING)) {
      throw new TypeCheckException("Invalid type '" + type + "': expected string");
    }
  }

  public static void validateTypeEquality(AFSType actual, AFSType expected) {
    if (!actual.equals(expected)) {
      throw new TypeCheckException("Type mismatch: expected '" + expected + "', but found '" + actual + "'");
    }
  }
  public static void validBooleanType(AFSType type) {
    if (!(type instanceof SimpleType) || !type.equals(SimpleType.BOOL)) {
      throw new TypeCheckException("Invalid type '" + type + "': expected bool");
    }
  }

  public static void validateVoidType(AFSType type) {
    if (!(type instanceof SimpleType) || !type.equals(SimpleType.VOID)) {
      throw new TypeCheckException("Invalid type '" + type + "': expected void");
    }
  }
}
