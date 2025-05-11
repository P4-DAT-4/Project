package afs.checker;

import afs.checker.exceptions.*;
import afs.checker.types.*;
import afs.nodes.expr.*;
import afs.nodes.stmt.*;
import afs.nodes.event.*;
import afs.nodes.type.*;
import afs.nodes.def.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeChecker {
    public final Map<String, AFSType> symbolTable = new HashMap<>();
    private final Map<String, AFSType> eventSymbolTable = new HashMap<>();

    private AFSType DeclarationType(DefNode def) {
        switch (def) {
            case DefDeclarationNode declarationNode -> { 
                AFSType type = TypeType(declarationNode.getType());
                ExprNode expr = declarationNode.getExpression();

                if (!(type instanceof ListType)) {
                    TypeValidator.validatePrimitiveType(type);
                    AFSType exprType = ExprType(expr);
                    TypeValidator.validateTypeEquality(exprType, type);
                } else {
                    validateListType(type, declarationNode.getExpression());
                }

                // Check if the identifier is already declared
                ExprIdentifierNode identifierNode = declarationNode.getIdentifier();
                String identifier = identifierNode.getIdentifier();
                if (symbolTable.containsKey(identifier)) {
                    throw new TypeCheckException("Identifier '" + identifier + "' already declared");
                } else {
                    symbolTable.put(identifier, type);
                }

                return type;
            }
            case DefFunctionNode functionNode -> {
                AFSType type = TypeType(functionNode.getType());
                if (!(type instanceof ListType)) {
                    TypeValidator.validatePrimitiveType(type);
                } else {
                    // Handle ListType validation
                }
                
                // Validate function identifier
                ExprIdentifierNode identifierNode = functionNode.getIdentifier();
                String identifier = identifierNode.getIdentifier();
                if (symbolTable.containsKey(identifier)) {
                    throw new TypeCheckException("Identifier '" + identifier + "' already declared");
                } else {
                    symbolTable.put(identifier, type);
                }

                // Validate function parameters
                Map<String, AFSType> paramSymbolTable = new HashMap<>();
                List<Param> params = functionNode.getParameters();
                for (Param param : params) {
                    AFSType paramType = TypeType(param.getType());
                    if (!(paramType instanceof ListType)) {
                        TypeValidator.validatePrimitiveType(paramType);
                    } else {
                        // Handle ListType validation
                    }

                    ExprIdentifierNode paramIdentifierNode = param.getIdentifier();
                    String paramIdentifier = paramIdentifierNode.getIdentifier();
                    if (paramSymbolTable.containsKey(paramIdentifier)) {
                        throw new TypeCheckException("Identifier '" + paramIdentifier + "' already declared in function");
                    } else {
                        paramSymbolTable.put(paramIdentifier, paramType);
                    } 
                }

                // Validate function body
                AFSType stmtType = StmtType(functionNode.getStatement());
                if (type instanceof ListType) {
                    if (!(stmtType instanceof ListType)) {
                        throw new TypeCheckException("Function body does not return a list as expected");
                    }
                    TypeValidator.validateTypeEquality(stmtType, type);
                } else {
                    TypeValidator.validateTypeEquality(stmtType, type);
                }

                return SimpleType.VOID;
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
                // Validate the left type
                AFSType leftType = EventType(compositionNode.getLeftEvent());
                TypeValidator.validBooleanType(leftType);

                // Validate the right type
                AFSType rightType = EventType(compositionNode.getRightEvent());
                TypeValidator.validBooleanType(rightType);

                return leftType;
            }
            case EventDeclarationNode declarationNode -> {
                // Validate the type
                AFSType exprType = ExprType(declarationNode.getExpression());
                TypeValidator.validBooleanType(exprType);

                // Validate the function call type
                AFSType functionCallType = ExprType(declarationNode.getFunctionCall());
                TypeValidator.validateIdentifierType(functionCallType);

                // Get the identifier
                /*String identifier = declarationNode.getIdentifier().getIdentifier();

                if (eventSymbolTable.containsKey(identifier)) {
                    throw new TypeCheckException("Identifier '" + identifier + "' not declared");
                } else {
                    eventSymbolTable.put(identifier, exprType);
                }*/
                

                // Validate the function call identifier exists
                ExprFunctionCallNode functionCall = declarationNode.getFunctionCall();
                String functionCallIdentifier = functionCall.getIdentifier().getIdentifier();
                if (!symbolTable.containsKey(functionCallIdentifier)) {
                    throw new TypeCheckException("Identifier '" + functionCallIdentifier + "' already declared");
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
                TypeValidator.validBooleanType(exprType);

                AFSType leftStmt = StmtType(ifNode.getLeftStatement());
                AFSType rightStmt = StmtType(ifNode.getRightStatement());
                TypeValidator.validateTypeEquality(rightStmt, leftStmt);

                return SimpleType.VOID;
            }
            case StmtWhileNode whileNode -> {
                AFSType exprType = ExprType(whileNode.getExpression());
                TypeValidator.validBooleanType(exprType);


                AFSType stmtType = StmtType(whileNode.getStatement());
                TypeValidator.validateVoidType(stmtType);

                return SimpleType.VOID;
            }
            case StmtAssignmentNode assignmentNode -> {
                
                // Get the left expression
                ExprNode leftExpr = assignmentNode.getLeftExpression();
                
                // Get the identifier
                String identifier = null;
                if (leftExpr instanceof ExprIdentifierNode) {
                    identifier = ((ExprIdentifierNode) leftExpr).getIdentifier();
                } else if (leftExpr instanceof ExprListAccessNode) {
                    //identifier = ((ExprListAccessNode) leftExpr).getIdentifier();
                } else {
                    throw new TypeCheckException("Invalid left expression type: " + leftExpr);
                }

                // Check if the identifier is declared
                if (!symbolTable.containsKey(identifier)) {
                    throw new TypeCheckException("Identifier '" + identifier + "' not declared");
                } 

                // The the type of the identifier
                AFSType identifierType = symbolTable.get(identifier);

                // validate the right expression type
                ExprNode rightExpr = assignmentNode.getRightExpression();
                AFSType rightType = ExprType(assignmentNode.getRightExpression());
                if (!(rightExpr instanceof ExprListAccessNode)) {
                    TypeValidator.validateTypeEquality(identifierType, rightType);
                } else {
                    // Handle ListType validation
                    //AFSType listType = ((ExprListAccessNode) rightExpr).getListType();
                }

                return rightType;
            }
            case StmtBlockNode blockNode -> {
                return null; // TODO
            }
            case StmtCompositionNode compositionNode -> {
                AFSType leftType = StmtType(compositionNode.getLeftStatement());
                AFSType rightType = StmtType(compositionNode.getRightStatement());
                TypeValidator.validateTypeEquality(rightType, leftType);
                return leftType;
            }
            case StmtDeclarationNode declarationNode -> {
                // Validate the type
                AFSType type = TypeType(declarationNode.getType());
                TypeValidator.validatePrimitiveType(type);

                // Check if the identifier is declared
                String identifier = declarationNode.getIdentifier().getIdentifier();

                if (symbolTable.containsKey(identifier)) {
                    throw new TypeCheckException("Variable '" + identifier + "' is already declared");
                } else {
                    symbolTable.put(identifier, type);
                }

                // Validate the expression
                AFSType exprType = ExprType(declarationNode.getExpression());
                TypeValidator.validateTypeEquality(exprType, type);    
                
                return type;
            }
            case StmtFunctionCallNode functionCallNode -> {
                // Get the identifier
                AFSType functionCallType = ExprType(functionCallNode.getFunctionCall());
                TypeValidator.validateIdentifierType(functionCallType);

                String identifierName = functionCallNode.getFunctionCall().getIdentifier().getIdentifier();
                // Validate the identifier
                if (!symbolTable.containsKey(identifierName)) {
                    throw new TypeCheckException("Identifier '" + identifierName + "' not declared");
                }
                
                return SimpleType.VOID;
            }
            case StmtReturnNode returnNode -> {
                AFSType exprType = ExprType(returnNode.getExpression());
    
                // Validate basic type correctness
                if (!(exprType instanceof ListType)) {
                    TypeValidator.validatePrimitiveType(exprType);
                } else {
                    // Handle ListType validation

                }
                
                // Validate against function's declared return type
                TypeValidator.validateTypeEquality(exprType, null); // TODO: Get the actual return type from the function declaration
                
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
                        TypeValidator.validateBinop(leftType);
                        
                        AFSType rightType = ExprType(binopNode.getRightExpression());
                        TypeValidator.validateBinop(rightType);
                        
                        return leftType;
                    }
                    case CONCAT -> { // Tjek denne!
                        AFSType leftType = ExprType(binopNode.getLeftExpression());
                        boolean validLeft = leftType.equals(SimpleType.STRING) || leftType.equals(SimpleType.SHAPE) || leftType instanceof ListType;

                        if (!validLeft) {
                            throw new TypeCheckException("Invalid type '" + leftType + "': " + "Expected string or shape or list");
                        }

                        AFSType rightType = ExprType(binopNode.getRightExpression());
                        TypeValidator.validateTypeEquality(rightType, leftType);
                        
                        return leftType;
                    }
                    case LT, EQ -> {
                        AFSType leftType = ExprType(binopNode.getLeftExpression());
                        TypeValidator.validateBinop(leftType);

                        AFSType rightType = ExprType(binopNode.getRightExpression());
                        TypeValidator.validateBinop(rightType);
                        
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
                        AFSType exprType = ExprType(unopNode.getExpression());
                        TypeValidator.validBooleanType(exprType);
                        return SimpleType.BOOL;
                    }
                    case NEG -> {
                        AFSType exprType = ExprType(unopNode.getExpression());
                        TypeValidator.validateBinop(exprType);
                        return exprType;
                    }
                    default -> {
                        throw new IllegalExpressionException("Invalid expression: " + UnOp);
                    }
                }
            }
            case ExprIdentifierNode identifierNode -> {
                // Validate the identifier type
                String identifier = identifierNode.getIdentifier();
                if (symbolTable.containsKey(identifier)) {
                    throw new TypeCheckException("Identifier '" + identifier + "' already declared");
                } else {
                    // TODO
                }
                AFSType identifierType = symbolTable.get(identifier);
                return identifierType;
            }
            case ExprCurveNode curveNode -> {
                // Get expressions
                List<ExprNode> expressions = curveNode.getExpressions();

                // Validate the first expression type
                ExprNode firstExpressionType = expressions.get(0);
                AFSType firstExprType = ExprType(firstExpressionType);
                TypeValidator.validateBinop(firstExprType);

                // Validate the rest of the expressions
                for (int i = 1; i < expressions.size(); i++) {
                    AFSType exprType = ExprType(expressions.get(i));
                    TypeValidator.validateBinop(exprType);
                    TypeValidator.validateTypeEquality(firstExprType, exprType);
                }
                return SimpleType.SHAPE;
            }
            case ExprFunctionCallNode FuncionCallNode -> {
                // Get the identifier
                ExprIdentifierNode identifier = FuncionCallNode.getIdentifier();
                
                // Validate the identifier
                AFSType identifierType = ExprType(identifier);
                TypeValidator.validateIdentifierType(identifierType);

                String identifierName = identifier.getIdentifier();
                if (!symbolTable.containsKey(identifierName)) {
                    throw new TypeCheckException("Identifier '" + identifierName + "' not declared");
                }
                
                // Get the function type from the symbol table
                AFSType functionType = symbolTable.get(identifierName);
                if (functionType == null) {
                    throw new TypeCheckException("Identifier '" + identifierName + "' not declared");
                }

                // Validate the function call
                List<ExprNode> arguments = FuncionCallNode.getArguments();
                for (ExprNode argument : arguments) {
                    AFSType argType = ExprType(argument);
                    TypeValidator.validateBinop(argType);
                    TypeValidator.validateTypeEquality(argType, functionType);
                }

                return SimpleType.VOID;
            }
            case ExprLineNode lineNode -> {
                List<ExprNode> expressions = lineNode.getExpressions();
                for (ExprNode expreesion : expressions) {
                    AFSType exprType = ExprType(expreesion);
                    TypeValidator.validateBinop(exprType);
                }
                return SimpleType.SHAPE;
            }
            case ExprListDeclaration listDeclarationNode -> {
                List<ExprNode> expressions = listDeclarationNode.getExpressions();
                // Get type of the first expression
                AFSType firstExprType = ExprType(expressions.get(0));
                TypeValidator.validateBinop(firstExprType);
                for (ExprNode expression : expressions) {
                    AFSType exprType = ExprType(expression);
                    TypeValidator.validateBinop(exprType);
                
                }
                
                return firstExprType;
            }
            case ExprPlaceNode pladeNode -> {
                AFSType leftType = ExprType(pladeNode.getLeftExpression());
                TypeValidator.validateShapeType(leftType);

                AFSType rightType = ExprType(pladeNode.getRightExpression());
                // TODO


                return SimpleType.VOID;
            }
            case ExprTextNode textNode -> {
                AFSType ExprType = ExprType(textNode.getExpression());
                
                // Ikke sikker på hvad type det skal være

                return null; // TODO
            }
            case ExprScaleNode scaleNode -> {
                AFSType leftType = ExprType(scaleNode.getLeftExpression());
                TypeValidator.validateShapeType(leftType);

                AFSType rightType = ExprType(scaleNode.getRightExpression());
                TypeValidator.validateBinop(rightType);

                return SimpleType.VOID;
            }
            case ExprRotateNode rotateNode -> {
                AFSType leftType = ExprType(rotateNode.getLeftExpression());
                TypeValidator.validateShapeType(leftType);

                AFSType middleType = ExprType(rotateNode.getMiddleExpression());
                TypeValidator.validateBinop(middleType);

                AFSType rightType = ExprType(rotateNode.getRightExpression());
                TypeValidator.validateShapeType(rightType);

                return SimpleType.VOID;
            }
            case ExprListAccessNode listAccessNode -> {

                return null; // TODO
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

    private void validateListType(AFSType declaredType, ExprNode expr) {
    if (!(declaredType instanceof ListType)) {
      throw new TypeCheckException("Declared type '" + declaredType + "' is not a list type");
    }
    if (!(expr instanceof ExprListDeclaration)) {
      throw new TypeCheckException("Expression '" + expr + "' is not a list declaration");
    }
    List<ExprNode> elements = ((ExprListDeclaration) expr).getExpressions();
    AFSType type = ((ListType) declaredType).getType();
    for (ExprNode element : elements) {
      AFSType actualType = ExprType(element);
      if (type instanceof ListType) {
        validateListType(type, element);

      } else {
        TypeValidator.validateTypeEquality(actualType, type);
      }
    }
  }

    // Visitor method for testing
    public AFSType processDefNode(DefNode defNode) {
        return DeclarationType(defNode);
    }
    
    public AFSType processEventNode(EventNode eventNode) {
        return EventType(eventNode);
    }
    
    public AFSType processStmtNode(StmtNode stmtNode) {
        return StmtType(stmtNode);
    }
    
    public AFSType processTypeNode(TypeNode typeNode) {
        return TypeType(typeNode);
    }
    
    public AFSType processExprNode(ExprNode exprNode) {
        return ExprType(exprNode);
    }
}
