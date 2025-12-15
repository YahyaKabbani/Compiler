// Generated from D:/Applications/Installed/IntelliJ IDEA Community Edition 2025.2.4/projects/Antlr - Copy/grammarPythonFlask/FlaskPythonParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FlaskPythonParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FlaskPythonParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(FlaskPythonParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(FlaskPythonParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#importStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStmt(FlaskPythonParser.ImportStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#fromImportStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFromImportStmt(FlaskPythonParser.FromImportStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(FlaskPythonParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#functionDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDef(FlaskPythonParser.FunctionDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(FlaskPythonParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#decorator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecorator(FlaskPythonParser.DecoratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(FlaskPythonParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#forStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(FlaskPythonParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(FlaskPythonParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuite(FlaskPythonParser.SuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(FlaskPythonParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(FlaskPythonParser.ArgListContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(FlaskPythonParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#dictLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictLiteral(FlaskPythonParser.DictLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#listLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListLiteral(FlaskPythonParser.ListLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link FlaskPythonParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(FlaskPythonParser.ArgumentContext ctx);
}