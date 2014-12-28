package org.dgso.testrunner;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestBuilderFactory {
    private static ArrayList<TestBuilder> testBuilders;
    private static Logger tbfLogger = Logger.getLogger(TestBuilderFactory.class);
    private static String testOutputFolder;

    public static void createTestBuilders(String templateFolder, String templateFile, String outputFolder, String outputFile, int instanceCount) {
        testBuilders = new ArrayList<TestBuilder>(instanceCount);

        setTestOutputFolder(outputFolder);

        File directory = new File(getTestOutputFolder());
        boolean mkdirResult = directory.mkdir();
        tbfLogger.debug("Result from creating directory: " + directory.getAbsolutePath() + ": " + mkdirResult);

        for (int i = 0; i < instanceCount; i++) {
            TestBuilder tb = new TestBuilder(templateFolder, templateFile, outputFolder, outputFile, i);
            testBuilders.add(tb);
        }
    }

    public static TestBuilder getTestBuilder(int instanceID) {
        if (instanceID < testBuilders.size() && instanceID > 0) {
            return testBuilders.get(instanceID);
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

    }

    public static String getTestOutputFolder() {
        return testOutputFolder;
    }

    public static void setTestOutputFolder(String testOutputFolder) {
        TestBuilderFactory.testOutputFolder = testOutputFolder;
    }
}
