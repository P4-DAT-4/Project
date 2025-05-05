package afs.astbuilder.checker;

import afs.astbuilder.checker.exceptions.*;
import afs.astbuilder.checker.types.*;
import afs.astbuilder.nodes.expr.*;
import afs.astbuilder.nodes.stmt.*;
import afs.astbuilder.nodes.event.*;
import afs.astbuilder.nodes.type.*;
import afs.astbuilder.nodes.def.*;

import java.lang.reflect.Type;
import java.util.List;




public class ExprTypeChecker {

    private AFSType DeclarationType(DefNode def) {
        switch (def) {
            case DefDeclarationNode declarationNode -> {
                AFSType type = TypeType(declarationNode.getType());
                TypeValidator.validatePrimitiveType(type);

                AFSType identifierType = ExprType(declarationNode.getIdentifier());
                TypeValidator.validateIdentifierType(identifierType);

                AFSType exprType = ExprType(declarationNode.getExpression());
                TypeValidator.validateTypeEquality(exprType, type);

                return type;
            }
            case DefFunctionNode functionNode -> {
                AFSType type = TypeType(functionNode.getType());
                TypeValidator.validatePrimitiveType(type);

                AFSType identifierType = ExprType(functionNode.getIdentifier());
                TypeValidator.validateIdentifierType(identifierType);


                List<Param> params = functionNode.getParameters();
                for (Param param : params) {
                    AFSType paramType = TypeType(param.getType());
                    TypeValidator.validatePrimitiveType(paramType);

                    AFSType paramIdentifierType = ExprType(param.getIdentifier());
                    TypeValidator.validateIdentifierType(paramIdentifierType);
                    
                }

                AFSType stmtType = StmtType(functionNode.getStatement());
                TypeValidator.validateTypeEquality(identifierType, stmtType);

                return type;
            }
            case DefVisualizeNode visualizeNode -> {
                AFSType functionCallType = ExprType(visualizeNode.getFunctionCall());
                TypeValidator.validateIdentifierType(functionCallType);

                AFSType eventType = EventType(visualizeNode.getEvent());
                TypeValidator.validBooleanType(eventType);

                return SimpleType.VOID;
            }
            default -> {
                throw new IllegalDefException("Unexpected value: " + def);
            }
        }
    }

    private AFSType EventType(EventNode event) {
        switch(event) {
            case EventCompositionNode compositionNode -> {
                AFSType leftType = EventType(compositionNode.getLeftEvent());
                TypeValidator.validBooleanType(leftType);

                AFSType rightType = EventType(compositionNode.getRightEvent());
                TypeValidator.validBooleanType(rightType);

                return leftType;
            }
            case EventDeclarationNode declarationNode -> {
                AFSType exprType = ExprType(declarationNode.getExpression());
                TypeValidator.validBooleanType(exprType);

                AFSType functionCallType = ExprType(declarationNode.getFunctionCall());
                TypeValidator.validateIdentifierType(functionCallType);

                return SimpleType.BOOL;
            }
            default -> throw new IllegalEventException("Unexpected value: " + event);
        }
    }

