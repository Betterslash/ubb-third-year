import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarTagsGenerator{

    private static int ENTRIES_NUMBER = 10;
    private static final List<String> alphabet = initializeAlphabet();
    private static final Random RANDOM = new Random();

    public static void setEntriesNumber(int newEntriesNumber){
        ENTRIES_NUMBER = newEntriesNumber;
    }

    private static List<String> initializeAlphabet() {
        var result = new ArrayList<String>();
        for (int i = 'a'; i <= 'z'; i++) {
            result.add(String.valueOf((char) i).toUpperCase());
        }
        return result;
    }

    private static String retreiveTagString(){
        var result = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            var position = RANDOM.nextInt(0, alphabet.size());
            result.append(alphabet.get(position));
        }
        return result.toString();
    }

    private static String generateTag(){
        var tagCity = "CJ";
        var tagNumber = RANDOM.nextInt(10, 99);
        var tagString = retreiveTagString();
        return tagCity + tagNumber + tagString;
    }

    public static void generate(@Nullable String path) {
        if(path == null){
            path = "input.in";
        }
        try {
            var file = new File(path);
            if(!file.exists()){
                var created = file.createNewFile();
                if(created){
                    System.out.printf("File created at path %s ...%n", file.getAbsolutePath());
                }else{
                    System.out.println("File was not created ...");
                }
            }else{
                System.out.printf("File already exists at path %s ...%n", file.getAbsolutePath());
            }
            var writer = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < ENTRIES_NUMBER; i++) {
                writer.write(generateTag() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
