import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.dgso.antlrv4parser.ANTLRv4Lexer;
import org.dgso.antlrv4parser.ANTLRv4Parser;
import org.dgso.superoptimizer.ANTLRv4Grammar;
import org.dgso.superoptimizer.ANTLRv4Visitor;
import org.dgso.superoptimizer.SuperoptimizerBuilder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Main {
    private static Logger mainLogger;


    public static void main(String[] args) throws Exception{
        String inputFile;
        String startingRule;

        // setup logger
        mainLogger = Logger.getLogger(Main.class);
        BasicConfigurator.configure();

        // create a CharStream that reads from standard input
        if (args.length != 2) {
            mainLogger.error("dgso requires 2 arguments: the grammar file reference and start ANTLRv4 rule");
            System.exit(-1);
        }

        inputFile = args[0];
        startingRule = args[1];

        InputStream is = System.in;
        if (inputFile != null) {
            is = new FileInputStream(inputFile);
        }

        // setup lexer and parser
        ANTLRInputStream input = new ANTLRInputStream(is);
        ANTLRv4Lexer lexer = new ANTLRv4Lexer(input); // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); // create a parser that feeds off the tokens buffer
        ANTLRv4Parser parser = new ANTLRv4Parser(tokens);

        //start parsing the "grammarSpec" rule in the ANTLRv4 parser/lexer file
        ParseTree tree = parser.grammarSpec();
        ANTLRv4Visitor av = new ANTLRv4Visitor();

        //start visiting parser tree
        ANTLRv4Grammar grammar = (ANTLRv4Grammar) av.visit(tree);

        SuperoptimizerBuilder sb = new SuperoptimizerBuilder();
        ArrayList<String> superoptimizedStatements = sb.generateSuperoptimizedStatements(startingRule, grammar);
        System.out.println("-----" + superoptimizedStatements);
    }
}
