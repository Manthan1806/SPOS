%{
#include<stdio.h>
int num=1;
%}
%token WORD CONJUNCTION
%%
paragraph: vsentence
	| vsentence' 'paragraph
	;
vsentence: ssentence {printf("%d : Simple Sentence\n",num++);}
	| csentence {printf("%d : Compound Sentence\n",num++);}
	;
csentence:cclause'.' ;
ssentence:sclause'.' ;
cclause:sclause' 'CONJUNCTION' 'sclause
        | sclause' 'CONJUNCTION' 'cclause 
       ;
sclause: sclause' 'WORD 
	   | WORD 
	   ;
%%
main()
{
yyparse();
}
yyerror(s)
char *s;
{
fprintf(stderr, "%s\n",s);
}

