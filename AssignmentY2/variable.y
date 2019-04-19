%{
#include<stdio.h>
%}
%token TYPE ROP ASSIGN AOP LOP ID NUM STRING
%%
statement: TYPE' 'list';' {printf("valid declaration in java");};
list: id 
	| assignment 
	| list','assignment 
	| list','id 
	;
assignment: id ASSIGN STRING 
	| id ASSIGN expression 
	;
expression:'('expression')' 
	| id op expression 
	| NUM op expression 
	| id 
	| NUM 
	;
op: AOP 
	| LOP 
	| ROP 
	;
	
id: ID ;

%%
main(int argc,char** argv)
{	 
	yyparse();
}
yyerror(s)
char *s;
{
fprintf(stderr, "%s\n",s);
}

