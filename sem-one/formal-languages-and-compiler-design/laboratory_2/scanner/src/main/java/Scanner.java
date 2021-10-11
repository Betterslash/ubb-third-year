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

    public static boolean isSpecialQuote(String line, int index){
        if(index == 0){
            return false;
        }else return Objects.equals(line, "\\");
    }

    public static PifPair getStringToken(String line, int index){
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

    public static PifPair getOperatorToken(String line, int index){
        var token = new StringBuilder();
        while(index < line.length() && isInOperator(line.charAt(index))){
            token.append(line.charAt(index));
            index += 1;
        }
        return new PifPair(token.toString(), index);
    }

    public static List<String> getTokens(String line){
        var index = 0;
        var token = new StringBuilder();
        var result = new ArrayList<String>();
        while (index < line.length()){
            var currentChar = line.charAt(index);
            if(currentChar == '"'){
                var res = getStringToken(line, index);
                token=new StringBuilder(res.getKey());
                index = res.getValue();
                result.add(token.toString());
                token = new StringBuilder();
            }
            else if(isInOperator(currentChar)){
                var res = getOperatorToken(line, index);
                if(!token.toString().equals("")) {
                    result.add(token.toString());
                }
                token = new StringBuilder(res.getKey());
                index = res.getValue();
                result.add(token.toString());
                token = new StringBuilder();
            }
            else if(isInSeparators(currentChar)){
                if(!token.toString().equals("")) {
                    result.add(token.toString());
                }
                result.add(String.valueOf(currentChar));
                token = new StringBuilder();
                index += 1;
            }else{
                token.append(currentChar);
                index += 1;
            }
        }
        if(!token.toString().equals("")){
            result.add(token.toString());
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
