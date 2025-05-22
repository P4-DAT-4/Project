//package afs.semantic_analysis;
//
//import afs.semantic_analysis.exceptions.*;
//import afs.semantic_analysis.types.*;
//import afs.nodes.expr.*;
//import afs.nodes.prog.ProgNode;
//import afs.nodes.stmt.*;
//import afs.nodes.event.*;
//import afs.nodes.type.*;
//import afs.nodes.def.*;
//
//import java.util.List;
//
//public class TypeChecker {
//    public void checkProgram(ProgNode program) {
//        DeclarationType(program.getDefinition());
//    }
//
//    private AFSType DeclarationType(DefNode def) {
//        switch (def) {
//            case DefDeclarationNode declarationNode -> {
//                AFSType type = TypeType(declarationNode.getType());
//                ExprNode expr = declarationNode.getExpression();
//
//                AFSType exprType = ExprType(expr);
//                TypeValidator.validateTypeEquality(exprType, type);
//
//                // Check if the identifier is already declared
//                String identifier = declarationNode.getIdentifier();
//                TypeEnvironment.declareVar(identifier, type);
//
//                def.setType(type);
//
//                DeclarationType(declarationNode.getDefinition());
//                return type;
//            }
//            case DefFunctionNode functionNode -> { // ok
//                AFSType type = TypeType(functionNode.getType());
//                if (!(type instanceof ListType) && !(type.equals(SimpleType.VOID)) && !(type.equals(SimpleType.SHAPE))) {
//                    TypeValidator.validatePrimitiveType(type);
//                }
//
//                // Validate function identifier
//                String identifier = functionNode.getIdentifier();
//                FunctionType functionType = new FunctionType(type);
//
//                List<Param> params = functionNode.getParameters();
//                for (Param param : params) {
//                    AFSType paramType = TypeType(param.getType());
//                    if (!(paramType instanceof ListType)) {
//                        TypeValidator.validatePrimitiveType(paramType);
//                    }
//                    functionType.addParam(param);
//                }
//
//                TypeEnvironment.declareFun(identifier, functionType);
//
//                TypeEnvironment.enterFunction(identifier);
//                StmtType(functionNode.getStatement());
//                TypeEnvironment.exitFunction();
//
//                def.setType(type);
//
//
//                DeclarationType(functionNode.getDefinition());
//                return type;
//            }
//            case DefVisualizeNode visualizeNode -> {
//                // Lookup function
//                String identifier = visualizeNode.getIdentifier();
//                FunctionType funType = TypeEnvironment.lookupFun(identifier);
//
//                if (funType.getReturnType() == SimpleType.SHAPE) {
//                    throw new TypeCheckException(String.format("Cannot visualize img '%s' that returns a shape.", identifier));
//                }
//
//                checkFunction(identifier, visualizeNode.getArguments(), funType.getParams());
//
//                EventType(visualizeNode.getEvent()); // check all events
//
//                def.setType(SimpleType.VOID);
//                return SimpleType.VOID;
//            }
//            default -> {
//                throw new IllegalDefException("Unexpected value: " + def);
//            }
//        }
//    }
//
//    private AFSType EventType(EventNode event) {
//        switch(event) {
//            case EventCompositionNode compositionNode -> {
//                // Check both events
//                EventType(compositionNode.getLeftEvent());
//                EventType(compositionNode.getRightEvent());
//
//                compositionNode.setType(SimpleType.VOID);
//                return SimpleType.VOID;
//            }
//            // e do x(arrow(e))
//            case EventDeclarationNode declarationNode -> {
//                // Validate the type
//                ExprNode expr = declarationNode.getExpression();
//                ExprType(expr);
//
//                if (!(expr instanceof ExprFunctionCallNode) && !(expr instanceof ExprIdentifierNode)) {
//                    AFSType exprType = ExprType(declarationNode.getExpression());
//                    TypeValidator.validBooleanType(exprType);
//                }
//
//                // Validate the function call type
//                String funIdentifier = declarationNode.getIdentifier();
//                FunctionType funType = TypeEnvironment.lookupFun(funIdentifier);
//
//                checkFunction(funIdentifier, declarationNode.getArguments(), funType.getParams());
//
//                declarationNode.setType(expr.getAFSType());
//                return expr.getAFSType();
//            }
//            default -> throw new IllegalEventException("Unexpected value: " + event);
//        }
//    }
//
//    private AFSType StmtType(StmtNode stmt) {
//        switch(stmt) {
//            case StmtIfNode ifNode -> {
//                AFSType exprType = ExprType(ifNode.getExpression());
//                TypeValidator.validBooleanType(exprType);
//
//                TypeEnvironment.enterScope();
//                StmtType(ifNode.getLeftStatement());
//                TypeEnvironment.exitScope();
//
//                TypeEnvironment.enterScope();
//                StmtType(ifNode.getRightStatement());
//                TypeEnvironment.exitScope();
//
//                ifNode.setType(SimpleType.VOID);
//                return SimpleType.VOID;
//            }
//            case StmtWhileNode whileNode -> {
//                AFSType exprType = ExprType(whileNode.getExpression());
//                TypeValidator.validBooleanType(exprType);
//
//                TypeEnvironment.enterScope();
//                StmtType(whileNode.getStatement());
//                TypeEnvironment.exitScope();
//
//                whileNode.setType(SimpleType.VOID);
//                return SimpleType.VOID;
//            }
//            case StmtAssignmentNode assignmentNode -> {
//                String identifier = assignmentNode.getIdentifier();
//                AFSType varType = TypeEnvironment.lookupVar(identifier);
//
//                ExprNode Expr = assignmentNode.getExpression();
//                AFSType ExprType = ExprType(Expr);
//                TypeValidator.validateTypeEquality(ExprType, varType);
//
//                assignmentNode.setType(Expr.getAFSType());
//                return Expr.getAFSType();
//            }
//            case StmtDeclarationNode declarationNode -> {
//                TypeNode type = declarationNode.getType();
//
//                ExprNode expr = declarationNode.getExpression();
//                AFSType exprType = ExprType(expr);
//
//                TypeValidator.validateTypeEquality(exprType, TypeType(type));
//
//                String identifier = declarationNode.getIdentifier();
//
//                TypeEnvironment.enterScope();
//                TypeEnvironment.declareVar(identifier, exprType);
//                StmtType(declarationNode.getStatement());
//                TypeEnvironment.exitScope();
//
//                declarationNode.setType(exprType);
//                return exprType;
//            }
//            case StmtCompositionNode compositionNode -> {
//                StmtType(compositionNode.getLeftStatement());
//                StmtType(compositionNode.getRightStatement());
//
//                compositionNode.setType(SimpleType.VOID);
//                return SimpleType.VOID;
//            }
//            case StmtFunctionCallNode functionCallNode -> {
//                // Get the identifier
//                String identifier = functionCallNode.getIdentifier();
//
//                // Get the return type of the function
//                FunctionType funType = TypeEnvironment.lookupFun(identifier);
//
//                // Check type if different from SHAPE
//                if (funType.getReturnType() == SimpleType.SHAPE) {
//                    throw new TypeCheckException(String.format("Cannot call function '%s' that returns a shape as an imperative statement.", identifier));
//                }
//
//                checkFunction(identifier, functionCallNode.getArguments(), funType.getParams());
//
//                functionCallNode.setType(funType.getReturnType());
//                return funType.getReturnType();
//            }
//            case StmtReturnNode returnNode -> {
//                AFSType exprType = ExprType(returnNode.getExpression());
//
//                String funIdentifier = TypeEnvironment.getCurrentFunction();
//                FunctionType function = TypeEnvironment.lookupFun(funIdentifier);
//
//                // Validate against function's declared return type
//                TypeValidator.validateTypeEquality(exprType, function.getReturnType());
//
//                returnNode.setType(exprType);
//                return exprType;
//            }
//            // x[arrow(e)] = e - x[1][2][3] = 2;
//            case StmtListAssignmentNode ListAssignmentNode -> {
//                String identifier = ListAssignmentNode.getIdentifier();
//                AFSType varType = TypeEnvironment.lookupVar(identifier);
//
//                List<ExprNode> expressions = ListAssignmentNode.getExpressions(); // x[1][2][3]
//                for (ExprNode expression : expressions) {
//                    if (!(varType instanceof ListType)) {
//                        throw new TypeCheckException(String.format("Cannot access index on '%s' that is not a list.", identifier));
//                    }
//                    varType = ((ListType) varType).getType();
//                    TypeValidator.validateIntType(ExprType(expression));
//                }
//
//                ExprNode expr = ListAssignmentNode.getExpression();
//                AFSType exprType = ExprType(expr);
//
//                TypeValidator.validateTypeEquality(exprType, varType);
//
//                ListAssignmentNode.setType(exprType);
//                return exprType;
//            }
//            case StmtSkipNode skipNode -> {
//                skipNode.setType(SimpleType.VOID);
//                return SimpleType.VOID;
//            }
//            default -> throw new IllegalStatementException("Unexpected value: " + stmt);
//        }
//    }
//
//    private AFSType ExprType(ExprNode expr) {
//        switch(expr) {
//            case ExprBinopNode binopNode -> {
//                BinOp binOp = binopNode.getOp();
//                switch (binOp) {
//                    case ADD, SUB, MUL, DIV -> {
//                        AFSType leftType = ExprType(binopNode.getLeftExpression());
//                        TypeValidator.validateBinop(leftType);
//
//                        AFSType rightType = ExprType(binopNode.getRightExpression());
//                        TypeValidator.validateTypeEquality(leftType, rightType);
//
//                        binopNode.setType(leftType);
//                        return leftType;
//                    }
//                    case CONCAT -> {
//                        AFSType leftType = ExprType(binopNode.getLeftExpression());
//                        boolean validLeft = leftType.equals(SimpleType.STRING) || leftType.equals(SimpleType.SHAPE) || leftType instanceof ListType;
//
//                        if (!validLeft) {
//                            throw new TypeCheckException("Invalid type '" + leftType + "': " + "Expected string or shape or list");
//                        }
//
//                        AFSType rightType = ExprType(binopNode.getRightExpression());
//                        TypeValidator.validateTypeEquality(rightType, leftType);
//
//                        binopNode.setType(leftType);
//                        return leftType;
//                    }
//                    case LT -> {
//                        AFSType leftType = ExprType(binopNode.getLeftExpression());
//                        TypeValidator.validateBinop(leftType);
//
//                        AFSType rightType = ExprType(binopNode.getRightExpression());
//                        TypeValidator.validateTypeEquality(leftType, rightType);
//
//                        binopNode.setType(SimpleType.BOOL);
//                        return SimpleType.BOOL;
//                    }
//                    case EQ -> {
//                        // int, double, string, boolean
//                        AFSType leftType = ExprType(binopNode.getLeftExpression());
//                        boolean validLeft = leftType.equals(SimpleType.INT) || leftType.equals(SimpleType.DOUBLE) || leftType.equals(SimpleType.STRING) || leftType.equals(SimpleType.BOOL);
//
//                        if (!validLeft) {
//                            throw new TypeCheckException("Invalid type '" + leftType + "': " + "Expected int, double, string or boolean");
//                        }
//
//                        AFSType rightType = ExprType(binopNode.getRightExpression());
//                        TypeValidator.validateTypeEquality(leftType, rightType);
//
//                        binopNode.setType(SimpleType.BOOL);
//                        return SimpleType.BOOL;
//                    }
//                    case AND -> {
//                        AFSType leftType = ExprType(binopNode.getLeftExpression());
//                        TypeValidator.validBooleanType(leftType);
//
//                        AFSType rightType = ExprType(binopNode.getRightExpression());
//                        TypeValidator.validBooleanType(rightType);
//
//                        binopNode.setType(SimpleType.BOOL);
//                        return SimpleType.BOOL;
//                    }
//                    default -> {
//                        throw new IllegalExpressionException("Invalid expression: " + binOp);
//                    }
//                }
//            }
//
//            case ExprUnopNode unopNode -> {
//                UnOp UnOp = unopNode.getUnOp();
//                switch (UnOp) {
//                    case NOT -> {
//                        AFSType exprType = ExprType(unopNode.getExpression());
//                        TypeValidator.validBooleanType(exprType);
//
//                        unopNode.setType(SimpleType.BOOL);
//                        return SimpleType.BOOL;
//                    }
//                    case NEG -> {
//                        AFSType exprType = ExprType(unopNode.getExpression());
//                        TypeValidator.validateBinop(exprType);
//
//                        unopNode.setType(exprType);
//                        return exprType;
//                    }
//                    default -> {
//                        throw new IllegalExpressionException("Invalid expression: " + UnOp);
//                    }
//                }
//            }
//            case ExprIdentifierNode identifierNode -> {
//                String identifier = identifierNode.getIdentifier();
//
//                AFSType varType = TypeEnvironment.lookupVar(identifier);
//
//                identifierNode.setType(varType);
//                return varType;
//            }
//            // curve [e_1, ... , e_n] - n >= 6, n mod 2 = 0
//            // curve (1,2) (3,4), (5, 6), (7, 8), (9, 10)
//            // curve A, B, C, D, E : kurve fra A til C med B som kontrolpunkt, og en kurve fra C til E med D som kontrolpunkt
//            case ExprCurveNode curveNode -> {
//                // Get expressions
//                List<ExprNode> expressions = curveNode.getExpressions();
//
//                if (expressions.size() < 6) {
//                    throw new TypeCheckException("Curve must have at least 3 points");
//                } else if (expressions.size() % 4 != 2) {
//                    throw new TypeCheckException("Curve must have three points for the first curve, and two points for every curve thereafter.");
//                }
//
//                // Validate the expressions
//                for (int i = 1; i < expressions.size(); i++) {
//                    AFSType exprType = ExprType(expressions.get(i));
//                    TypeValidator.validateDoubleType(exprType);
//                }
//
//                curveNode.setType(SimpleType.SHAPE);
//                return SimpleType.SHAPE;
//            }
//            case ExprFunctionCallNode FunctionCallNode -> {
//                // Get the identifier
//                String identifier = FunctionCallNode.getIdentifier();
//
//                FunctionType function = TypeEnvironment.lookupFun(identifier);
//
//                checkFunction(identifier, FunctionCallNode.getArguments(), function.getParams());
//
//                AFSType returnType = function.getReturnType();
//
//                FunctionCallNode.setType(returnType);
//                return returnType;
//            }
//            case ExprLineNode lineNode -> {
//                List<ExprNode> expressions = lineNode.getExpressions();
//
//                if (expressions.size() < 4) {
//                    throw new TypeCheckException("Line must have at least 2 points");
//                } else if (expressions.size() % 2 != 0) {
//                    throw new TypeCheckException("Cannot have half a point in a line");
//                }
//
//                for (ExprNode expreesion : expressions) {
//                    AFSType exprType = ExprType(expreesion);
//                    TypeValidator.validateDoubleType(exprType);
//                }
//
//                lineNode.setType(SimpleType.SHAPE);
//                return SimpleType.SHAPE;
//            }
//            case ExprListDeclaration ListDeclarationNode -> {
//
//                List<ExprNode> expressions = ListDeclarationNode.getExpressions();
//
//                AFSType firstExprType = ExprType(expressions.getFirst());
//
//                for (ExprNode expression : expressions) {
//                    AFSType exprType = ExprType(expression);
//                    TypeValidator.validateTypeEquality(exprType, firstExprType);
//
//                }
//
//                AFSType listType = new ListType(firstExprType);
//                ListDeclarationNode.setType(listType);
//                return listType;
//            }
//            // place e1 at (e2, e3)
//            case ExprPlaceNode PlaceNode -> {
//                AFSType leftType = ExprType(PlaceNode.getLeftExpression());
//                TypeValidator.validateShapeType(leftType);
//
//                AFSType middleType = ExprType(PlaceNode.getMiddleExpression());
//                TypeValidator.validateDoubleType(middleType);
//
//                AFSType rightType = ExprType(PlaceNode.getRightExpression());
//                TypeValidator.validateDoubleType(rightType);
//
//                PlaceNode.setType(SimpleType.SHAPE);
//                return SimpleType.SHAPE;
//            }
//            case ExprTextNode TextNode -> {
//                AFSType ExprType = ExprType(TextNode.getExpression());
//                TypeValidator.validateStringType(ExprType);
//
//                TextNode.setType(SimpleType.SHAPE);
//                return SimpleType.SHAPE;
//            }
//            case ExprScaleNode scaleNode -> {
//                AFSType leftType = ExprType(scaleNode.getLeftExpression());
//                TypeValidator.validateShapeType(leftType);
//
//                AFSType rightType = ExprType(scaleNode.getRightExpression());
//                TypeValidator.validateDoubleType(rightType);
//
//                scaleNode.setType(SimpleType.SHAPE);
//                return SimpleType.SHAPE;
//            }
//            case ExprRotateNode RotateNode -> {
//                AFSType leftType = ExprType(RotateNode.getLeftExpression());
//                TypeValidator.validateShapeType(leftType);
//
//                AFSType middleType = ExprType(RotateNode.getMiddleExpression());
//                if (middleType instanceof ListType) {
//                    middleType = ((ListType) middleType).getType();
//                    TypeValidator.validateDoubleType(middleType);
//                } else if (!(middleType.equals(SimpleType.SHAPE))) {
//                    throw new TypeCheckException("Invalid type '" + middleType + "': " + "Expected list of doubles or shape");
//                }
//
//                AFSType rightType = ExprType(RotateNode.getRightExpression());
//                TypeValidator.validateDoubleType(rightType);
//
//                RotateNode.setType(SimpleType.SHAPE);
//                return SimpleType.SHAPE;
//            }
//            case ExprListAccessNode listAccessNode -> {
//                String identifier = listAccessNode.getIdentifier();
//                AFSType varType = TypeEnvironment.lookupVar(identifier);
//
//                List<ExprNode> expressions = listAccessNode.getExpressions();
//                for (ExprNode expression : expressions) {
//                    if (!(varType instanceof ListType)) {
//                        throw new TypeCheckException(String.format("Cannot access index on '%s' that is not a list.", identifier));
//                    }
//                    varType = ((ListType) varType).getType();
//                    TypeValidator.validateIntType(ExprType(expression));
//                }
//
//                listAccessNode.setType(varType);
//                return varType;
//            }
//
//
//            case ExprStringNode ignored -> {
//                expr.setType(SimpleType.STRING);
//                return SimpleType.STRING;
//            }
//            case ExprIntNode ignored -> {
//                expr.setType(SimpleType.INT);
//                return SimpleType.INT;
//            }
//            case ExprDoubleNode ignored -> {
//                expr.setType(SimpleType.DOUBLE);
//                return SimpleType.DOUBLE;
//            }
//            case ExprBoolNode ignored -> {
//                expr.setType(SimpleType.BOOL);
//                return SimpleType.BOOL;
//            }
//            default -> throw new IllegalStateException("Unexpected value: " + expr);
//        }
//    }
//
//    private AFSType TypeType(TypeNode type) {
//        switch(type) {
//            case TypeBoolNode ignored -> {
//                type.setType(SimpleType.BOOL);
//                return SimpleType.BOOL;
//            }
//            case TypeIntNode ignored -> {
//                type.setType(SimpleType.INT);
//                return SimpleType.INT;
//            }
//            case TypeDoubleNode ignored -> {
//                type.setType(SimpleType.DOUBLE);
//                return SimpleType.DOUBLE;
//            }
//            case TypeStringNode ignored -> {
//                type.setType(SimpleType.STRING);
//                return SimpleType.STRING;
//            }
//            case TypeShapeNode ignored -> {
//                type.setType(SimpleType.SHAPE);
//                return SimpleType.SHAPE;
//            }
//            case TypeVoidNode ignored -> {
//                type.setType(SimpleType.VOID);
//                return SimpleType.VOID;
//            }
//            case TypeListNode listNode -> {
//                AFSType listType = new ListType(TypeType(listNode.getType()));
//                listNode.setType(listType);
//                return listType;
//            }
//            default -> throw new IllegalStateException("Unexpected value: " + type);
//        }
//    }
//
//    private void checkFunction(String identifier, List<ExprNode> arguments, List<Param> params) {
//        int numArgs = arguments.size();
//        int numParams = params.size();
//        boolean equalSize = numArgs == numParams;
//        if (!equalSize) {
//            throw new TypeCheckException(String.format("Function '%s' expected %d arguments, but got %d", identifier, numParams, numArgs));
//        }
//
//        for (int i = 0; i < arguments.size(); i++) {
//            AFSType argType = ExprType(arguments.get(i));
//            AFSType paramType = TypeType(params.get(i).getType());
//            TypeValidator.validateTypeEquality(argType, paramType);
//        }
//    }
//
//    // Visitors for testing
//    public AFSType processDefNode(DefNode defNode) {
//        return DeclarationType(defNode);
//    }
//    public AFSType processEventNode(EventNode eventNode) {
//        return EventType(eventNode);
//    }
//    public AFSType processStmtNode(StmtNode stmtNode) {
//        return StmtType(stmtNode);
//    }
//    public AFSType processTypeNode(TypeNode typeNode) {
//        return TypeType(typeNode);
//    }
//    public AFSType processExprNode(ExprNode exprNode) {
//        return ExprType(exprNode);
//    }
//}