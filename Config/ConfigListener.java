// Generated from Config.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ConfigParser}.
 */
public interface ConfigListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ConfigParser#config}.
	 * @param ctx the parse tree
	 */
	void enterConfig(ConfigParser.ConfigContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConfigParser#config}.
	 * @param ctx the parse tree
	 */
	void exitConfig(ConfigParser.ConfigContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConfigParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ConfigParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConfigParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ConfigParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConfigParser#key}.
	 * @param ctx the parse tree
	 */
	void enterKey(ConfigParser.KeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConfigParser#key}.
	 * @param ctx the parse tree
	 */
	void exitKey(ConfigParser.KeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ConfigParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(ConfigParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConfigParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(ConfigParser.ValueContext ctx);
}