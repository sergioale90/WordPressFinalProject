package framework;

import utils.LoggerManager;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CredentialsManager {
    private Properties properties;
    private static final LoggerManager log = LoggerManager.getInstance();
    private static final String envFilePath = System.getProperty("user.dir") + File.separator + "environment.properties";
    private static CredentialsManager instance;
    private String envId;
    private CredentialsManager() {
        initialize();
    }
    private void initialize() {
        log.info("Reading credentials");
        String wpEnvironmentId = System.getProperty("envId");
        if ((wpEnvironmentId == null) || (wpEnvironmentId.isEmpty())) {
            envId = "QA01";
        } else {
            envId = wpEnvironmentId;
        }
        log.info("Wordpress Environment Id --> " + envId);

        properties = new Properties();
        Properties envProperties = new Properties();
        try {
            envProperties.load(new FileInputStream(envFilePath));
        } catch (IOException e) {
            log.error ("Unable to load properties file");
        }
        properties.putAll(envProperties);
    }
    public static CredentialsManager getInstance() {
        if (instance == null) {
            instance = new CredentialsManager();
        }
        return instance;
    }
    public String getEnvironmentSetting(String setting) {
        return (String) getInstance().properties.get(setting);
    }
    public String getEnvId() {
        return envId;
    }
    public String getBaseURL() {
        return getEnvironmentSetting(getEnvId().toLowerCase() + ".baseURL");
    }
    public String getUsername(String userRole) {
        return getEnvironmentSetting(getEnvId().toLowerCase() + "." + userRole + ".username");
    }
    public String getPassword(String userRole) {
        return getEnvironmentSetting(getEnvId().toLowerCase() + "." + userRole + ".password");
    }
}
