package org.gso.processrunner;

import org.apache.log4j.Logger;

import java.util.TreeMap;

public class TestRunner extends ProcessRunner {

    protected TestRunner(String templateFolder, String templateFile, String outputFolder, String outputFile, String testStringPath, String startingRule, int timeout, int builderID) {
        super(templateFolder, templateFile, outputFolder, outputFile, testStringPath, timeout, builderID);
        this.setStartingRule(startingRule);
        processRunnerLogger = Logger.getLogger(TestRunner.class);
    }

    public Object runProcesses() {
        int count = 1, size = this.getPrograms().size();
        TreeMap<String, String> successfulPrograms = new TreeMap<>();
        for (String program : this.getPrograms()) {
            processRunnerLogger.info("Currently running test " + count + " of " + size);
            this.buildProgram(this.getStartingRule(), program);
            String processOutput = this.runProgram();
            String SUCCESS_TEXT = "true";
            if (processOutput.startsWith(SUCCESS_TEXT)) {
                successfulPrograms.put(program, processOutput.substring(SUCCESS_TEXT.length()));
            }
            count++;
        }

        setResults(successfulPrograms);

        return this.getResults();
    }

    public void outputTestResults(TreeMap<String, String> resultsMap, String startingRule, String resultsFilePath) {
        final String HEADER_TEXT = "output result";
        outputResults(resultsMap, startingRule, HEADER_TEXT, resultsFilePath);
    }
}
