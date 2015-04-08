package org.gso;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.gso.processrunner.ProcessRunnerFactory;
import org.gso.processrunner.TestRunner;
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
    private static String testResultsOutputFilePath;
    private static String scenarioResultsOutputFilePath;
    private static String tabDelimitedFileResultsHeader;
    private static String mode;
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
    private static String log4j_properties_location;

    private static final String TEST_ONLY_MODE = "tests only";
    private static final String SCENARIO_ONLY_MODE = "scenarios only";

    private static int startingProgram;
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
        mode = properties.getProperty("mode");
        tabDelimitedFileResultsHeader = properties.getProperty("tab_delimited_file_results_header");
        startingProgram = Integer.parseInt(properties.getProperty("starting_program"));
        recursionLimit = Integer.parseInt(properties.getProperty("program_builder_recursion_limit"));
        startingRule = properties.getProperty("starting_rule");
        programListOutputFilePath = properties.getProperty("program_list_output_file_path");
        testResultsOutputFilePath = properties.getProperty("test_results_output_file_path");
        scenarioResultsOutputFilePath = properties.getProperty("scenario_results_output_file_path");
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
        startingProgram = Integer.parseInt(properties.getProperty("starting_program"));
        testInstanceCount = Integer.parseInt(properties.getProperty("test_instance_count"));
        scenarioInstanceCount = Integer.parseInt(properties.getProperty("scenario_instance_count"));

        log4j_properties_location = properties.getProperty("log4j_properties_location");
        PropertyConfigurator.configure(log4j_properties_location);
    }


    public static void runAsSingleProcessStandalone(String inputFile) {
        try {
            setupParameters(inputFile);
            gsoLogger.info("Step 1 of 5: Generating programs. Current time: " + new java.util.Date());

            ArrayList<String> programs = ProgramBuilder.getAllProgramsFromGrammar(grammar_path, startingRule, recursionLimit);
            ProgramBuilder.outputListOfProgramsToFile(programs, programListOutputFilePath);
            gsoLogger.info(programs.size() + " programs created.");

            ArrayList<String> scenarioPrograms;

            if(!mode.equals(SCENARIO_ONLY_MODE)) {
                gsoLogger.info("Step 2 of 5: Running tests. Current time: " + new java.util.Date());
                ProcessRunnerFactory testRunners = new ProcessRunnerFactory();
                testRunners.createTestRunners(testTemplateFolder, testTemplateFile, testOutputFolder, testOutputFile, testScriptPath, startingRule, timeout, testInstanceCount);
                testRunners.assignProgramsToProcessRunners(programs, startingProgram);
                programs.clear();
                TreeMap<String, String> testResults = new TreeMap<>();
                if(testInstanceCount > 1) {
                    testResults = testRunners.runAllProcessesInParallel();
                }
                else {
                    testResults = testRunners.runAllProcessesInSerial();
                }
                testRunners.cleanupProcessOutputFolder();
                gsoLogger.info("Step 3 of 5: Outputting test results. Current time: " + new java.util.Date());
                ((TestRunner) testRunners.getFirstProcessRunner()).outputTestResults(testResults, startingRule, testResultsOutputFilePath);
                scenarioPrograms = new ArrayList<>(testResults.keySet());
            }
            else {
                gsoLogger.info("Skipping step 2 of 5: Running tests. Current time: " + new java.util.Date());
                gsoLogger.info("Skipping step 3 of 5: Outputting test results. Current time: " + new java.util.Date());
                scenarioPrograms = new ArrayList<>(programs);
            }

            if(!mode.equals(TEST_ONLY_MODE)) {
                gsoLogger.info("Step 4 of 5: Running scenarios. Current time: " + new java.util.Date());
                ProcessRunnerFactory scenarioRunners = new ProcessRunnerFactory();
                scenarioRunners.createScenarioRunners(scenarioTemplateFolder, scenarioTemplateFile, scenarioOutputFolder, scenarioOutputFile, scenarioScriptPath, startingRule, timeout, scenarioInstanceCount);
                scenarioRunners.assignProgramsToProcessRunners(scenarioPrograms, startingProgram);
                TreeMap<String, String> scenarioResults = new TreeMap<>();
                if (scenarioInstanceCount > 1) {
                    scenarioResults = scenarioRunners.runAllProcessesInSerial();
                } else {
                    scenarioResults = scenarioRunners.runAllProcessesInSerial();
                }
                scenarioRunners.cleanupProcessOutputFolder();

                gsoLogger.info("Step 5 of 5: Outputting scenario results. Current time: " + new java.util.Date());
                String formattedResults = ScenarioRunner.formatResultsForConsoleOutput(scenarioResults, startingRule, consoleResultsHeader);
                ((ScenarioRunner) scenarioRunners.getFirstProcessRunner()).outputScenarioResults(scenarioResults, startingRule, tabDelimitedFileResultsHeader, scenarioResultsOutputFilePath);
                gsoLogger.info(formattedResults);
            }
            else {
                gsoLogger.info("Skipping step 4 of 5: Running scenarios. Current time: " + new java.util.Date());
                gsoLogger.info("Skipping step 5 of 5: Outputting scenario results. Current time: " + new java.util.Date());
            }

        } catch (IOException e) {
            gsoLogger.error(e.getMessage());
        }
    }
}
