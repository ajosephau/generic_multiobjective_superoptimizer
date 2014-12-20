package org.dgso.superoptimizer;

import org.antlr.v4.runtime.misc.NotNull;
import org.apache.log4j.Logger;
import org.dgso.antlrv4parser.ANTLRv4Parser;
import org.dgso.antlrv4parser.ANTLRv4ParserBaseVisitor;

import java.util.ArrayList;

/**
 * ANTLRv4 visitor parser to handle ANTLR text.
 */
public class ANTLRv4Visitor extends ANTLRv4ParserBaseVisitor {

    private ArrayList<GrammarClass> grammarObjects;
    private String currentGrammarSpec;
    private String currentParserRuleSpec;

    private Logger logger;

    public ANTLRv4Visitor() {
        logger = Logger.getLogger(ANTLRv4Visitor.class);
        grammarObjects = new ArrayList<GrammarClass>();
        currentGrammarSpec = "";
        currentParserRuleSpec = "";

    }

    /**
     * Visit a parse tree produced by {@link org.dgso.antlrv4parser.ANTLRv4Parser#grammarSpec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    public ArrayList<GrammarClass> visitGrammarSpec(@NotNull ANTLRv4Parser.GrammarSpecContext ctx) {
        currentGrammarSpec = ctx.id().getText();

        logger.debug("* ANTLRv4 Grammar Spec detected");
        logger.debug("* ANTLRv4 Grammar Spec text: " + currentGrammarSpec);

        GrammarClass grammarSpec = new GrammarClass(currentGrammarSpec, "", GrammarType.ANTLR_GRAMMAR_SPEC);
        grammarObjects.add(grammarSpec);

        visitChildren(ctx);

        return grammarObjects;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ArrayList<GrammarClass> visitParserRuleSpec(@NotNull ANTLRv4Parser.ParserRuleSpecContext ctx) {
        currentParserRuleSpec = ctx.getStart().getText();

        logger.debug("**** ANTLRv4 Parser Rule Spec detected");
        logger.debug("**** ANTLRv4 Parser Rule Spec text: " + ctx.getText());
        logger.debug("**** ANTLRv4 Parser Rule Spec RHS: " + ctx.ruleBlock().getText());
        logger.debug("**** ANTLRv4 Parser Rule Spec LHS: " + ctx.getStart().getText());
        logger.debug("**** ANTLRv4 Parser Rule Spec parent: " + ctx.getText());

        GrammarClass parserRuleSpec = new GrammarClass(currentParserRuleSpec, currentGrammarSpec, GrammarType.PARSER_RULE_SPEC);
        grammarObjects.add(parserRuleSpec);

        visitChildren(ctx);

        return grammarObjects;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ArrayList<GrammarClass> visitRuleref(@NotNull ANTLRv4Parser.RulerefContext ctx) {
        String ruleRefName = ctx.getText();

        logger.debug("****** ANTLRv4 Rule Reference detected");
        logger.debug("****** ANTLRv4 Rule Reference text: " + ruleRefName);

        GrammarClass grammarSpec = new GrammarClass(ruleRefName, currentParserRuleSpec, GrammarType.RULE_REFERENCE);
        grammarObjects.add(grammarSpec);

        visitChildren(ctx);

        return grammarObjects;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ArrayList<GrammarClass> visitAtom(@NotNull ANTLRv4Parser.AtomContext ctx) {
        if (ctx.terminal() != null) {
            String atomName = ctx.getText();

            logger.debug("******* ANTLRv4 Atom detected");
            logger.debug("******* ANTLRv4 Atom text: " + atomName);

            GrammarClass grammarSpec = new GrammarClass(atomName, currentParserRuleSpec, GrammarType.RULE_ATOM);
            grammarObjects.add(grammarSpec);
        }

        visitChildren(ctx);

        return grammarObjects;
    }
}
