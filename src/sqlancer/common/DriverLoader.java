package sqlancer.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.net.HttpURLConnection;
import java.nio.file.StandardCopyOption;
import java.util.jar.JarFile;

public class DriverLoader {

    private DriverLoader() {
    }

    public static class DriverLoadResult {
        public final Class<?> driverClass;
        public final URLClassLoader classLoader;

        public DriverLoadResult(Class<?> driverClass, URLClassLoader classLoader) {
            this.driverClass = driverClass;
            this.classLoader = classLoader;
        }
    }

    public static DriverLoadResult loadDriver(String driverClass, String dbms) throws Exception {
        String version = DriverManager.getDriverVersion(dbms);
        Path driverPath = DriverManager.getDriverPath(dbms, version);

        System.out.println(
                "[DEBUG] Requested driver for " + dbms + ", version: " + version + " driverPath: " + driverPath);

        // 1. Download the driver
        downloadDriver(dbms, version, driverPath);

        // 2. Create classloader with system classloader as parent
        URLClassLoader driverLoader = new URLClassLoader(new URL[] { driverPath.toUri().toURL() },
                ClassLoader.getSystemClassLoader());

        // 3. Load the driver class
        Class<?> loadedDriverClass = driverLoader.loadClass(driverClass);

        // 4. Verify version
        String loadedVersion = getDriverVersion(loadedDriverClass, driverPath);

        if (!version.equals(loadedVersion)) {
            driverLoader.close();
            throw new IllegalStateException("Version mismatch! Expected " + version + " but got " + loadedVersion);
        }

        System.out.println("[SUCCESS] Loaded driver: " + driverClass + " v" + loadedVersion);

        return new DriverLoadResult(loadedDriverClass, driverLoader);
        // return loadedDriverClass;
    }

    private static String getDriverVersion(Class<?> driverClass, Path driverPath) {
        try {
            // Try manifest first
            try (JarFile jar = new JarFile(driverPath.toFile())) {
                String manifestVersion = jar.getManifest().getMainAttributes().getValue("Implementation-Version");
                if (manifestVersion != null) {
                    return manifestVersion;
                }
            }

            // Try driver-specific method
            try {
                Method versionMethod = driverClass.getMethod("getClientVersion");
                return (String) versionMethod.invoke(null);
            } catch (NoSuchMethodException e) {
                // Fall through
            }

            // Fallback to filename parsing
            return driverPath.getFileName().toString().replaceAll(".*-([\\d.]+)\\.jar", "$1");

        } catch (Exception e) {
            System.err.println("Warning: Could not determine driver version");
            return "UNKNOWN";
        }
    }

    private static void downloadDriver(String dbms, String version, Path target) throws IOException {
        String artifactId = getArtifactId(dbms);
        String groupPath = getGroupPath(dbms);
        String repoUrl = DriverManager.getRepositoryUrl();

        String downloadUrl = String.format("%s/%s/%s/%s/%s-%s.jar", repoUrl, groupPath, artifactId, version, artifactId,
                version);

        System.out.println("Downloading driver from: " + downloadUrl);

        Files.createDirectories(target.getParent());
        HttpURLConnection connection = (HttpURLConnection) new URL(downloadUrl).openConnection();
        try (InputStream in = connection.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static String getArtifactId(String dbms) {
        switch (dbms) {
        case "sqlite":
            return "sqlite-jdbc";
        case "duckdb":
            return "duckdb_jdbc";
        default:
            throw new IllegalArgumentException("Unknown DBMS: " + dbms);
        }
    }

    private static String getGroupPath(String dbms) {
        switch (dbms) {
        case "sqlite":
            return "org/xerial";
        case "duckdb":
            return "org/duckdb";
        default:
            throw new IllegalArgumentException("Unknown DBMS: " + dbms);
        }
    }
}
