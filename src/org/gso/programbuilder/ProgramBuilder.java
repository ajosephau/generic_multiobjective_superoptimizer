package org.gso.programbuilder;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.gso.antlrv4parser.ANTLRv4Lexer;
import org.gso.antlrv4parser.ANTLRv4Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class ProgramBuilder {

    private static int RECURSION_LIMIT = 4;
    private static Logger programBuilderLogger = Logger.getLogger(ProgramBuilder.class);

    public ProgramBuilder() {
    }
    
    public static void outputListOfProgramsToFile(ArrayList<String> programs, String pathToFile) {
        try {
            PrintWriter pw = new PrintWriter(pathToFile);
            long i = 0;
            int numberOfPrograms = programs.size();
            int programSizeStringLength = (int) Math.log10(numberOfPrograms) + 1;
            pw.write("Number of programs: " + programs.size() + System.lineSeparator());

            for(String program: programs) {
                i++;
                String programNumber = StringUtils.leftPad(Long.toString(i), programSizeStringLength, '0');
                pw.write(programNumber + " of " + numberOfPrograms + ":\t" + program + System.lineSeparator());
            }
            pw.close();
        }
        catch (FileNotFoundException e) {
            programBuilderLogger.error(e);
            System.exit(-1);
        }
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

    public static ArrayList<String> getAllProgramsFromGrammar(String inputFile, String startingRule, int recursionLimit) {
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
        return pb.generateAllProgramsFromGrammar(startingRule, grammar, recursionLimit);
    }


    public ArrayList<String> generateAllProgramsFromGrammar(String startingRule, ANTLRv4Grammar grammar, int recursionLimit) {
        ANTLRv4GrammarClass parserRuleSpec = getParserRuleSpec(startingRule, grammar);

        RECURSION_LIMIT = recursionLimit;

        if (parserRuleSpec == null) {
            programBuilderLogger.error("Unable to find starting rule: " + startingRule + " in grammar.");
            System.exit(-1);
        }

        return generateAllProgramsFromGrammar(grammar, parserRuleSpec, 0);
    }

    private ArrayList<String> generateAllProgramsFromGrammar(ANTLRv4Grammar grammar, ANTLRv4GrammarClass grammarObject, int recursion_count) {
        ArrayList<String> returnList = new ArrayList<>();

        if (grammarObject == null) {
            programBuilderLogger.error("An error in the grammar file was detected.");
            System.exit(-1);
        }

        programBuilderLogger.debug(StringUtils.repeat('.', recursion_count) + " Recursion Depth: " + recursion_count + ", Grammar Object: " + grammarObject.getDescriptiveText());

        if (recursion_count > RECURSION_LIMIT) {
            return returnList;
        }

        if (grammarObject.getType() == ANTLRv4GrammarType.PARSER_RULE_SPEC) {
            String leftHandSide = "";

            ArrayList<String> labeled_alts = new ArrayList<>();

            ArrayList<ANTLRv4GrammarClass> childObjects = grammarObject.getChildren();
            for (ANTLRv4GrammarClass labeled_alt : childObjects) {
                // will always get a parser rule spec, so sub objects will be labeled_alt's
                programBuilderLogger.debug(StringUtils.repeat('.', recursion_count) + " Recursion Depth: " + recursion_count + ", Labeled Alternative \"" + labeled_alt.getIdentifier() + "\" Rule: " + (childObjects.indexOf(labeled_alt) + 1) + " of " + childObjects.size());

                labeled_alts.clear();
                labeled_alts.add(leftHandSide);

                ArrayList<ANTLRv4GrammarClass> subChildObjects = labeled_alt.getChildren();
                for (ANTLRv4GrammarClass subGrammarObject : subChildObjects) {
                    programBuilderLogger.debug(StringUtils.repeat('.', recursion_count) + " Recursion Depth: " + recursion_count + ", Labeled Alternative \"" + subGrammarObject.getIdentifier() + "\" Children: " + (subChildObjects.indexOf(subGrammarObject) + 1) + " of " + subChildObjects.size());


                    if (subGrammarObject.getType() == ANTLRv4GrammarType.RULE_REFERENCE) {
                        ArrayList<String> parser_rule_specs = generateAllProgramsFromGrammar(grammar, getParserRuleSpec(subGrammarObject.getIdentifier(), grammar), recursion_count + 1);
                        ArrayList<String> new_labeled_alts = new ArrayList<>();
                        for (String labeledAltEntry : labeled_alts) {
                            for (String parser_rule_spec : parser_rule_specs) {
                                //programBuilderLogger.debug("-> Adding rule_ref \"" + parser_rule_spec + "\" to \"" + labeledAltEntry + "\"");
                                new_labeled_alts.add(labeledAltEntry + parser_rule_spec);
                            }
                        }
                        labeled_alts.clear();
                        labeled_alts.addAll(new_labeled_alts);
                    }
                    if (subGrammarObject.getType() == ANTLRv4GrammarType.RULE_TERMINAL) {
                        for (int i = 0; i < labeled_alts.size(); i++) {
                            //programBuilderLogger.debug("-> Adding terminal text \"" + subGrammarObject.getIdentifier() + "\" to \"" + labeled_alts.get(i) + "\"");
                            labeled_alts.set(i, labeled_alts.get(i) + subGrammarObject.getIdentifier());
                        }
                    }
                    if (subGrammarObject.getType() == ANTLRv4GrammarType.ALT_LIST) {
                        ArrayList<String> new_labeled_alts = new ArrayList<>();
                        ArrayList<String> alt_list = generateAllProgramsFromGrammar(grammar, subGrammarObject, recursion_count + 1);
                        for (String labeledAltEntry : labeled_alts) {
                            for (String alternative : alt_list) {
                                //programBuilderLogger.debug("-> Adding alt_list \"" + alternative + "\" to \"" + labeledAltEntry + "\"");
                                new_labeled_alts.add(labeledAltEntry + alternative);
                            }
                        }
                        labeled_alts.clear();
                        labeled_alts.addAll(new_labeled_alts);
                    }
                }
                programBuilderLogger.debug(StringUtils.repeat('.', recursion_count) + " Recursion Depth: " + recursion_count + ", Labeled Alternative \"" + labeled_alt.getIdentifier() + "\": Adding generated programs to interim list. Please wait...");
                returnList.addAll(labeled_alts);
                returnList.trimToSize();
            }
        }

        if (grammarObject.getType() == ANTLRv4GrammarType.ALT_LIST) {
            for (ANTLRv4GrammarClass subGrammarObject : grammarObject.getChildren()) {
                if (subGrammarObject.getType() == ANTLRv4GrammarType.RULE_TERMINAL) {
                    returnList.add(subGrammarObject.getIdentifier());
                }
            }
        }
        return returnList;
    }
}
