import Pif.PifSortedListPair;
import constants.LanguageSpecifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public static PifSortedListPair getStringToken(String line, int index){
        var token = new StringBuilder();
        var quotes = 0;
        while (quotes < 2 && line.length() > index){
            if(line.charAt(index) == '"'){
                quotes += 1;
            }
            token.append(line.charAt(index));
            index += 1;
        }
        return new PifSortedListPair(token.toString(), index);
    }

    public static PifSortedListPair getOperatorToken(String line, int index, Boolean wasLeftConstat){
        var token = new StringBuilder();
        if(line.charAt(index) == '-' && !wasLeftConstat){
            if(index + 1 < line.length() && Character.isDigit(line.charAt(index + 1))) {
                token.append('-');
                index += 1;
            }
            while(index < line.length() && Character.isDigit(line.charAt(index))){
                token.append(line.charAt(index));
                index+=1;
            }
            return new PifSortedListPair(token.toString(), index);
        }
        while(index < line.length() && isInOperator(line.charAt(index))){
            token.append(line.charAt(index));
            index += 1;
        }

        return new PifSortedListPair(token.toString(), index);
    }

    public static List<String> getTokens(String line){
        var index = 0;
        var token = new StringBuilder();
        var result = new ArrayList<String>();
        var wasLastOneConstant = true;
        while (index < line.length()){
            var currentChar = line.charAt(index);
            if(currentChar == '"'){
                var res = getStringToken(line, index);
                token=new StringBuilder(res.getKey());
                index = res.getValue();
                result.add(token.toString());
                wasLastOneConstant = true;
                token = new StringBuilder();
            }
            else if(isInOperator(currentChar)){
                var res = getOperatorToken(line, index, wasLastOneConstant);
                if(!token.toString().equals("")) {
                    result.add(token.toString());
                }
                token = new StringBuilder(res.getKey());
                index = res.getValue();
                result.add(token.toString());
                wasLastOneConstant = true;
                token = new StringBuilder();
            }
            else if(isInSeparators(currentChar)){
                    if(!token.toString().equals("")) {
                        result.add(token.toString());
                    }
                    result.add(String.valueOf(currentChar));
                    token = new StringBuilder();
                    wasLastOneConstant = false;
                    index += 1;
            }else{
                token.append(currentChar);
                index += 1;
                wasLastOneConstant = true;
            }
        }
        if(!token.toString().equals("")){
            result.add(token.toString());
        }
        return result
                .stream()
                .filter(e -> !Objects.equals(e, " "))
                .collect(Collectors.toList());
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
        var pattern = Pattern.compile("^[a-zA-Z]([a-zA-Z]|[0-9]){0,100}$");
        var matcher = pattern.matcher(token);
        return matcher.find();
    }

    public static boolean isConstant(String token){
        var pattern = Pattern.compile("^(0|[+\\-]?[1-9][0-9]*)$|^'.'$|^\".*\"$");
        var matcher = pattern.matcher(token);
        return matcher.find();
    }
}
