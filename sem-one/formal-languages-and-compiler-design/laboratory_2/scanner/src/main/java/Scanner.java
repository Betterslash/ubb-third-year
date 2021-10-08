import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Scanner {

    public static boolean isInOperator(char character){
        for (var operator: LanguageSpecifications.operators) {
            var charAsString = "" + character;
            if(operator.contains(charAsString)){
                return true;
            }
        }
        return false;
    }

    public static boolean isSpecialQuote(String line, Integer index){
        if(index == 0){
            return false;
        }else return Objects.equals(line, "\\");
    }

    public static PifPair getStringToken(String line, Integer index){
        var token = new StringBuilder();
        var quotes = 0;
        while (quotes < 2 && line.length() > index){
            if(line.charAt(index) == '"'){
                quotes += 1;
            }
            token.append(line.charAt(index));
            index += 1;
        }
        return new PifPair(token.toString(), index);
    }

    public static PifPair getOperatorToken(String line, Integer index){
        var token = new StringBuilder();
        while(index < line.length() && isInOperator(line.charAt(index))){
            token.append(line.charAt(index));
            index += 1;
        }
        return new PifPair(token.toString(), index);
    }

    public static List<String> getTokens(String line){
        var token = new StringBuilder();
        var index = 0;
        var result = new ArrayList<String>();
        while (index < line.length()){
            if(line.charAt(index) == '"'){
                var res = getStringToken(line, index);
                result.add(res.getValue(), res.getKey());
                token = new StringBuilder();
            }
            else if(isInOperator(line.charAt(index))){
                var res = getOperatorToken(line, index);
                result.add(res.getValue(), res.getKey());
                token = new StringBuilder();
            }
            else if(isInSeparators(line.charAt(index))){
                index += 1;
                var resul = "" + line.charAt(index);
                token = new StringBuilder();
                result.add(index, resul);
            }else{
                index += 1;
            }
        }
        return result;
    }

    private static boolean isInSeparators(char charAt) {
        var charAsString = "" + charAt;
        for (var separator: LanguageSpecifications.separators) {
            if(separator.contains(charAsString)){
                return true;
            }
        }
        return false;
    }

    public static boolean isIdentifier(String token){
        var pattern = Pattern.compile("^[a-zA-Z]([a-zA-Z]|[0-9]){0,9}$");
        var matcher = pattern.matcher(token);
        return matcher.find();
    }
    public static boolean isConstant(String token){
        var pattern = Pattern.compile("^(0|[+\\-]?[1-9][0-9]*)$|^'.'$|^\".*\"$");
        var matcher = pattern.matcher(token);
        return matcher.find();
    }
}
