import model.Grammar;
import service.lr_zero_parser.LRZeroParser;
import utils.Constants;


public class Main {
    public static void main(String[] args) {
        var grammar = new Grammar.Reader().read(Constants.STATIC_DEFAULT_PATH);
        var parser = new LRZeroParser(grammar);
        parser.parse();
    }
}
