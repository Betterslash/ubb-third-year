import Pif.PifHashTablePair;
import Pif.ProgramInternalForm;
import constants.ConfigurationConstants;
import constants.LanguageSpecifications;
import exception.LexicalErrorException;
import symbol_table.HashTableWrapper;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var internalRepresentation = new HashTableWrapper();
        var symbolTable = new SymbolTable<>(internalRepresentation);
        var pif = new ProgramInternalForm<PifHashTablePair>();
        var lineNumber = 0;
        var myObj = new File(ConfigurationConstants.FILE_PATH + "program_1err.txt");
        var myReader = new java.util.Scanner(myObj);
        var line = "";
        while (myReader.hasNextLine()) {
            line = myReader.nextLine();
            var tokens = Scanner.getTokens(line);
            int finalLineNumber = lineNumber;
            tokens.forEach(e -> {
                    if (LanguageSpecifications.all.contains(e)) {
                        pif.add(new PifHashTablePair(e, symbolTable.getNullValue()));
                    } else if (Scanner.isIdentifier(e)) {
                        symbolTable.add(e);
                        pif.add(new PifHashTablePair(LanguageSpecifications.IDENTIFIER_KEY, symbolTable.getId(e)));
                    } else if (Scanner.isConstant(e)) {
                        symbolTable.add(e);
                        pif.add(new PifHashTablePair(LanguageSpecifications.CONSTANT_KEY, symbolTable.getId(e)));
                    } else {
                        throw new LexicalErrorException(String.format(ConfigurationConstants.LEXICAL_ERROR_MESSAGE, e, finalLineNumber));
                    }
            });
            lineNumber += 1;
        }
        myReader.close();
        pif.getInternalRepresentaion()
                .forEach(System.out::println);
        System.out.println("Symbol table");
        symbolTable.getInternalRepresentation()
                .getInternalRepresentation()
                .entrySet()
                .forEach(System.out::println);

    }
}
