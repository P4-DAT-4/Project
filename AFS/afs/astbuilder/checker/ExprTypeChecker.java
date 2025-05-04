package afs.astbuilder.checker;

import afs.astbuilder.checker.exceptions.*;
import afs.astbuilder.checker.types.*;
import afs.astbuilder.nodes.expr.*;
import afs.astbuilder.nodes.stmt.*;
import afs.astbuilder.nodes.event.*;
import afs.astbuilder.nodes.type.*;
import afs.astbuilder.nodes.def.*;
import java.util.List;




public class ExprTypeChecker {

    private AFSType DeclarationType(DefNode def) {
        switch (def) {
            case DefDeclarationNode declarationNode -> {
                AFSType type = TypeType(declarationNode.getType());
                boolean validType = type.equals(SimpleType.INT) || type.equals(SimpleType.DOUBLE) || type.equals(SimpleType.STRING) || type.equals(SimpleType.BOOL);

                if (!validType) {
                    throw new TypeCheckException("Invalid type '" + type + "': " + "Expected int, double, string or bool");
                }

                AFSType identifierType = ExprType(declarationNode.getIdentifier());
                boolean validIdentifier = identifierType.equals(SimpleType.STRING);
                if (!validIdentifier) {
                    throw new TypeCheckException("Invalid identifier type '" + identifierType + "': " + "Expected string");
                }
                AFSType exprType = ExprType(declarationNode.getExpression());
                boolean validExpr = exprType.equals(type);
                if (!validExpr) {
                    throw new TypeCheckException("Invalid type '" + exprType + "': " + "Expected same type as declaration: " + type);
                }
                return type;
            }
            case DefFunctionNode functionNode -> {
                AFSType type = TypeType(functionNode.getType());
                boolean validType = type.equals(SimpleType.INT) || type.equals(SimpleType.DOUBLE) || type.equals(SimpleType.STRING) || type.equals(SimpleType.BOOL);
                if (!validType) {
                    throw new TypeCheckException("Invalid type '" + type + "': " + "Expected int, double, string or bool");
                }

                AFSType identifierType = ExprType(functionNode.getIdentifier());
                boolean validIdentifier = identifierType.equals(SimpleType.STRING);
                if (!validIdentifier) {
                    throw new TypeCheckException("Invalid identifier type '" + identifierType + "': " + "Expected string");
                }

                List<Param> params = functionNode.getParameters();
                for (Param param : params) {
                    AFSType paramType = TypeType(param.getType());
                    boolean validParamType = paramType.equals(SimpleType.INT) || paramType.equals(SimpleType.DOUBLE) || paramType.equals(SimpleType.STRING) || paramType.equals(SimpleType.BOOL);
                    if (!validParamType) {
                        throw new TypeCheckException("Invalid type '" + paramType + "': " + "Expected int, double, string or bool");
                    }

                    AFSType paramIdentifierType = ExprType(param.getIdentifier());
                    boolean validParamIdentifier = paramIdentifierType.equals(SimpleType.STRING);
                    if (!validParamIdentifier) {
                        throw new TypeCheckException("Invalid identifier type '" + paramIdentifierType + "': " + "Expected string");
                    }
                }

                AFSType stmtType = StmtType(functionNode.getStatement());
                boolean validStmt = stmtType.equals(type);
                if (!validStmt) {
                    throw new TypeCheckException("Invalid type '" + stmtType + "': " + "Expected same type as function: " + type);
                }

                return type;
            }
            case DefVisualizeNode visualizeNode -> {
                AFSType functionCallType = ExprType(visualizeNode.getFunctionCall());
                boolean validFunctionCall = functionCallType.equals(SimpleType.STRING);
                if (!validFunctionCall) {
                    throw new TypeCheckException("Invalid type '" + functionCallType + "': " + "Expected string");
                }

                AFSType eventType = EventType(visualizeNode.getEvent());
                boolean validEvent = eventType.equals(SimpleType.BOOL);
                if (!validEvent) {
                    throw new TypeCheckException("Invalid type '" + eventType + "': " + "Expected bool");
                }
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
                boolean validLeft = leftType.equals(SimpleType.BOOL);
                if (!validLeft) {
                    throw new TypeCheckException("Invalid type '" + leftType + "': " + "Expected bool");
                }

                AFSType rightType = EventType(compositionNode.getRightEvent());
                boolean validRight = rightType.equals(SimpleType.BOOL);
                if (!validRight) {
                    throw new TypeCheckException("Invalid type '" + rightType + "': " + "Expected bool");
                }

                return leftType;
            }
            case EventDeclarationNode declarationNode -> {
                AFSType exprType = ExprType(declarationNode.getExpression());
                boolean validExpr = exprType.equals(SimpleType.BOOL);
                if (!validExpr) {
                    throw new TypeCheckException("Invalid type '" + exprType + "': " + "Expected bool");
                }

                AFSType functionCallType = ExprType(declarationNode.getFunctionCall());
                boolean validFunctionCall = functionCallType.equals(SimpleType.STRING);
                if (!validFunctionCall) {
                    throw new TypeCheckException("Invalid type '" + functionCallType + "': " + "Expected string");
                }
                return SimpleType.BOOL;
            }
            default -> throw new IllegalEventException("Unexpected value: " + event);
        }
    }

