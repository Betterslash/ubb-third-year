%{
#include <stdio.h>
#include <stdlib.h>

#define YYDEBUG 1
%}

%token IDENTIFIER
%token CONSTANT
%token WHILE
%token IF
%token THEN
%token FOR
%token LESS_THAN
%token GREATER_THAN
%token LESS_OR_EQUAL_THAN
%token GREATER_OR_EQUAL_THAN
%token SEMI_COLON
%token COMA
%token ASSIGNMENT
%token EQUAL
%token MULTIPLY
%token MINUS
%token PLUS
%token DIVISION
%token VAR
%token BOOL
%token INT
%token CHAR
%token DOTS
%token OPEN_ROUND_PARENTHESIS 
%token CLOSE_ROUND_PARENTHESIS 
%token OPEN_SQUARE_PARENTHESIS 
%token CLOSE_SQUARE_PARENTHESIS 
%token OPEN_CURLY_PARENTHESIS 
%token CLOSE_CURLY_PARENTHESIS 
%token WRITE 
%token WRITELN 
%token READ 
%token READLN 
%token ELSE
%token DIFFERENT
%token COLON
%token TYPEDEF
%token NUMBER
%token WORD
%token ARRAY

%start program

%%

program         : VAR decllist SEMI_COLON compoundStmt;
decllist        : declaration SEMI_COLON decllist | declaration ;
declaration     : IDENTIFIER COLON type | arrayDeclaration ;
arrayDeclaration: ARRAY OPEN_SQUARE_PARENTHESIS NUMBER CLOSE_SQUARE_PARENTHESIS DOTS type ; 
type            : primitiveType | userDefinedType ;
primitiveType   : INT | BOOL | CHAR ;
userDefinedType : TYPEDEF DOTS IDENTIFIER OPEN_CURLY_PARENTHESIS decllist CLOSE_CURLY_PARENTHESIS ;
compoundStmt    : OPEN_CURLY_PARENTHESIS stmtlist CLOSE_CURLY_PARENTHESIS ;
stmtlist        : stmt SEMI_COLON stmtlist | stmt;
stmt            : simplestmt | structstmt ;
simplestmt      : assignstmt | iostmt ;
assignstmt      : IDENTIFIER ASSIGNMENT expression;
expression      : term | term PLUS expression | term MINUS expression | term MULTIPLY expression | term DIVISION expression ;
term            : IDENTIFIER | NUMBER | WORD ;
iostmt          : READ OPEN_ROUND_PARENTHESIS IDENTIFIER CLOSE_ROUND_PARENTHESIS | READLN OPEN_ROUND_PARENTHESIS IDENTIFIER CLOSE_ROUND_PARENTHESIS | WRITE OPEN_ROUND_PARENTHESIS IDENTIFIER CLOSE_ROUND_PARENTHESIS | WRITELN OPEN_ROUND_PARENTHESIS IDENTIFIER CLOSE_ROUND_PARENTHESIS ;
structstmt      : compoundStmt | ifStmt | whileStmt | forStmt ;
ifStmt          : IF OPEN_ROUND_PARENTHESIS condition CLOSE_ROUND_PARENTHESIS compoundStmt | IF OPEN_ROUND_PARENTHESIS condition CLOSE_ROUND_PARENTHESIS  compoundStmt ELSE compoundStmt ;
whileStmt       : WHILE OPEN_ROUND_PARENTHESIS condition CLOSE_ROUND_PARENTHESIS compoundStmt ;
forStmt         : FOR OPEN_ROUND_PARENTHESIS assignstmt SEMI_COLON relation SEMI_COLON expression CLOSE_ROUND_PARENTHESIS compoundStmt ;
condition       : expression relation expression ;
relation        : DIFFERENT | LESS_OR_EQUAL_THAN | LESS_THAN | GREATER_OR_EQUAL_THAN | GREATER_THAN | EQUAL ;

%%

yyerror(char *s)
{
  printf("%s\n", s);
}

extern FILE *yyin;

main(int argc, char **argv)
{
  if (argc > 1) 
    yyin = fopen(argv[1], "r");
  if ( (argc > 2) && ( !strcmp(argv[2], "-d") ) ) 
    yydebug = 1;
  if ( !yyparse() ) 
    fprintf(stderr,"\t U GOOOOOD !!!\n");
}