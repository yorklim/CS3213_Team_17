package sqlancer.common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class DriverManager {
    private static final String CONFIG_FILE = "configs/drivers.properties";
    private static final String DEFAULT_CONFIG = "configs/default-drivers.properties";
    private static final String DRIVER_DIR = "drivers";
    private static final String DEFAULT_REPO = "https://repo1.maven.org/maven2";
    private static final Properties properties = new Properties();

    static {
        initialize();
    }

    private static void initialize() {
        try {
            // Try to load existing config first
            Path configPath = Paths.get(CONFIG_FILE);
            if (Files.exists(configPath)) {
                try (InputStream input = Files.newInputStream(configPath)) {
                    properties.load(input);
                    System.out.println("Loaded configuration from: " + configPath);
                    return;
                }
            }

            // Load default config from classpath
            try (InputStream defaultInput = DriverManager.class.getResourceAsStream(DEFAULT_CONFIG)) {
                if (defaultInput == null) {
                    throw new IOException("Default configuration not found in classpath: " + DEFAULT_CONFIG);
                }

                // Create parent directories if needed
                Files.createDirectories(configPath.getParent());

                // Copy default config
                Files.copy(defaultInput, configPath);
                System.out.println("Created default configuration at: " + configPath);

                // Reload the properties
                try (InputStream input = Files.newInputStream(configPath)) {
                    properties.load(input);
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR: Failed to initialize driver configuration. Using hardcoded defaults.");
            loadHardcodedDefaults();
            e.printStackTrace();
        }
    }

    private static void loadHardcodedDefaults() {
        properties.setProperty("sqlite.driver.version", "3.47.2.0");
        properties.setProperty("duckdb.driver.version", "1.1.3");
    }

    public static String getDriverVersion(String dbms) {
        return properties.getProperty(dbms + ".driver.version");
    }

    public static String getRepositoryUrl() {
        return properties.getProperty("repository.url", DEFAULT_REPO);
    }

    public static Path getDriverPath(String dbms, String version) {
        return Paths.get(DRIVER_DIR, dbms + "-driver-" + version + ".jar");
    }
}