    private AFSType StmtType(StmtNode stmt) {
        switch(stmt) {
            case StmtIfNode ifNode -> {
                AFSType exprType = ExprType(ifNode.getExpression());
                TypeValidator.validBooleanType(exprType);

                AFSType leftStmt = StmtType(ifNode.getLeftStatement());
                AFSType rightStmt = StmtType(ifNode.getRightStatement());
                TypeValidator.validateTypeEquality(rightStmt, leftStmt);

                return leftStmt;
            }
            case StmtWhileNode whileNode -> {
                AFSType exprType = ExprType(whileNode.getExpression());
                TypeValidator.validBooleanType(exprType);


                AFSType stmtType = StmtType(whileNode.getStatement());
                TypeValidator.validateVoidType(stmtType);

                return SimpleType.VOID;
            }
            case StmtAssignmentNode assignmentNode -> { // Ikke færdig
                AFSType leftType = ExprType(assignmentNode.getLeftExpression());
                
                if (!(leftType instanceof ListType)) {
                    boolean validLeft = leftType.equals(SimpleType.INT) || leftType.equals(SimpleType.DOUBLE) || leftType.equals(SimpleType.STRING) || leftType.equals(SimpleType.BOOL);
                    if (!validLeft) {
                        throw new TypeCheckException("Invalid type '" + leftType + "': " + "Expected int, double, string or bool");
                    }
                    AFSType rightType = ExprType(assignmentNode.getRightExpression());
                    boolean validRight = rightType.equals(leftType);;
                    if (!validRight) {
                        throw new TypeCheckException("Invalid type '" + rightType + "': " + "Expected same type as left expression: " + leftType);
                    }
                    return leftType;
                } else {
                    AFSType rightType = ExprType(assignmentNode.getRightExpression());
                    if(!(rightType instanceof ListType)) {
                        throw new TypeCheckException("Invalid type '" + rightType + "': " + "Expected same type as left expression: " + leftType);
                    }
                    ListType leftListType = (ListType) leftType;
                    ListType rightListType = (ListType) rightType;
                    if(!(leftListType.equals(rightListType))) {
                        throw new TypeCheckException("Invalid type '" + rightType + "': " + "Expected same type as left expression: " + leftType);
                    }
                    return leftListType;
                }
            }
            case StmtBlockNode blockNode -> {
                return null; // TODO
            }
            case StmtCompositionNode compositionNode -> {
                AFSType leftType = StmtType(compositionNode.getLeftStatement());
                AFSType rightType = StmtType(compositionNode.getRightStatement());

                boolean validComposition = leftType.equals(rightType);

                if (!validComposition) {
                    throw new TypeCheckException("Invalid type '" + leftType + "': " + "Expected same type as right statement: " + rightType);
                }
                return leftType;
            }
            case StmtDeclarationNode declarationNode -> {
                AFSType type = TypeType(declarationNode.getType());
                TypeValidator.validatePrimitiveType(type);

                AFSType identifierType = ExprType(declarationNode.getIdentifier());
                TypeValidator.validateIdentifierType(identifierType);
                
                AFSType exprType = ExprType(declarationNode.getExpression());
                TypeValidator.validateTypeEquality(exprType, type);

                return type;
            }
            case StmtFunctionCallNode functionCallNode -> {
                AFSType functionCallType = ExprType(functionCallNode.getFunctionCall());
                TypeValidator.validateIdentifierType(functionCallType);

                return SimpleType.VOID;
            }
            case StmtReturnNode returnNode -> {
                AFSType exprType = ExprType(returnNode.getExpression());
                if (!(exprType instanceof ListType)) {
                    TypeValidator.validatePrimitiveType(exprType);
                } else {
                    // TODO
                }

                return exprType;
            }
            case StmtSkipNode skipNode -> {
                return SimpleType.VOID;
            }
            default -> throw new IllegalStatementException("Unexpected value: " + stmt);
        }
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
                    case LT, EQ -> {
                        AFSType leftType = ExprType(binopNode.getLeftExpression());
                        boolean validLeft = leftType.equals(SimpleType.INT) || leftType.equals(SimpleType.DOUBLE);

                        if (!validLeft) {
                            throw new TypeCheckException("Invalid type '" + leftType + "': " + "Expected int or double");
                        }
                        AFSType rightType = ExprType(binopNode.getRightExpression());
                        boolean validRight = rightType.equals(leftType);
                        if (!validRight) {
                            throw new TypeCheckException("Invalid type '" + rightType + "': " + "Expected int or double");
                        }
                        return SimpleType.BOOL;
                    }
                    case AND -> {
                        AFSType leftType = ExprType(binopNode.getLeftExpression());
                        TypeValidator.validBooleanType(leftType);

                        AFSType rightType = ExprType(binopNode.getRightExpression());
                        TypeValidator.validBooleanType(rightType);
                        
                        return SimpleType.BOOL;
                    }
                    default -> {
                        throw new IllegalExpressionException("Invalid expression: " + binOp);
                    }
                }
            }
            
            case ExprUnopNode unopNode -> {
                UnOp UnOp = unopNode.getUnOp();
                switch (UnOp) {
                    case NOT -> {
                        AFSType exprType = ExprType(unopNode.getExpr());
                        TypeValidator.validBooleanType(exprType);

                        return SimpleType.BOOL;
                    }
                    case NEG -> {
                        AFSType exprType = ExprType(unopNode.getExpr());
                        boolean validExpr = exprType.equals(SimpleType.INT) || exprType.equals(SimpleType.DOUBLE);

                        if (!validExpr) {
                            throw new TypeCheckException("Invalid type '" + exprType + "': " + "Expected int or double");
                        }
                        return exprType;
                    }
                    default -> {
                        throw new IllegalExpressionException("Invalid expression: " + UnOp);
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
            case TypeDoubleNode ignored -> {
                return SimpleType.DOUBLE;
            }
            case TypeStringNode ignored -> {
                return SimpleType.STRING;
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
