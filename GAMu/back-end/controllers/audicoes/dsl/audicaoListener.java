// Generated from audicao.g4 by ANTLR 4.4

    import java.io.*;
    import java.util.*;
    import java.lang.*;
    import java.sql.*;
    import java.util.logging.Logger;
    import java.util.logging.Level;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link audicaoParser}.
 */
public interface audicaoListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link audicaoParser#dur}.
	 * @param ctx the parse tree
	 */
	void enterDur(@NotNull audicaoParser.DurContext ctx);
	/**
	 * Exit a parse tree produced by {@link audicaoParser#dur}.
	 * @param ctx the parse tree
	 */
	void exitDur(@NotNull audicaoParser.DurContext ctx);
	/**
	 * Enter a parse tree produced by {@link audicaoParser#caminhobd}.
	 * @param ctx the parse tree
	 */
	void enterCaminhobd(@NotNull audicaoParser.CaminhobdContext ctx);
	/**
	 * Exit a parse tree produced by {@link audicaoParser#caminhobd}.
	 * @param ctx the parse tree
	 */
	void exitCaminhobd(@NotNull audicaoParser.CaminhobdContext ctx);
	/**
	 * Enter a parse tree produced by {@link audicaoParser#bd}.
	 * @param ctx the parse tree
	 */
	void enterBd(@NotNull audicaoParser.BdContext ctx);
	/**
	 * Exit a parse tree produced by {@link audicaoParser#bd}.
	 * @param ctx the parse tree
	 */
	void exitBd(@NotNull audicaoParser.BdContext ctx);
	/**
	 * Enter a parse tree produced by {@link audicaoParser#metaInfo}.
	 * @param ctx the parse tree
	 */
	void enterMetaInfo(@NotNull audicaoParser.MetaInfoContext ctx);
	/**
	 * Exit a parse tree produced by {@link audicaoParser#metaInfo}.
	 * @param ctx the parse tree
	 */
	void exitMetaInfo(@NotNull audicaoParser.MetaInfoContext ctx);
	/**
	 * Enter a parse tree produced by {@link audicaoParser#data}.
	 * @param ctx the parse tree
	 */
	void enterData(@NotNull audicaoParser.DataContext ctx);
	/**
	 * Exit a parse tree produced by {@link audicaoParser#data}.
	 * @param ctx the parse tree
	 */
	void exitData(@NotNull audicaoParser.DataContext ctx);
	/**
	 * Enter a parse tree produced by {@link audicaoParser#dados}.
	 * @param ctx the parse tree
	 */
	void enterDados(@NotNull audicaoParser.DadosContext ctx);
	/**
	 * Exit a parse tree produced by {@link audicaoParser#dados}.
	 * @param ctx the parse tree
	 */
	void exitDados(@NotNull audicaoParser.DadosContext ctx);
	/**
	 * Enter a parse tree produced by {@link audicaoParser#passwd}.
	 * @param ctx the parse tree
	 */
	void enterPasswd(@NotNull audicaoParser.PasswdContext ctx);
	/**
	 * Exit a parse tree produced by {@link audicaoParser#passwd}.
	 * @param ctx the parse tree
	 */
	void exitPasswd(@NotNull audicaoParser.PasswdContext ctx);
	/**
	 * Enter a parse tree produced by {@link audicaoParser#hora}.
	 * @param ctx the parse tree
	 */
	void enterHora(@NotNull audicaoParser.HoraContext ctx);
	/**
	 * Exit a parse tree produced by {@link audicaoParser#hora}.
	 * @param ctx the parse tree
	 */
	void exitHora(@NotNull audicaoParser.HoraContext ctx);
	/**
	 * Enter a parse tree produced by {@link audicaoParser#audicao}.
	 * @param ctx the parse tree
	 */
	void enterAudicao(@NotNull audicaoParser.AudicaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link audicaoParser#audicao}.
	 * @param ctx the parse tree
	 */
	void exitAudicao(@NotNull audicaoParser.AudicaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link audicaoParser#user}.
	 * @param ctx the parse tree
	 */
	void enterUser(@NotNull audicaoParser.UserContext ctx);
	/**
	 * Exit a parse tree produced by {@link audicaoParser#user}.
	 * @param ctx the parse tree
	 */
	void exitUser(@NotNull audicaoParser.UserContext ctx);
	/**
	 * Enter a parse tree produced by {@link audicaoParser#atuacao}.
	 * @param ctx the parse tree
	 */
	void enterAtuacao(@NotNull audicaoParser.AtuacaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link audicaoParser#atuacao}.
	 * @param ctx the parse tree
	 */
	void exitAtuacao(@NotNull audicaoParser.AtuacaoContext ctx);
}