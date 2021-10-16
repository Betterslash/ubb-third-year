package constants;

import java.util.*;
import java.util.stream.Collectors;

public final class LanguageSpecifications {
    public static final List<String> separators =
            Arrays.asList("[", "]", " ", "{", "}", "(", ")", ";", "\n");

    public static final List<String> operators =
            Arrays.asList("+", "-", "*", "/", "%", "<", "<=", "=", ">=", ">",
                    ">>", "<<", "==", "&&", "||", "!", "!=", "&", "<-", ":", "::",
                    "|", "^", "++", "--", ",");

    public static final List<String> reservedKeyWords =
            Arrays.asList("for", "while", "if", "readln", "writeln",
                    "read", "write", "var", "else", "int",
                    "bool", "char", "string", "array");

    public static final List<String> all = initializeAll();

    public static final Map<String, Integer> tokensTable = initializeTokensTable();

    private static Map<String, Integer> initializeTokensTable() {
        var result = new HashMap<String, Integer>();
        int index = 2;
        for (var token: all){
            result.put(token, index);
            index += 1;
        }
        result.put("identifier", 0);
        result.put("constant", 1);
        return result;
    }

    private static List<String> initializeAll() {
        var result = new ArrayList<String>();
        result.addAll(separators);
        result.addAll(separators.stream().map(e -> e.toUpperCase(Locale.ROOT)).collect(Collectors.toList()));
        result.addAll(operators);
        result.addAll(operators.stream().map(e -> e.toUpperCase(Locale.ROOT)).collect(Collectors.toList()));
        result.addAll(reservedKeyWords);
        result.addAll(reservedKeyWords.stream().map(e -> e.toUpperCase(Locale.ROOT)).collect(Collectors.toList()));
        return result;
    }
}
