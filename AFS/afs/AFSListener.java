// Generated from AFS.g4 by ANTLR 4.13.2

    package afs;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AFSParser}.
 */
public interface AFSListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code Main}
	 * labeled alternative in {@link AFSParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterMain(AFSParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Main}
	 * labeled alternative in {@link AFSParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitMain(AFSParser.MainContext ctx);
	/**
	 * Enter a parse tree produced by {@link AFSParser#visStmt}.
	 * @param ctx the parse tree
	 */
	void enterVisStmt(AFSParser.VisStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link AFSParser#visStmt}.
	 * @param ctx the parse tree
	 */
	void exitVisStmt(AFSParser.VisStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EventFunc}
	 * labeled alternative in {@link AFSParser#event}.
	 * @param ctx the parse tree
	 */
	void enterEventFunc(AFSParser.EventFuncContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EventFunc}
	 * labeled alternative in {@link AFSParser#event}.
	 * @param ctx the parse tree
	 */
	void exitEventFunc(AFSParser.EventFuncContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FnDef}
	 * labeled alternative in {@link AFSParser#def}.
	 * @param ctx the parse tree
	 */
	void enterFnDef(AFSParser.FnDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FnDef}
	 * labeled alternative in {@link AFSParser#def}.
	 * @param ctx the parse tree
	 */
	void exitFnDef(AFSParser.FnDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VisDef}
	 * labeled alternative in {@link AFSParser#def}.
	 * @param ctx the parse tree
	 */
	void enterVisDef(AFSParser.VisDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VisDef}
	 * labeled alternative in {@link AFSParser#def}.
	 * @param ctx the parse tree
	 */
	void exitVisDef(AFSParser.VisDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VarDef}
	 * labeled alternative in {@link AFSParser#def}.
	 * @param ctx the parse tree
	 */
	void enterVarDef(AFSParser.VarDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VarDef}
	 * labeled alternative in {@link AFSParser#def}.
	 * @param ctx the parse tree
	 */
	void exitVarDef(AFSParser.VarDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link AFSParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(AFSParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link AFSParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(AFSParser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DeclStmtDef}
	 * labeled alternative in {@link AFSParser#declStmt}.
	 * @param ctx the parse tree
	 */
	void enterDeclStmtDef(AFSParser.DeclStmtDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DeclStmtDef}
	 * labeled alternative in {@link AFSParser#declStmt}.
	 * @param ctx the parse tree
	 */
	void exitDeclStmtDef(AFSParser.DeclStmtDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DeclStmtIf}
	 * labeled alternative in {@link AFSParser#declStmt}.
	 * @param ctx the parse tree
	 */
	void enterDeclStmtIf(AFSParser.DeclStmtIfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DeclStmtIf}
	 * labeled alternative in {@link AFSParser#declStmt}.
	 * @param ctx the parse tree
	 */
	void exitDeclStmtIf(AFSParser.DeclStmtIfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DeclStmtFuncCall}
	 * labeled alternative in {@link AFSParser#declStmt}.
	 * @param ctx the parse tree
	 */
	void enterDeclStmtFuncCall(AFSParser.DeclStmtFuncCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DeclStmtFuncCall}
	 * labeled alternative in {@link AFSParser#declStmt}.
	 * @param ctx the parse tree
	 */
	void exitDeclStmtFuncCall(AFSParser.DeclStmtFuncCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link AFSParser#declBlock}.
	 * @param ctx the parse tree
	 */
	void enterDeclBlock(AFSParser.DeclBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link AFSParser#declBlock}.
	 * @param ctx the parse tree
	 */
	void exitDeclBlock(AFSParser.DeclBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImpStmtDef}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 */
	void enterImpStmtDef(AFSParser.ImpStmtDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImpStmtDef}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 */
	void exitImpStmtDef(AFSParser.ImpStmtDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImpStmtAss}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 */
	void enterImpStmtAss(AFSParser.ImpStmtAssContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImpStmtAss}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 */
	void exitImpStmtAss(AFSParser.ImpStmtAssContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImpStmtListAss}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 */
	void enterImpStmtListAss(AFSParser.ImpStmtListAssContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImpStmtListAss}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 */
	void exitImpStmtListAss(AFSParser.ImpStmtListAssContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImpStmtIf}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 */
	void enterImpStmtIf(AFSParser.ImpStmtIfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImpStmtIf}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 */
	void exitImpStmtIf(AFSParser.ImpStmtIfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImpStmtWhl}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 */
	void enterImpStmtWhl(AFSParser.ImpStmtWhlContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImpStmtWhl}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 */
	void exitImpStmtWhl(AFSParser.ImpStmtWhlContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImpStmtRtn}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 */
	void enterImpStmtRtn(AFSParser.ImpStmtRtnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImpStmtRtn}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 */
	void exitImpStmtRtn(AFSParser.ImpStmtRtnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImpStmtFuncCall}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 */
	void enterImpStmtFuncCall(AFSParser.ImpStmtFuncCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImpStmtFuncCall}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 */
	void exitImpStmtFuncCall(AFSParser.ImpStmtFuncCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link AFSParser#impBlock}.
	 * @param ctx the parse tree
	 */
	void enterImpBlock(AFSParser.ImpBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link AFSParser#impBlock}.
	 * @param ctx the parse tree
	 */
	void exitImpBlock(AFSParser.ImpBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImpExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 */
	void enterImpExpr(AFSParser.ImpExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImpExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 */
	void exitImpExpr(AFSParser.ImpExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PlaceExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 */
	void enterPlaceExpr(AFSParser.PlaceExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PlaceExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 */
	void exitPlaceExpr(AFSParser.PlaceExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ScaleExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 */
	void enterScaleExpr(AFSParser.ScaleExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ScaleExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 */
	void exitScaleExpr(AFSParser.ScaleExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RotateExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 */
	void enterRotateExpr(AFSParser.RotateExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RotateExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 */
	void exitRotateExpr(AFSParser.RotateExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TextExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 */
	void enterTextExpr(AFSParser.TextExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TextExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 */
	void exitTextExpr(AFSParser.TextExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LineExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 */
	void enterLineExpr(AFSParser.LineExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LineExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 */
	void exitLineExpr(AFSParser.LineExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CurveExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 */
	void enterCurveExpr(AFSParser.CurveExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CurveExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 */
	void exitCurveExpr(AFSParser.CurveExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulExpr(AFSParser.MulExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulExpr(AFSParser.MulExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(AFSParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(AFSParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSubExpr(AFSParser.SubExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSubExpr(AFSParser.SubExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NegExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNegExpr(AFSParser.NegExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NegExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNegExpr(AFSParser.NegExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GtExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGtExpr(AFSParser.GtExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GtExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGtExpr(AFSParser.GtExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddExpr(AFSParser.AddExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddExpr(AFSParser.AddExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code String}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterString(AFSParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code String}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitString(AFSParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Double}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterDouble(AFSParser.DoubleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Double}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitDouble(AFSParser.DoubleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Int}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInt(AFSParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInt(AFSParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(AFSParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(AFSParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConcatExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterConcatExpr(AFSParser.ConcatExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConcatExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitConcatExpr(AFSParser.ConcatExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DivExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterDivExpr(AFSParser.DivExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DivExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitDivExpr(AFSParser.DivExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NEgExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNEgExpr(AFSParser.NEgExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NEgExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNEgExpr(AFSParser.NEgExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Bool}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBool(AFSParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Bool}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBool(AFSParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EqExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEqExpr(AFSParser.EqExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EqExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEqExpr(AFSParser.EqExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListIndexExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterListIndexExpr(AFSParser.ListIndexExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListIndexExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitListIndexExpr(AFSParser.ListIndexExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GEqExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGEqExpr(AFSParser.GEqExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GEqExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGEqExpr(AFSParser.GEqExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LsExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLsExpr(AFSParser.LsExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LsExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLsExpr(AFSParser.LsExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterListExpr(AFSParser.ListExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitListExpr(AFSParser.ListExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(AFSParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(AFSParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ID}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterID(AFSParser.IDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ID}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitID(AFSParser.IDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(AFSParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(AFSParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LEqExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLEqExpr(AFSParser.LEqExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LEqExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLEqExpr(AFSParser.LEqExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FuncCallExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFuncCallExpr(AFSParser.FuncCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FuncCallExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFuncCallExpr(AFSParser.FuncCallExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link AFSParser#listAccess}.
	 * @param ctx the parse tree
	 */
	void enterListAccess(AFSParser.ListAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link AFSParser#listAccess}.
	 * @param ctx the parse tree
	 */
	void exitListAccess(AFSParser.ListAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link AFSParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void enterFuncCall(AFSParser.FuncCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link AFSParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void exitFuncCall(AFSParser.FuncCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BoolType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBoolType(AFSParser.BoolTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BoolType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBoolType(AFSParser.BoolTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIntType(AFSParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIntType(AFSParser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 */
	void enterStringType(AFSParser.StringTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 */
	void exitStringType(AFSParser.StringTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DoubleType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 */
	void enterDoubleType(AFSParser.DoubleTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DoubleType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 */
	void exitDoubleType(AFSParser.DoubleTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ShapeType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 */
	void enterShapeType(AFSParser.ShapeTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ShapeType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 */
	void exitShapeType(AFSParser.ShapeTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 */
	void enterListType(AFSParser.ListTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 */
	void exitListType(AFSParser.ListTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VoidType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 */
	void enterVoidType(AFSParser.VoidTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VoidType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 */
	void exitVoidType(AFSParser.VoidTypeContext ctx);
}