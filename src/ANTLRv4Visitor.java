import org.antlr.v4.runtime.misc.NotNull;
import org.apache.log4j.Logger;
import org.dgso.antlrv4parser.ANTLRv4Parser;
import org.dgso.antlrv4parser.ANTLRv4ParserBaseVisitor;

/**
 * ANTLRv4 visitor parser to handle ANTLR text.
 */
public class ANTLRv4Visitor extends ANTLRv4ParserBaseVisitor {

    static Logger logger;

    public ANTLRv4Visitor() {
        logger = Logger.getLogger(ANTLRv4Visitor.class);
    }


    /**
     * Visit a parse tree produced by {@link ANTLRv4Parser#grammarSpec}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Boolean visitGrammarSpec(@NotNull ANTLRv4Parser.GrammarSpecContext ctx){
        logger.debug("* ANTLRv4 Grammar Spec detected");

        visitChildren(ctx);

        return true;
    }

    /**
     * Visit a parse tree produced by {@link ANTLRv4Parser#rules}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Boolean visitRules(@NotNull ANTLRv4Parser.RulesContext ctx) {
        logger.debug("** ANTLRv4 Rule detected");

        visitChildren(ctx);

        return true;
    }

    /**
     * Visit a parse tree produced by {@link ANTLRv4Parser#ruleSpec}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    public Boolean visitRuleSpec(@NotNull ANTLRv4Parser.RuleSpecContext ctx){
        logger.debug("*** ANTLRv4 Rule Spec detected");

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
        logger.debug("**** ANTLRv4 Parser Rule Spec detected");
        logger.debug("**** ANTLRv4 Parser Rule Spec text: " + ctx.getText());
        logger.debug("**** ANTLRv4 Parser Rule Spec RHS: " + ctx.ruleBlock().getText());
        logger.debug("**** ANTLRv4 Parser Rule Spec LHS: " + ctx.getStart().getText());

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
        logger.debug("***** ANTLRv4 Labeled Alternative detected");
        logger.debug("***** ANTLRv4 Labeled Alternative text: " + ctx.getText());

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
        logger.debug("****** ANTLRv4 Alternative detected");
        logger.debug("****** ANTLRv4 Alternative text: " + ctx.getText());

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
        logger.debug("******* ANTLRv4 Element detected");
        logger.debug("******* ANTLRv4 Element text: " + ctx.getText());

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
        logger.debug("****** ANTLRv4 Rule Reference detected");
        logger.debug("****** ANTLRv4 Rule Reference text: " + ctx.getText());

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
        if (ctx.terminal() != null) {
            logger.debug("******* ANTLRv4 Atom detected");
            logger.debug("******* ANTLRv4 Atom text: " + ctx.terminal().STRING_LITERAL());
        }

        visitChildren(ctx);

        return true;
    }
}
