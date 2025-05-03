package afs.astbuilder.checker;

import afs.astbuilder.checker.exceptions.TypeCheckException;
import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.checker.types.ListType;
import afs.astbuilder.checker.types.SimpleType;
import afs.astbuilder.nodes.expr.*;
import afs.astbuilder.nodes.stmt.StmtNode;
import afs.astbuilder.nodes.type.TypeBoolNode;
import afs.astbuilder.nodes.type.TypeIntNode;
import afs.astbuilder.nodes.type.TypeNode;

public class ExprTypeChecker {

    private AFSType EventType(StmtNode stmt) {

    }

    private AFSType StmtType(StmtNode stmt) {

    }

    private AFSType ExprType(ExprNode expr) {
        switch(expr) {
            case ExprBinopNode binopNode -> {
                BinOp binOp = binopNode.getOp();
                switch (binOp) {
                    case ADD, SUB, MUL, DIV -> {
                        AFSType leftType = ExprType(binopNode.getLeftExpression());
                        boolean validLeft = leftType.equals(SimpleType.DOUBLE) ||  leftType.equals(SimpleType.INT);

                        if (!validLeft) {
                            throw new TypeCheckException("Invalid type '" + leftType + "': " + "Expected int or double");
                        }

                        AFSType rightType = ExprType(binopNode.getRightExpression());
                        boolean validRight = rightType.equals(leftType);

                        if (!validRight) {
                            throw new TypeCheckException("Invalid type '" + rightType + "': " + "Expected int or double");
                        }
                        return leftType;
                    }
                    case CONCAT -> {
                        AFSType leftType = ExprType(binopNode.getLeftExpression());
                        boolean validLeft = leftType.equals(SimpleType.STRING) || leftType.equals(SimpleType.SHAPE) || leftType instanceof ListType;

                        if (!validLeft) {
                            throw new TypeCheckException("Invalid type '" + leftType + "': " + "Expected string or shape or list");
                        }

                        AFSType rightType = ExprType(binopNode.getRightExpression());
                        boolean validRight = rightType.equals(leftType);

                        if (!validRight) {
                            throw new TypeCheckException("Invalid right type");
                        }
                        return leftType;
                    }
                }
            }

            case ExprUnopNode unopNode -> {
                UnOp UnOp = unopNode.getUnOp();
                switch (UnOp) {
                    case NOT -> {

                    }
                    case NEG -> {

                    }
                }
            }

            case ExprStringNode ignored -> {
                return SimpleType.STRING;
            }
            case ExprIntNode ignored -> {
                return SimpleType.INT;
            }
            case ExprDoubleNode ignored -> {
                return SimpleType.DOUBLE;
            }
            case ExprBoolNode ignored -> {
                return SimpleType.BOOL;
            }
            default -> throw new IllegalStateException("Unexpected value: " + expr);
        }
    }

    private AFSType TypeType(TypeNode type) {
        switch(type) {
            case TypeBoolNode ignored -> {
                return SimpleType.BOOL;
            }
            case TypeIntNode ignored -> {
                return SimpleType.INT;
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
