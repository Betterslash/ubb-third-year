import exception.InitializerException;

import java.io.IOException;
import java.util.Properties;

public final class ProgramInitializer {
    public static boolean IS_INITIALIZED = false;
    public static String IMAGES_FOLDER_PATH;
    public static String TEST_IMAGE_PATH;

    public static void initializeProgram(){
        try {
            var properties = new Properties();
            properties.load(ProgramInitializer.class.getClassLoader().getResourceAsStream("config.properties"));
            IMAGES_FOLDER_PATH = properties.getProperty("image.path");
            TEST_IMAGE_PATH = properties.getProperty("image.test");
            IS_INITIALIZED = true;
        } catch (IOException e) {
            throw new InitializerException(e.getMessage());
        }
    }
    
}
