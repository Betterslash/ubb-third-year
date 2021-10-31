package model;

import com.sun.tools.javac.Main;
import lombok.Data;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

@Data
public final class MatrixWorkerProperties {
    public static WorkerType WORKER_TYPE;
    public static Integer THREADS_NUMBER;

    public static final int SIZE_ONE = ThreadLocalRandom.current().nextInt(1, 10);
    public static final int SIZE_TWO = ThreadLocalRandom.current().nextInt(1, 10);

    public static void initializeProperties() throws IOException {
        var prop = new Properties();
        prop.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
        WORKER_TYPE = WorkerType.valueOf(prop.getProperty("worker.type"));
        THREADS_NUMBER = Integer.valueOf(prop.getProperty("worker.threadsNumber"));
    }
}
