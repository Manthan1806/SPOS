%{
#include<stdio.h>
%}
%token ID BUILTIN SC NL COMMA

%left '+' '-'
%left '*' '/' '%'
%left '(' ')'

%%
start: start V
| V
;

V : BUILTIN varlist SC NL {printf("Valid\n\n");}
;

varlist:varlist COMMA S | S
;

S: ID'='E  COMMA SC{
       printf("Valid1\n\n");
       return 0;
     }
   | ID;
     
     
E:E'+'E 
 |E'-'E 
 |E'*'E 
 |E'/'E 
 |E'%'E 
 |'('E')' 
 | ID
;

%%

void yyerror(const char *str){printf("error\n\n");}

main()
{
	yyparse();
}
