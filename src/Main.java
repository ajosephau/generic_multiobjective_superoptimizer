import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.dgso.antlrv4parser.*;

public class Main {

    public static void main(String[] args) throws Exception {
        // create a CharStream that reads from standard input
        ANTLRInputStream input = new ANTLRInputStream(System.in); // create a lexer that feeds off of input CharStream
        ANTLRv4Lexer lexer = new ANTLRv4Lexer(input); // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); // create a parser that feeds off the tokens buffer
        ANTLRv4Parser parser = new ANTLRv4Parser(tokens);
        ParseTree tree = parser.parserRuleSpec();
        System.out.println(tree.toStringTree(parser)); // print LISP-style tree
    }
}
