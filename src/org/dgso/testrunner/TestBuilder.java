package org.dgso.testrunner;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestBuilder {
    private static Logger tbLogger = Logger.getLogger(TestBuilder.class);
    private String templateFile;
    private String templateFolder;
    private final String TEST_FOLDER_PREFIX = "output_";
    private String outputFolder;
    private String outputFile;
    private int builderID;
    private Template programTemplate;


    protected TestBuilder(String templateFolder, String templateFile, String outputFolder, String outputFile, int builderID) {
        this.setTemplateFolder(templateFolder);
        this.setTemplateFile(templateFile);
        this.setBuilderID(builderID);
        this.setOutputFolder(outputFolder);
        this.setOutputFile(outputFile);

        Configuration cfg = new Configuration();
        try {
            cfg.setDirectoryForTemplateLoading(new File(this.getTemplateFolder()));
            programTemplate = cfg.getTemplate(this.getTemplateFile());

        } catch (IOException e) {
            tbLogger.error(e);
        }
    }

    public void buildProgram(String statement) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("statement", statement);

        try {
            File directory = new File(getProgramOutputFolder());

            boolean mkdirResult = directory.mkdir();
            tbLogger.debug("Result from creating directory: " + directory.getAbsolutePath() + ": " + mkdirResult);

            FileWriter file = new FileWriter(getProgramOutputFile());
            programTemplate.process(data, file);
            file.flush();
            file.close();
        } catch (TemplateException e) {
            tbLogger.error(e);
        } catch (IOException e) {
            tbLogger.error(e);
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
}
