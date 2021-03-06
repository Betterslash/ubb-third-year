package utils;

import exception.InitializerException;

import java.io.IOException;
import java.util.*;

public final class ProgramInitializer {
    public static boolean IS_INITIALIZED = false;
    public static String IMAGES_FOLDER_PATH;
    public static String TEST_IMAGE_PATH;
    public static double CONST = (double)1/4;
    public static double[][] QUANTIZATION_MATRIX = {
            {6, 4, 4, 6, 10, 16, 20, 24},
            {5, 5, 6, 8, 10, 23, 24, 22},
            {6, 5, 6, 10, 16, 23, 28, 22},
            {6, 7, 9, 12, 20, 35, 32, 25},
            {7, 9, 15, 22, 27, 44, 41, 31},
            {10, 14, 22, 26, 32, 42, 45, 37},
            {20, 26, 31, 35, 41, 48, 48, 40},
            {29, 37, 38, 39, 45, 40, 41, 40}};

    public static Map<Integer, List<Integer>> coefficientTable = initializeCoefficientTable();

    private static Map<Integer, List<Integer>> initializeCoefficientTable() {
        var result = new HashMap<Integer, List<Integer>>();
        for (int i = 2; i < 11; i++) {
            result.put(i, initializeArray(i));
        }
        result.put(1, Arrays.asList(-1, 1));
        return result;
    }

    private static List<Integer> initializeArray(int i) {
        var result = new ArrayList<Integer>();
        var value = (int) (-Math.pow(2, i));
        result.add(value + 1);
        result.add(value / 2);
        result.add(-value / 2);
        result.add(-value - 1);
        return result;
    }

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
