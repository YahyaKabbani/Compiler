// Generated from C:/Users/VISION/Desktop/compiler/Compiler/grammarsHTML/HTMLJinja2Parser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link HTMLJinja2Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface HTMLJinja2ParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link HTMLJinja2Parser#htmlDocument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlDocument(HTMLJinja2Parser.HtmlDocumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLJinja2Parser#htmlElements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlElements(HTMLJinja2Parser.HtmlElementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLJinja2Parser#htmlElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlElement(HTMLJinja2Parser.HtmlElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLJinja2Parser#htmlContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlContent(HTMLJinja2Parser.HtmlContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLJinja2Parser#htmlAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlAttribute(HTMLJinja2Parser.HtmlAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLJinja2Parser#htmlChardata}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlChardata(HTMLJinja2Parser.HtmlChardataContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLJinja2Parser#htmlMisc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlMisc(HTMLJinja2Parser.HtmlMiscContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLJinja2Parser#htmlComment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlComment(HTMLJinja2Parser.HtmlCommentContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLJinja2Parser#script}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScript(HTMLJinja2Parser.ScriptContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLJinja2Parser#style}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStyle(HTMLJinja2Parser.StyleContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLJinja2Parser#jinja}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinja(HTMLJinja2Parser.JinjaContext ctx);
}