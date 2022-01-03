#include <stdlib.h>
#include <stdio.h>
#include "scanner.h"
#include "lex.yy.c"

extern int yylex();
extern int yyLineNumber;
extern char* yyText;

char  *names[] = {};

int main(int argc, char** argv){
    ++argv;
    --argc;
    if(argc > 0){
        yyin = fopen(argv[0], "r");
    }else{
        yyin = stdin;
    }
    int nToken, vToken;
    nToken = yylex();

    while(nToken){
        printf("%d\n", nToken);
        if(nToken == -1){
            return 0;
        }
        nToken = yylex();
    }
    return 0;
}