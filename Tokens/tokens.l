%{
#include<stdio.h>
int comments=0, code=0, whiteSpace=0, keywords=0, operator=0;
%}

%x COMMENT
%%
^[ \t]*"/*" { BEGIN COMMENT;}
^[ \t]*"/*".*"*/"[ \t]*\n { comments++; }

<COMMENT>"*/"[ \t]*\n { BEGIN 0; comments++;}
<COMMENT>"*/" { BEGIN 0; }
<COMMENT>\n { comments++; }
<COMMENT>.\n { comments++; }

^[ \t]*\n { whiteSpace++; }

"+" |
"-" |
"*" |
"/"  | 
"&&" |
"&" |
"^" 	{ operator++; printf("%s is an operator\n\n", yytext); }

.+"/*".*"*/".*\n { code++; }
.*"/*".*"*/".+\n { code++; }
.+"/*".*\n { code++; BEGIN COMMENT; }
.\n { code++; }

"abstract" | 
"break" |
"byte" | 
"case" | 
"catch" |
"class" |
"const" |
"do" |
"else" |
"enum" |
"for" |
"if" |
"import" |
"public" |
"return" |
"static" |
"switch" |
"throw" |
"throws" |
"try" |
"void" |
"while" { keywords++; printf("%s is a keyword\n\n", yytext); }

"false" |
"null" |
"true" { keywords++;printf("%s is a literal\n\n", yytext); }


"boolean" |
"char" |
"double" |
"float" |
"int" |
"String" { keywords++; printf("%s is a datatype\n\n", yytext); }

.	; /* ignore everything else */
 
%%

int yywrap (void)
{
    return 1;
}

int main()
{

	yyin=fopen("code.java","r");
	yylex();

	printf("\n");	

	printf("code = %d\ncomments = %d\nwhite space = %d\noperator = %d\nkeywords = %d\n",code, comments, whiteSpace, operator, keywords);
	
	printf("\n");
	return 0;
}
