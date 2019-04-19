%{
#include<stdio.h>
int ch=0, bl=0, ln=0, wr=0;
%}

%%
[\n] {ln++;wr++;}
[\t] {bl++;wr++;}
[" "] {bl++;wr++;}
["."] {ch++;wr++;ln++;}
[^\n\t] {ch++;}
%%

int yywrap (void )
{
    return 1;
}

int main()
{

yyin=fopen("file.txt","r");
yylex();

printf("Character = %d\nBlank = %d\nLines = %d\nWords = %d\n", ch, bl, ln-1, wr-1);

return 0;
}
