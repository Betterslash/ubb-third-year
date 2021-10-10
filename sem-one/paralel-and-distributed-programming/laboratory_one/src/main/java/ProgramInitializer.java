import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.util.Properties;

public final class ProgramInitializer {
    public static String LOGGER_CONFIGURATION_PATH;
    public static Integer PRODUCTS_NUMBER;
    public static Integer SELL_NUMBER;
    public static Integer SELL_SIZE;

    public static void initialize() throws IOException {
        var prop = new Properties();
        prop.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
        LOGGER_CONFIGURATION_PATH = prop.getProperty("loggerPath");
        PropertyConfigurator.configure(LOGGER_CONFIGURATION_PATH);
        PRODUCTS_NUMBER = Integer.parseInt(prop.getProperty("productsNumber"));
        SELL_NUMBER = Integer.parseInt(prop.getProperty("sellNumber"));
        SELL_SIZE = Integer.parseInt(prop.getProperty("maxSellSize"));
    }
}
