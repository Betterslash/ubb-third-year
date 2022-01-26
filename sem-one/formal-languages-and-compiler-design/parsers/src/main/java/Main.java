import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        /*var grammar = new Grammar.Reader().read(Constants.STATIC_DEFAULT_PATH);
        var parser = new LRZeroParser(grammar);
        parser.parse();*/
        var matches = Pattern.matches("0400 \\| +04", "0400");
        System.out.println(matches);
    }
}
