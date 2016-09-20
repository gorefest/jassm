package io.crowdcode.jenkins.jassm.freemarker;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import io.crowdcode.jenkins.jassm.data.JassmContainer;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Arrays;

/**
 * Created by marcus on 26.07.2016.
 */
public class JassmPageDumper {

    static File TEMP_OUTPUT_DIRECTORY;
    static File TEMPLATE_FILE;
    static Configuration cfg;

    public static void initialize(){

        cfg = new Configuration(Configuration.VERSION_2_3_23);
        try {
            File tmpFile = File.createTempFile("jassm", "tmp");
            File tmpDir = tmpFile.getParentFile();
            tmpDir.deleteOnExit();
            TEMP_OUTPUT_DIRECTORY = new File(tmpDir, "jassm"+System.currentTimeMillis());
            if (!TEMP_OUTPUT_DIRECTORY.mkdirs()) {
                throw new RuntimeException("Creation of temp directory "+TEMP_OUTPUT_DIRECTORY.getAbsolutePath()+" failed");
            }
            TEMP_OUTPUT_DIRECTORY.deleteOnExit();

            TEMPLATE_FILE = new File(TEMP_OUTPUT_DIRECTORY,"output.ftlh");

            InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream("output.ftlh");

            if (resource == null) {
                resource = JassmPageDumper.class.getClassLoader().getResourceAsStream("output.ftlh");
            }


            FileUtils.copyInputStreamToFile(resource,TEMPLATE_FILE);

            cfg.setDirectoryForTemplateLoading(TEMP_OUTPUT_DIRECTORY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
    }

    static {
        initialize();
    }

    @edu.umd.cs.findbugs.annotations.SuppressWarnings(
            value="DM_DEFAULT_ENCODING",
            justification="Screw this.")
    public static synchronized void writeOutputFile(JassmContainer container, File destinationDirectory, String outputCaption, String columnCaption1, String columnCaption2, String columnCaption3, String columnCaption4, String templateName) throws IOException, TemplateException {
        PageModel pageModel = new PageModel();
        pageModel.setHeadline(outputCaption);
        pageModel.setColumnCaption1(columnCaption1);
        pageModel.setColumnCaption2(columnCaption2);
        pageModel.setColumnCaption3(columnCaption3);
        pageModel.setColumnCaption4(columnCaption4);
        pageModel.setJassmDataRows(Arrays.asList(container.getJassmDataRows()));

        final Template temp;
        if (templateName != null && !templateName.trim().isEmpty()) {
            File file = new File(templateName);
            cfg.setDirectoryForTemplateLoading(file.getParentFile());
            temp = cfg.getTemplate(file.getName());
        } else {
                /* Get the template (uses cache internally) */
            temp = cfg.getTemplate("output.ftlh");
        }

        /* Merge data-model with template */
        File file = new File(destinationDirectory, "index.html");
        System.out.println(file.getAbsoluteFile());
        try(Writer out = new FileWriter(file)) {
            temp.process(pageModel, out);
        }
    }
}
