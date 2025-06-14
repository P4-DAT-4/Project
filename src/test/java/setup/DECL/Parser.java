package setup.DECL;

import afs.nodes.def.*;
import afs.nodes.event.*;
import afs.nodes.expr.*;
import afs.nodes.prog.*;
import afs.nodes.stmt.*;
import afs.nodes.type.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/* The required start of a Coco/R grammar file. "Program" specifies the starting symbol of the grammar. */


public class Parser {
	public static final int _EOF = 0;
	public static final int _INT = 1;
	public static final int _DOUBLE = 2;
	public static final int _STRING = 3;
	public static final int _IDENT = 4;
	public static final int maxT = 49;

	static final boolean _T = true;
	static final boolean _x = false;
	static final int minErrDist = 2;

	public Token t;    // last recognized token
	public Token la;   // lookahead token
	int errDist = minErrDist;
	
	public Scanner scanner;
	public Errors errors;

	public StmtNode mainNode = null; // This contains the AST generated by calling parser.Parse().

    public boolean hasErrors() {
        return errors.count > 0;
    }

    private StmtNode declToCompStmt(List<StmtNode> stmts) {
        if (stmts.isEmpty()) {
            return new StmtSkipNode();
        }
        StmtNode left = stmts.getFirst();

        if (stmts.size() == 1) {
            return addReturnIfDeclaration(left);
        } else {
            int line = left.getLineNumber();
            int col = left.getColumnNumber();
            List<StmtNode> rest = stmts.subList(1, stmts.size());
            StmtNode right = declToCompStmt(rest);
            if (left instanceof StmtDeclarationNode) {
                return new StmtDeclarationNode(
                    ((StmtDeclarationNode) left).getType(),
                    ((StmtDeclarationNode) left).getIdentifier(),
                    ((StmtDeclarationNode) left).getExpression(),
                    right, line, col);
            } else {
                return new StmtCompositionNode(left, right, line, col);
            }
        }
    }
    private StmtNode addReturnIfDeclaration(StmtNode stmt) {
        if (stmt instanceof StmtDeclarationNode) {
            int line = stmt.getLineNumber();
            int col = stmt.getColumnNumber();
            ExprIdentifierNode ident = new ExprIdentifierNode(((StmtDeclarationNode) stmt).getIdentifier(), line, col);
            StmtReturnNode ret = new StmtReturnNode(ident, line, col);
            return new StmtDeclarationNode(((StmtDeclarationNode) stmt).getType(), ((StmtDeclarationNode) stmt).getIdentifier(), ((StmtDeclarationNode) stmt).getExpression(), ret, line, col);
        } else {
            return stmt;
        }
    }

    private ExprNode makeUnOpExpr(List<Character> unaries, ExprNode base, int line, int col) {
        ExprNode result = base;
        int index = 0;
        while (index < unaries.size()) {
            char ch = unaries.get(index);
            result = switch (ch) {
                case '!' -> new ExprUnopNode(result, UnOp.NOT, line, col);
                case '-' -> new ExprUnopNode(result, UnOp.NEG, line, col);
                default -> throw new FatalError("Unknown unary operator: " + ch);
            };
            index++;
        }
        return result;
    }

    private ExprNode makeBinOpExpr(ExprNode left, String op, ExprNode right, int line, int column) {
        return switch (op) {
            case "*" -> new ExprBinopNode(left, BinOp.MUL, right, line, column);
            case "/" -> new ExprBinopNode(left, BinOp.DIV, right, line, column);
            case "+" -> new ExprBinopNode(left, BinOp.ADD, right, line, column);
            case "-" -> new ExprBinopNode(left, BinOp.SUB, right, line, column);
            case "++" -> new ExprBinopNode(left, BinOp.CONCAT, right, line, column);
            case "<=" -> {  ExprNode equals = makeBinOpExpr(left, "==", right, line, column);
                            ExprNode lessThan = makeBinOpExpr(left, "<", right, line, column);
                            yield makeBinOpExpr(equals, "||", lessThan, line, column);
            }
            case ">=" -> {  ExprNode equals = makeBinOpExpr(left, "==", right, line, column);
                            ExprNode lessThan = makeBinOpExpr(left, ">", right, line, column);
                            yield makeBinOpExpr(equals, "||", lessThan, line, column);
            }
            case "<" -> new ExprBinopNode(left, BinOp.LT, right, line, column);
            case ">" -> new ExprBinopNode(right, BinOp.LT, left, line, column);
            case "==" -> new ExprBinopNode(left, BinOp.EQ, right, line, column);
            case "!=" -> {  ExprNode equal = makeBinOpExpr(left, "==", right, line, column);
                            yield new ExprUnopNode(equal, UnOp.NOT, line, column);
            }
            case "&&" -> new ExprBinopNode(left, BinOp.AND, right, line, column);
            case "||" -> {  ExprNode notLeftExpr = new ExprUnopNode(left, UnOp.NOT, line, column);
                            ExprNode notRightExpr = new ExprUnopNode(right, UnOp.NOT, line, column);
                            ExprNode andNode = makeBinOpExpr(notLeftExpr, "&&", notRightExpr, line, column);
                            yield new ExprUnopNode(andNode, UnOp.NOT, line, column);
            }
            default -> throw new FatalError("Unknown binary operator: " + op);
        };
    }

/*------------------------------------------------------------------------*/
/* The following section contains the token specification of AFS.*/


