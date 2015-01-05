import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.dgso.processrunner.ProcessRunnerFactory;
import org.dgso.programbuilder.ProgramBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeMap;

public class Main {
    private static Logger mainLogger = Logger.getLogger(Main.class);

    private static String grammar_path;
    private static int recursion_limit;
    private static String startingRule;
    private static String testTemplateFolder;
    private static String testTemplateFile;
    private static String testOutputFolder;
    private static String testOutputFile;
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

            ProcessRunnerFactory.createProcessBuilders(testTemplateFolder, testTemplateFile, testOutputFolder, testOutputFile, testScriptPath, timeout, instanceCount);

            ProcessRunnerFactory.assignStatementsToTestRunners(statements);

            TreeMap<String, String> results = ProcessRunnerFactory.runAllProcessesInSerial();

            ProcessRunnerFactory.cleanupTestOutputFolder();
            System.out.println(results);
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
        testTemplateFolder = properties.getProperty("test_template_folder");
        testTemplateFile = properties.getProperty("test_template_file");
        testOutputFolder = properties.getProperty("test_output_folder");
        testOutputFile = properties.getProperty("test_output_file");
        testScriptPath = properties.getProperty("test_script_path");
        timeout = Integer.parseInt(properties.getProperty("timeout"));
        instanceCount = Integer.parseInt(properties.getProperty("instance_count"));
    }
}
