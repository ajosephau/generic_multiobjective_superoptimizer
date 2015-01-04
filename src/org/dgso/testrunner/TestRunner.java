package org.dgso.testrunner;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.exec.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestRunner {
    private static Logger trLogger = Logger.getLogger(TestRunner.class);
    private final String TEST_FOLDER_PREFIX = "output_";
    private String templateFile;
    private String templateFolder;
    private String outputFolder;
    private String outputFile;
    private String testStringPath;
    private int timeout;
    private int builderID;
    private Template programTemplate;


    protected TestRunner(String templateFolder, String templateFile, String outputFolder, String outputFile, String testStringPath, int timeout, int builderID) {
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

    public static Logger getTrLogger() {
        return trLogger;
    }

    public static void setTrLogger(Logger trLogger) {
        TestRunner.trLogger = trLogger;
    }

    public void buildProgram(String statement) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("statement", statement);

        try {
            File directory = new File(getProgramOutputFolder());

            boolean mkdirResult = directory.mkdir();
            trLogger.debug("Result from creating directory: " + directory.getAbsolutePath() + ": " + mkdirResult);

            FileWriter file = new FileWriter(getProgramOutputFile());
            programTemplate.process(data, file);
            file.flush();
            file.close();
        } catch (TemplateException e) {
            trLogger.error(e);
        } catch (IOException e) {
            trLogger.error(e);
        }
    }

    public void runProgram() {
        CommandLine cmdLine = new CommandLine(getTestStringPath());
        cmdLine.addArgument(getProgramOutputFile());

        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

        ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout);
        Executor executor = new DefaultExecutor();
        executor.setExitValue(0);
        executor.setWatchdog(watchdog);

        try {
            executor.execute(cmdLine, resultHandler);

            resultHandler.waitFor();
            int exitValue = resultHandler.getExitValue();
            trLogger.debug("Exit value: " + exitValue);
            Exception executeException = resultHandler.getException();
            if (executeException != null) {
                trLogger.error(executeException);
            }
        } catch (IOException e) {
            trLogger.error(e);
        } catch (InterruptedException e) {
            trLogger.error(e);
        }
    }

    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
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

    public String getTEST_FOLDER_PREFIX() {
        return TEST_FOLDER_PREFIX + this.getBuilderID();
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public String getProgramOutputFolder() {
        return getOutputFolder() + "/" + getTEST_FOLDER_PREFIX();
    }

    public String getProgramOutputFile() {
        return getProgramOutputFolder() + "/" + getOutputFile();
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
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

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Template getProgramTemplate() {
        return programTemplate;
    }

    public void setProgramTemplate(Template programTemplate) {
        this.programTemplate = programTemplate;
    }
}
