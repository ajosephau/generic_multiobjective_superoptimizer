package org.dgso.superoptimizer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.TreeMap;

public class SuperoptimizerBuilder {

    private static final int RECURSION_LIMIT = 5;
    private Logger superoptimizerBuilderLogger;//recursion depth?

    public SuperoptimizerBuilder() {
        superoptimizerBuilderLogger = Logger.getLogger(SuperoptimizerBuilder.class);
        BasicConfigurator.configure();
    }

    public static ANTLRv4GrammarClass getParserRuleSpec(String startingRule, ANTLRv4Grammar grammar) {
        return getObject(startingRule, grammar, ANTLRv4GrammarType.PARSER_RULE_SPEC);
    }

    public static ANTLRv4GrammarClass getObject(String startingRule, ANTLRv4Grammar grammar, ANTLRv4GrammarType type) {
        TreeMap<ANTLRv4GrammarType, ArrayList<ANTLRv4GrammarClass>> searchGrammar = grammar.getGrammarObjects();

        ArrayList<ANTLRv4GrammarClass> allParserRuleSpecs = searchGrammar.get(type);

        for (ANTLRv4GrammarClass parserRuleSpec : allParserRuleSpecs) {
            if (parserRuleSpec.getIdentifier().equals(startingRule)) {
                return parserRuleSpec;
            }
        }

        return null;
    }

    public ArrayList<String>  generateSuperoptimizedStatements(String startingRule, ANTLRv4Grammar grammar) {
        ANTLRv4GrammarClass parserRuleSpec = getParserRuleSpec(startingRule, grammar);

        if (parserRuleSpec == null) {
            superoptimizerBuilderLogger.error("Unable to find starting rule: " + startingRule + " in grammar.");
            System.exit(-1);
        }

        return getSuperoptimizedStatements(grammar, parserRuleSpec, 0);
    }

    private ArrayList<String> getSuperoptimizedStatements(ANTLRv4Grammar grammar, ANTLRv4GrammarClass grammarObject, int recursion_count) {
        ArrayList<String> returnList = new ArrayList<String>();

        if (recursion_count > RECURSION_LIMIT) {
            return returnList;
        }

        if (grammarObject.getType() == ANTLRv4GrammarType.PARSER_RULE_SPEC) {
            String leftHandSide;
            if(recursion_count > 0) {
                leftHandSide = "";
            }
            else {
                leftHandSide = grammarObject.getIdentifier() + " :";
            }

            ArrayList<String> labeled_alts = new ArrayList<String>();

            for (ANTLRv4GrammarClass labeled_alt : grammarObject.getChildren()) {
                // will always get a parser rule spec, so sub objects will be labeled_alt's
                labeled_alts.clear();
                labeled_alts.add(leftHandSide);
                for (ANTLRv4GrammarClass subGrammarObject : labeled_alt.getChildren()) {
                    if (subGrammarObject.getType() == ANTLRv4GrammarType.RULE_REFERENCE) {
                        System.out.println("Rule: " + subGrammarObject.getIdentifier());

                        ArrayList<String> parser_rule_specs = getSuperoptimizedStatements(grammar, getParserRuleSpec(subGrammarObject.getIdentifier(), grammar), recursion_count + 1);
                        ArrayList<String> new_labeled_alts = new ArrayList<String>();

                        for (String labeledAltEntry : labeled_alts) {
                            for (String parser_rule_spec : parser_rule_specs) {
                                new_labeled_alts.add(labeledAltEntry + parser_rule_spec);
                            }
                        }

                        labeled_alts = new_labeled_alts;
                    }
                    if (subGrammarObject.getType() == ANTLRv4GrammarType.RULE_TERMINAL) {
                        System.out.println("Terminal: " + subGrammarObject.getIdentifier());
                        for (int i = 0; i < labeled_alts.size(); i++) {
                            labeled_alts.set(i, labeled_alts.get(i) + " " + subGrammarObject.getIdentifier());
                        }
                    }
                    if (subGrammarObject.getType() == ANTLRv4GrammarType.ALT_LIST) {
                        System.out.println("Alt_list: " + subGrammarObject.getIdentifier());

                        ArrayList<String> new_labeled_alts = new ArrayList<String>();

                        ArrayList<String> alt_list = getSuperoptimizedStatements(grammar, subGrammarObject, recursion_count + 1);
                        for (String labeledAltEntry : labeled_alts) {
                            for (String alternative : alt_list) {
                                new_labeled_alts.add(labeledAltEntry + " " + alternative);
                            }
                        }
                        labeled_alts = new_labeled_alts;

                    }
                    System.out.println("Parser_Rule_Spec Alt: " + labeled_alt);
                }
                returnList.addAll(labeled_alts);
            }
        }

        if (grammarObject.getType() == ANTLRv4GrammarType.ALT_LIST) {
            for (ANTLRv4GrammarClass subGrammarObject : grammarObject.getChildren()) {
                if (subGrammarObject.getType() == ANTLRv4GrammarType.RULE_REFERENCE) {
                    System.out.println("Rule: " + subGrammarObject.getIdentifier());
                }
                if (subGrammarObject.getType() == ANTLRv4GrammarType.RULE_TERMINAL) {
                    returnList.add(subGrammarObject.getIdentifier());
                }
                if (subGrammarObject.getType() == ANTLRv4GrammarType.ALT_LIST) {
                    System.out.println("Alt_list: " + subGrammarObject.getIdentifier());
                }
                System.out.println("Alt_List sub object:" + subGrammarObject);
            }
        }
        return returnList;
    }
}
