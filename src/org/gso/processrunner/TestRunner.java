package org.gso.processrunner;

import org.apache.log4j.Logger;

import java.util.TreeMap;

public class TestRunner extends ProcessRunner {

    protected TestRunner(String templateFolder, String templateFile, String outputFolder, String outputFile, String testStringPath, int timeout, int builderID) {
        super(templateFolder, templateFile, outputFolder, outputFile, testStringPath, timeout, builderID);
        trLogger = Logger.getLogger(TestRunner.class);
    }

    public TreeMap<String, String> runProcesses() {
        TreeMap<String, String> successfulPrograms = new TreeMap<>();
        for (String statement : this.getStatements()) {
            this.buildProgram(statement);
            String processOutput = this.runProgram();
            String SUCCESS_TEXT = "true";
            if (processOutput.startsWith(SUCCESS_TEXT)) {
                successfulPrograms.put(statement, processOutput.substring(SUCCESS_TEXT.length()));
            }
        }

        return successfulPrograms;
    }
}
