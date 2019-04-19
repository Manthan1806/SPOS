#include<stdio.h> 
#include<stdlib.h> 
#include<unistd.h> 
int main() 
{ 
    // char *args1[]={"./a.out",NULL}; 
    // execv(args1[0],args1); 

 	// char *args2[]={"ps",NULL}; 
  	// execvp(args2[0],args2); 

	char *join[]={"join","file1","file2",NULL};
	execvp(join[0],join);
    printf("Ending-----\n"); 
  
    return 0; 
} 