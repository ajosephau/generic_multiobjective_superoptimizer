import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.dgso.programbuilder.ProgramBuilder;
import org.dgso.testrunner.TestRunner;
import org.dgso.testrunner.TestRunnerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    private static Logger mainLogger = Logger.getLogger(Main.class);

    private static String grammar_path;
    private static int recursion_limit;
    private static String startingRule;
    private static String templateFolder;
    private static String templateFile;
    private static String outputFolder;
    private static String outputFile;
    private static String testScriptPath;
    private static int timeout;
    private static int instanceCount;

    public static void main(String[] args) {
        String inputFile;

        // setup logger
        BasicConfigurator.configure();

        // create a CharStream that reads from standard input
        if (args.length != 1) {
            mainLogger.error("dgso requires 1 argument: the path to the configuration file.");
            System.exit(-1);
        }

        inputFile = args[0];

        try {
            setupParameters(inputFile);

            ArrayList<String> statements = ProgramBuilder.getAllStatementsFromGrammar(grammar_path, startingRule, recursion_limit);

            TestRunnerFactory.createTestBuilders(templateFolder, templateFile, outputFolder, outputFile, testScriptPath, timeout, instanceCount);

            TestRunner tr = TestRunnerFactory.getTestBuilder(1);

            //test code - to be substituted with entries in "statements" arraylist
            tr.buildProgram("asdfasdfasdfasdf");
            tr.runProgram();

            TestRunnerFactory.cleanupTestOutputFolder();
            System.out.println(statements);
        } catch (IOException e) {
            mainLogger.error(e.getMessage());
        }
    }

    public static void setupParameters(String pathToConfigFile) throws IOException {
        Properties properties = new Properties();
        InputStream is = new FileInputStream(pathToConfigFile);
        properties.load(is);

        grammar_path = properties.getProperty("grammar_path");
        recursion_limit = Integer.parseInt(properties.getProperty("program_builder_recursion_limit"));
        startingRule = properties.getProperty("starting_rule");
        templateFolder = properties.getProperty("template_folder");
        templateFile = properties.getProperty("template_file");
        outputFolder = properties.getProperty("output_folder");
        outputFile = properties.getProperty("output_file");
        testScriptPath = properties.getProperty("test_script_path");
        timeout = Integer.parseInt(properties.getProperty("timeout"));
        instanceCount = Integer.parseInt(properties.getProperty("instance_count"));
    }
}
