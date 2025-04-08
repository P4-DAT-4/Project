// Generated from MiniLang.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MiniLangParser}.
 */
public interface MiniLangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MiniLangParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MiniLangParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(MiniLangParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(MiniLangParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#funcDecl}.
	 * @param ctx the parse tree
	 */
	void enterFuncDecl(MiniLangParser.FuncDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#funcDecl}.
	 * @param ctx the parse tree
	 */
	void exitFuncDecl(MiniLangParser.FuncDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(MiniLangParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(MiniLangParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MiniLangParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MiniLangParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(MiniLangParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(MiniLangParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MiniLangParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MiniLangParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(MiniLangParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(MiniLangParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MiniLangParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MiniLangParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(MiniLangParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(MiniLangParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(MiniLangParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(MiniLangParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#funcCallStmt}.
	 * @param ctx the parse tree
	 */
	void enterFuncCallStmt(MiniLangParser.FuncCallStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#funcCallStmt}.
	 * @param ctx the parse tree
	 */
	void exitFuncCallStmt(MiniLangParser.FuncCallStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void enterFuncCall(MiniLangParser.FuncCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void exitFuncCall(MiniLangParser.FuncCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(MiniLangParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(MiniLangParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniLangParser#relop}.
	 * @param ctx the parse tree
	 */
	void enterRelop(MiniLangParser.RelopContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniLangParser#relop}.
	 * @param ctx the parse tree
	 */
	void exitRelop(MiniLangParser.RelopContext ctx);
}