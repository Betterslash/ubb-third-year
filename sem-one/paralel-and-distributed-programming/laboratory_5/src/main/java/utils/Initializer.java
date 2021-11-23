package utils;

import java.io.IOException;
import java.util.Properties;

public final class Initializer {
    public static int POLYNOMIAL_MAX_VALUE;
    private static final Properties properties = new Properties();
    public static int POLYNOMIAL_MAX_LENGTH;

    public static void initialize(){
        try {
            properties.load(Initializer.class.getClassLoader().getResourceAsStream("config.properties"));
            POLYNOMIAL_MAX_LENGTH = Integer.parseInt(properties.getProperty("polynomial.max_length"));
            POLYNOMIAL_MAX_VALUE = Integer.parseInt(properties.getProperty("polynomial.max_value"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
