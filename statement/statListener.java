// Generated from stat.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link statParser}.
 */
public interface statListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link statParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(statParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link statParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(statParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link statParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(statParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link statParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(statParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link statParser#key}.
	 * @param ctx the parse tree
	 */
	void enterKey(statParser.KeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link statParser#key}.
	 * @param ctx the parse tree
	 */
	void exitKey(statParser.KeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link statParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(statParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link statParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(statParser.ValueContext ctx);
}