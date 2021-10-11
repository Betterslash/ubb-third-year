<h1>
    Symbol table representation as HashTable
</h1>
<h2>
    HashTableWrapper class : 
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
            </ul>
        </label>
    </div>
</h2>