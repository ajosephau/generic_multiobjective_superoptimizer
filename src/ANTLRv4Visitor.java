import org.antlr.v4.runtime.misc.NotNull;
import org.dgso.antlrv4parser.ANTLRv4Parser;
import org.dgso.antlrv4parser.ANTLRv4ParserBaseVisitor;

/**
 * ANTLRv4 visitor parser to handle ANTLR text.
 */
public class ANTLRv4Visitor extends ANTLRv4ParserBaseVisitor {
    @Override
    public Boolean visitGrammarSpec(ANTLRv4Parser.GrammarSpecContext ctx) {
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
        System.out.println("Atom text: " + ctx.getText());
        if (ctx.terminal() != null) {
            System.out.println("Atom type: " + ctx.terminal().STRING_LITERAL());
        }

        visitChildren(ctx);

        return true;
    }
}
