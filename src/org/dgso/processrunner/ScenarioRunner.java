package org.dgso.processrunner;

import java.util.TreeMap;

public class ScenarioRunner extends ProcessRunner {

    TreeMap<String, String> scenarioResults;

    protected ScenarioRunner(String templateFolder, String templateFile, String outputFolder, String outputFile, String testStringPath, int timeout, int builderID) {
        super(templateFolder, templateFile, outputFolder, outputFile, testStringPath, timeout, builderID);
    }

    public TreeMap<String, String> runProcesses() {
        scenarioResults = new TreeMap<>();
        for (String statement : this.getStatements()) {
            this.buildProgram(statement);
            String processOutput = this.runProgram();
            scenarioResults.put(statement, processOutput);
        }

        return scenarioResults;
    }

    public static String formatResults(TreeMap<String, String> resultsMap) {
        resultsMap.clear();
        return "";
    }
}
