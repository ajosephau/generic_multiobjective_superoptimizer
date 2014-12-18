import org.antlr.v4.runtime.misc.NotNull;
import org.dgso.antlrv4parser.ANTLRv4Parser;
import org.dgso.antlrv4parser.ANTLRv4ParserBaseVisitor;

/**
 * ANTLRv4 visitor parser to handle ANTLR text.
 */
public class ANTLRv4Visitor extends ANTLRv4ParserBaseVisitor {
    /**
     * Visit a parse tree produced by {@link ANTLRv4Parser#grammarSpec}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Boolean visitGrammarSpec(@NotNull ANTLRv4Parser.GrammarSpecContext ctx){
        System.out.println("Grammar Spec");

        visitChildren(ctx);

        return true;
    }

    /**
     * Visit a parse tree produced by {@link ANTLRv4Parser#rules}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Boolean visitRules(@NotNull ANTLRv4Parser.RulesContext ctx) {
        System.out.println("Rules");

        visitChildren(ctx);

        return true;
    }

    /**
     * Visit a parse tree produced by {@link ANTLRv4Parser#ruleSpec}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Boolean visitRuleSpec(@NotNull ANTLRv4Parser.RuleSpecContext ctx){
        System.out.println("Rule spec");

        visitChildren(ctx);

        return true;
    }



    /**
     * {@inheritDoc}
     * <p/>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public Boolean visitParserRuleSpec(@NotNull ANTLRv4Parser.ParserRuleSpecContext ctx) {
        System.out.println("ParserRuleSpec Rule text: " + ctx.getText());
        System.out.println("ParserRuleSpec RHS: " + ctx.ruleBlock().getText());
        System.out.println("ParserRuleSpec LHS: " + ctx.getStart().getText());

        visitChildren(ctx);
        return true;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public Boolean visitLabeledAlt(@NotNull ANTLRv4Parser.LabeledAltContext ctx) {
        System.out.println("LabeledAlt Rule text: " + ctx.getText());
        visitChildren(ctx);
        return true;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public Boolean visitAlternative(@NotNull ANTLRv4Parser.AlternativeContext ctx) {
        System.out.println("****");
        System.out.println("Alternative text: " + ctx.getText());
        System.out.println("****");

        visitChildren(ctx);
        return true;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public Boolean visitElement(@NotNull ANTLRv4Parser.ElementContext ctx) {
        System.out.println("Element text: " + ctx.getText());

        visitChildren(ctx);

        return true;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public Boolean visitRuleref(@NotNull ANTLRv4Parser.RulerefContext ctx) {
        System.out.println("RuleRef text: " + ctx.getText());

        visitChildren(ctx);

        return true;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public Boolean visitAtom(@NotNull ANTLRv4Parser.AtomContext ctx) {
//        System.out.println("Atom text: " + ctx.getText());
        if (ctx.terminal() != null) {
            System.out.println("Atom type: " + ctx.terminal().STRING_LITERAL());
        }

        visitChildren(ctx);

        return true;
    }
}