	public Parser(Scanner scanner) {
		this.scanner = scanner;
		errors = new Errors();
	}

	void SynErr (int n) {
		if (errDist >= minErrDist) errors.SynErr(la.line, la.col, n);
		errDist = 0;
	}

	public void SemErr (String msg) {
		if (errDist >= minErrDist) errors.SemErr(t.line, t.col, msg);
		errDist = 0;
	}
	
	void Get () {
		for (;;) {
			t = la;
			la = scanner.Scan();
			if (la.kind <= maxT) {
				++errDist;
				break;
			}

			la = t;
		}
	}
	
	void Expect (int n) {
		if (la.kind==n) Get(); else { SynErr(n); }
	}
	
	boolean StartOf (int s) {
		return set[s][la.kind];
	}
	
	void ExpectWeak (int n, int follow) {
		if (la.kind == n) Get();
		else {
			SynErr(n);
			while (!StartOf(follow)) Get();
		}
	}
	
	boolean WeakSeparator (int n, int syFol, int repFol) {
		int kind = la.kind;
		if (kind == n) { Get(); return true; }
		else if (StartOf(repFol)) return false;
		else {
			SynErr(n);
			while (!(set[syFol][kind] || set[repFol][kind] || set[0][kind])) {
				Get();
				kind = la.kind;
			}
			return StartOf(syFol);
		}
	}
	
	void Program() {
		mainNode = Decl();
	}

	StmtNode  Decl() {
		StmtNode  decl;
		decl = new StmtSkipNode(); 
		if (StartOf(1)) {
			decl = DeclDecl();
		} else if (la.kind == 9) {
			decl = DeclIf();
		} else if (StartOf(2)) {
			ExprNode expr = DeclExpr();
			Expect(7);
			decl = new StmtReturnNode(expr, t.line, t.col); 
		} else SynErr(50);
		return decl;
	}

	StmtNode  DeclBlock() {
		StmtNode  stmt;
		List<StmtNode> decls = new ArrayList<>(); 
		Expect(5);
		while (StartOf(3)) {
			StmtNode decl = Decl();
			decls.add(decl); 
		}
		Expect(6);
		stmt = declToCompStmt(decls); 
		return stmt;
	}

	StmtNode  DeclDecl() {
		StmtNode  decl;
		ExprNode declExpr = null; 
		TypeNode type = DeclType();
		Expect(4);
		int line = t.line; int col = t.col; String ident = t.val; 
		Expect(8);
		if (StartOf(2)) {
			declExpr = DeclExpr();
		} else if (StartOf(4)) {
			declExpr = Expr();
		} else SynErr(51);
		decl = new StmtDeclarationNode(type, ident, declExpr, null, line, col); 
		Expect(7);
		return decl;
	}

	StmtNode  DeclIf() {
		StmtNode  decl;
		while (!(la.kind == 0 || la.kind == 9)) {SynErr(52); Get();}
		Expect(9);
		int line = t.line; int col = t.col; 
		Expect(10);
		ExprNode expr = Expr();
		Expect(11);
		Expect(12);
		StmtNode thenDecl = DeclBlock();
		decl = new StmtIfNode(expr, thenDecl, new StmtSkipNode(), line, col); 
		if (la.kind == 13) {
			Get();
			StmtNode elseDecl = DeclBlock();
			decl = new StmtIfNode(expr, thenDecl, elseDecl, line, col); 
		}
		return decl;
	}

