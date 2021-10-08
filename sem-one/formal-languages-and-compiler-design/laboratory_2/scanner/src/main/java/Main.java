import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var symbolTable = new SymbolTable();
        var pif = new ProgramInternalForm();
        var lineNumber = 0;
        var myObj = new File("D:\\Uni\\sem-one\\formal-languages-and-compiler-design\\laboratory_2\\scanner\\src\\main\\resources\\program_1.txt");
        var myReader = new java.util.Scanner(myObj);
        var line = "";
        while (myReader.hasNextLine()) {
            line = myReader.nextLine();
            var tokens = Scanner.getTokens(line);
            tokens.forEach(e -> {
                        if(LanguageSpecifications.all.contains(e)){
                            pif.add(new PifPair(e, -1));
                        }else if(Scanner.isIdentifier(e)){
                            symbolTable.add(e);
                        }else if(Scanner.isConstant(e)){
                            symbolTable.add(e);
                        }else{
                            throw new RuntimeException("Smth went wrong sir...");
                        }
                    });
            lineNumber += 1;
        }
        myReader.close();
    }
}
