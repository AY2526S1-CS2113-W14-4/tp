package internity.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Utility class responsible for configuring and providing a centralized logger
 * for the Internity application.
 *
 * <p>
 * This class automatically creates a log file located at
 * {@code ./logs/internity.log} and sets up a {@link FileHandler} to append
 * log messages to it.
 * </p>
 *
 * <p>
 * The logger writes messages in plain-text format using
 * {@link SimpleFormatter} and captures all log levels ({@link Level#ALL}).
 * Parent console handlers are disabled to avoid duplicate logging output.
 * </p>
 */
public class InternityLogger {
    private static final String LOG_FILE_PATH = "./logs/internity.log";
    private static final Logger logger = Logger.getLogger("internity");
    private static boolean isInitialized = false;
    private static boolean isEnabled = false;

    /**
     * Returns the main logger for the application.
     * Initializes logging to a file on the first call.
     *
     * @return a configured Logger instance
     */
    public static Logger getLogger() {
        if (!isEnabled) {
            disableLogging();
            return logger;
        }
        if (!isInitialized) {
            setupLogging();
            isInitialized = true;
        }
        return logger;
    }

    /**
     * Configures the logger to write to a file.
     * Creates the logs directory if it doesn't exist.
     */
    private static void setupLogging() {
        try {
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get("./logs"));
            FileHandler fileHandler = new FileHandler(LOG_FILE_PATH, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.OFF);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to initialize logger.");
        }
    }

    private static void disableLogging() {
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
    }

}
