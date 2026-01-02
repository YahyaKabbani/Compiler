// Generated from C:/Users/VISION/Desktop/compiler/Compiler/grammarPythonFlask/FlaskPythonParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FlaskPythonParser}.
 */
public interface FlaskPythonParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(FlaskPythonParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(FlaskPythonParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(FlaskPythonParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(FlaskPythonParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#simpleStmt}.
	 * @param ctx the parse tree
	 */
	void enterSimpleStmt(FlaskPythonParser.SimpleStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#simpleStmt}.
	 * @param ctx the parse tree
	 */
	void exitSimpleStmt(FlaskPythonParser.SimpleStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#compoundStmt}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStmt(FlaskPythonParser.CompoundStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#compoundStmt}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStmt(FlaskPythonParser.CompoundStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#decoratedDef}.
	 * @param ctx the parse tree
	 */
	void enterDecoratedDef(FlaskPythonParser.DecoratedDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#decoratedDef}.
	 * @param ctx the parse tree
	 */
	void exitDecoratedDef(FlaskPythonParser.DecoratedDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#decorator}.
	 * @param ctx the parse tree
	 */
	void enterDecorator(FlaskPythonParser.DecoratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#decorator}.
	 * @param ctx the parse tree
	 */
	void exitDecorator(FlaskPythonParser.DecoratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#importStmt}.
	 * @param ctx the parse tree
	 */
	void enterImportStmt(FlaskPythonParser.ImportStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#importStmt}.
	 * @param ctx the parse tree
	 */
	void exitImportStmt(FlaskPythonParser.ImportStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#fromImportStmt}.
	 * @param ctx the parse tree
	 */
	void enterFromImportStmt(FlaskPythonParser.FromImportStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#fromImportStmt}.
	 * @param ctx the parse tree
	 */
	void exitFromImportStmt(FlaskPythonParser.FromImportStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(FlaskPythonParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(FlaskPythonParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#functionDef}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDef(FlaskPythonParser.FunctionDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#functionDef}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDef(FlaskPythonParser.FunctionDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(FlaskPythonParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(FlaskPythonParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(FlaskPythonParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(FlaskPythonParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(FlaskPythonParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(FlaskPythonParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(FlaskPythonParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(FlaskPythonParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#suite}.
	 * @param ctx the parse tree
	 */
	void enterSuite(FlaskPythonParser.SuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#suite}.
	 * @param ctx the parse tree
	 */
	void exitSuite(FlaskPythonParser.SuiteContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(FlaskPythonParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(FlaskPythonParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(FlaskPythonParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(FlaskPythonParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(FlaskPythonParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(FlaskPythonParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(FlaskPythonParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(FlaskPythonParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#dictLiteral}.
	 * @param ctx the parse tree
	 */
	void enterDictLiteral(FlaskPythonParser.DictLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#dictLiteral}.
	 * @param ctx the parse tree
	 */
	void exitDictLiteral(FlaskPythonParser.DictLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#dictEntry}.
	 * @param ctx the parse tree
	 */
	void enterDictEntry(FlaskPythonParser.DictEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#dictEntry}.
	 * @param ctx the parse tree
	 */
	void exitDictEntry(FlaskPythonParser.DictEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlaskPythonParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void enterListLiteral(FlaskPythonParser.ListLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskPythonParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void exitListLiteral(FlaskPythonParser.ListLiteralContext ctx);
}