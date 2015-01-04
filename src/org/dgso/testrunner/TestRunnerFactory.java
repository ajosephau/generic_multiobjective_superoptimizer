package org.dgso.testrunner;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestRunnerFactory {
    private static ArrayList<TestRunner> testRunners;
    private static Logger tbfLogger = Logger.getLogger(TestRunnerFactory.class);
    private static String testOutputFolder;

    public static void createTestBuilders(String templateFolder, String templateFile, String outputFolder, String outputFile, String testScriptPath, int timeout, int instanceCount) {
        testRunners = new ArrayList<TestRunner>(instanceCount);

        setTestOutputFolder(outputFolder);

        File directory = new File(getTestOutputFolder());
        boolean mkdirResult = directory.mkdir();
        tbfLogger.debug("Result from creating directory: " + directory.getAbsolutePath() + ": " + mkdirResult);

        for (int i = 0; i < instanceCount; i++) {
            TestRunner tb = new TestRunner(templateFolder, templateFile, outputFolder, outputFile, testScriptPath, timeout, i);
            testRunners.add(tb);
        }
    }

    public static TestRunner getTestBuilder(int instanceID) {
        if (instanceID < testRunners.size() && instanceID > 0) {
            return testRunners.get(instanceID);
        } else {
            return null;
        }
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
        TestRunnerFactory.testOutputFolder = testOutputFolder;
    }
}
