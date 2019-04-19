%{
	#include<stdio.h>
	
%}
number [0-9]+
identifier [a-zA-Z]+[_a-zA-Z0-9]*
assignment [=]
arithop [+\-*/]
endofline [;]
ignore [ \t\n]+
%%
("import")([ ]+)(.*)("\n") printf("%s is an import statement.\n",yytext);
("public static void main")("(String[] args)"|"(String args[])") printf("%s is main\n",yytext);
{ignore} ;
"if"|"else"|"do"|"while"|"for"|"switch" printf("%s is a control structure.\n",yytext);
"public"|"private"|"protected" printf("%s is a keyword.\n",yytext);
"extends"|"implements"|"class"|"interface" printf("%s is a keyword.\n",yytext);
"static"|"final"|"abstract" printf("%s is a keyword.\n",yytext);
"/*".*"*/"|"//".*"\n" printf("%s is a comment.\n",yytext);
"("|")"|"{"|"}" printf("%s is a delimiter.\n",yytext);
"int"|"char"|"double"|"float"|"boolean"|"long"|"long double"|"boolean" printf("%s is a type.\n",yytext);
"=="|"<"|">"|"<="|">=" printf("%s is a comparizon operator.\n",yytext);
{assignment}|{arithop}= printf("%s is an assignment operator.\n",yytext);
{arithop} printf("%s is an arithmetic operator.\n",yytext);
"&&"|"||"|"!"  printf("%s is a logical operator.\n",yytext);
{endofline} printf("%s is the end of line.\n",yytext);
{identifier} printf("%s is an identifier\n",yytext);
{number} printf("%s is a number\n",yytext);
. printf("%s is nothing great\n",yytext);
%%
	main(int argc,char** argv)
	{
	if (argc > 1) {
	FILE *f;
	f = fopen(argv[1], "r" ) ;
	if(!f)
	{
	exit(1);
	}
	yyin = f;
	}	 
		yylex();
	}
	int yywrap (void )
	{
	    return 1;
	}
