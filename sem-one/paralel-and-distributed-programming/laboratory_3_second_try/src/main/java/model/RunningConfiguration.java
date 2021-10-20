package model;

import org.apache.log4j.PropertyConfigurator;
import com.sun.tools.javac.Main;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public final class RunningConfiguration {
    public static SplittingType SPLITTING_TYPE;
    public static WorkerType WORKER_TYPE;
    public static Integer THREAD_NUMBER;
    public static int MATRIX_SIZE_ONE;
    public static int MATRIX_SIZE_TWO;
    public static int MATRIX_SIZE_THREE;
    public static String LOGGER_CONFIGURATION_PATH;

    public static void initializeConfigurations() throws IOException {
        var prop = new Properties();
        prop.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
        LOGGER_CONFIGURATION_PATH = prop.getProperty("loggerPath");
        PropertyConfigurator.configure(LOGGER_CONFIGURATION_PATH);
        WORKER_TYPE = WorkerType.valueOf(prop.getProperty("worker.type"));
        THREAD_NUMBER = Integer.valueOf(prop.getProperty("worker.threadsNumber"));
        SPLITTING_TYPE = SplittingType.valueOf(prop.getProperty("worker.splittingType"));
        MATRIX_SIZE_ONE = 1500;
        MATRIX_SIZE_TWO = 1500;
        MATRIX_SIZE_THREE = 1500;
    }
}
