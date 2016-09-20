package io.crowdcode.jenkins.jassm;
import com.google.inject.Binding;
import com.google.inject.Key;
import freemarker.template.TemplateException;
import hudson.EnvVars;
import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import io.crowdcode.jenkins.jassm.data.JassmContainer;
import io.crowdcode.jenkins.jassm.dtoservice.JassmDataRowDtoService;
import io.crowdcode.jenkins.jassm.freemarker.JassmPageDumper;
import io.crowdcode.jenkins.jassm.io.JassmStorage;
import io.crowdcode.jenkins.jassm.io.LockService;
import jenkins.model.Jenkins;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 */
public class JassmBuilder extends Builder implements SimpleBuildStep {

    private final String group;
    private final String id;
    private final String columnValue1;
    private final String columnValue2;
    private final String columnValue3;
    private final String columnValue4;


    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public JassmBuilder(String group, String id, String columnValue1, String columnValue2, String columnValue3, String columnValue4) {
        this.group = group;
        this.id = id;
        this.columnValue1 = columnValue1;
        this.columnValue2 = columnValue2;
        this.columnValue3 = columnValue3;
        this.columnValue4 = columnValue4;
    }

    public String getColumnValue1() {
        return columnValue1;
    }

    public String getColumnValue2() {
        return columnValue2;
    }

    public String getColumnValue3() {
        return columnValue3;
    }

    public String getColumnValue4() {
        return columnValue4;
    }

    public String getGroup() {
        return group;
    }

    public String getId() {
        return id;
    }

    public interface VariableResolver{
        String resolve(String name);
    }

    @Override
    public void perform(final Run<?,?> build, FilePath workspace, Launcher launcher, final TaskListener listener) {
        final VariableResolver resolver=  new VariableResolver() {
            @Override
            public String resolve(String name) {
                if (name != null && (name.trim().startsWith("%") || name.trim().startsWith("$"))) {
                    name = name.substring(2, name.length()-1);
                    String result = null;
                    try {
                        EnvVars environment = build.getEnvironment(listener);
                        result = environment.get(name, name);
                    } catch (InterruptedException|IOException e) {
                        e.printStackTrace();
                    }
                    return result;
                } else {
                    return name;
                }
            }
        };


        DescriptorImpl descriptor = getDescriptor();
        File outputDestination = new File(descriptor.getOutputDestination());
        File file = new File(outputDestination, "/jassm");
        File datastore = new File(outputDestination, "/jassm-datastore.xml");
        LockService.lock(file);
        listener.getLogger().println("Writing Statistics");
        try{
            JassmStorage.updateRow(JassmDataRowDtoService.convert(this, resolver), datastore);
            JassmPageDumper.writeOutputFile(JassmStorage.loadDataStore(datastore), outputDestination, descriptor.pageHeadline, descriptor.columnCaption1, descriptor.columnCaption2, descriptor.columnCaption3, descriptor.columnCaption4, getDescriptor().getTemplateName());

            listener.getLogger().println("JASSM has written all data to "+outputDestination.getAbsolutePath());

        } catch (IOException| TemplateException e) {
            e.printStackTrace();
            listener.getLogger().println("Unexpected Error has happended:"+e.getMessage());
        } finally {
            LockService.unlock(file);
        }

        // This is where you 'build' the project.
        // Since this is a dummy, we just say 'hello world' and call that a build.

        // This also shows how you can consult the global configuration of the builder
/*        if (getDescriptor().getUseFrench())
            listener.getLogger().println("Bonjour, "+name+"!");
        else
            listener.getLogger().println("Hello, "+name+"!");
*/
    }

    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl)super.getDescriptor();
    }

    /**
     * Descriptor for {@link JassmBuilder}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     *
     * <p>
     * See <tt>src/main/resources/hudson/plugins/hello_world/JassmBuilder/*.jelly</tt>
     * for the actual HTML fragment for the configuration screen.
     */
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        private String pageHeadline;
        private String columnCaption1;
        private String columnCaption2;
        private String columnCaption3;
        private String columnCaption4;

        private String outputDestination;
        private String templateName;

        /**
         * In order to load the persisted global configuration, you have to 
         * call load() in the constructor.
         */
        public DescriptorImpl() {
            load();
        }

        /**
         * Performs on-the-fly validation of the form field 'name'.
         *
         * @param value
         *      This parameter receives the value that the user has typed.
         * @return
         *      Indicates the outcome of the validation. This is sent to the browser.
         *      <p>
         *      Note that returning {@link FormValidation#error(String)} does not
         *      prevent the form from being saved. It just means that a message
         *      will be displayed to the user.
         */
        public FormValidation doCheckId(@QueryParameter String value)
                throws IOException, ServletException {
            if (value == null || value.trim().isEmpty()){
                return FormValidation.error("Please set an ID");
            } else {
                return FormValidation.ok();
            }
        }


        public FormValidation doCheckOutputDestination(@QueryParameter String value)
                throws IOException, ServletException {
            if (value == null || value.trim().isEmpty()){
                return FormValidation.error("Please set an output path where the plugin will write its stats.");
            }else {
                File file = new File(value);
                if (!file.exists()) {
                    return FormValidation.error("Please enter an existing path");
                } else if (!file.isDirectory()) {
                    return FormValidation.error("The entered path is no directory");
                } else if (!file.canWrite()) {
                    return FormValidation.error("Insufficient privileges. You must be allowed to WRITE to the entered directory");
                } else {
                    return FormValidation.ok();
                }
            }
        }



        public FormValidation doCheckTemplateName(@QueryParameter String value)
                throws IOException, ServletException {
            if (value == null || value.trim().isEmpty()){
                return FormValidation.ok(); // use default template
            }else {
                File file = new File(value);
                if (!file.exists()) {
                    return FormValidation.error("Please enter an existing template path");
                } else if (file.isDirectory()) {
                    return FormValidation.error("The entered path is a directory");
                } else if (!file.canRead()) {
                    return FormValidation.error("Insufficient privileges. You must be allowed to READ to the entered file");
                } else {
                    return FormValidation.ok();
                }
            }
        }




        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types 
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "JASSM Descriptor Cofiguration";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            //   To persist global configuration information,
            // set that to properties and call save().
            pageHeadline = formData.getString("pageHeadline");
            columnCaption1 = formData.getString("columnCaption1");
            columnCaption2 = formData.getString("columnCaption2");
            columnCaption3 = formData.getString("columnCaption3");
            columnCaption4 = formData.getString("columnCaption4");
            outputDestination = formData.getString("outputDestination");
            templateName = formData.getString("templateName");

            // ^Can also use req.bindJSON(this, formData);
            //  (easier when there are many fields; need set* methods for this, like setUseFrench)
            save();
            return super.configure(req,formData);
        }


        public String getColumnCaption1() {
            return columnCaption1;
        }

        public String getColumnCaption2() {
            return columnCaption2;
        }

        public String getColumnCaption3() {
            return columnCaption3;
        }

        public String getColumnCaption4() {
            return columnCaption4;
        }

        public String getOutputDestination() {
            return outputDestination;
        }

        public String getPageHeadline() {
            return pageHeadline;
        }

        public String getTemplateName() {
            return templateName;
        }
    }
}

