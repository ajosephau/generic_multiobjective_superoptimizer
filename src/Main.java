import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.dgso.antlrv4parser.ANTLRv4Lexer;
import org.dgso.antlrv4parser.ANTLRv4Parser;
import org.dgso.programbuilder.ANTLRv4Grammar;
import org.dgso.programbuilder.ANTLRv4Visitor;
import org.dgso.programbuilder.ProgramBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    private static Logger mainLogger;
    private static String grammar_path;
    private static int recursion_limit;
    private static String startingRule;

    public static void main(String[] args) {
        String inputFile;

        // setup logger
        mainLogger = Logger.getLogger(Main.class);
        BasicConfigurator.configure();

        // create a CharStream that reads from standard input
        if (args.length != 1) {
            mainLogger.error("dgso requires 1 argument: the path to the configuration file.");
            System.exit(-1);
        }

        inputFile = args[0];

        try {
            setupParameters(inputFile);
        } catch (IOException e) {
            mainLogger.error(e.getMessage());
        }

        ArrayList<String> statements = getAllStatementsFromGrammar(grammar_path, startingRule, recursion_limit);

        System.out.println(statements);
    }

    public static ArrayList<String> getAllStatementsFromGrammar(String inputFile, String startingRule, int recursionLimit) {
        InputStream is = System.in;
        try {
            if (inputFile != null) {
                is = new FileInputStream(inputFile);
            }
        } catch (FileNotFoundException e) {
            mainLogger.error(e.getMessage());
        }

        // setup lexer and parser
        ANTLRInputStream input = null;
        try {
            input = new ANTLRInputStream(is);
        } catch (IOException e) {
            mainLogger.error(e.getMessage());
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

    public static void setupParameters(String pathToConfigFile) throws IOException {
        Properties properties = new Properties();
        InputStream is = new FileInputStream(pathToConfigFile);
        properties.load(is);

        grammar_path = properties.getProperty("grammar_path");
        recursion_limit = Integer.parseInt(properties.getProperty("program_builder_recursion_limit"));
        startingRule = properties.getProperty("starting_rule");

    }
}