    private AFSType StmtType(StmtNode stmt) {
        switch(stmt) {
            case StmtIfNode ifNode -> {
                AFSType exprType = ExprType(ifNode.getExpression());
                boolean validExpr = exprType.equals(SimpleType.BOOL);
                if (!validExpr) {
                    throw new TypeCheckException("Invalid type '" + exprType + "': " + "Expected bool");
                }

                AFSType leftStmt = StmtType(ifNode.getLeftStatement());
                AFSType rightStmt = StmtType(ifNode.getRightStatement());
                boolean validLeft = leftStmt.equals(rightStmt);
                if (!validLeft) {
                    throw new TypeCheckException("Invalid type '" + leftStmt + "': " + "Expected same type as right statement: " + rightStmt);
                }
                return leftStmt;
            }
            case StmtWhileNode whileNode -> {
                AFSType exprType = ExprType(whileNode.getExpression());
                boolean validExpr = exprType.equals(SimpleType.BOOL);
                if (!validExpr) {
                    throw new TypeCheckException("Invalid type '" + exprType + "': " + "Expected bool");
                }

                AFSType stmtType = StmtType(whileNode.getStatement());
                boolean validStmt = stmtType.equals(SimpleType.VOID);
                if (!validStmt) {
                    throw new TypeCheckException("Invalid type '" + stmtType + "': " + "Expected void");
                }
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
                boolean validtype = type.equals(SimpleType.INT) || type.equals(SimpleType.DOUBLE) || type.equals(SimpleType.STRING) || type.equals(SimpleType.BOOL);
                if (!validtype) {
                    throw new TypeCheckException("Invalid type '" + type + "': " + "Expected int, double, string or bool");
                }

                AFSType identifierType = ExprType(declarationNode.getIdentifier());
                boolean validIdentifier = identifierType.equals(SimpleType.STRING);
                if (!validIdentifier) {
                    throw new TypeCheckException("Invalid identifier type '" + identifierType + "': " + "Expected string");
                }
                AFSType exprType = ExprType(declarationNode.getExpression());
                boolean validExpr = exprType.equals(type);
                if (!validExpr) {
                    throw new TypeCheckException("Invalid type '" + exprType + "': " + "Expected same type as declaration: " + type);
                }
                return type;
            }
            case StmtFunctionCallNode functionCallNode -> {
              return null; //TODO
            }
            case StmtReturnNode returnNode -> {
                AFSType exprType = ExprType(returnNode.getExpression());
                boolean validExpr = exprType.equals(SimpleType.INT) || exprType.equals(SimpleType.DOUBLE) || exprType.equals(SimpleType.STRING) || exprType.equals(SimpleType.BOOL) || exprType instanceof ListType;

                if (!validExpr) {
                    throw new TypeCheckException("Invalid type '" + exprType + "': " + "Expected int, double, string, bool or list");
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
                        boolean validLeft = leftType.equals(SimpleType.BOOL);

                        if (!validLeft) {
                            throw new TypeCheckException("Invalid type '" + leftType + "': " + "Expected bool");
                        }
                        AFSType rightType = ExprType(binopNode.getRightExpression());
                        boolean validRight = rightType.equals(leftType);
                        if (!validRight) {
                            throw new TypeCheckException("Invalid type '" + rightType + "': " + "Expected bool");
                        }
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
                        boolean validExpr = exprType.equals(SimpleType.BOOL);

                        if (!validExpr) {
                            throw new TypeCheckException("Invalid type '" + exprType + "': " + "Expected bool");
                        }
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
