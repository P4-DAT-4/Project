package afs.astbuilder.checker;

import java.util.Set;
import afs.astbuilder.checker.exceptions.TypeCheckException;
import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.checker.types.SimpleType;

public class TypeValidator {
  private static final Set<SimpleType> SIMPLE_TYPES = Set.of(
    SimpleType.INT,
    SimpleType.DOUBLE,
    SimpleType.BOOL,
    SimpleType.STRING
  );

  // Checks for simple types
  public static void validatePrimitiveType(AFSType type) {
    if(!(type instanceof SimpleType) || !SIMPLE_TYPES.contains(type)) {
      throw new TypeCheckException("Invalid type '" + type + "': expected int, double, string, or bool");
    }
  }

  // Checks for strings
  public static void validateIdentifierType(AFSType type) {
    if (!type.equals(SimpleType.STRING)) {
      throw new TypeCheckException("Invalid type '" + type + "': expected string");
    }
  }

  // Checks for equality between two types
  public static void validateTypeEquality(AFSType actual, AFSType expected) {
    if (!actual.equals(expected)) {
      throw new TypeCheckException("Type mismatch: expected '" + expected + "', but found '" + actual + "'");
    }
  }
  // Checks for Bool
  public static void validBooleanType(AFSType type) {
    if (!(type instanceof SimpleType) || !type.equals(SimpleType.BOOL)) {
      throw new TypeCheckException("Invalid type '" + type + "': expected bool");
    }
  }

  // Checks for void
  public static void validateVoidType(AFSType type) {
    if (!(type instanceof SimpleType) || !type.equals(SimpleType.VOID)) {
      throw new TypeCheckException("Invalid type '" + type + "': expected void");
    }
  }
  // Checks for ints and doubles
  public static void validateBinop(AFSType Type) {
    if (!(Type instanceof SimpleType) || !(Type.equals(SimpleType.INT) || Type.equals(SimpleType.DOUBLE))) {
      throw new TypeCheckException("Invalid type '" + Type + "': expected int or double");
    }
  }
}
