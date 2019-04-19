%{
	#include<stdio.h>
	int lineno=0;
	int wordno=0;
	int charno=0;
%}
%%
\n {lineno++;charno++;}
[^ \t\n]+ {wordno++; charno+=yyleng;}
. {charno++;}
%%
	main()
	{
		yylex();
		printf("WordCount is %d, LineCount is %d, CharacterCount is %d\n",wordno,lineno,charno);
		
	}
	int yywrap (void )
	{
	    return 1;
	}

