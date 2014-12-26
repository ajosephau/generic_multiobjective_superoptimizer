package org.dgso.programbuilder;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.dgso.antlrv4parser.ANTLRv4Lexer;
import org.dgso.antlrv4parser.ANTLRv4Parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeMap;

public class ProgramBuilder {

    private static int RECURSION_LIMIT = 4;
    private static Logger programBuilderLogger = Logger.getLogger(ProgramBuilder.class);

    public ProgramBuilder() {
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

    public static ArrayList<String> getAllStatementsFromGrammar(String inputFile, String startingRule, int recursionLimit) {
        InputStream is = System.in;
        try {
            if (inputFile != null) {
                is = new FileInputStream(inputFile);
            }
        } catch (FileNotFoundException e) {
            programBuilderLogger.error(e.getMessage());
        }

        // setup lexer and parser
        ANTLRInputStream input = null;
        try {
            input = new ANTLRInputStream(is);
        } catch (IOException e) {
            programBuilderLogger.error(e.getMessage());
        }

        ANTLRv4Lexer lexer = new ANTLRv4Lexer(input); // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); // create a parser that feeds off the tokens buffer
        ANTLRv4Parser parser = new ANTLRv4Parser(tokens);

        //start parsing the "grammarSpec" rule in the ANTLRv4 parser/lexer file
        ParseTree tree = parser.grammarSpec();
        ANTLRv4Visitor av = new ANTLRv4Visitor();

        //start visiting parser tree
        ANTLRv4Grammar grammar = (ANTLRv4Grammar) av.visit(tree);

        ProgramBuilder pb = new ProgramBuilder();
        return pb.generateAllStatementsFromGrammar(startingRule, grammar, recursionLimit);
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

        if (grammarObject == null) {
            programBuilderLogger.error("An error in the grammar file was detected.");
            System.exit(-1);
        }
        if (grammarObject.getType() == ANTLRv4GrammarType.PARSER_RULE_SPEC) {
            String leftHandSide;
            if (recursion_count > 0) {
                leftHandSide = "";
            } else {
                leftHandSide = grammarObject.getIdentifier() + " :";
            }

            ArrayList<String> labeled_alts = new ArrayList<String>();


            ArrayList<ANTLRv4GrammarClass> childObjects = grammarObject.getChildren();
            for (ANTLRv4GrammarClass labeled_alt : childObjects) {
                // will always get a parser rule spec, so sub objects will be labeled_alt's
                programBuilderLogger.debug("Recursion ID " + recursion_count + " Labeled Alternative Rule " + (childObjects.indexOf(labeled_alt) + 1) + " of " + childObjects.size());

                labeled_alts.clear();
                labeled_alts.add(leftHandSide);

                ArrayList<ANTLRv4GrammarClass> subChildObjects = labeled_alt.getChildren();
                for (ANTLRv4GrammarClass subGrammarObject : subChildObjects) {
                    programBuilderLogger.debug("Recursion ID " + recursion_count + " Labeled Alternative Children " + (subChildObjects.indexOf(subGrammarObject) + 1) + " of " + subChildObjects.size());


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
