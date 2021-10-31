link : https://github.com/Betterslash/ubb-third-year/tree/master/sem-one/formal-languages-and-compiler-design/laboratory_2/scanner/src/main/java

<section>
<h2>Statement: Implement a scanner (lexical analyzer): Implement the scanning algorithm and use ST from lab 2 for the symbol table.</h2>
<h1>Scanner</h1>
<h2>Scanner diagram</h2>
<img src="../resources/diagrams/diagram.png" alt="uml_diagram"/>
<h2>
    How does it work : 
        
</h2>
<h3>
    Symbol table representation as HashTable
</h3>
<h2> Requirement</h2>
<h3>Statement: Implement the Symbol Table (ST) as the specified data structure, with the corresponding operations</h3>

<section>
    <></section>
     symbol_table.HashTableWrapper class : 
        <label>  
        Fields :
            <ul>
                <li>
                    - internalRepresentation : HashTable(Integer -> key, String -> value)
                </li>
                <li>
                    - divisonFactor : int (used for hash function computation)
                </li>
            </ul>
        </label>
        <label>  
        Methods :
            <ul>
                <li>
                    + default constructor -> initializes an empty internalRepresentation
                </li>
                <li>
                    + add(token : string) -> verifies if token is already is in internalRepresentation 
                                             if it is not -> adds the token
                                             else ->    
                </li>
                <li>
                    - computeHashFunction(valueOfTokenAsAscii : int) -> returns the computed hash function
                </li>
                <li>
                    - contains(token : string) -> returns true if internalRepresentaion contains token false otherwise
                </li>
                <li>
                    -  getEntry(token : string) -> returns the representation for a token in hashtable 
                </li>
                <li>
                    + getId(token : string) -> returns a Pif.CustomPair(hashPosition : int, arrayPosition : int) for an entry
                </li>
            </ul>
        </label>

</section>
<h4>-> Goes line by line, character by character and does the suitable checks to determine if a character should be one of three main types : </h4>
<p>Constant</p>
<p>Identifier</p>
<p>Language specification included word -> operator, separator</p>
<h4>-> </h4>
<h3>Functions : </h3>
<ul>
    <li>
        <p>isInOperator -> checks if a character is part of an orpeator</p>
    </li>
    <li>
        <p>getStringOperator -> determines if the token will be a string and goes to determine the resulted string constant</p>
    </li>
    <li>
        <p>getOperatorToken -> checks if a character is part of an operator also if the character is '-' then checks if the '-' isn't actually a number sign and if it is it will return the negative resulted number else it will return the operator </p>
    </li>
    <li>
        <p>isInSeparators -> checks if a character is part of an separators</p>
    </li>
    <li>
        <p>isIdentifier -> checks if a character is an identifier type entry</p>
    </li>
    <li>
        <p>isConstant -> checks if an character is a constant type entry</p>
    </li>
    <li>
        <p>getTokens -> main function which build paris of key values as a list and returns them, those key value pairs representing the PIF entries</p>
    </li>
</ul>

<section>
    <h1>Human readable description
    </h1>
    <H2>How does it work except of generic scanning</H2>
    <div>
        -> LanguageSpecifications Object -> determines which characters are forming a valid entry in my toy language 
        * after initializing itself
        -> ConfigurationConstants has only some paths and string formatation rules for the program to be easly modifiable
        -> Because the Symbol table takes as internal representation any SynbaleTableRepresentation children it can work with both SortedList implemented in symbol_table as well as with the HashTableWrapper 
        -> PifResource works as the SymbolTableRepresentation such that it makes the implementations adaptable to multiple types of symbol table internal representations
    </div>
</section>

<section>
    <H2> Test cases : </H2>
<ul>
    <li>
        Test Case -> "asdkl" 'a
VAR a, b, c : INT;
max : INT;
{
max <- 0;
IF(a >= b){
IF(a >= c){
max <- a;
WRITELN(a);
}
}
IF(b >= a){
IF(b >= c){
max <- b;
WRITELN(b);
}
}
IF(c >= b){
IF(c >= a){
max <- c;
WRITELN(c);
}
}
}
<p></p>
Success -> PifResource(key=constant, value=CustomPair(hashTableKey=601, arrayListKey=0))
PifResource(key=VAR, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=101, arrayListKey=0))
PifResource(key=,, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=103, arrayListKey=0))
PifResource(key=,, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=105, arrayListKey=0))
PifResource(key=:, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=INT, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=;, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=342, arrayListKey=0))
PifResource(key=:, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=INT, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=;, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key={, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=342, arrayListKey=0))
PifResource(key=<-, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=constant, value=CustomPair(hashTableKey=65, arrayListKey=0))
PifResource(key=;, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=IF, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=(, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=101, arrayListKey=0))
PifResource(key=>=, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=103, arrayListKey=0))
PifResource(key=), value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key={, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=IF, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=(, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=101, arrayListKey=0))
PifResource(key=>=, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=105, arrayListKey=0))
PifResource(key=), value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key={, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=342, arrayListKey=0))
PifResource(key=<-, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=101, arrayListKey=0))
PifResource(key=;, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=WRITELN, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=(, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=101, arrayListKey=0))
PifResource(key=), value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=;, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=}, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=}, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=IF, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=(, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=103, arrayListKey=0))
PifResource(key=>=, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=101, arrayListKey=0))
PifResource(key=), value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key={, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=IF, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=(, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=103, arrayListKey=0))
PifResource(key=>=, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=105, arrayListKey=0))
PifResource(key=), value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key={, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=342, arrayListKey=0))
PifResource(key=<-, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=103, arrayListKey=0))
PifResource(key=;, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=WRITELN, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=(, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=103, arrayListKey=0))
PifResource(key=), value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=;, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=}, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=}, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=IF, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=(, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=105, arrayListKey=0))
PifResource(key=>=, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=103, arrayListKey=0))
PifResource(key=), value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key={, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=IF, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=(, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=105, arrayListKey=0))
PifResource(key=>=, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=101, arrayListKey=0))
PifResource(key=), value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key={, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=342, arrayListKey=0))
PifResource(key=<-, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=105, arrayListKey=0))
PifResource(key=;, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=WRITELN, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=(, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=identifier, value=CustomPair(hashTableKey=105, arrayListKey=0))
PifResource(key=), value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=;, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=}, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=}, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
PifResource(key=}, value=CustomPair(hashTableKey=-1, arrayListKey=-1))
Symbol table
65=[0]
601=["asdkl"]
105=[c]
103=[b]
101=[a]
342=[max]
    </li>
    <li>
    Test case -> program_1err.txt ->
    VAR a, b, c : INT;
max : INT;
{
t <- 4
max <- '0;
IF(1a >= b){
IF(1a >= c){
max <- 1a;
WRITELN(1a);
}
}
IF(b >= 1a){
IF(b >= c){
max <- b;
WRITELN(b);
}
}
IF(c >= b){
IF(c >= 1a){
max <- c;
WRITELN(c);
}
}
}
<p></p>
Failure -> 
 Token '0 couldn't be scanned at line number 4 -> please review the code master ...
</li>
</ul>
</section>