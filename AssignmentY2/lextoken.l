%{
	#include<stdio.h>
	#include "y.tab.h"
	extern int yylval;
%}
number [0-9]+\.?[0-9]*
identifier [a-zA-Z]+[_a-zA-Z0-9]*
assignment [=]
arithop [+\-*/]
endofline [;]
ignore [ \t\n]+
%%
\".*\" return(STRING);
"int"|"char"|"double"|"float"|"boolean"|"long"|"long double"|"boolean" { return(TYPE);}
"=="|"<"|">"|"<="|">=" return(ROP);
{assignment}|{arithop}= return(ASSIGN);
{arithop} return(AOP);
"&&"|"||"|"!"  return(LOP);
{identifier} { return(ID);}
{number} return(NUM);
. return(yytext[0]);
%%
	
	int yywrap (void )
	{
	    return 1;
	}