	ExprNode  DeclExpr() {
		ExprNode  declExpr;
		declExpr = null; 
		switch (la.kind) {
		case 14: {
			declExpr = Text();
			break;
		}
		case 15: {
			declExpr = Line();
			break;
		}
		case 18: {
			declExpr = Curve();
			break;
		}
		case 19: {
			declExpr = Place();
			break;
		}
		case 21: {
			declExpr = Scale();
			break;
		}
		case 23: {
			declExpr = Rotate();
			break;
		}
		default: SynErr(53); break;
		}
		return declExpr;
	}

	TypeNode  DeclType() {
		TypeNode  type;
		type = null; 
		if (la.kind == 43) {
			Get();
			type = new TypeShapeNode(t.line, t.col); 
		} else if (StartOf(5)) {
			type = Type();
		} else SynErr(54);
		return type;
	}

	ExprNode  Expr() {
		ExprNode  expr;
		expr = AndExpr();
		while (la.kind == 25) {
			Get();
			String op = t.val; int line = t.line; int col = t.col; 
			ExprNode right = AndExpr();
			expr = makeBinOpExpr(expr, op, right, line, col); 
		}
		return expr;
	}

	ExprNode  Text() {
		ExprNode  declExpr;
		Expect(14);
		int line = t.line; int col = t.col; 
		ExprNode expr = Expr();
		declExpr = new ExprTextNode(expr, line, col); 
		return declExpr;
	}

	ExprNode  Line() {
		ExprNode  declExpr;
		List<ExprNode> exprList = new ArrayList<>(); 
		Expect(15);
		int line = t.line; int col = t.col; 
		Expect(10);
		ExprNode expr = Expr();
		exprList.add(expr); 
		Expect(16);
		expr = Expr();
		exprList.add(expr); 
		Expect(11);
		Expect(17);
		Expect(10);
		expr = Expr();
		exprList.add(expr); 
		Expect(16);
		expr = Expr();
		exprList.add(expr); 
		Expect(11);
		while (la.kind == 17) {
			Get();
			Expect(10);
			expr = Expr();
			exprList.add(expr); 
			Expect(16);
			expr = Expr();
			exprList.add(expr); 
			Expect(11);
		}
		declExpr = new ExprLineNode(exprList, line, col); 
		return declExpr;
	}

	ExprNode  Curve() {
		ExprNode  declExpr;
		List<ExprNode> exprList = new ArrayList<>(); 
		Expect(18);
		int line = t.line; int col = t.col; 
		Expect(10);
		ExprNode expr = Expr();
		exprList.add(expr); 
		Expect(16);
		expr = Expr();
		exprList.add(expr); 
		Expect(11);
		Expect(17);
		Expect(10);
		expr = Expr();
		exprList.add(expr); 
		Expect(16);
		expr = Expr();
		exprList.add(expr); 
		Expect(11);
		Expect(17);
		Expect(10);
		expr = Expr();
		exprList.add(expr); 
		Expect(16);
		expr = Expr();
		exprList.add(expr); 
		Expect(11);
		while (la.kind == 17) {
			Get();
			Expect(10);
			expr = Expr();
			exprList.add(expr); 
			Expect(16);
			expr = Expr();
			exprList.add(expr); 
			Expect(11);
			Expect(17);
			Expect(10);
			expr = Expr();
			exprList.add(expr); 
			Expect(16);
			expr = Expr();
			exprList.add(expr); 
			Expect(11);
		}
		declExpr = new ExprCurveNode(exprList, line, col); 
		return declExpr;
	}

	ExprNode  Place() {
		ExprNode  declExpr;
		Expect(19);
		int line = t.line; int col = t.col; 
		ExprNode lExpr = Expr();
		Expect(20);
		Expect(10);
		ExprNode mExpr = Expr();
		Expect(16);
		ExprNode rExpr = Expr();
		Expect(11);
		declExpr = new ExprPlaceNode(lExpr, mExpr, rExpr, line, col); 
		return declExpr;
	}

