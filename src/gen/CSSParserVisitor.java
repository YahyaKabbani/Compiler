package gen;// Generated from C:/Users/yazan/OneDrive/Desktop/Compiler/grammersCSS/CSSParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CSSParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CSSParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CSSParser#stylesheet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStylesheet(CSSParser.StylesheetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code goodCharset}
	 * labeled alternative in {@link CSSParser#charset}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGoodCharset(CSSParser.GoodCharsetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code badCharset}
	 * labeled alternative in {@link CSSParser#charset}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBadCharset(CSSParser.BadCharsetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code goodImport}
	 * labeled alternative in {@link CSSParser#imports}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGoodImport(CSSParser.GoodImportContext ctx);
	/**
	 * Visit a parse tree produced by the {@code badImport}
	 * labeled alternative in {@link CSSParser#imports}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBadImport(CSSParser.BadImportContext ctx);
	/**
	 * Visit a parse tree produced by the {@code goodNamespace}
	 * labeled alternative in {@link CSSParser#namespace_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGoodNamespace(CSSParser.GoodNamespaceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code badNamespace}
	 * labeled alternative in {@link CSSParser#namespace_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBadNamespace(CSSParser.BadNamespaceContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#namespacePrefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespacePrefix(CSSParser.NamespacePrefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#media}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMedia(CSSParser.MediaContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#mediaQueryList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMediaQueryList(CSSParser.MediaQueryListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#mediaQuery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMediaQuery(CSSParser.MediaQueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#mediaType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMediaType(CSSParser.MediaTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#mediaExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMediaExpression(CSSParser.MediaExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#mediaFeature}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMediaFeature(CSSParser.MediaFeatureContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#page}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPage(CSSParser.PageContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#pseudoPage}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPseudoPage(CSSParser.PseudoPageContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#selectorGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectorGroup(CSSParser.SelectorGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#selector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelector(CSSParser.SelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#combinator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCombinator(CSSParser.CombinatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#simpleSelectorSequence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleSelectorSequence(CSSParser.SimpleSelectorSequenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#typeSelector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeSelector(CSSParser.TypeSelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#typeNamespacePrefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeNamespacePrefix(CSSParser.TypeNamespacePrefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#elementName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementName(CSSParser.ElementNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#universal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUniversal(CSSParser.UniversalContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#className}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassName(CSSParser.ClassNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#attrib}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrib(CSSParser.AttribContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#pseudo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPseudo(CSSParser.PseudoContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#functionalPseudo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionalPseudo(CSSParser.FunctionalPseudoContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(CSSParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#negation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegation(CSSParser.NegationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#negationArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegationArg(CSSParser.NegationArgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code goodOperator}
	 * labeled alternative in {@link CSSParser#operator_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGoodOperator(CSSParser.GoodOperatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code badOperator}
	 * labeled alternative in {@link CSSParser#operator_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBadOperator(CSSParser.BadOperatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code goodProperty}
	 * labeled alternative in {@link CSSParser#property_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGoodProperty(CSSParser.GoodPropertyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code badProperty}
	 * labeled alternative in {@link CSSParser#property_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBadProperty(CSSParser.BadPropertyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code knownRuleset}
	 * labeled alternative in {@link CSSParser#ruleset}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKnownRuleset(CSSParser.KnownRulesetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unknownRuleset}
	 * labeled alternative in {@link CSSParser#ruleset}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnknownRuleset(CSSParser.UnknownRulesetContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#declarationList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationList(CSSParser.DeclarationListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code knownDeclaration}
	 * labeled alternative in {@link CSSParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKnownDeclaration(CSSParser.KnownDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unknownDeclaration}
	 * labeled alternative in {@link CSSParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnknownDeclaration(CSSParser.UnknownDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#prio}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrio(CSSParser.PrioContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(CSSParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(CSSParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code knownTerm}
	 * labeled alternative in {@link CSSParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKnownTerm(CSSParser.KnownTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unknownTerm}
	 * labeled alternative in {@link CSSParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnknownTerm(CSSParser.UnknownTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code badTerm}
	 * labeled alternative in {@link CSSParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBadTerm(CSSParser.BadTermContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#function_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_(CSSParser.Function_Context ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#dxImageTransform}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDxImageTransform(CSSParser.DxImageTransformContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#hexcolor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHexcolor(CSSParser.HexcolorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(CSSParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#percentage}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPercentage(CSSParser.PercentageContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#dimension}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDimension(CSSParser.DimensionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#unknownDimension}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnknownDimension(CSSParser.UnknownDimensionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#any_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAny_(CSSParser.Any_Context ctx);
	/**
	 * Visit a parse tree produced by the {@code unknownAtRule}
	 * labeled alternative in {@link CSSParser#atRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnknownAtRule(CSSParser.UnknownAtRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#unused}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnused(CSSParser.UnusedContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(CSSParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#nestedStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNestedStatement(CSSParser.NestedStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#groupRuleBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupRuleBody(CSSParser.GroupRuleBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#supportsRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSupportsRule(CSSParser.SupportsRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#supportsCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSupportsCondition(CSSParser.SupportsConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#supportsConditionInParens}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSupportsConditionInParens(CSSParser.SupportsConditionInParensContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#supportsNegation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSupportsNegation(CSSParser.SupportsNegationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#supportsConjunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSupportsConjunction(CSSParser.SupportsConjunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#supportsDisjunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSupportsDisjunction(CSSParser.SupportsDisjunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#supportsDeclarationCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSupportsDeclarationCondition(CSSParser.SupportsDeclarationConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#generalEnclosed}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeneralEnclosed(CSSParser.GeneralEnclosedContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#url}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUrl(CSSParser.UrlContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#var_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_(CSSParser.Var_Context ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#calc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCalc(CSSParser.CalcContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#calcSum}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCalcSum(CSSParser.CalcSumContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#calcProduct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCalcProduct(CSSParser.CalcProductContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#calcValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCalcValue(CSSParser.CalcValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#fontFaceRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFontFaceRule(CSSParser.FontFaceRuleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code knownFontFaceDeclaration}
	 * labeled alternative in {@link CSSParser#fontFaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKnownFontFaceDeclaration(CSSParser.KnownFontFaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unknownFontFaceDeclaration}
	 * labeled alternative in {@link CSSParser#fontFaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnknownFontFaceDeclaration(CSSParser.UnknownFontFaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#keyframesRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyframesRule(CSSParser.KeyframesRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#keyframeBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyframeBlock(CSSParser.KeyframeBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#keyframeSelector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyframeSelector(CSSParser.KeyframeSelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#viewport}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitViewport(CSSParser.ViewportContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#counterStyle}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCounterStyle(CSSParser.CounterStyleContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#fontFeatureValuesRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFontFeatureValuesRule(CSSParser.FontFeatureValuesRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#fontFamilyNameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFontFamilyNameList(CSSParser.FontFamilyNameListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#fontFamilyName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFontFamilyName(CSSParser.FontFamilyNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#featureValueBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFeatureValueBlock(CSSParser.FeatureValueBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#featureType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFeatureType(CSSParser.FeatureTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#featureValueDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFeatureValueDefinition(CSSParser.FeatureValueDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdent(CSSParser.IdentContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSSParser#ws}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWs(CSSParser.WsContext ctx);
}