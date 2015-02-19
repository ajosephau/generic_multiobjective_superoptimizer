package org.gso.processrunner;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.exec.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

public abstract class ProcessRunner {
    protected Logger processRunnerLogger = Logger.getLogger(ProcessRunner.class);
    private final String TEST_FOLDER_PREFIX = "output_";
    private String templateFile;
    private String templateFolder;
    private String outputFolder;
    private String outputFile;
    private String testStringPath;
    private String processInput;
    private String processOutput;
    private ArrayList<String> programs = new ArrayList<>();
    private TreeMap<String, String> results = new TreeMap<>();
    private String startingRule;
    private int timeout;
    private int builderID;
    private Template programTemplate;

    protected ProcessRunner(String templateFolder, String templateFile, String outputFolder, String outputFile, String testStringPath, int timeout, int builderID) {
        this.setTemplateFolder(templateFolder);
        this.setTemplateFile(templateFile);
        this.setBuilderID(builderID);
        this.setOutputFolder(outputFolder);
        this.setOutputFile(outputFile);
        this.setTestStringPath(testStringPath);
        this.setTimeout(timeout);

        Configuration cfg = new Configuration();
        try {
            cfg.setDirectoryForTemplateLoading(new File(this.getTemplateFolder()));
            programTemplate = cfg.getTemplate(this.getTemplateFile());

        } catch (IOException e) {
            processRunnerLogger.error(e);
        }
    }

    public void buildProgram(String startingRule, String program) {
        Map<String, String> data = new HashMap<>();
        processInput = program;

        data.put(startingRule, processInput);

        try {
            File directory = new File(getProgramOutputFolder());

            boolean mkdirResult = directory.mkdir();
            processRunnerLogger.debug("Result from creating directory: " + directory.getAbsolutePath() + ": " + mkdirResult);

            FileWriter file = new FileWriter(getProgramOutputFile());
            programTemplate.process(data, file);
            file.flush();
            file.close();
        } catch (TemplateException | IOException e) {
            processRunnerLogger.error(e);
        }
    }

    public String runProgram() {
        CommandLine cmdLine = new CommandLine(getTestStringPath());
        cmdLine.addArgument(getProgramOutputFolder());
        cmdLine.addArgument(getProgramOutputFile());

        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Executor executor = new DefaultExecutor();

        try {
            executor.setExitValue(0);
            executor.setWatchdog(watchdog);
            executor.setStreamHandler(new PumpStreamHandler(out));

            executor.execute(cmdLine, resultHandler);
            resultHandler.waitFor();

            int exitValue = resultHandler.getExitValue();

            processRunnerLogger.debug("Exit value: " + exitValue);

            Exception executeException = resultHandler.getException();
            if (executeException != null) {
                processRunnerLogger.error(executeException);
            }

            processOutput = removeNewlines(out.toString());

        } catch (IOException | InterruptedException e) {
            processRunnerLogger.error(e);
        }

        processRunnerLogger.debug("Program: \"" + processInput + "\" obtained result: \"" + processOutput + "\"");

        return processOutput;
    }

    protected void outputResults(TreeMap<String, String> resultsMap, String startingRule, String resultsHeader, String resultsFilePath) {
        try {
            PrintWriter pw = new PrintWriter(resultsFilePath);
            final String TAB_DELIMNITER = "\t";
            Set<String> ruleSet = resultsMap.keySet();

            pw.write(startingRule + TAB_DELIMNITER + resultsHeader + System.lineSeparator());

            if (!ruleSet.isEmpty()) {
                for (String rule : ruleSet) {
                    pw.write(rule + TAB_DELIMNITER + resultsMap.get(rule) + System.lineSeparator());
                }
            } else {
                pw.write("Empty results table." + System.lineSeparator());
            }

            pw.close();
        } catch (FileNotFoundException e) {
            processRunnerLogger.error(e);
            System.exit(-1);
        }
    }

    public abstract Object runProcesses();

    public String removeNewlines(String inputString) {
        return inputString.replace("\n", "").replace("\r", "");
    }

    public void addProgramToPrograms(String program) {
        getPrograms().add(program);
    }

    public int getBuilderID() {
        return builderID;
    }

    public void setBuilderID(int builderID) {
        this.builderID = builderID;
    }

    public String getTemplateFolder() {
        return templateFolder;
    }

    public void setTemplateFolder(String templateFolder) {
        this.templateFolder = templateFolder;
    }

    public String getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }

    public String getTEST_FOLDER_PREFIX() {
        return TEST_FOLDER_PREFIX + this.getBuilderID();
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public String getProgramOutputFolder() {
        return getOutputFolder() + System.getProperty("file.separator")  + getTEST_FOLDER_PREFIX();
    }

    public String getProgramOutputFile() {
        return getProgramOutputFolder() + System.getProperty("file.separator")  + getOutputFile();
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public String getTestStringPath() {
        return testStringPath;
    }

    public void setTestStringPath(String testStringPath) {
        this.testStringPath = testStringPath;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public ArrayList<String> getPrograms() {
        return programs;
    }

    public TreeMap<String, String> getResults() {
        return results;
    }

    public void setResults(TreeMap<String, String> results) {
        this.results = results;
    }

    public String getStartingRule() {
        return startingRule;
    }

    public void setStartingRule(String startingRule) {
        this.startingRule = startingRule;
    }
}
