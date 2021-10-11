import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class HashTableTest {
    public static void main(String[] args) {
        var hashTableWrapper = new HashTableWrapper();
        var myObj = new File("D:\\Uni\\sem-one\\formal-languages-and-compiler-design\\laboratory_2\\scanner\\src\\main\\resources\\program_2.txt");
        try {
            var myReader = new Scanner(myObj);
            while (myReader.hasNextLine()){
                var line = Arrays.stream(myReader.nextLine().split(" ")).toList();
                line.forEach(hashTableWrapper::add);
            }
            hashTableWrapper.getInternalRepresentation()
                    .forEach((key, value) -> value.forEach(q -> System.out.println(q + " with key " + hashTableWrapper.getId(q))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
