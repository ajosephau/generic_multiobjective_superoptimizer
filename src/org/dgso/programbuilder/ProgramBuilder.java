package org.dgso.programbuilder;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.TreeMap;

public class ProgramBuilder {

    private static int RECURSION_LIMIT = 4;
    private Logger programBuilderLogger;//recursion depth?

    public ProgramBuilder() {
        programBuilderLogger = Logger.getLogger(ProgramBuilder.class);
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

    public ArrayList<String> generateAllStatementsFromGrammar(String startingRule, ANTLRv4Grammar grammar, int recursionLimit) {
        ANTLRv4GrammarClass parserRuleSpec = getParserRuleSpec(startingRule, grammar);

        RECURSION_LIMIT = recursionLimit;

        if (parserRuleSpec == null) {
            programBuilderLogger.error("Unable to find starting rule: " + startingRule + " in grammar.");
            System.exit(-1);
        }

        return generateAllStatementsFromGrammar(grammar, parserRuleSpec, 0);
    }

    private ArrayList<String> generateAllStatementsFromGrammar(ANTLRv4Grammar grammar, ANTLRv4GrammarClass grammarObject, int recursion_count) {
        ArrayList<String> returnList = new ArrayList<String>();

        if (recursion_count > RECURSION_LIMIT) {
            return returnList;
        }

        if(grammarObject == null) {
            programBuilderLogger.error("An error in the grammar file was detected.");
            System.exit(-1);
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


            ArrayList<ANTLRv4GrammarClass> childObjects = grammarObject.getChildren();
            for (ANTLRv4GrammarClass labeled_alt : childObjects) {
                // will always get a parser rule spec, so sub objects will be labeled_alt's
                programBuilderLogger.info("Recursion ID " + recursion_count + " Labeled Alternative Rule " + (childObjects.indexOf(labeled_alt) + 1) + " of " + childObjects.size());

                labeled_alts.clear();
                labeled_alts.add(leftHandSide);

                ArrayList<ANTLRv4GrammarClass> subChildObjects = labeled_alt.getChildren();
                for (ANTLRv4GrammarClass subGrammarObject : subChildObjects) {
                    programBuilderLogger.info("Recursion ID " + recursion_count + " Labeled Alternative Children " + (subChildObjects.indexOf(subGrammarObject) + 1) + " of " + subChildObjects.size());


                    if (subGrammarObject.getType() == ANTLRv4GrammarType.RULE_REFERENCE) {
                        ArrayList<String> parser_rule_specs = generateAllStatementsFromGrammar(grammar, getParserRuleSpec(subGrammarObject.getIdentifier(), grammar), recursion_count + 1);
                        ArrayList<String> new_labeled_alts = new ArrayList<String>();
                        for (String labeledAltEntry : labeled_alts) {
                            for (String parser_rule_spec : parser_rule_specs) {
                                new_labeled_alts.add(labeledAltEntry + parser_rule_spec);
                            }
                        }
                        labeled_alts.clear();
                        labeled_alts.addAll(new_labeled_alts);
                    }
                    if (subGrammarObject.getType() == ANTLRv4GrammarType.RULE_TERMINAL) {
                        for (int i = 0; i < labeled_alts.size(); i++) {
                            labeled_alts.set(i, labeled_alts.get(i) + " " + subGrammarObject.getIdentifier());
                        }
                    }
                    if (subGrammarObject.getType() == ANTLRv4GrammarType.ALT_LIST) {
                        ArrayList<String> new_labeled_alts = new ArrayList<String>();
                        ArrayList<String> alt_list = generateAllStatementsFromGrammar(grammar, subGrammarObject, recursion_count + 1);
                        for (String labeledAltEntry : labeled_alts) {
                            for (String alternative : alt_list) {
                                new_labeled_alts.add(labeledAltEntry + " " + alternative);
                            }
                        }
                        labeled_alts.clear();
                        labeled_alts.addAll(new_labeled_alts);
                    }
                }
                returnList.addAll(labeled_alts);
            }
        }

        if (grammarObject.getType() == ANTLRv4GrammarType.ALT_LIST) {
            for (ANTLRv4GrammarClass subGrammarObject : grammarObject.getChildren()) {
                if (subGrammarObject.getType() == ANTLRv4GrammarType.RULE_TERMINAL) {
                    returnList.add(subGrammarObject.getIdentifier());
                }
//                if (subGrammarObject.getType() == ANTLRv4GrammarType.RULE_REFERENCE) {
//                }
//                if (subGrammarObject.getType() == ANTLRv4GrammarType.ALT_LIST) {
//                }
            }
        }
        return returnList;
    }
}
