package org.gso.processrunner;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class ProcessRunnerFactory {
    private static ArrayList<ProcessRunner> processRunners;
    private static Logger tbfLogger = Logger.getLogger(ProcessRunnerFactory.class);
    private static String processOutputFolder;

    public void createTestRunners(String templateFolder, String templateFile, String outputFolder, String outputFile, String testScriptPath, String startingRule, int timeout, int instanceCount) {
        createProcessRunners(outputFolder);

        processRunners = new ArrayList<>(instanceCount);
        for (int i = 0; i < instanceCount; i++) {
            TestRunner tr = new TestRunner(templateFolder, templateFile, outputFolder, outputFile, testScriptPath, startingRule, timeout, i);
            processRunners.add(tr);
        }
    }

    public void createScenarioRunners(String templateFolder, String templateFile, String outputFolder, String outputFile, String testScriptPath, String startingRule, int timeout, int instanceCount) {
        createProcessRunners(outputFolder);

        processRunners = new ArrayList<>(instanceCount);
        for (int i = 0; i < instanceCount; i++) {
            ScenarioRunner sr = new ScenarioRunner(templateFolder, templateFile, outputFolder, outputFile, testScriptPath, startingRule, timeout, i);
            processRunners.add(sr);
        }
    }

    private void createProcessRunners(String outputFolder) {
        setProcessOutputFolder(outputFolder);

        File directory = new File(getProcessOutputFolder());
        boolean mkdirResult = directory.mkdir();
        tbfLogger.debug("Result from creating directory: " + directory.getAbsolutePath() + ": " + mkdirResult);
    }

    public void assignProgramsToProcessRunners(ArrayList<String> programs, int startingProgram) {
        int numProcessRunners = processRunners.size();
        for (int i = 0; i < programs.size(); i++) {
            processRunners.get(i % numProcessRunners).addProgramToPrograms(programs.get(i));
        }

        for(ProcessRunner pr: processRunners) {
            if(startingProgram < pr.getPrograms().size()) {
                pr.setPrograms(new ArrayList(pr.getPrograms().subList(startingProgram, pr.getPrograms().size())));
            }
        }
    }

    public TreeMap<String, String> runAllProcessesInSerial() {
        TreeMap<String, String> results = new TreeMap<>();


        for (ProcessRunner pr : processRunners) {
            pr.runProcesses();
        }

        for (ProcessRunner pr : processRunners) {
            results.putAll(pr.getResults());
        }

        return results;
    }


    public TreeMap<String, String> runAllProcessesInParallel() {
        TreeMap<String, String> results = new TreeMap<>();

        processRunners.parallelStream().map(ProcessRunner::runProcesses).count();

        for (ProcessRunner pr : processRunners) {
            results.putAll(pr.getResults());
        }

        return results;
    }


    public void cleanupProcessOutputFolder() {
        File directory = new File(getProcessOutputFolder());
        try {
            FileUtils.deleteDirectory(directory);
        } catch (IOException e) {
            tbfLogger.error(e);
        }
        tbfLogger.debug("Result from deleting directory: " + getProcessOutputFolder() + ": true");
    }

    public ProcessRunner getFirstProcessRunner() {
        return processRunners.get(0);
    }
    
    public String getProcessOutputFolder() {
        return processOutputFolder;
    }

    public void setProcessOutputFolder(String processOutputFolder) {
        ProcessRunnerFactory.processOutputFolder = processOutputFolder;
    }
}
