package org.gso;

import org.apache.log4j.Logger;
import org.gso.processrunner.ProcessRunnerFactory;
import org.gso.processrunner.ScenarioRunner;
import org.gso.programbuilder.ProgramBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TreeMap;

public class GenericSuperoptimizer {
    private static Logger gsoLogger = Logger.getLogger(GenericSuperoptimizer.class);

    private static String grammar_path;
    private static String startingRule;
    private static String programListOutputFilePath;
    private static String resultsOutputFilePath;
    private static String tabDelimitedFileResultsHeader;
    private static String consoleResultsHeader;
    private static String testTemplateFolder;
    private static String testTemplateFile;
    private static String testOutputFolder;
    private static String testOutputFile;
    private static String testScriptPath;
    private static String scenarioTemplateFolder;
    private static String scenarioTemplateFile;
    private static String scenarioOutputFolder;
    private static String scenarioOutputFile;
    private static String scenarioScriptPath;

    private static int testInstanceCount;
    private static int scenarioInstanceCount;
    private static int recursionLimit;
    private static int timeout;

    public static void setupParameters(String pathToConfigFile) throws IOException {
        Properties properties = new Properties();
        InputStream is = new FileInputStream(pathToConfigFile);
        properties.load(is);

        grammar_path = properties.getProperty("grammar_path");
        consoleResultsHeader = properties.getProperty("console_results_header");
        tabDelimitedFileResultsHeader = properties.getProperty("tab_delimited_file_results_header");
        recursionLimit = Integer.parseInt(properties.getProperty("program_builder_recursion_limit"));
        startingRule = properties.getProperty("starting_rule");
        programListOutputFilePath = properties.getProperty("program_list_output_file_path");
        resultsOutputFilePath = properties.getProperty("results_output_file_path");
        testTemplateFolder = properties.getProperty("test_template_folder");
        testTemplateFile = properties.getProperty("test_template_file");
        testOutputFolder = properties.getProperty("test_output_folder");
        testOutputFile = properties.getProperty("test_output_file");
        testScriptPath = properties.getProperty("test_script_path");
        scenarioTemplateFolder = properties.getProperty("scenario_template_folder");
        scenarioTemplateFile = properties.getProperty("scenario_template_file");
        scenarioOutputFolder = properties.getProperty("scenario_output_folder");
        scenarioOutputFile = properties.getProperty("scenario_output_file");
        scenarioScriptPath = properties.getProperty("scenario_script_path");

        timeout = Integer.parseInt(properties.getProperty("timeout"));
        testInstanceCount = Integer.parseInt(properties.getProperty("test_instance_count"));
        scenarioInstanceCount = Integer.parseInt(properties.getProperty("scenario_instance_count"));
    }


    public static void runAsSingleProcessStandalone(String inputFile) {
        try {
            setupParameters(inputFile);

            ArrayList<String> programs = ProgramBuilder.getAllProgramsFromGrammar(grammar_path, startingRule, recursionLimit);
            ProgramBuilder.outputListOfProgramsToFile(programs, programListOutputFilePath);
            gsoLogger.info(programs.size() + " programs created.");

            ProcessRunnerFactory testRunners = new ProcessRunnerFactory();
            testRunners.createTestRunners(testTemplateFolder, testTemplateFile, testOutputFolder, testOutputFile, testScriptPath, timeout, testInstanceCount);
            testRunners.assignStatementsToProcessRunners(programs);
            TreeMap<String, String> testResults = testRunners.runAllProcessesInSerial();
            testRunners.cleanupProcessOutputFolder();

            ArrayList<String> scenarioStatements = new ArrayList<>(testResults.keySet());

            ProcessRunnerFactory scenarioRunners = new ProcessRunnerFactory();
            scenarioRunners.createScenarioRunners(scenarioTemplateFolder, scenarioTemplateFile, scenarioOutputFolder, scenarioOutputFile, scenarioScriptPath, timeout, scenarioInstanceCount);
            scenarioRunners.assignStatementsToProcessRunners(scenarioStatements);
            TreeMap<String, String> results = scenarioRunners.runAllProcessesInSerial();
            scenarioRunners.cleanupProcessOutputFolder();

            String formattedResults = ScenarioRunner.formatResultsForConsoleOutput(results, startingRule, consoleResultsHeader);
            ((ScenarioRunner) scenarioRunners.getFirstProcessRunner()).outputScenarioResults(results, startingRule, tabDelimitedFileResultsHeader, resultsOutputFilePath);
            gsoLogger.info(formattedResults);
        } catch (IOException e) {
            gsoLogger.error(e.getMessage());
        }
    }
}
