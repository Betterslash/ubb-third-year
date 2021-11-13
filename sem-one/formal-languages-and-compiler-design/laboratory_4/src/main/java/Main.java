import model.parser.DescendentRecursiveParser;

public class Main {
    public static void main(String[] args) {
        var descendentRecursiveParser = new DescendentRecursiveParser();
        var response = descendentRecursiveParser.parse();
        System.out.println(response);
    }
}
