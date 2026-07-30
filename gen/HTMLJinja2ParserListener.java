// Generated from D:/Applications/Installed/IntelliJ IDEA Community Edition 2025.2.4/projects/Antlr - Copy/grammarsHTML/HTMLJinja2Parser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HTMLJinja2Parser}.
 */
public interface HTMLJinja2ParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HTMLJinja2Parser#htmlDocument}.
	 * @param ctx the parse tree
	 */
	void enterHtmlDocument(HTMLJinja2Parser.HtmlDocumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLJinja2Parser#htmlDocument}.
	 * @param ctx the parse tree
	 */
	void exitHtmlDocument(HTMLJinja2Parser.HtmlDocumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLJinja2Parser#htmlElements}.
	 * @param ctx the parse tree
	 */
	void enterHtmlElements(HTMLJinja2Parser.HtmlElementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLJinja2Parser#htmlElements}.
	 * @param ctx the parse tree
	 */
	void exitHtmlElements(HTMLJinja2Parser.HtmlElementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLJinja2Parser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void enterHtmlElement(HTMLJinja2Parser.HtmlElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLJinja2Parser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void exitHtmlElement(HTMLJinja2Parser.HtmlElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLJinja2Parser#htmlContent}.
	 * @param ctx the parse tree
	 */
	void enterHtmlContent(HTMLJinja2Parser.HtmlContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLJinja2Parser#htmlContent}.
	 * @param ctx the parse tree
	 */
	void exitHtmlContent(HTMLJinja2Parser.HtmlContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLJinja2Parser#htmlAttribute}.
	 * @param ctx the parse tree
	 */
	void enterHtmlAttribute(HTMLJinja2Parser.HtmlAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLJinja2Parser#htmlAttribute}.
	 * @param ctx the parse tree
	 */
	void exitHtmlAttribute(HTMLJinja2Parser.HtmlAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLJinja2Parser#htmlChardata}.
	 * @param ctx the parse tree
	 */
	void enterHtmlChardata(HTMLJinja2Parser.HtmlChardataContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLJinja2Parser#htmlChardata}.
	 * @param ctx the parse tree
	 */
	void exitHtmlChardata(HTMLJinja2Parser.HtmlChardataContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLJinja2Parser#htmlMisc}.
	 * @param ctx the parse tree
	 */
	void enterHtmlMisc(HTMLJinja2Parser.HtmlMiscContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLJinja2Parser#htmlMisc}.
	 * @param ctx the parse tree
	 */
	void exitHtmlMisc(HTMLJinja2Parser.HtmlMiscContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLJinja2Parser#htmlComment}.
	 * @param ctx the parse tree
	 */
	void enterHtmlComment(HTMLJinja2Parser.HtmlCommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLJinja2Parser#htmlComment}.
	 * @param ctx the parse tree
	 */
	void exitHtmlComment(HTMLJinja2Parser.HtmlCommentContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLJinja2Parser#script}.
	 * @param ctx the parse tree
	 */
	void enterScript(HTMLJinja2Parser.ScriptContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLJinja2Parser#script}.
	 * @param ctx the parse tree
	 */
	void exitScript(HTMLJinja2Parser.ScriptContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLJinja2Parser#style}.
	 * @param ctx the parse tree
	 */
	void enterStyle(HTMLJinja2Parser.StyleContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLJinja2Parser#style}.
	 * @param ctx the parse tree
	 */
	void exitStyle(HTMLJinja2Parser.StyleContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLJinja2Parser#jinja}.
	 * @param ctx the parse tree
	 */
	void enterJinja(HTMLJinja2Parser.JinjaContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLJinja2Parser#jinja}.
	 * @param ctx the parse tree
	 */
	void exitJinja(HTMLJinja2Parser.JinjaContext ctx);
}