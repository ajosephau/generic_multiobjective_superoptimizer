package org.dgso.testrunner;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class TestBuilder {
    private static Logger tbLogger = Logger.getLogger(TestBuilder.class);
    private String templateFile;
    private String templateFolder;
    private String startingRule;
    private int builderID;
    private Template programTemplate;


    protected TestBuilder(String templateFolder, String templateFile, int builderID) {
        this.setTemplateFolder(templateFolder);
        this.setTemplateFile(templateFile);
        this.setBuilderID(builderID);

        BasicConfigurator.configure();

        Configuration cfg = new Configuration();
        try {
            cfg.setDirectoryForTemplateLoading(new File(templateFolder));

            programTemplate = cfg.getTemplate(templateFile);
        } catch (IOException e) {
            tbLogger.error(e);
        }
    }

    public void buildProgram(String statement) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("statement", statement);

        try {
            // Console output
            Writer out = new OutputStreamWriter(System.out);
            programTemplate.process(data, out);
        } catch (TemplateException e) {
            tbLogger.error(e);
        } catch (IOException e) {
            tbLogger.error(e);
        }

    }

    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }

    public String getStartingRule() {
        return startingRule;
    }

    public void setStartingRule(String startingRule) {
        this.startingRule = startingRule;
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
}