	ExprNode  Scale() {
		ExprNode  declExpr;
		Expect(21);
		int line = t.line; int col = t.col; 
		ExprNode lExpr = Expr();
		Expect(22);
		Expect(10);
		ExprNode mExpr = Expr();
		Expect(16);
		ExprNode rExpr = Expr();
		Expect(11);
		declExpr = new ExprScaleNode(lExpr, mExpr, rExpr, line, col); 
		return declExpr;
	}

	ExprNode  Rotate() {
		ExprNode  declExpr;
		Expect(23);
		int line = t.line; int col = t.col; 
		ExprNode fExpr = Expr();
		Expect(24);
		Expect(10);
		ExprNode sExpr = Expr();
		Expect(16);
		ExprNode tExpr = Expr();
		Expect(11);
		Expect(22);
		ExprNode lExpr = Expr();
		declExpr = new ExprRotateNode(fExpr, sExpr, tExpr, lExpr, line, col); 
		return declExpr;
	}

	ExprNode  AndExpr() {
		ExprNode  expr;
		expr = EqExpr();
		while (la.kind == 26) {
			Get();
			String op = t.val; int line = t.line; int col = t.col; 
			ExprNode right = EqExpr();
			expr = makeBinOpExpr(expr, op, right, line, col); 
		}
		return expr;
	}

	ExprNode  EqExpr() {
		ExprNode  expr;
		expr = RelExpr();
		while (la.kind == 27 || la.kind == 28) {
			if (la.kind == 27) {
				Get();
			} else {
				Get();
			}
			String op = t.val; int line = t.line; int col = t.col; 
			ExprNode right = RelExpr();
			expr = makeBinOpExpr(expr, op, right, line, col); 
		}
		return expr;
	}

	ExprNode  RelExpr() {
		ExprNode  expr;
		expr = ConcatExpr();
		while (StartOf(6)) {
			if (la.kind == 29) {
				Get();
			} else if (la.kind == 30) {
				Get();
			} else if (la.kind == 31) {
				Get();
			} else {
				Get();
			}
			String op = t.val; int line = t.line; int col = t.col; 
			ExprNode right = ConcatExpr();
			expr = makeBinOpExpr(expr, op, right, line, col); 
		}
		return expr;
	}

	ExprNode  ConcatExpr() {
		ExprNode  expr;
		expr = PlusExpr();
		while (la.kind == 33) {
			Get();
			String op = t.val; int line = t.line; int col = t.col; 
			ExprNode right = PlusExpr();
			expr = makeBinOpExpr(expr, op, right, line, col); 
		}
		return expr;
	}

	ExprNode  PlusExpr() {
		ExprNode  expr;
		expr = MultExpr();
		while (la.kind == 34 || la.kind == 35) {
			if (la.kind == 34) {
				Get();
			} else {
				Get();
			}
			String op = t.val; int line = t.line; int col = t.col; 
			ExprNode right = MultExpr();
			expr = makeBinOpExpr(expr, op, right, line, col); 
		}
		return expr;
	}

	ExprNode  MultExpr() {
		ExprNode  expr;
		expr = NotExpr();
		while (la.kind == 36 || la.kind == 37) {
			if (la.kind == 36) {
				Get();
			} else {
				Get();
			}
			String op = t.val; int line = t.line; int col = t.col; 
			ExprNode right = NotExpr();
			expr = makeBinOpExpr(expr, op, right, line, col); 
		}
		return expr;
	}

	ExprNode  NotExpr() {
		ExprNode  expr;
		List<Character> unaries = new ArrayList(); int line = -1; int col = -1; 
		while (la.kind == 35 || la.kind == 38) {
			if (la.kind == 38) {
				Get();
				unaries.add('!'); 
			} else {
				Get();
				unaries.add('-'); 
			}
			line = t.line; col = t.col; 
		}
		expr = Term();
		expr = makeUnOpExpr(unaries, expr, line, col);  
		return expr;
	}

