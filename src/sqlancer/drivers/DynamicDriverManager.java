package sqlancer.drivers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

public class DynamicDriverManager {
    private static final String CONFIG_FILE = "sqlancer-drivers.properties";
    private static final String DRIVER_CACHE_DIR = ".sqlancer-drivers";
    private static Properties properties;

    private DynamicDriverManager() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    static {
        properties = new Properties();
        try (InputStream input = DynamicDriverManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not load driver configuration: " + e.getMessage());
        }
    }

    public static void registerDriver(String dbType) throws Exception {
        String version = properties.getProperty(dbType + ".driver.version");
        String urlTemplate = properties.getProperty(dbType + ".driver.url");

        if (version == null || urlTemplate == null) {
            return; // Use default driver
        }

        String driverUrl = urlTemplate.replace("{version}", version);
        Path cachedDriver = downloadAndCacheDriver(driverUrl, dbType, version);

        // Use try-with-resources to auto-close the ClassLoader
        try (URLClassLoader child = new URLClassLoader(new URL[] { cachedDriver.toUri().toURL() },
                DynamicDriverManager.class.getClassLoader())) {

            Class<?> driverClass;
            switch (dbType.toLowerCase()) {
            case "sqlite":
            case "sqlite3":
                driverClass = child.loadClass("org.sqlite.JDBC");
                break;
            case "duckdb":
                driverClass = child.loadClass("org.duckdb.DuckDBDriver");
                break;
            default:
                throw new IllegalArgumentException("Unsupported database type: " + dbType);
            }

            // Register the driver (no need to close - DriverManager retains it)
            Driver driver = (Driver) driverClass.getDeclaredConstructor().newInstance();
            DriverManager.registerDriver(new DriverShim(driver));
        } // ClassLoader auto-closed here
    }

    private static Path downloadAndCacheDriver(String url, String dbType, String version) throws IOException {
        Path cacheDir = Path.of(System.getProperty("user.home"), DRIVER_CACHE_DIR);
        Files.createDirectories(cacheDir);

        String fileName = String.format("%s-%s.jar", dbType, version);
        Path driverPath = cacheDir.resolve(fileName);

        if (!Files.exists(driverPath)) {
            try (InputStream in = new URL(url).openStream()) {
                Files.copy(in, driverPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }

        return driverPath;
    }
}
