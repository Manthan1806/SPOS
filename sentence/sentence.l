%{
#include <stdio.h>
#include "y.tab.h"
%}

%%
"dogs" |
"birds" return NOUN; 

"run" |
"fly"  return VERB; 

"fast" |
"high" return ADJECTIVE;

"and" |
"because" |
"but"  return CONJUNCTION; 

. ;
return yytext[0];
%%

int yywrap()
{
	return 1;
}
