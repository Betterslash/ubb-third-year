github link to the repository :
https://github.com/Betterslash/ubb-third-year/tree/master/sem-one/formal-languages-and-compiler-design/laboratory-3/src/main

<h1>Finite Automata</h1>
<h2>Requirements</h2>
Write a program that:

1. Reads the elements of a FA (from file)
2. Displays the elements of a finite automata, using a menu: the set of states, the alphabet, all the transitions, the set of final states.
3. For a DFA, verify if a sequence is accepted by the FA.

<h2>Finite Automata File Format</h2>
General:

1. digit ::= 0 | 1 | 2 |...| 9
2. elemDigit ::= 1 | 2 | ... | 9
3. number ::= elemDigit{digit} | 0
4. chr ::= 'A' | 'B' | 'C' | ... | 'Z' | 'a' | 'b' | ... | 'z'
5. word ::= chr{chr}

Grammar Specific:
1. file ::= Q\n E\n q0\n S\n 
2. Q ::= 'Q = {' Qelems '}'
    1. Qelems ::= ' 'word number','Qelems | ' q'word number
3. E :: = 'E = {' Eelems '}'
   1. Eelems ::= ' 'number','Eelems | ' number'
4. q0 ::= 'q0 = 'word number
5. F ::= 'F = {' Qelems '}'
6. S ::= 'S = {\n' state ' }'
   1. state ::= '(' Qelems', ' number ') -> ' Qelems',\n\t'state |  '(' Qelems', ' number ') -> ' Qelems'

<h2>Diagram </h2>
<img src='src/main/resources/diagram.png'>
