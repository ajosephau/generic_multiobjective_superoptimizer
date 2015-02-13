package org.gso.processrunner;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeMap;

public class ScenarioRunner extends ProcessRunner {

    TreeMap<String, String> scenarioResults;
    
    protected ScenarioRunner(String templateFolder, String templateFile, String outputFolder, String outputFile, String testStringPath, int timeout, int builderID) {
        super(templateFolder, templateFile, outputFolder, outputFile, testStringPath, timeout, builderID);
        processRunnerLogger = Logger.getLogger(ScenarioRunner.class);
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
    
    public void outputScenarioResults(TreeMap<String, String> resultsMap, String startingRule, String resultsHeader, String resultsFilePath) {
        try {
            PrintWriter pw = new PrintWriter(resultsFilePath);
            final String TAB_DELIMNITER = "\t";
            Set<String> ruleSet = resultsMap.keySet();

            pw.write(startingRule + TAB_DELIMNITER + resultsHeader + System.lineSeparator());
            
            if(!ruleSet.isEmpty()) {
                for(String rule : ruleSet) {
                    pw.write(rule + TAB_DELIMNITER + resultsMap.get(rule) + System.lineSeparator());
                }
            }
            else {
                pw.write("Empty results table." + System.lineSeparator());
            }
            
            pw.close();
        }
        catch (FileNotFoundException e) {
            processRunnerLogger.error(e);
            System.exit(-1);
        }
    }

    public static String formatResultsForConsoleOutput(TreeMap<String, String> resultsMap, String startingRule, String resultsHeader) {
        String resultString = System.lineSeparator();
        final String LEFT_MARGIN = "*  ", DIVIDER = "  *  ", RIGHT_MARGIN = "  *" + System.lineSeparator();

        Set<String> ruleSet = resultsMap.keySet();

        if(!ruleSet.isEmpty()) {
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
        }
        else {
            resultString += "********************" + System.lineSeparator();
            resultString += "Empty results table." + System.lineSeparator();
            resultString += "********************";
        }

        return resultString;
    }

    private static String generateHeader(int width) {
        return StringUtils.leftPad("", width, '*') + System.lineSeparator();
    }
}
