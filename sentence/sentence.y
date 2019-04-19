%{
#include<stdio.h>
void yyerror(char*);
int yylex();
%}
%token NOUN PRONOUN VERB ADVERB ADJECTIVE PREPOSITION CONJUNCTION
%%
start : start sentence 
| sentence
;

sentence:   simple_sentence { printf("Parsed a simple sentence.\n"); }
| compound_sentence { printf("Parsed a compound sentence.\n"); }
;
simple_sentence: subject verb object
| subject verb object prep_phrase
;
compound_sentence : simple_sentence CONJUNCTION simple_sentence
| compound_sentence CONJUNCTION simple_sentence
;
subject: NOUN
| PRONOUN
| ADJECTIVE 
;
verb : VERB
| ADVERB VERB
| verb VERB
;
object : NOUN
| ADJECTIVE 
;
prep_phrase : PREPOSITION NOUN
;
%%
extern FILE *yyin;
int main( )
{
	yyin = fopen("file.txt","r");
	do
	{
		yyparse();
	}
	while ( !feof(yyin));
	//yyparse();
	return 0;
}
void yyerror(char *s)
{
	fprintf (stderr, "%s\n" , s) ;
}
