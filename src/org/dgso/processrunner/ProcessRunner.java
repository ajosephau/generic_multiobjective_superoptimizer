package org.dgso.processrunner;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.exec.*;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public abstract class ProcessRunner {
    protected Logger trLogger = Logger.getLogger(ProcessRunner.class);
    private final String TEST_FOLDER_PREFIX = "output_";
    private String templateFile;
    private String templateFolder;
    private String outputFolder;
    private String outputFile;
    private String testStringPath;
    private String processInput;
    private String processOutput;
    private ArrayList<String> statements = new ArrayList<>();
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
            trLogger.error(e);
        }
    }

    public void buildProgram(String statement) {
        Map<String, String> data = new HashMap<>();
        processInput = statement;

        data.put("statement", processInput);

        try {
            File directory = new File(getProgramOutputFolder());

            boolean mkdirResult = directory.mkdir();
            trLogger.debug("Result from creating directory: " + directory.getAbsolutePath() + ": " + mkdirResult);

            FileWriter file = new FileWriter(getProgramOutputFile());
            programTemplate.process(data, file);
            file.flush();
            file.close();
        } catch (TemplateException | IOException e) {
            trLogger.error(e);
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

            trLogger.debug("Exit value: " + exitValue);

            Exception executeException = resultHandler.getException();
            if (executeException != null) {
                trLogger.error(executeException);
            }

            processOutput = removeNewlines(out.toString());

        } catch (IOException | InterruptedException e) {
            trLogger.error(e);
        }

        trLogger.debug("Program: \"" + processInput + "\" obtained result: \"" + processOutput + "\"");

        return processOutput;
    }

    public abstract TreeMap<String, String> runProcesses();

    public String removeNewlines(String inputString) {
        return inputString.replace("\n", "").replace("\r", "");
    }

    public void addStatementToStatements(String statement) {
        getStatements().add(statement);
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
        return getOutputFolder() + "/" + getTEST_FOLDER_PREFIX();
    }

    public String getProgramOutputFile() {
        return getProgramOutputFolder() + "/" + getOutputFile();
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

    public ArrayList<String> getStatements() {
        return statements;
    }
}
