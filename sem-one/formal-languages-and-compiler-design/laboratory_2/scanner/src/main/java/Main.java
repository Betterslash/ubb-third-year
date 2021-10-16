import Pif.PifHashTablePair;
import Pif.ProgramInternalForm;
import constants.LanguageSpecifications;
import symbol_table.HashTableWrapper;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var internalRepresentation = new HashTableWrapper();
        var symbolTable = new SymbolTable<>(internalRepresentation);
        var pif = new ProgramInternalForm<PifHashTablePair>();
        var lineNumber = 0;
        var myObj = new File("D:\\Uni\\sem-one\\formal-languages-and-compiler-design\\laboratory_2\\scanner\\src\\main\\resources\\program_2.txt");
        var myReader = new java.util.Scanner(myObj);
        var line = "";
        while (myReader.hasNextLine()) {
            line = myReader.nextLine();
            var tokens = Scanner.getTokens(line);
            tokens.forEach(e -> {
                        if(LanguageSpecifications.all.contains(e)){
                            pif.add(new PifHashTablePair(e, symbolTable.getNullValue()));
                        }else if(Scanner.isIdentifier(e)){
                            symbolTable.add(e);
                            pif.add(new PifHashTablePair("identifier", symbolTable.getId(e)));
                        }else if(Scanner.isConstant(e)){
                            symbolTable.add(e);
                            pif.add(new PifHashTablePair("constant", symbolTable.getId(e)));
                        }else{
                            System.out.println(e);
                        }
                    });
            lineNumber += 1;
        }
        myReader.close();
    }
}
