// Generated from AFS.g4 by ANTLR 4.13.2

    package afs;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AFSParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AFSVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code Main}
	 * labeled alternative in {@link AFSParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMain(AFSParser.MainContext ctx);
	/**
	 * Visit a parse tree produced by {@link AFSParser#visStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVisStmt(AFSParser.VisStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EventFunc}
	 * labeled alternative in {@link AFSParser#event}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEventFunc(AFSParser.EventFuncContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FnDef}
	 * labeled alternative in {@link AFSParser#def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFnDef(AFSParser.FnDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VisDef}
	 * labeled alternative in {@link AFSParser#def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVisDef(AFSParser.VisDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VarDef}
	 * labeled alternative in {@link AFSParser#def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDef(AFSParser.VarDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AFSParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(AFSParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DeclStmtDef}
	 * labeled alternative in {@link AFSParser#declStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclStmtDef(AFSParser.DeclStmtDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DeclStmtIf}
	 * labeled alternative in {@link AFSParser#declStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclStmtIf(AFSParser.DeclStmtIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DeclStmtFuncCall}
	 * labeled alternative in {@link AFSParser#declStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclStmtFuncCall(AFSParser.DeclStmtFuncCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link AFSParser#declBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclBlock(AFSParser.DeclBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImpStmtDef}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImpStmtDef(AFSParser.ImpStmtDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImpStmtAss}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImpStmtAss(AFSParser.ImpStmtAssContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImpStmtListAss}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImpStmtListAss(AFSParser.ImpStmtListAssContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImpStmtIf}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImpStmtIf(AFSParser.ImpStmtIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImpStmtWhl}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImpStmtWhl(AFSParser.ImpStmtWhlContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImpStmtRtn}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImpStmtRtn(AFSParser.ImpStmtRtnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImpStmtFuncCall}
	 * labeled alternative in {@link AFSParser#impStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImpStmtFuncCall(AFSParser.ImpStmtFuncCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link AFSParser#impBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImpBlock(AFSParser.ImpBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImpExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImpExpr(AFSParser.ImpExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PlaceExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlaceExpr(AFSParser.PlaceExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ScaleExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScaleExpr(AFSParser.ScaleExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RotateExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRotateExpr(AFSParser.RotateExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TextExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTextExpr(AFSParser.TextExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LineExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLineExpr(AFSParser.LineExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CurveExpr}
	 * labeled alternative in {@link AFSParser#declExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurveExpr(AFSParser.CurveExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulExpr(AFSParser.MulExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(AFSParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubExpr(AFSParser.SubExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NegExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegExpr(AFSParser.NegExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GtExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGtExpr(AFSParser.GtExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExpr(AFSParser.AddExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code String}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(AFSParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Double}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDouble(AFSParser.DoubleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(AFSParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(AFSParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConcatExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConcatExpr(AFSParser.ConcatExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DivExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivExpr(AFSParser.DivExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NEgExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNEgExpr(AFSParser.NEgExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Bool}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(AFSParser.BoolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqExpr(AFSParser.EqExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListIndexExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListIndexExpr(AFSParser.ListIndexExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GEqExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGEqExpr(AFSParser.GEqExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LsExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLsExpr(AFSParser.LsExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListExpr(AFSParser.ListExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(AFSParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ID}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitID(AFSParser.IDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(AFSParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LEqExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLEqExpr(AFSParser.LEqExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FuncCallExpr}
	 * labeled alternative in {@link AFSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCallExpr(AFSParser.FuncCallExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link AFSParser#listAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListAccess(AFSParser.ListAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link AFSParser#funcCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(AFSParser.FuncCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(AFSParser.BoolTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(AFSParser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringType(AFSParser.StringTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DoubleType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleType(AFSParser.DoubleTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ShapeType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShapeType(AFSParser.ShapeTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListType(AFSParser.ListTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VoidType}
	 * labeled alternative in {@link AFSParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVoidType(AFSParser.VoidTypeContext ctx);
}