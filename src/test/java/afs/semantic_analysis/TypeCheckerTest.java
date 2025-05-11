//package afs.semantic_analysis;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//
//import afs.semantic_analysis.exceptions.TypeCheckException;
//import afs.semantic_analysis.types.*;
//import afs.nodes.def.*;
//import afs.nodes.expr.*;
//import afs.nodes.stmt.*;
//import afs.nodes.type.*;
//
//
//// Do ./gralew test to run the tests
//
//public class TypeCheckerTest extends TypeChecker{
//
//  private TypeChecker typeChecker;
//  @BeforeEach
//  public void setUp() {
//    typeChecker = new TypeChecker();
//  }
//
//
//  @Nested
//  class DeclarationType {
//    @Test
//    public void DeclarationNode() {
//      // Int
//      TypeIntNode typeIntNode = new TypeIntNode(1, 1);
//      ExprIdentifierNode identifierNode = new ExprIdentifierNode("x", 1, 1);
//      ExprNode expressionNode = new ExprIntNode("1", 1, 1);
//      DefDeclarationNode declarationNode = new DefDeclarationNode(typeIntNode, identifierNode, expressionNode, 1, 1);
//      AFSType resultType = typeChecker.processDefNode(declarationNode);
//      assertEquals(resultType, SimpleType.INT);
//
//      // Double
//      TypeDoubleNode typeDoubleNode = new TypeDoubleNode(1, 1);
//      ExprIdentifierNode identifierNode2 = new ExprIdentifierNode("y", 1, 1);
//      ExprNode expressionNode2 = new ExprDoubleNode("1.0", 1, 1);
//      DefDeclarationNode declarationNode2 = new DefDeclarationNode(typeDoubleNode, identifierNode2, expressionNode2, 1, 1);
//      AFSType resultType2 = typeChecker.processDefNode(declarationNode2);
//      assertEquals(resultType2, SimpleType.DOUBLE);
//
//      // String
//      TypeStringNode typeStringNode = new TypeStringNode(1, 1);
//      ExprIdentifierNode identifierNode3 = new ExprIdentifierNode("z", 1, 1);
//      ExprNode expressionNode3 = new ExprStringNode("10/10", 1, 1);
//      DefDeclarationNode declarationNode3 = new DefDeclarationNode(typeStringNode, identifierNode3, expressionNode3, 1, 1);
//      AFSType resultType3 = typeChecker.processDefNode(declarationNode3);
//      assertEquals(resultType3, SimpleType.STRING);
//      // Bool
//      TypeBoolNode typeBoolNode = new TypeBoolNode(1, 1);
//      ExprIdentifierNode identifierNode4 = new ExprIdentifierNode("a", 1, 1);
//      ExprNode expressionNode4 = new ExprBoolNode("1", 1, 1);
//      DefDeclarationNode declarationNode4 = new DefDeclarationNode(typeBoolNode, identifierNode4, expressionNode4, 1, 1);
//      AFSType resultType4 = typeChecker.processDefNode(declarationNode4);
//      assertEquals(resultType4, SimpleType.BOOL);
//
//      // Shape
//      /*TypeShapeNode typeShapeNode = new TypeShapeNode(1, 1);
//      ExprIdentifierNode identifierNode5 = new ExprIdentifierNode("b", 1, 1);
//      ExprNode expressionNode5 = new ExprShapeNode("Circle", 1, 1);
//      DefDeclarationNode declarationNode5 = new DefDeclarationNode(typeShapeNode, identifierNode5, expressionNode5, 1, 1);
//      AFSType resultType5 = typeChecker.processDefNode(declarationNode5);
//      assertEquals(resultType5, SimpleType.SHAPE);*/
//
//      // List
//      /*TypeListNode typeListNode = new TypeListNode(1, 1);
//      ExprIdentifierNode identifierNode6 = new ExprIdentifierNode("c", 1, 1);
//      ExprNode expressionNode6 = new ExprListNode("1,2,3", 1, 1);
//      DefDeclarationNode declarationNode6 = new DefDeclarationNode(typeListNode, identifierNode6, expressionNode6, 1, 1);
//      AFSType resultType6 = typeChecker.processDefNode(declarationNode6);
//      assertEquals(resultType6, SimpleType.LIST);*/
//
//      // Invalid type
//      TypeIntNode typeInvalidNode = new TypeIntNode(1, 1);
//      ExprIdentifierNode identifierNodeInvalid = new ExprIdentifierNode("d", 1, 1);
//      ExprNode expressionNodeInvalid = new ExprStringNode("Waow", 1, 1);
//      DefDeclarationNode declarationNodeInvalid = new DefDeclarationNode(typeInvalidNode, identifierNodeInvalid, expressionNodeInvalid, 1, 1);
//      TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
//        typeChecker.processDefNode(declarationNodeInvalid);
//      });
//      assertEquals("Type mismatch: expected 'INT', but found 'STRING'", exception.getMessage());
//    }
//    @Test
//    public void FunctionNode() {
//      // Int
//      TypeIntNode typeNode = new TypeIntNode(1, 1);
//      ExprIdentifierNode identifierNode = new ExprIdentifierNode("add", 1, 1);
//      List<Param> parameters = List.of(
//        new Param(new TypeIntNode(1, 1), new ExprIdentifierNode("a", 1, 1), 0, 0),
//        new Param(new TypeIntNode(1, 1), new ExprIdentifierNode("b", 1, 1), 0, 0)
//      );
//
//      StmtDeclarationNode declaration = new StmtDeclarationNode(new TypeIntNode(1, 1), new ExprIdentifierNode("result", 1, 1), new ExprIntNode("0", 1, 1), 1, 1);
//      StmtNode statement = new StmtBlockNode(declaration, null, 1, 1);
//
//      StmtNode funcStmt = new StmtBlockNode(declaration, statement, 1, 1);
//      DefFunctionNode functionNode = new DefFunctionNode(typeNode, identifierNode, parameters, funcStmt, 1, 1);
//      AFSType returnType = typeChecker.processDefNode(functionNode);
//      assertEquals(returnType, SimpleType.INT);
//
//      // Invalid: type mismatch
//
//    }
//    @Test
//    public void VisualizeNode() {
//
//    }
//  }
//  @Nested
//  class EventType {
//    @Test
//    public void CompositionNode() {
//
//    }
//    @Test
//    public void DeclarationNode() {
//
//    }
//  }
//  @Nested
//  class StatementType {
//    @Test
//    public void IfNode() {
//      // Bool
//      /*
//      ExprNode expr = new ExprBoolNode("1", 1, 1);
//      StmtDeclarationNode declaration = new StmtDeclarationNode(new TypeBoolNode(1, 1), new ExprIdentifierNode("x", 1, 1), new ExprBoolNode("1", 1, 1), 1, 2);
//      StmtNode leftStatement = new StmtBlockNode(declaration, null, 1, 2);
//      StmtNode rightStatement = new StmtBlockNode(declaration, null, 1, 3);
//      StmtNode leftStmt = new StmtBlockNode(declaration, leftStatement, 0, 0);
//      StmtNode rightStmt = new StmtBlockNode(declaration, rightStatement, 0, 0);
//      StmtNode ifNode = new StmtIfNode(expr, leftStmt, rightStmt, 1, 2);
//
//      AFSType returnType = typeChecker.processStmtNode(ifNode);
//      assertEquals(returnType, SimpleType.VOID);*/
//
//    }
//    @Test
//    public void WhileNode() {
//      /*ExprNode expr = new ExprBoolNode("1", 1, 1);
//      StmtNode statement = new StmtBlockNode(null, null, 1, 2);
//      StmtNode whileNode = new StmtWhileNode(expr, statement, 1, 2);
//      AFSType returnType = typeChecker.processStmtNode(whileNode);
//      assertEquals(returnType, SimpleType.VOID);*/
//
//    }
//    @Test
//    public void AssignmentNode() {
//      // Int
//      typeChecker.symbolTable.put("x", SimpleType.INT);
//      ExprNode expr = new ExprIntNode("1", 1, 1);
//      ExprIdentifierNode identifier = new ExprIdentifierNode("x", 1, 1);
//      StmtNode assignmentNode = new StmtAssignmentNode(identifier, expr, 1, 2);
//      AFSType returnType = typeChecker.processStmtNode(assignmentNode);
//      assertEquals(returnType, SimpleType.INT);
//      typeChecker.symbolTable.remove("x");
//
//      // Double
//      typeChecker.symbolTable.put("y", SimpleType.DOUBLE);
//      ExprNode expr2 = new ExprDoubleNode("1.0", 1, 1);
//      ExprIdentifierNode identifier2 = new ExprIdentifierNode("y", 1, 1);
//      StmtNode assignmentNode2 = new StmtAssignmentNode(identifier2, expr2, 1, 2);
//      AFSType returnType2 = typeChecker.processStmtNode(assignmentNode2);
//      assertEquals(returnType2, SimpleType.DOUBLE);
//      typeChecker.symbolTable.remove("y");
//
//      // String
//      typeChecker.symbolTable.put("z", SimpleType.STRING);
//      ExprNode expr3 = new ExprStringNode("AFTW", 1, 1);
//      ExprIdentifierNode identifier3 = new ExprIdentifierNode("z", 1, 1);
//      StmtNode assignmentNode3 = new StmtAssignmentNode(identifier3, expr3, 1, 2);
//      AFSType returnType3 = typeChecker.processStmtNode(assignmentNode3);
//      assertEquals(returnType3, SimpleType.STRING);
//      typeChecker.symbolTable.remove("z");
//
//      // Bool
//      typeChecker.symbolTable.put("a", SimpleType.BOOL);
//      ExprNode expr4 = new ExprBoolNode("1", 1, 1);
//      ExprIdentifierNode identifier4 = new ExprIdentifierNode("a", 1, 1);
//      StmtNode assignmentNode4 = new StmtAssignmentNode(identifier4, expr4, 1, 2);
//      AFSType returnType4 = typeChecker.processStmtNode(assignmentNode4);
//      assertEquals(returnType4, SimpleType.BOOL);
//      typeChecker.symbolTable.remove("a");
//
//      // Invalid
//      typeChecker.symbolTable.put("b", SimpleType.INT);
//      ExprNode exprInvalid = new ExprStringNode("AFS Rocks", 1, 1);
//      ExprIdentifierNode identifierInvalid = new ExprIdentifierNode("b", 1, 1);
//      StmtNode assignmentNodeInvalid = new StmtAssignmentNode(identifierInvalid, exprInvalid, 1, 2);
//      TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
//        typeChecker.processStmtNode(assignmentNodeInvalid);
//      });
//      assertEquals("Type mismatch: expected 'STRING', but found 'INT'", exception.getMessage());
//      typeChecker.symbolTable.remove("b");
//    }
//    @Test
//    public void BlockNode() {
//
//    }
//    @Test
//    public void DeclarationNode() {
//      // Int
//      TypeIntNode typeNode = new TypeIntNode(1, 1);
//      ExprIdentifierNode identifierNode = new ExprIdentifierNode("x", 1, 1);
//      ExprNode expressionNode = new ExprIntNode("1", 1, 1);
//      StmtDeclarationNode declarationNode = new StmtDeclarationNode(typeNode, identifierNode, expressionNode, 1, 1);
//      AFSType returnType = typeChecker.processStmtNode(declarationNode);
//      assertEquals(returnType, SimpleType.INT);
//      AFSType symbol = typeChecker.symbolTable.get("x");
//      assertEquals(symbol, SimpleType.INT);
//
//      // Double
//      TypeDoubleNode typeNode2 = new TypeDoubleNode(1, 1);
//      ExprIdentifierNode identifierNode2 = new ExprIdentifierNode("y", 1, 1);
//      ExprNode expressionNode2 = new ExprDoubleNode("1.0", 1, 1);
//      StmtDeclarationNode declarationNode2 = new StmtDeclarationNode(typeNode2, identifierNode2, expressionNode2, 1, 1);
//      AFSType returnType2 = typeChecker.processStmtNode(declarationNode2);
//      assertEquals(returnType2, SimpleType.DOUBLE);
//      AFSType symbol2 = typeChecker.symbolTable.get("y");
//      assertEquals(symbol2, SimpleType.DOUBLE);
//      typeChecker.symbolTable.remove("y");
//
//      // String
//      TypeStringNode typeNode3 = new TypeStringNode(1, 1);
//      ExprIdentifierNode identifierNode3 = new ExprIdentifierNode("z", 1, 1);
//      ExprNode expressionNode3 = new ExprStringNode("MustHave", 1, 1);
//      StmtDeclarationNode declarationNode3 = new StmtDeclarationNode(typeNode3, identifierNode3, expressionNode3, 1, 1);
//      AFSType returnType3 = typeChecker.processStmtNode(declarationNode3);
//      assertEquals(returnType3, SimpleType.STRING);
//      AFSType symbol3 = typeChecker.symbolTable.get("z");
//      assertEquals(symbol3, SimpleType.STRING);
//      typeChecker.symbolTable.remove("z");
//
//      // Bool
//      TypeBoolNode typeNode4 = new TypeBoolNode(1, 1);
//      ExprIdentifierNode identifierNode4 = new ExprIdentifierNode("a", 1, 1);
//      ExprNode expressionNode4 = new ExprBoolNode("1", 1, 1);
//      StmtDeclarationNode declarationNode4 = new StmtDeclarationNode(typeNode4, identifierNode4, expressionNode4, 1, 1);
//      AFSType returnType4 = typeChecker.processStmtNode(declarationNode4);
//      assertEquals(returnType4, SimpleType.BOOL);
//      AFSType symbol4 = typeChecker.symbolTable.get("a");
//      assertEquals(symbol4, SimpleType.BOOL);
//      typeChecker.symbolTable.remove("a");
//
//      // invalid: declaration already declared
//      typeChecker.symbolTable.put("x", SimpleType.INT);
//      TypeIntNode typeNodeInvalid = new TypeIntNode(1, 1);
//      ExprIdentifierNode identifierNodeInvalid = new ExprIdentifierNode("x", 1, 1);
//      ExprNode expressionNodeInvalid = new ExprIntNode("1", 1, 1);
//      StmtDeclarationNode declarationNodeInvalid = new StmtDeclarationNode(typeNodeInvalid, identifierNodeInvalid, expressionNodeInvalid, 1, 1);
//      TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
//        typeChecker.processStmtNode(declarationNodeInvalid);
//      });
//      assertEquals("Variable 'x' is already declared", exception.getMessage());
//      typeChecker.symbolTable.remove("x");
//
//      // invalid: declaration type mismatch
//      TypeIntNode typeNodeInvalid2 = new TypeIntNode(1, 1);
//      ExprIdentifierNode identifierNodeInvalid2 = new ExprIdentifierNode("y", 1, 1);
//      ExprNode expressionNodeInvalid2 = new ExprStringNode("Best", 1, 1);
//      StmtDeclarationNode declarationNodeInvalid2 = new StmtDeclarationNode(typeNodeInvalid2, identifierNodeInvalid2, expressionNodeInvalid2, 1, 1);
//      TypeCheckException exception2 = assertThrows(TypeCheckException.class, () -> {
//        typeChecker.processStmtNode(declarationNodeInvalid2);
//      });
//      assertEquals("Type mismatch: expected 'INT', but found 'STRING'", exception2.getMessage());
//      typeChecker.symbolTable.remove("y");
//    }
//    @Test
//    public void FunctionCallNode() {
//
//    }
//    @Test
//    public void ReturnNode() {
//      // Int
//      /*ExprNode expr = new ExprIntNode("1", 1, 1);
//      ReturnNode returnNode = new DefReturnNode(expr, 1, 2);
//      AFSType returnType = typeChecker.processStmtNode(returnNode);
//      assertEquals(returnType, SimpleType.INT);*/
//
//      // List
//    }
//  }
//  @Nested
//  class ExpressionType {
//    @Nested
//    class BinOpExpr {
//      @Test
//      public void AddSubMulDiv() {
//        // Integer
//        ExprNode leftExpr = new ExprIntNode("1", 1, 1);
//        ExprNode rightExpr = new ExprIntNode("2", 1, 2);
//        ExprNode addExpr = new ExprBinopNode(leftExpr, BinOp.ADD, rightExpr, 1, 3);
//        AFSType resultType = typeChecker.processExprNode(addExpr);
//        assertEquals(resultType, SimpleType.INT);
//
//        // Double
//        ExprNode leftExpr2 = new ExprDoubleNode("1.0", 1, 1);
//        ExprNode rightExpr2 = new ExprDoubleNode("2.0", 1, 2);
//        ExprNode addExpr2 = new ExprBinopNode(leftExpr2, BinOp.ADD, rightExpr2, 1, 3);
//        AFSType resultType2 = typeChecker.processExprNode(addExpr2);
//        assertEquals(resultType2, SimpleType.DOUBLE);
//
//        // String
//        ExprNode leftExpr3 = new ExprStringNode("AFS", 1, 1);
//        ExprNode rightExpr3 = new ExprStringNode("Best", 1, 2);
//        ExprNode addExpr3 = new ExprBinopNode(leftExpr3, BinOp.ADD, rightExpr3, 1, 3);
//        TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
//          typeChecker.processExprNode(addExpr3);
//        });
//        assertEquals("Invalid type 'STRING': expected int or double", exception.getMessage());
//
//        // invalid: type mismatch
//        ExprNode leftExpr4 = new ExprIntNode("1", 1, 1);
//        ExprNode rightExpr4 = new ExprStringNode("N1", 1, 2);
//        ExprNode addExpr4 = new ExprBinopNode(leftExpr4, BinOp.ADD, rightExpr4, 1, 3);
//        TypeCheckException exception2 = assertThrows(TypeCheckException.class, () -> {
//          typeChecker.processExprNode(addExpr4);
//        });
//        assertEquals("Invalid type 'STRING': expected int or double", exception2.getMessage());
//      }
//
//      @Test
//      public void CONCAT() {
//        // String
//        ExprNode leftExpr = new ExprStringNode("Top", 1, 1);
//        ExprNode rightExpr = new ExprStringNode("Tier", 1, 2);
//        ExprNode concatExpr = new ExprBinopNode(leftExpr, BinOp.CONCAT, rightExpr, 1, 3);
//        AFSType resultType = typeChecker.processExprNode(concatExpr);
//        assertEquals(resultType, SimpleType.STRING);
//
//        // Shape
//        /*ExprNode leftExpr2 = new ExprShapeNode("Circle", 1, 1);
//        ExprNode rightExpr2 = new ExprShapeNode("Square", 1, 2);
//        ExprNode concatExpr2 = new ExprBinopNode(leftExpr2, BinOp.CONCAT, rightExpr2, 1, 3);
//        AFSType resultType2 = typeChecker.processExprNode(concatExpr2);
//        assertEquals(resultType2, SimpleType.SHAPE);
//        */
//
//        // List
//        /*ListType leftList = new ListType("1,2,3", 1, 1);
//        ListType rightList = new ExprListNode("4,5,6", 1, 2);
//        ExprNode concatExpr3 = new ExprBinopNode(leftExpr3, BinOp.CONCAT, rightExpr3, 1, 3);
//        AFSType resultType3 = typeChecker.processExprNode(concatExpr3);
//        assertEquals(resultType3, SimpleType.LIST);*/
//
//        // Invalid: wrong type
//        ExprNode leftExpr3 = new ExprIntNode("1", 1, 1);
//        ExprNode rightExpr3 = new ExprIntNode("2", 1, 2);
//        ExprNode concatExpr3 = new ExprBinopNode(leftExpr3, BinOp.CONCAT, rightExpr3, 1, 3);
//        TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
//          typeChecker.processExprNode(concatExpr3);
//        });
//        assertEquals("Invalid type 'INT': Expected string or shape or list", exception.getMessage());
//      }
//
//      @Test
//      public void LtEq() {
//        // Int
//        ExprNode leftExpr = new ExprIntNode("1", 1, 1);
//        ExprNode rightExpr = new ExprIntNode("2", 1, 2);
//        ExprNode ltExpr = new ExprBinopNode(leftExpr, BinOp.LT, rightExpr, 1, 3);
//        AFSType resultType = typeChecker.processExprNode(ltExpr);
//        assertEquals(resultType, SimpleType.BOOL);
//
//        // Double
//        ExprNode leftExpr2 = new ExprDoubleNode("1.0", 1, 1);
//        ExprNode rightExpr2 = new ExprDoubleNode("2.0", 1, 2);
//        ExprNode ltExpr2 = new ExprBinopNode(leftExpr2, BinOp.EQ, rightExpr2, 1, 3);
//        AFSType resultType2 = typeChecker.processExprNode(ltExpr2);
//        assertEquals(resultType2, SimpleType.BOOL);
//
//        // String
//        ExprNode leftExpr3 = new ExprStringNode("World", 1, 1);
//        ExprNode rightExpr3 = new ExprStringNode("Best", 1, 2);
//        ExprNode ltExpr3 = new ExprBinopNode(leftExpr3, BinOp.LT, rightExpr3, 1, 3);
//        TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
//          typeChecker.processExprNode(ltExpr3);
//        });
//        assertEquals("Invalid type 'STRING': expected int or double", exception.getMessage());
//
//        // invalid: type mismatch
//        ExprNode leftExpr4 = new ExprIntNode("1", 1, 1);
//        ExprNode rightExpr4 = new ExprStringNode("Hero", 1, 2);
//        ExprNode ltExpr4 = new ExprBinopNode(leftExpr4, BinOp.LT, rightExpr4, 1, 3);
//        TypeCheckException exception2 = assertThrows(TypeCheckException.class, () -> {
//          typeChecker.processExprNode(ltExpr4);
//        });
//        assertEquals("Invalid type 'STRING': expected int or double", exception2.getMessage());
//      }
//
//      @Test
//      public void AND() {
//        // Bool
//        ExprNode leftExpr = new ExprBoolNode("1", 1, 1);
//        ExprNode rightExpr = new ExprBoolNode("0", 1, 2);
//        ExprNode andExpr = new ExprBinopNode(leftExpr, BinOp.AND, rightExpr, 1, 3);
//        AFSType resultType = typeChecker.processExprNode(andExpr);
//        assertEquals(resultType, SimpleType.BOOL);
//
//        // Invalid: wrong type
//        ExprNode leftExpr2 = new ExprIntNode("1", 1, 1);
//        ExprNode rightExpr2 = new ExprIntNode("0", 1, 2);
//        ExprNode andExpr2 = new ExprBinopNode(leftExpr2, BinOp.AND, rightExpr2, 1, 3);
//        TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
//          typeChecker.processExprNode(andExpr2);
//        });
//        assertEquals("Invalid type 'INT': expected bool", exception.getMessage());
//      }
//    }
//
//    @Nested
//    class UnOpExpr {
//      @Test
//      public void NOT() {
//        // Bool
//        ExprNode expr = new ExprBoolNode("1", 1, 1);
//        ExprNode notExpr = new ExprUnopNode(expr, UnOp.NOT, 1, 2);
//        AFSType resultType = typeChecker.processExprNode(notExpr);
//        assertEquals(resultType, SimpleType.BOOL);
//
//        // Invalid: wrong type
//        ExprNode expr2 = new ExprIntNode("1", 1, 1);
//        ExprNode notExpr2 = new ExprUnopNode(expr2, UnOp.NOT, 1, 2);
//        TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
//          typeChecker.processExprNode(notExpr2);
//        });
//        assertEquals("Invalid type 'INT': expected bool", exception.getMessage());
//      }
//
//      @Test
//      public void NEG() {
//        // Int
//        ExprNode expr = new ExprIntNode("1", 1, 1);
//        ExprNode negExpr = new ExprUnopNode(expr, UnOp.NEG, 1, 2);
//        AFSType resultType = typeChecker.processExprNode(negExpr);
//        assertEquals(resultType, SimpleType.INT);
//
//        // Double
//        ExprNode expr2 = new ExprDoubleNode("1.0", 1, 1);
//        ExprNode negExpr2 = new ExprUnopNode(expr2, UnOp.NEG, 1, 2);
//        AFSType resultType2 = typeChecker.processExprNode(negExpr2);
//        assertEquals(resultType2, SimpleType.DOUBLE);
//
//        // Invalid: wrong type
//        ExprNode expr3 = new ExprStringNode("Best", 1, 1);
//        ExprNode negExpr3 = new ExprUnopNode(expr3, UnOp.NEG, 1, 2);
//        TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
//          typeChecker.processExprNode(negExpr3);
//        });
//        assertEquals("Invalid type 'STRING': expected int or double", exception.getMessage());
//      }
//    }
//    @Test
//    public void IdentifierNode() {
//      // Int
//      typeChecker.symbolTable.put("x", SimpleType.INT);
//      ExprIdentifierNode identifier = new ExprIdentifierNode("x", 1, 1);
//      AFSType returnType = typeChecker.processExprNode(identifier);
//      assertEquals(returnType, SimpleType.INT);
//      typeChecker.symbolTable.remove("x");
//
//      // Double
//      typeChecker.symbolTable.put("y", SimpleType.DOUBLE);
//      ExprIdentifierNode identifier2 = new ExprIdentifierNode("y", 1, 1);
//      AFSType returnType2 = typeChecker.processExprNode(identifier2);
//      assertEquals(returnType2, SimpleType.DOUBLE);
//      typeChecker.symbolTable.remove("y");
//
//      // String
//      typeChecker.symbolTable.put("z", SimpleType.STRING);
//      ExprIdentifierNode identifier3 = new ExprIdentifierNode("z", 1, 1);
//      AFSType returnType3 = typeChecker.processExprNode(identifier3);
//      assertEquals(returnType3, SimpleType.STRING);
//      typeChecker.symbolTable.remove("z");
//
//      // Bool
//      typeChecker.symbolTable.put("a", SimpleType.BOOL);
//      ExprIdentifierNode identifier4 = new ExprIdentifierNode("a", 1, 1);
//      AFSType returnType4 = typeChecker.processExprNode(identifier4);
//      assertEquals(returnType4, SimpleType.BOOL);
//      typeChecker.symbolTable.remove("a");
//
//      // Invalid: not declared
//      TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
//        ExprIdentifierNode identifierInvalid = new ExprIdentifierNode("b", 1, 1);
//        typeChecker.processExprNode(identifierInvalid);
//      });
//      assertEquals("Identifier 'b' not declared", exception.getMessage());
//    }
//    @Test
//    public void ListDeclarationNode() {
//      // Int
//      List<ExprNode> expressions = List.of(
//        new ExprIntNode("1", 1, 1),
//        new ExprIntNode("2", 1, 2)
//        );
//      ExprListDeclaration listDeclaration = new ExprListDeclaration(expressions, 1, 1);
//      AFSType returnType = typeChecker.processExprNode(listDeclaration);
//      assertEquals(returnType, SimpleType.INT);
//
//      // Double
//      List<ExprNode> expressions2 = List.of(
//        new ExprDoubleNode("1.0", 1, 1),
//        new ExprDoubleNode("2.0", 1, 2)
//        );
//      ExprListDeclaration listDeclaration2 = new ExprListDeclaration(expressions2, 1, 1);
//      AFSType returnType2 = typeChecker.processExprNode(listDeclaration2);
//      assertEquals(returnType2, SimpleType.DOUBLE);
//
//      // String
//      List<ExprNode> expressions3 = List.of(
//        new ExprStringNode("AFS", 1, 1),
//        new ExprStringNode("Best", 1, 2)
//        );
//      ExprListDeclaration listDeclaration3 = new ExprListDeclaration(expressions3, 1, 1);
//      AFSType returnType3 = typeChecker.processExprNode(listDeclaration3);
//      assertEquals(returnType3, SimpleType.STRING);
//      // Bool
//      List<ExprNode> expressions4 = List.of(
//        new ExprBoolNode("1", 1, 1),
//        new ExprBoolNode("0", 1, 2)
//        );
//      ExprListDeclaration listDeclaration4 = new ExprListDeclaration(expressions4, 1, 1);
//      AFSType returnType4 = typeChecker.processExprNode(listDeclaration4);
//      assertEquals(returnType4, SimpleType.BOOL);
//
//      // Shape
//      // TODO
//
//      // Invalid: type mismatch
//      List<ExprNode> expressionsInvalid = List.of(
//        new ExprIntNode("1", 1, 1),
//        new ExprStringNode("Best", 1, 2)
//        );
//      ExprListDeclaration listDeclarationInvalid = new ExprListDeclaration(expressionsInvalid, 1, 1);
//      TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
//        typeChecker.processExprNode(listDeclarationInvalid);
//      });
//      assertEquals("Type mismatch: expected 'INT', but found 'STRING'", exception.getMessage());
//
//    }
//
//  }
//  @Nested
//  class TypeNode {
//    @Test
//    public void TypeIntNode() {
//      // Int
//      TypeIntNode type = new TypeIntNode(1, 1);
//      AFSType returnType = typeChecker.processTypeNode(type);
//      assertEquals(returnType, SimpleType.INT);
//    }
//    @Test
//    public void TypeBoolNode() {
//      TypeBoolNode type = new TypeBoolNode(1,1);
//      AFSType returnType = typeChecker.processTypeNode(type);
//      assertEquals(returnType, SimpleType.BOOL);
//    }
//    @Test
//    public void TypeStringNode() {
//      TypeStringNode type = new TypeStringNode(1,1);
//      AFSType returnType = typeChecker.processTypeNode(type);
//      assertEquals(returnType, SimpleType.STRING);
//    }
//    @Test
//    public void TypeDoubleNode() {
//      TypeDoubleNode type = new TypeDoubleNode(1,1);
//      AFSType returnType = typeChecker.processTypeNode(type);
//      assertEquals(returnType, SimpleType.DOUBLE);
//    }
//  }
//}
