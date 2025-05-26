package afs.semantic_analysis;

import afs.semantic_analysis.exceptions.*;
import afs.semantic_analysis.types.*;
import afs.nodes.expr.*;
import afs.nodes.prog.ProgNode;
import afs.nodes.stmt.*;
import afs.nodes.event.*;
import afs.nodes.type.*;
import afs.nodes.def.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TypeChecker {
    public void checkProgram(ProgNode program) {
        Environment env = new Environment();
        DeclarationType(env, program.getDefinition());
    }
    private AFSType DeclarationType(Environment env, DefNode def) {
        switch (def) {
            case DefDeclarationNode declarationNode -> {
                AFSType type = TypeType(declarationNode.getType());
                ExprNode expr = declarationNode.getExpression();

                AFSType exprType = ExprType(env, expr);
                TypeValidator.validateTypeEquality(exprType, type);

                // Check if the identifier is already declared
                String identifier = declarationNode.getIdentifier();

                // Check if the identifier is already declared
                if (env.check(identifier)) {
                    throw new TypeCheckException(String.format("Global variable '%s' already declared", identifier));
                }

                // declare the variable in the environment
                env.declare(identifier, type);

                def.setType(type);
                DeclarationType(env, declarationNode.getDefinition());

                return type;
            }
            case DefFunctionNode functionNode -> {
                AFSType type = TypeType(functionNode.getType());
                
                // Validate function identifier
                String identifier = functionNode.getIdentifier();
                if (env.check(identifier)) {
                    throw new TypeCheckException(String.format("Function '%s' already declared", identifier));
                }

                Environment funEnv = new Environment(env); // copy of env

                FunctionType functionType = new FunctionType(type);

                List<String> paramNames = new ArrayList<>();
                List<Param> params = functionNode.getParameters();
                for (Param param : params) {
                    String ident = param.getIdentifier();
                    // Check if param is already declared
                    if (paramNames.contains(ident)) {
                        throw new TypeCheckException(String.format("Duplicate parameter '%s'", ident));
                    }
                    // Add the param to the list of param names
                    paramNames.add(ident);

                    // check the type is non-void
                    AFSType paramType = TypeType(param.getType());
                    if (paramType.equals(SimpleType.VOID)) {
                        throw new TypeCheckException("Cannot declare parameter of type void");
                    }
                    // declare the param the in the new environment
                    funEnv.declare(param.getIdentifier(), paramType);
                    functionType.addParamType(paramType);
                }

                funEnv.declare(identifier, functionType);
                funEnv.declare("return", functionType.getReturnType());
                env.declare(identifier, functionType);

                StmtType(funEnv, functionNode.getStatement());
                def.setType(type);

                DeclarationType(env, functionNode.getDefinition());
                return type;
            }
            case DefVisualizeNode visualizeNode -> {
                // Lookup function
                String identifier = visualizeNode.getIdentifier();

                AFSType type = env.lookup(identifier);

                if (!(type instanceof FunctionType funType)) {
                    throw new TypeCheckException(String.format("Cannot visualize function '%s' that is not a function.", identifier));
                }
                List<ExprNode> args = visualizeNode.getArguments();

                // Check if the function is valid
                checkFunction(env, identifier, funType, args);

                EventType(env, visualizeNode.getEvent()); // check all events

                return null;
            }
            default -> {
                throw new IllegalDefException("Unexpected value: " + def);
            }
        }
    }
    private AFSType EventType(Environment env ,EventNode event) {
        switch(event) {
            case EventCompositionNode compositionNode -> {
                // Check both events
                EventType(env, compositionNode.getLeftEvent());
                EventType(env, compositionNode.getRightEvent());
            }
            case EventDeclarationNode declarationNode -> {
                // Validate the type
                String conIdent = declarationNode.getIdent(); // condition
                String funIdent = declarationNode.getFunIdent(); // fun identifier
                env.lookup(conIdent);

                // Check if the function is valid
                AFSType type = env.lookup(funIdent);
                if (!(type instanceof FunctionType funType)) {
                    throw new TypeCheckException(String.format("Cannot visualize '%s' that is not a function.", funIdent));
                }
                checkFunction(env, funIdent, funType, declarationNode.getArguments());
            }
            default -> throw new IllegalEventException("Unexpected value: " + event);
        }
        return null;
    }
    private AFSType StmtType(Environment env, StmtNode stmt) {
        switch(stmt) {
            case StmtIfNode ifNode -> {
                AFSType exprType = ExprType(env, ifNode.getExpression());
                TypeValidator.validBooleanType(exprType);

                Environment rightEnv = new Environment(env); // copy of env
                StmtType(rightEnv, ifNode.getLeftStatement());

                Environment leftEnv = new Environment(env); // copy of env
                StmtType(leftEnv, ifNode.getRightStatement());

                return null;
            }
            case StmtWhileNode whileNode -> {
                AFSType exprType = ExprType(env, whileNode.getExpression());
                TypeValidator.validBooleanType(exprType);

                Environment whileEnv = new Environment(env); // copy of env
                StmtType(whileEnv, whileNode.getStatement());

                return null;
            }
            case StmtAssignmentNode assignmentNode -> {
                String identifier = assignmentNode.getIdentifier();
                AFSType varType = env.lookup(identifier);

                ExprNode Expr = assignmentNode.getExpression();
                AFSType ExprType = ExprType(env, Expr);
                TypeValidator.validateTypeEquality(ExprType, varType);

                assignmentNode.setType(Expr.getAFSType());
                return Expr.getAFSType();
            }
            case StmtDeclarationNode declarationNode -> {
                TypeNode type = declarationNode.getType();

                ExprNode expr = declarationNode.getExpression();
                AFSType exprType = ExprType(env, expr);

                TypeValidator.validateTypeEquality(exprType, TypeType(type));

                String identifier = declarationNode.getIdentifier();

                Environment newEnv = new Environment(env); // copy of env
                newEnv.declare(identifier, exprType);
                StmtType(newEnv, declarationNode.getStatement());

                declarationNode.setType(exprType);
                return exprType;
            }
            case StmtCompositionNode compositionNode -> {
                Environment leftEnv = new Environment(env); // copy of env
                StmtType(leftEnv, compositionNode.getLeftStatement());

                Environment rightEnv = new Environment(env); // copy of env
                StmtType(rightEnv, compositionNode.getRightStatement());

                return null;
            }
            case StmtFunctionCallNode functionCallNode -> {
                // Get the identifier
                String identifier = functionCallNode.getIdentifier();

                AFSType type = env.lookup(identifier);
                if (!(type instanceof FunctionType funType)) {
                    throw new TypeCheckException(String.format("Cannot call '%s' that is not a function", identifier));
                }

                // Get the args
                List<ExprNode> args = functionCallNode.getArguments();

                // Check the call is valid.
                checkFunction(env, identifier, funType, args);

                return null;
            }
            case StmtReturnNode returnNode -> {
                AFSType exprType = ExprType(env, returnNode.getExpression());
                AFSType funRetType = env.lookup("return");

                // Validate the return type
                TypeValidator.validateTypeEquality(exprType, funRetType);

                // Check the return is not null
                if (funRetType.equals(SimpleType.VOID)) {
                    throw new TypeCheckException(String.format("Cannot return type void at line %d, col %d", returnNode.getLineNumber(), returnNode.getColumnNumber()));
                }

                returnNode.setType(exprType);
                return exprType;
            }
            case StmtListAssignmentNode ListAssignmentNode -> {
                String identifier = ListAssignmentNode.getIdentifier();
                List<ExprNode> expressions = ListAssignmentNode.getExpressions(); // x[1][2][3]
                ExprNode rightExpression = ListAssignmentNode.getExpression();

                AFSType leftType = ExprType(env, new ExprListAccessNode(
                        identifier, expressions, ListAssignmentNode.getLineNumber(),
                        ListAssignmentNode.getColumnNumber())
                );

                AFSType rightType = ExprType(env, rightExpression);

                TypeValidator.validateTypeEquality(leftType, rightType);

                ListAssignmentNode.setType(leftType);
                return leftType;
            }
            case StmtSkipNode ignored -> {
                return null;
            }
            default -> throw new IllegalStatementException("Unexpected value: " + stmt);
        }
    }
    private AFSType ExprType(Environment env, ExprNode expr) {
        switch(expr) {
            case ExprBinopNode binopNode -> {
                BinOp binOp = binopNode.getOp();
                switch (binOp) {
                    case ADD, SUB, MUL, DIV -> {
                        AFSType leftType = ExprType(env, binopNode.getLeftExpression());
                        TypeValidator.validateBinop(leftType);
                        
                        AFSType rightType = ExprType(env, binopNode.getRightExpression());
                        TypeValidator.validateTypeEquality(leftType, rightType);

                        binopNode.setType(leftType);
                        return leftType;
                    }
                    case CONCAT -> {
                        AFSType leftType = ExprType(env, binopNode.getLeftExpression());
                        boolean validLeft = leftType.equals(SimpleType.STRING) || leftType.equals(SimpleType.SHAPE) || leftType instanceof ListType;

                        if (!validLeft) {
                            throw new TypeCheckException("Invalid type '" + leftType + "': " + "Expected string or shape or list");
                        }

                        AFSType rightType = ExprType(env, binopNode.getRightExpression());
                        TypeValidator.validateTypeEquality(rightType, leftType);

                        binopNode.setType(leftType);
                        return leftType;
                    }
                    case LT -> {
                        AFSType leftType = ExprType(env, binopNode.getLeftExpression());
                        TypeValidator.validateBinop(leftType);

                        AFSType rightType = ExprType(env, binopNode.getRightExpression());
                        TypeValidator.validateTypeEquality(leftType, rightType);

                        binopNode.setType(SimpleType.BOOL);
                        return SimpleType.BOOL;
                    }
                    case EQ -> {
                        AFSType leftType = ExprType(env, binopNode.getLeftExpression());
                        boolean validLeft = leftType.equals(SimpleType.INT) || leftType.equals(SimpleType.DOUBLE) || leftType.equals(SimpleType.STRING) || leftType.equals(SimpleType.BOOL);

                        if (!validLeft) {
                            throw new TypeCheckException("Invalid type '" + leftType + "': " + "Expected int, double, string or boolean");
                        }

                        AFSType rightType = ExprType(env, binopNode.getRightExpression());
                        TypeValidator.validateTypeEquality(leftType, rightType);

                        binopNode.setType(SimpleType.BOOL);
                        return SimpleType.BOOL;
                    }
                    case AND -> {
                        AFSType leftType = ExprType(env, binopNode.getLeftExpression());
                        TypeValidator.validBooleanType(leftType);

                        AFSType rightType = ExprType(env, binopNode.getRightExpression());
                        TypeValidator.validBooleanType(rightType);

                        binopNode.setType(SimpleType.BOOL);
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
                        AFSType exprType = ExprType(env, unopNode.getExpression());
                        TypeValidator.validBooleanType(exprType);

                        unopNode.setType(SimpleType.BOOL);
                        return SimpleType.BOOL;
                    }
                    case NEG -> {
                        AFSType exprType = ExprType(env, unopNode.getExpression());
                        TypeValidator.validateBinop(exprType);

                        unopNode.setType(exprType);
                        return exprType;
                    }
                    default -> {
                        throw new IllegalExpressionException("Invalid expression: " + UnOp);
                    }
                }
            }
            case ExprIdentifierNode identifierNode -> {
                String identifier = identifierNode.getIdentifier();

                AFSType varType = env.lookup(identifier);

                identifierNode.setType(varType);
                return varType;
            }
            case ExprCurveNode curveNode -> {
                // Get expressions
                List<ExprNode> expressions = curveNode.getExpressions();

                if (expressions.size() < 6) {
                    throw new TypeCheckException("Curve must have at least 3 points");
                } else if (expressions.size() % 4 != 2) {
                    throw new TypeCheckException("Curve must have three points for the first curve, and two points for every curve thereafter.");
                }

                // Validate the expressions
                for (int i = 1; i < expressions.size(); i++) {
                    AFSType exprType = ExprType(env, expressions.get(i));
                    TypeValidator.validateDoubleType(exprType);
                }

                curveNode.setType(SimpleType.SHAPE);
                return SimpleType.SHAPE;
            }
            case ExprFunctionCallNode FunctionCallNode -> {
                // Get the identifier
                String identifier = FunctionCallNode.getIdentifier();
                
                AFSType type = env.lookup(identifier);
                if (!(type instanceof FunctionType funType)) {
                    throw new TypeCheckException(String.format("Cannot call '%s' that is not a function", identifier));
                }

                List<ExprNode> args = FunctionCallNode.getArguments();

                checkFunction(env, identifier, funType, args);

                AFSType returnType = funType.getReturnType();

                FunctionCallNode.setType(returnType);
                return returnType;
            }
            case ExprLineNode lineNode -> {
                List<ExprNode> expressions = lineNode.getExpressions();

                if (expressions.size() < 4) {
                    throw new TypeCheckException("Line must have at least 2 points");
                } else if (expressions.size() % 2 != 0) {
                    throw new TypeCheckException("Cannot have half a point in a line");
                }

                for (ExprNode expression : expressions) {
                    AFSType exprType = ExprType(env, expression);
                    TypeValidator.validateDoubleType(exprType);
                }

                lineNode.setType(SimpleType.SHAPE);
                return SimpleType.SHAPE;
            }
            case ExprListDeclaration ListDeclarationNode -> {

                List<ExprNode> expressions = ListDeclarationNode.getExpressions();

                AFSType firstExprType = ExprType(env, expressions.getFirst());

                for (ExprNode expression : expressions) {
                    AFSType exprType = ExprType(env, expression);
                    TypeValidator.validateTypeEquality(exprType, firstExprType);
                }

                AFSType listType = new ListType(firstExprType);
                ListDeclarationNode.setType(listType);
                return listType;
            }
            case ExprPlaceNode PlaceNode -> {
                AFSType leftType = ExprType(env, PlaceNode.getFExpression());
                TypeValidator.validateShapeType(leftType);

                AFSType middleType = ExprType(env, PlaceNode.getSExpression());
                TypeValidator.validateDoubleType(middleType);

                AFSType rightType = ExprType(env, PlaceNode.getFExpression());
                TypeValidator.validateDoubleType(rightType);

                PlaceNode.setType(SimpleType.SHAPE);
                return SimpleType.SHAPE;
            }
            case ExprTextNode TextNode -> {
                AFSType ExprType = ExprType(env, TextNode.getExpression());
                TypeValidator.validateStringType(ExprType);

                TextNode.setType(SimpleType.SHAPE);
                return SimpleType.SHAPE;
            }
            case ExprScaleNode scaleNode -> {
                AFSType leftType = ExprType(env, scaleNode.getLeftExpression());
                TypeValidator.validateShapeType(leftType);

                AFSType rightType = ExprType(env, scaleNode.getRightExpression());
                TypeValidator.validateDoubleType(rightType);

                scaleNode.setType(SimpleType.SHAPE);
                return SimpleType.SHAPE;
            }
            case ExprRotateNode RotateNode -> {
                AFSType leftType = ExprType(env, RotateNode.getFirstExpression());
                TypeValidator.validateShapeType(leftType);

                AFSType secondType = ExprType(env, RotateNode.getSecondExpression());
                if (secondType instanceof ListType) {
                    secondType = ((ListType) secondType).getType();
                    TypeValidator.validateDoubleType(secondType);
                } else if (!(secondType.equals(SimpleType.SHAPE))) {
                    throw new TypeCheckException("Invalid type '" + secondType + "': " + "Expected list of doubles or shape");
                }

                AFSType thirdType = ExprType(env, RotateNode.getThirdExpression());
                if (thirdType instanceof ListType) {
                    thirdType = ((ListType) thirdType).getType();
                    TypeValidator.validateDoubleType(thirdType);
                } else if (!(thirdType.equals(SimpleType.SHAPE))) {
                    throw new TypeCheckException("Invalid type '" + thirdType + "': " + "Expected list of doubles or shape");
                }

                AFSType rightType = ExprType(env, RotateNode.getLastExpression());
                TypeValidator.validateDoubleType(rightType);

                RotateNode.setType(SimpleType.SHAPE);
                return SimpleType.SHAPE;
            }
            case ExprListAccessNode listAccessNode -> {
                String identifier = listAccessNode.getIdentifier();
                List<ExprNode> expressions = listAccessNode.getExpressions();

                // Validate int type for the first expression
                AFSType exprType = ExprType(env, expressions.getFirst());
                TypeValidator.validateIntType(exprType);

                // Lookup the variable in the environment
                AFSType varType = env.lookup(identifier);
                if (!(varType instanceof ListType)) {
                    throw new TypeCheckException(String.format("Cannot access '%s' which is not a list", identifier));
                }

                // Base case: Single access
                if (expressions.size() == 1) {
                    listAccessNode.setType(((ListType) varType).getType());
                    return ((ListType) varType).getType();
                } else { // Recursive case: Multi-dimensional access
                    int line = listAccessNode.getLineNumber();
                    int col = listAccessNode.getColumnNumber();

                    ExprNode listAccess = new ExprListAccessNode(identifier, expressions.subList(1, expressions.size()), line, col);
                    AFSType listAccessType = ExprType(env, listAccess);

                    if (!(listAccessType instanceof ListType)) {
                        throw new TypeCheckException(String.format("Cannot access index on '%s' that is not a list", identifier));
                    }

                    AFSType resType = ((ListType) listAccessType).getType();
                    listAccessNode.setType(resType);
                    return resType;
                }
            }
            case ExprStringNode ignored -> {
                expr.setType(SimpleType.STRING);
                return SimpleType.STRING;
            }
            case ExprIntNode ignored -> {
                expr.setType(SimpleType.INT);
                return SimpleType.INT;
            }
            case ExprDoubleNode ignored -> {
                expr.setType(SimpleType.DOUBLE);
                return SimpleType.DOUBLE;
            }
            case ExprBoolNode ignored -> {
                expr.setType(SimpleType.BOOL);
                return SimpleType.BOOL;
            }
            default -> throw new IllegalStateException("Unexpected value: " + expr);
        }
    }
    private AFSType TypeType(TypeNode type) {
        switch(type) {
            case TypeBoolNode ignored -> {
                type.setType(SimpleType.BOOL);
                return SimpleType.BOOL;
            }
            case TypeIntNode ignored -> {
                type.setType(SimpleType.INT);
                return SimpleType.INT;
            }
            case TypeDoubleNode ignored -> {
                type.setType(SimpleType.DOUBLE);
                return SimpleType.DOUBLE;
            }
            case TypeStringNode ignored -> {
                type.setType(SimpleType.STRING);
                return SimpleType.STRING;
            }
            case TypeShapeNode ignored -> {
                type.setType(SimpleType.SHAPE);
                return SimpleType.SHAPE;
            }
            case TypeVoidNode ignored -> {
                type.setType(SimpleType.VOID);
                return SimpleType.VOID;
            }
            case TypeListNode listNode -> {
                AFSType listType = new ListType(TypeType(listNode.getType()));
                listNode.setType(listType);
                return listType;
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    /**
     * Checks if the function call is valid.
     * @param identifier
     * @param funType
     * @param arguments
     */
    private void checkFunction(Environment env, String identifier, FunctionType funType, List<ExprNode> arguments) {
        int numArgs = arguments.size();
        int numParams = funType.getParamTypes().size();
        boolean equalSize = numArgs == numParams;
        if (!equalSize) {
            throw new TypeCheckException(String.format("Function '%s' expected %d arguments, but got %d", identifier, numParams, numArgs));
        }

        List<AFSType> paramTypes = funType.getParamTypes();
        for (int i = 0; i < arguments.size(); i++) {
            AFSType argType = ExprType(env, arguments.get(i));
            AFSType paramType = paramTypes.get(i);
            TypeValidator.validateTypeEquality(argType, paramType);
        }
    }

    // Visitors for testing
    public AFSType processDefNode(Environment env, DefNode defNode) {
        return DeclarationType(env, defNode);
    }
    public AFSType processEventNode(Environment env, EventNode eventNode) {
        return EventType(env, eventNode);
    }
    public AFSType processStmtNode(Environment env, StmtNode stmtNode) {
        return StmtType(env, stmtNode);
    }
    public AFSType processTypeNode(TypeNode typeNode) {
        return TypeType(typeNode);
    }
    public AFSType processExprNode(Environment env, ExprNode exprNode) {
        return ExprType(env, exprNode);
    }
}