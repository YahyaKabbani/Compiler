// Generated from C:/Users/yazan/OneDrive/Desktop/Compiler/grammersCSS/CSSParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CSSParser}.
 */
public interface CSSParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void enterStylesheet(CSSParser.StylesheetContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void exitStylesheet(CSSParser.StylesheetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code goodCharset}
	 * labeled alternative in {@link CSSParser#charset}.
	 * @param ctx the parse tree
	 */
	void enterGoodCharset(CSSParser.GoodCharsetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code goodCharset}
	 * labeled alternative in {@link CSSParser#charset}.
	 * @param ctx the parse tree
	 */
	void exitGoodCharset(CSSParser.GoodCharsetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code badCharset}
	 * labeled alternative in {@link CSSParser#charset}.
	 * @param ctx the parse tree
	 */
	void enterBadCharset(CSSParser.BadCharsetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code badCharset}
	 * labeled alternative in {@link CSSParser#charset}.
	 * @param ctx the parse tree
	 */
	void exitBadCharset(CSSParser.BadCharsetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code goodImport}
	 * labeled alternative in {@link CSSParser#imports}.
	 * @param ctx the parse tree
	 */
	void enterGoodImport(CSSParser.GoodImportContext ctx);
	/**
	 * Exit a parse tree produced by the {@code goodImport}
	 * labeled alternative in {@link CSSParser#imports}.
	 * @param ctx the parse tree
	 */
	void exitGoodImport(CSSParser.GoodImportContext ctx);
	/**
	 * Enter a parse tree produced by the {@code badImport}
	 * labeled alternative in {@link CSSParser#imports}.
	 * @param ctx the parse tree
	 */
	void enterBadImport(CSSParser.BadImportContext ctx);
	/**
	 * Exit a parse tree produced by the {@code badImport}
	 * labeled alternative in {@link CSSParser#imports}.
	 * @param ctx the parse tree
	 */
	void exitBadImport(CSSParser.BadImportContext ctx);
	/**
	 * Enter a parse tree produced by the {@code goodNamespace}
	 * labeled alternative in {@link CSSParser#namespace_}.
	 * @param ctx the parse tree
	 */
	void enterGoodNamespace(CSSParser.GoodNamespaceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code goodNamespace}
	 * labeled alternative in {@link CSSParser#namespace_}.
	 * @param ctx the parse tree
	 */
	void exitGoodNamespace(CSSParser.GoodNamespaceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code badNamespace}
	 * labeled alternative in {@link CSSParser#namespace_}.
	 * @param ctx the parse tree
	 */
	void enterBadNamespace(CSSParser.BadNamespaceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code badNamespace}
	 * labeled alternative in {@link CSSParser#namespace_}.
	 * @param ctx the parse tree
	 */
	void exitBadNamespace(CSSParser.BadNamespaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#namespacePrefix}.
	 * @param ctx the parse tree
	 */
	void enterNamespacePrefix(CSSParser.NamespacePrefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#namespacePrefix}.
	 * @param ctx the parse tree
	 */
	void exitNamespacePrefix(CSSParser.NamespacePrefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#media}.
	 * @param ctx the parse tree
	 */
	void enterMedia(CSSParser.MediaContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#media}.
	 * @param ctx the parse tree
	 */
	void exitMedia(CSSParser.MediaContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#mediaQueryList}.
	 * @param ctx the parse tree
	 */
	void enterMediaQueryList(CSSParser.MediaQueryListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#mediaQueryList}.
	 * @param ctx the parse tree
	 */
	void exitMediaQueryList(CSSParser.MediaQueryListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#mediaQuery}.
	 * @param ctx the parse tree
	 */
	void enterMediaQuery(CSSParser.MediaQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#mediaQuery}.
	 * @param ctx the parse tree
	 */
	void exitMediaQuery(CSSParser.MediaQueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#mediaType}.
	 * @param ctx the parse tree
	 */
	void enterMediaType(CSSParser.MediaTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#mediaType}.
	 * @param ctx the parse tree
	 */
	void exitMediaType(CSSParser.MediaTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#mediaExpression}.
	 * @param ctx the parse tree
	 */
	void enterMediaExpression(CSSParser.MediaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#mediaExpression}.
	 * @param ctx the parse tree
	 */
	void exitMediaExpression(CSSParser.MediaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#mediaFeature}.
	 * @param ctx the parse tree
	 */
	void enterMediaFeature(CSSParser.MediaFeatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#mediaFeature}.
	 * @param ctx the parse tree
	 */
	void exitMediaFeature(CSSParser.MediaFeatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#page}.
	 * @param ctx the parse tree
	 */
	void enterPage(CSSParser.PageContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#page}.
	 * @param ctx the parse tree
	 */
	void exitPage(CSSParser.PageContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#pseudoPage}.
	 * @param ctx the parse tree
	 */
	void enterPseudoPage(CSSParser.PseudoPageContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#pseudoPage}.
	 * @param ctx the parse tree
	 */
	void exitPseudoPage(CSSParser.PseudoPageContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#selectorGroup}.
	 * @param ctx the parse tree
	 */
	void enterSelectorGroup(CSSParser.SelectorGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#selectorGroup}.
	 * @param ctx the parse tree
	 */
	void exitSelectorGroup(CSSParser.SelectorGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void enterSelector(CSSParser.SelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void exitSelector(CSSParser.SelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#combinator}.
	 * @param ctx the parse tree
	 */
	void enterCombinator(CSSParser.CombinatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#combinator}.
	 * @param ctx the parse tree
	 */
	void exitCombinator(CSSParser.CombinatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#simpleSelectorSequence}.
	 * @param ctx the parse tree
	 */
	void enterSimpleSelectorSequence(CSSParser.SimpleSelectorSequenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#simpleSelectorSequence}.
	 * @param ctx the parse tree
	 */
	void exitSimpleSelectorSequence(CSSParser.SimpleSelectorSequenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#typeSelector}.
	 * @param ctx the parse tree
	 */
	void enterTypeSelector(CSSParser.TypeSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#typeSelector}.
	 * @param ctx the parse tree
	 */
	void exitTypeSelector(CSSParser.TypeSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#typeNamespacePrefix}.
	 * @param ctx the parse tree
	 */
	void enterTypeNamespacePrefix(CSSParser.TypeNamespacePrefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#typeNamespacePrefix}.
	 * @param ctx the parse tree
	 */
	void exitTypeNamespacePrefix(CSSParser.TypeNamespacePrefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#elementName}.
	 * @param ctx the parse tree
	 */
	void enterElementName(CSSParser.ElementNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#elementName}.
	 * @param ctx the parse tree
	 */
	void exitElementName(CSSParser.ElementNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#universal}.
	 * @param ctx the parse tree
	 */
	void enterUniversal(CSSParser.UniversalContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#universal}.
	 * @param ctx the parse tree
	 */
	void exitUniversal(CSSParser.UniversalContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#className}.
	 * @param ctx the parse tree
	 */
	void enterClassName(CSSParser.ClassNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#className}.
	 * @param ctx the parse tree
	 */
	void exitClassName(CSSParser.ClassNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#attrib}.
	 * @param ctx the parse tree
	 */
	void enterAttrib(CSSParser.AttribContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#attrib}.
	 * @param ctx the parse tree
	 */
	void exitAttrib(CSSParser.AttribContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#pseudo}.
	 * @param ctx the parse tree
	 */
	void enterPseudo(CSSParser.PseudoContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#pseudo}.
	 * @param ctx the parse tree
	 */
	void exitPseudo(CSSParser.PseudoContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#functionalPseudo}.
	 * @param ctx the parse tree
	 */
	void enterFunctionalPseudo(CSSParser.FunctionalPseudoContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#functionalPseudo}.
	 * @param ctx the parse tree
	 */
	void exitFunctionalPseudo(CSSParser.FunctionalPseudoContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(CSSParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(CSSParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#negation}.
	 * @param ctx the parse tree
	 */
	void enterNegation(CSSParser.NegationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#negation}.
	 * @param ctx the parse tree
	 */
	void exitNegation(CSSParser.NegationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#negationArg}.
	 * @param ctx the parse tree
	 */
	void enterNegationArg(CSSParser.NegationArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#negationArg}.
	 * @param ctx the parse tree
	 */
	void exitNegationArg(CSSParser.NegationArgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code goodOperator}
	 * labeled alternative in {@link CSSParser#operator_}.
	 * @param ctx the parse tree
	 */
	void enterGoodOperator(CSSParser.GoodOperatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code goodOperator}
	 * labeled alternative in {@link CSSParser#operator_}.
	 * @param ctx the parse tree
	 */
	void exitGoodOperator(CSSParser.GoodOperatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code badOperator}
	 * labeled alternative in {@link CSSParser#operator_}.
	 * @param ctx the parse tree
	 */
	void enterBadOperator(CSSParser.BadOperatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code badOperator}
	 * labeled alternative in {@link CSSParser#operator_}.
	 * @param ctx the parse tree
	 */
	void exitBadOperator(CSSParser.BadOperatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code goodProperty}
	 * labeled alternative in {@link CSSParser#property_}.
	 * @param ctx the parse tree
	 */
	void enterGoodProperty(CSSParser.GoodPropertyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code goodProperty}
	 * labeled alternative in {@link CSSParser#property_}.
	 * @param ctx the parse tree
	 */
	void exitGoodProperty(CSSParser.GoodPropertyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code badProperty}
	 * labeled alternative in {@link CSSParser#property_}.
	 * @param ctx the parse tree
	 */
	void enterBadProperty(CSSParser.BadPropertyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code badProperty}
	 * labeled alternative in {@link CSSParser#property_}.
	 * @param ctx the parse tree
	 */
	void exitBadProperty(CSSParser.BadPropertyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code knownRuleset}
	 * labeled alternative in {@link CSSParser#ruleset}.
	 * @param ctx the parse tree
	 */
	void enterKnownRuleset(CSSParser.KnownRulesetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code knownRuleset}
	 * labeled alternative in {@link CSSParser#ruleset}.
	 * @param ctx the parse tree
	 */
	void exitKnownRuleset(CSSParser.KnownRulesetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unknownRuleset}
	 * labeled alternative in {@link CSSParser#ruleset}.
	 * @param ctx the parse tree
	 */
	void enterUnknownRuleset(CSSParser.UnknownRulesetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unknownRuleset}
	 * labeled alternative in {@link CSSParser#ruleset}.
	 * @param ctx the parse tree
	 */
	void exitUnknownRuleset(CSSParser.UnknownRulesetContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#declarationList}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationList(CSSParser.DeclarationListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#declarationList}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationList(CSSParser.DeclarationListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code knownDeclaration}
	 * labeled alternative in {@link CSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterKnownDeclaration(CSSParser.KnownDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code knownDeclaration}
	 * labeled alternative in {@link CSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitKnownDeclaration(CSSParser.KnownDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unknownDeclaration}
	 * labeled alternative in {@link CSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterUnknownDeclaration(CSSParser.UnknownDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unknownDeclaration}
	 * labeled alternative in {@link CSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitUnknownDeclaration(CSSParser.UnknownDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#prio}.
	 * @param ctx the parse tree
	 */
	void enterPrio(CSSParser.PrioContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#prio}.
	 * @param ctx the parse tree
	 */
	void exitPrio(CSSParser.PrioContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(CSSParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(CSSParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(CSSParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(CSSParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code knownTerm}
	 * labeled alternative in {@link CSSParser#term}.
	 * @param ctx the parse tree
	 */
	void enterKnownTerm(CSSParser.KnownTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code knownTerm}
	 * labeled alternative in {@link CSSParser#term}.
	 * @param ctx the parse tree
	 */
	void exitKnownTerm(CSSParser.KnownTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unknownTerm}
	 * labeled alternative in {@link CSSParser#term}.
	 * @param ctx the parse tree
	 */
	void enterUnknownTerm(CSSParser.UnknownTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unknownTerm}
	 * labeled alternative in {@link CSSParser#term}.
	 * @param ctx the parse tree
	 */
	void exitUnknownTerm(CSSParser.UnknownTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code badTerm}
	 * labeled alternative in {@link CSSParser#term}.
	 * @param ctx the parse tree
	 */
	void enterBadTerm(CSSParser.BadTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code badTerm}
	 * labeled alternative in {@link CSSParser#term}.
	 * @param ctx the parse tree
	 */
	void exitBadTerm(CSSParser.BadTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#function_}.
	 * @param ctx the parse tree
	 */
	void enterFunction_(CSSParser.Function_Context ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#function_}.
	 * @param ctx the parse tree
	 */
	void exitFunction_(CSSParser.Function_Context ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#dxImageTransform}.
	 * @param ctx the parse tree
	 */
	void enterDxImageTransform(CSSParser.DxImageTransformContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#dxImageTransform}.
	 * @param ctx the parse tree
	 */
	void exitDxImageTransform(CSSParser.DxImageTransformContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#hexcolor}.
	 * @param ctx the parse tree
	 */
	void enterHexcolor(CSSParser.HexcolorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#hexcolor}.
	 * @param ctx the parse tree
	 */
	void exitHexcolor(CSSParser.HexcolorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(CSSParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(CSSParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#percentage}.
	 * @param ctx the parse tree
	 */
	void enterPercentage(CSSParser.PercentageContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#percentage}.
	 * @param ctx the parse tree
	 */
	void exitPercentage(CSSParser.PercentageContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#dimension}.
	 * @param ctx the parse tree
	 */
	void enterDimension(CSSParser.DimensionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#dimension}.
	 * @param ctx the parse tree
	 */
	void exitDimension(CSSParser.DimensionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#unknownDimension}.
	 * @param ctx the parse tree
	 */
	void enterUnknownDimension(CSSParser.UnknownDimensionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#unknownDimension}.
	 * @param ctx the parse tree
	 */
	void exitUnknownDimension(CSSParser.UnknownDimensionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#any_}.
	 * @param ctx the parse tree
	 */
	void enterAny_(CSSParser.Any_Context ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#any_}.
	 * @param ctx the parse tree
	 */
	void exitAny_(CSSParser.Any_Context ctx);
	/**
	 * Enter a parse tree produced by the {@code unknownAtRule}
	 * labeled alternative in {@link CSSParser#atRule}.
	 * @param ctx the parse tree
	 */
	void enterUnknownAtRule(CSSParser.UnknownAtRuleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unknownAtRule}
	 * labeled alternative in {@link CSSParser#atRule}.
	 * @param ctx the parse tree
	 */
	void exitUnknownAtRule(CSSParser.UnknownAtRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#unused}.
	 * @param ctx the parse tree
	 */
	void enterUnused(CSSParser.UnusedContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#unused}.
	 * @param ctx the parse tree
	 */
	void exitUnused(CSSParser.UnusedContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(CSSParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(CSSParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#nestedStatement}.
	 * @param ctx the parse tree
	 */
	void enterNestedStatement(CSSParser.NestedStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#nestedStatement}.
	 * @param ctx the parse tree
	 */
	void exitNestedStatement(CSSParser.NestedStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#groupRuleBody}.
	 * @param ctx the parse tree
	 */
	void enterGroupRuleBody(CSSParser.GroupRuleBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#groupRuleBody}.
	 * @param ctx the parse tree
	 */
	void exitGroupRuleBody(CSSParser.GroupRuleBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#supportsRule}.
	 * @param ctx the parse tree
	 */
	void enterSupportsRule(CSSParser.SupportsRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#supportsRule}.
	 * @param ctx the parse tree
	 */
	void exitSupportsRule(CSSParser.SupportsRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#supportsCondition}.
	 * @param ctx the parse tree
	 */
	void enterSupportsCondition(CSSParser.SupportsConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#supportsCondition}.
	 * @param ctx the parse tree
	 */
	void exitSupportsCondition(CSSParser.SupportsConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#supportsConditionInParens}.
	 * @param ctx the parse tree
	 */
	void enterSupportsConditionInParens(CSSParser.SupportsConditionInParensContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#supportsConditionInParens}.
	 * @param ctx the parse tree
	 */
	void exitSupportsConditionInParens(CSSParser.SupportsConditionInParensContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#supportsNegation}.
	 * @param ctx the parse tree
	 */
	void enterSupportsNegation(CSSParser.SupportsNegationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#supportsNegation}.
	 * @param ctx the parse tree
	 */
	void exitSupportsNegation(CSSParser.SupportsNegationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#supportsConjunction}.
	 * @param ctx the parse tree
	 */
	void enterSupportsConjunction(CSSParser.SupportsConjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#supportsConjunction}.
	 * @param ctx the parse tree
	 */
	void exitSupportsConjunction(CSSParser.SupportsConjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#supportsDisjunction}.
	 * @param ctx the parse tree
	 */
	void enterSupportsDisjunction(CSSParser.SupportsDisjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#supportsDisjunction}.
	 * @param ctx the parse tree
	 */
	void exitSupportsDisjunction(CSSParser.SupportsDisjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#supportsDeclarationCondition}.
	 * @param ctx the parse tree
	 */
	void enterSupportsDeclarationCondition(CSSParser.SupportsDeclarationConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#supportsDeclarationCondition}.
	 * @param ctx the parse tree
	 */
	void exitSupportsDeclarationCondition(CSSParser.SupportsDeclarationConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#generalEnclosed}.
	 * @param ctx the parse tree
	 */
	void enterGeneralEnclosed(CSSParser.GeneralEnclosedContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#generalEnclosed}.
	 * @param ctx the parse tree
	 */
	void exitGeneralEnclosed(CSSParser.GeneralEnclosedContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#url}.
	 * @param ctx the parse tree
	 */
	void enterUrl(CSSParser.UrlContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#url}.
	 * @param ctx the parse tree
	 */
	void exitUrl(CSSParser.UrlContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#var_}.
	 * @param ctx the parse tree
	 */
	void enterVar_(CSSParser.Var_Context ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#var_}.
	 * @param ctx the parse tree
	 */
	void exitVar_(CSSParser.Var_Context ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#calc}.
	 * @param ctx the parse tree
	 */
	void enterCalc(CSSParser.CalcContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#calc}.
	 * @param ctx the parse tree
	 */
	void exitCalc(CSSParser.CalcContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#calcSum}.
	 * @param ctx the parse tree
	 */
	void enterCalcSum(CSSParser.CalcSumContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#calcSum}.
	 * @param ctx the parse tree
	 */
	void exitCalcSum(CSSParser.CalcSumContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#calcProduct}.
	 * @param ctx the parse tree
	 */
	void enterCalcProduct(CSSParser.CalcProductContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#calcProduct}.
	 * @param ctx the parse tree
	 */
	void exitCalcProduct(CSSParser.CalcProductContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#calcValue}.
	 * @param ctx the parse tree
	 */
	void enterCalcValue(CSSParser.CalcValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#calcValue}.
	 * @param ctx the parse tree
	 */
	void exitCalcValue(CSSParser.CalcValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#fontFaceRule}.
	 * @param ctx the parse tree
	 */
	void enterFontFaceRule(CSSParser.FontFaceRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#fontFaceRule}.
	 * @param ctx the parse tree
	 */
	void exitFontFaceRule(CSSParser.FontFaceRuleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code knownFontFaceDeclaration}
	 * labeled alternative in {@link CSSParser#fontFaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterKnownFontFaceDeclaration(CSSParser.KnownFontFaceDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code knownFontFaceDeclaration}
	 * labeled alternative in {@link CSSParser#fontFaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitKnownFontFaceDeclaration(CSSParser.KnownFontFaceDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unknownFontFaceDeclaration}
	 * labeled alternative in {@link CSSParser#fontFaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterUnknownFontFaceDeclaration(CSSParser.UnknownFontFaceDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unknownFontFaceDeclaration}
	 * labeled alternative in {@link CSSParser#fontFaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitUnknownFontFaceDeclaration(CSSParser.UnknownFontFaceDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#keyframesRule}.
	 * @param ctx the parse tree
	 */
	void enterKeyframesRule(CSSParser.KeyframesRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#keyframesRule}.
	 * @param ctx the parse tree
	 */
	void exitKeyframesRule(CSSParser.KeyframesRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#keyframeBlock}.
	 * @param ctx the parse tree
	 */
	void enterKeyframeBlock(CSSParser.KeyframeBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#keyframeBlock}.
	 * @param ctx the parse tree
	 */
	void exitKeyframeBlock(CSSParser.KeyframeBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#keyframeSelector}.
	 * @param ctx the parse tree
	 */
	void enterKeyframeSelector(CSSParser.KeyframeSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#keyframeSelector}.
	 * @param ctx the parse tree
	 */
	void exitKeyframeSelector(CSSParser.KeyframeSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#viewport}.
	 * @param ctx the parse tree
	 */
	void enterViewport(CSSParser.ViewportContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#viewport}.
	 * @param ctx the parse tree
	 */
	void exitViewport(CSSParser.ViewportContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#counterStyle}.
	 * @param ctx the parse tree
	 */
	void enterCounterStyle(CSSParser.CounterStyleContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#counterStyle}.
	 * @param ctx the parse tree
	 */
	void exitCounterStyle(CSSParser.CounterStyleContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#fontFeatureValuesRule}.
	 * @param ctx the parse tree
	 */
	void enterFontFeatureValuesRule(CSSParser.FontFeatureValuesRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#fontFeatureValuesRule}.
	 * @param ctx the parse tree
	 */
	void exitFontFeatureValuesRule(CSSParser.FontFeatureValuesRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#fontFamilyNameList}.
	 * @param ctx the parse tree
	 */
	void enterFontFamilyNameList(CSSParser.FontFamilyNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#fontFamilyNameList}.
	 * @param ctx the parse tree
	 */
	void exitFontFamilyNameList(CSSParser.FontFamilyNameListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#fontFamilyName}.
	 * @param ctx the parse tree
	 */
	void enterFontFamilyName(CSSParser.FontFamilyNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#fontFamilyName}.
	 * @param ctx the parse tree
	 */
	void exitFontFamilyName(CSSParser.FontFamilyNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#featureValueBlock}.
	 * @param ctx the parse tree
	 */
	void enterFeatureValueBlock(CSSParser.FeatureValueBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#featureValueBlock}.
	 * @param ctx the parse tree
	 */
	void exitFeatureValueBlock(CSSParser.FeatureValueBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#featureType}.
	 * @param ctx the parse tree
	 */
	void enterFeatureType(CSSParser.FeatureTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#featureType}.
	 * @param ctx the parse tree
	 */
	void exitFeatureType(CSSParser.FeatureTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#featureValueDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFeatureValueDefinition(CSSParser.FeatureValueDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#featureValueDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFeatureValueDefinition(CSSParser.FeatureValueDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#ident}.
	 * @param ctx the parse tree
	 */
	void enterIdent(CSSParser.IdentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#ident}.
	 * @param ctx the parse tree
	 */
	void exitIdent(CSSParser.IdentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSSParser#ws}.
	 * @param ctx the parse tree
	 */
	void enterWs(CSSParser.WsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSSParser#ws}.
	 * @param ctx the parse tree
	 */
	void exitWs(CSSParser.WsContext ctx);
}