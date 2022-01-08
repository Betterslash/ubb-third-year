import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Pattern;

@Jsonable
public class CarTagChecker {
    private static boolean checkString(String carTag){
        return Pattern.matches("CJ[0-9]{2}[A-Z]{3}", carTag);
    }

    public static void checkFile(@Nullable String filePath){
        if(filePath == null){
            filePath = "input.in";
        }
        try {
            var reader = new BufferedReader(new FileReader(filePath));
            var result = new HashMap<String, Boolean>();
            reader.lines()
                    .toList()
                    .forEach(e -> result.put(e, checkString(e)));
            result.entrySet().forEach(System.out::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