	ExprNode  Term() {
		ExprNode  expr;
		expr = null; 
		switch (la.kind) {
		case 4: {
			Get();
			String ident = t.val; int line = t.line; int col = t.col; 
			expr = new ExprIdentifierNode(ident, line, col); 
			if (la.kind == 10 || la.kind == 41) {
				if (la.kind == 41) {
					expr = ExprListAccess(ident, line, col);
				} else {
					expr = ExprFuncCall(ident, line, col);
				}
			}
			break;
		}
		case 41: {
			expr = ExprListDeclaration();
			break;
		}
		case 1: {
			Get();
			expr = new ExprIntNode(t.val, t.line, t.col); 
			break;
		}
		case 2: {
			Get();
			expr = new ExprDoubleNode(t.val, t.line, t.col); 
			break;
		}
		case 3: {
			Get();
			expr = new ExprStringNode(t.val, t.line, t.col); 
			break;
		}
		case 39: case 40: {
			if (la.kind == 39) {
				Get();
			} else {
				Get();
			}
			expr = new ExprBoolNode(t.val, t.line, t.col); 
			break;
		}
		case 10: {
			Get();
			expr = Expr();
			Expect(11);
			break;
		}
		default: SynErr(55); break;
		}
		return expr;
	}

	ExprNode  ExprListAccess(String ident, int line, int col) {
		ExprNode  expr;
		List<ExprNode> exprList = new ArrayList<>(); 
		Expect(41);
		expr = Expr();
		exprList.add(expr); 
		Expect(42);
		while (la.kind == 41) {
			Get();
			expr = Expr();
			exprList.add(expr); 
			Expect(42);
		}
		expr = new ExprListAccessNode(ident, exprList, line, col); 
		return expr;
	}

	ExprFunctionCallNode  ExprFuncCall(String ident, int line, int col) {
		ExprFunctionCallNode  funcCall;
		List<ExprNode> exprList = new ArrayList<>(); 
		Expect(10);
		if (StartOf(4)) {
			ExprNode arg = Expr();
			exprList.add(arg); 
			while (la.kind == 16) {
				Get();
				arg = Expr();
				exprList.add(arg); 
			}
		}
		Expect(11);
		funcCall = new ExprFunctionCallNode(ident, exprList, line, col); 
		return funcCall;
	}

	ExprNode  ExprListDeclaration() {
		ExprNode  expr;
		List<ExprNode> exprs = new ArrayList<>(); 
		Expect(41);
		int line = t.line; int col = t.col; 
		if (StartOf(4)) {
			expr = Expr();
			exprs.add(expr); 
		}
		while (la.kind == 16) {
			Get();
			expr = Expr();
			exprs.add(expr); 
		}
		Expect(42);
		expr = new ExprListDeclaration(exprs, line, col); 
		return expr;
	}

	TypeNode  Type() {
		TypeNode  type;
		type = null; 
		if (la.kind == 45) {
			Get();
			type = new TypeIntNode(t.line, t.col); 
		} else if (la.kind == 46) {
			Get();
			type = new TypeDoubleNode(t.line, t.col); 
		} else if (la.kind == 47) {
			Get();
			type = new TypeBoolNode(t.line, t.col); 
		} else if (la.kind == 48) {
			Get();
			type = new TypeStringNode(t.line, t.col); 
		} else if (la.kind == 41) {
			Get();
			TypeNode innerType = Type();
			Expect(42);
			type = new TypeListNode(innerType, t.line, t.col); 
		} else SynErr(56);
		return type;
	}

	TypeNode  FunctionType() {
		TypeNode  type;
		type = null; 
		if (la.kind == 44) {
			Get();
			type = new TypeVoidNode(t.line, t.col); 
		} else if (StartOf(5)) {
			type = Type();
		} else SynErr(57);
		return type;
	}



	public void Parse() {
		la = new Token();
		la.val = "";		
		Get();
		Program();
		Expect(0);

		scanner.buffer.Close();
	}

	private static final boolean[][] set = {
		{_T,_x,_x,_x, _x,_x,_x,_x, _x,_T,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x},
		{_x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_T,_x,_T, _x,_T,_T,_T, _T,_x,_x},
		{_x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_T,_T, _x,_x,_T,_T, _x,_T,_x,_T, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x},
		{_x,_x,_x,_x, _x,_x,_x,_x, _x,_T,_x,_x, _x,_x,_T,_T, _x,_x,_T,_T, _x,_T,_x,_T, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_T,_x,_T, _x,_T,_T,_T, _T,_x,_x},
		{_x,_T,_T,_T, _T,_x,_x,_x, _x,_x,_T,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_T, _x,_x,_T,_T, _T,_T,_x,_x, _x,_x,_x,_x, _x,_x,_x},
		{_x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_T,_x,_x, _x,_T,_T,_T, _T,_x,_x},
		{_x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_T,_T,_T, _T,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x}

	};
} // end Parser


