link : https://github.com/Betterslash/ubb-third-year/tree/master/sem-one/formal-languages-and-compiler-design/laboratory_2/scanner/src/main/java
<div>
<h1>
    Symbol table representation as HashTable
</h1>

<h2>
    symbol_table.HashTableWrapper class : 
    <div>
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
    </div>
</h2>
</div>

<h1>Scanner</h1>
<h2>Scanner diagram</h2>
<img src="../resources/diagrams/diagram.png" alt="uml_diagram"/>
<