package org.dgso.processrunner;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Set;
import java.util.TreeMap;

public class ScenarioRunner extends ProcessRunner {

    TreeMap<String, String> scenarioResults;

    protected ScenarioRunner(String templateFolder, String templateFile, String outputFolder, String outputFile, String testStringPath, int timeout, int builderID) {
        super(templateFolder, templateFile, outputFolder, outputFile, testStringPath, timeout, builderID);
        trLogger = Logger.getLogger(ScenarioRunner.class);
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

    public static String formatResults(TreeMap<String, String> resultsMap, String startingRule, String resultsHeader) {
        String resultString = System.getProperty("line.separator");
        final String LEFT_MARGIN = "*  ", DIVIDER = "  *  ", RIGHT_MARGIN = "  *" + System.getProperty("line.separator");

        Set<String> ruleSet = resultsMap.keySet();

        int longestKey = Integer.max(ruleSet.stream().mapToInt(String::length).max().getAsInt(), startingRule.length());
        int longestValue = Integer.max(resultsMap.values().stream().mapToInt(String::length).max().getAsInt(), resultsHeader.length());
        int width = LEFT_MARGIN.length() + longestKey + DIVIDER.length() + longestValue + RIGHT_MARGIN.length() - 1;

        resultString += generateHeader(width);

        resultString += LEFT_MARGIN + StringUtils.center(startingRule,longestKey) + DIVIDER + StringUtils.center(resultsHeader,longestValue) + RIGHT_MARGIN;

        resultString += generateHeader(width);


        for(String rule : ruleSet) {
            resultString += LEFT_MARGIN + StringUtils.center(rule,longestKey) + DIVIDER + StringUtils.center(resultsMap.get(rule),longestValue) + RIGHT_MARGIN;
        }

        resultString += generateHeader(width);

        return resultString;
    }

    private static String generateHeader(int width) {
        return StringUtils.leftPad("", width, '*') + System.getProperty("line.separator");
    }
}