class Errors {
	public int count = 0;                                    // number of errors detected
	public java.io.PrintStream errorStream = System.out;     // error messages go to this stream
	public String errMsgFormat = "-- line {0} col {1}: {2}"; // 0=line, 1=column, 2=text
	
	protected void printMsg(int line, int column, String msg) {
		StringBuffer b = new StringBuffer(errMsgFormat);
		int pos = b.indexOf("{0}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, line); }
		pos = b.indexOf("{1}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, column); }
		pos = b.indexOf("{2}");
		if (pos >= 0) b.replace(pos, pos+3, msg);
		errorStream.println(b.toString());
	}
	
	public void SynErr (int line, int col, int n) {
		String s;
		switch (n) {
			case 0: s = "EOF expected"; break;
			case 1: s = "INT expected"; break;
			case 2: s = "DOUBLE expected"; break;
			case 3: s = "STRING expected"; break;
			case 4: s = "IDENT expected"; break;
			case 5: s = "\"{\" expected"; break;
			case 6: s = "\"}\" expected"; break;
			case 7: s = "\";\" expected"; break;
			case 8: s = "\"=\" expected"; break;
			case 9: s = "\"if\" expected"; break;
			case 10: s = "\"(\" expected"; break;
			case 11: s = "\")\" expected"; break;
			case 12: s = "\"then\" expected"; break;
			case 13: s = "\"else\" expected"; break;
			case 14: s = "\"text\" expected"; break;
			case 15: s = "\"line\" expected"; break;
			case 16: s = "\",\" expected"; break;
			case 17: s = "\"to\" expected"; break;
			case 18: s = "\"curve\" expected"; break;
			case 19: s = "\"place\" expected"; break;
			case 20: s = "\"at\" expected"; break;
			case 21: s = "\"scale\" expected"; break;
			case 22: s = "\"by\" expected"; break;
			case 23: s = "\"rotate\" expected"; break;
			case 24: s = "\"around\" expected"; break;
			case 25: s = "\"||\" expected"; break;
			case 26: s = "\"&&\" expected"; break;
			case 27: s = "\"==\" expected"; break;
			case 28: s = "\"!=\" expected"; break;
			case 29: s = "\"<=\" expected"; break;
			case 30: s = "\"<\" expected"; break;
			case 31: s = "\">=\" expected"; break;
			case 32: s = "\">\" expected"; break;
			case 33: s = "\"++\" expected"; break;
			case 34: s = "\"+\" expected"; break;
			case 35: s = "\"-\" expected"; break;
			case 36: s = "\"*\" expected"; break;
			case 37: s = "\"/\" expected"; break;
			case 38: s = "\"!\" expected"; break;
			case 39: s = "\"true\" expected"; break;
			case 40: s = "\"false\" expected"; break;
			case 41: s = "\"[\" expected"; break;
			case 42: s = "\"]\" expected"; break;
			case 43: s = "\"shape\" expected"; break;
			case 44: s = "\"void\" expected"; break;
			case 45: s = "\"int\" expected"; break;
			case 46: s = "\"double\" expected"; break;
			case 47: s = "\"bool\" expected"; break;
			case 48: s = "\"string\" expected"; break;
			case 49: s = "??? expected"; break;
			case 50: s = "invalid Decl"; break;
			case 51: s = "invalid DeclDecl"; break;
			case 52: s = "this symbol not expected in DeclIf"; break;
			case 53: s = "invalid DeclExpr"; break;
			case 54: s = "invalid DeclType"; break;
			case 55: s = "invalid Term"; break;
			case 56: s = "invalid Type"; break;
			case 57: s = "invalid FunctionType"; break;
			default: s = "error " + n; break;
		}
		printMsg(line, col, s);
		count++;
	}

	public void SemErr (int line, int col, String s) {	
		printMsg(line, col, s);
		count++;
	}
	
	public void SemErr (String s) {
		errorStream.println(s);
		count++;
	}
	
	public void Warning (int line, int col, String s) {	
		printMsg(line, col, s);
	}
	
	public void Warning (String s) {
		errorStream.println(s);
	}
} // Errors


class FatalError extends RuntimeException {
	public static final long serialVersionUID = 1L;
	public FatalError(String s) { super(s); }
}
