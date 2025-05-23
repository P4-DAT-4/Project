package afs.semantic_analysis;

import afs.semantic_analysis.exceptions.TypeCheckException;
import afs.semantic_analysis.types.AFSType;
import afs.semantic_analysis.types.SimpleType;

import java.util.Set;

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
      throw new TypeCheckException("Invalid type '" + type + "': expected 'INT', 'DOUBLE', 'STRING', or 'BOOL'");
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
    if (!type.equals(SimpleType.BOOL)) {
      throw new TypeCheckException("Invalid type '" + type + "': expected 'BOOL'");
    }
  }

  // Checks for ints and doubles
  public static void validateBinop(AFSType Type) {
    if (!(Type.equals(SimpleType.INT) || Type.equals(SimpleType.DOUBLE))) {
      throw new TypeCheckException("Invalid type '" + Type + "': expected 'INT' or 'DOUBLE'");
    }
  }
  public static void validateIntType(AFSType type) {
    if (!type.equals(SimpleType.INT)) {
      throw new TypeCheckException("Invalid type '" + type + "': expected 'INT'");
    }
  }
  public static void validateDoubleType(AFSType type) {
    if (!type.equals(SimpleType.DOUBLE)) {
      throw new TypeCheckException("Invalid type '" + type + "': expected 'DOUBLE'");
    }
  }
  public static void validateStringType(AFSType type) {
    if (!type.equals(SimpleType.STRING)) {
      throw new TypeCheckException("Invalid type '" + type + "': expected 'STRING'");
    }
  }
  public static void validateShapeType(AFSType type) {
    if (!type.equals(SimpleType.SHAPE)) {
      throw new TypeCheckException("Invalid type '" + type + "': expected 'SHAPE'");
    }
  }
  public static void validateShapeOrDouble(AFSType type) {
    if (!type.equals(SimpleType.SHAPE) && !type.equals(SimpleType.DOUBLE)) {
      throw new TypeCheckException("Invalid type '" + type + "': expected 'SHAPE' or 'DOUBLE'");
    }
  }
}
