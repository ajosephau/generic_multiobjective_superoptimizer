package org.dgso.processrunner;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class ProcessRunnerFactory {
    private static ArrayList<TestRunner> testRunners;
    private static Logger tbfLogger = Logger.getLogger(ProcessRunnerFactory.class);
    private static String testOutputFolder;

    public static void createProcessBuilders(String templateFolder, String templateFile, String outputFolder, String outputFile, String testScriptPath, int timeout, int instanceCount) {
        testRunners = new ArrayList<>(instanceCount);

        setTestOutputFolder(outputFolder);

        File directory = new File(getTestOutputFolder());
        boolean mkdirResult = directory.mkdir();
        tbfLogger.debug("Result from creating directory: " + directory.getAbsolutePath() + ": " + mkdirResult);

        for (int i = 0; i < instanceCount; i++) {
            TestRunner tb = new TestRunner(templateFolder, templateFile, outputFolder, outputFile, testScriptPath, timeout, i);
            testRunners.add(tb);
        }
    }

    public static void assignStatementsToTestRunners(ArrayList<String> statements) {
        int numTestRunners = testRunners.size();
        for (int i = 0; i < statements.size(); i++) {
            testRunners.get(i % numTestRunners).addStatementToStatements(statements.get(i));
        }
    }

    public static TreeMap<String, String> runAllProcessesInSerial() {
        TreeMap<String, String> results = new TreeMap<>();
        for (TestRunner tr : testRunners) {
            results.putAll(tr.runProcesses());
        }

        return results;
    }

    public static void cleanupTestOutputFolder() {
        File directory = new File(getTestOutputFolder());
        try {
            FileUtils.deleteDirectory(directory);
        } catch (IOException e) {
            tbfLogger.error(e);
        }
        tbfLogger.debug("Result from deleting directory: " + getTestOutputFolder() + ": true");
    }

    public static String getTestOutputFolder() {
        return testOutputFolder;
    }

    public static void setTestOutputFolder(String testOutputFolder) {
        ProcessRunnerFactory.testOutputFolder = testOutputFolder;
    }
}
