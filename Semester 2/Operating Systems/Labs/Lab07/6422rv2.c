#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/types.h>

int main(int argc, char **argv){
	for(int i = 0; i < 3; i++){
		int pid = fork();
		if(pid == 0){
			printf("Hello master!\n");
			exit(0);
		}
	}
	//printf("Ara-ara, sayonara!\n");
	for(int i = 0; i < 3; i++)
		wait(0);
	printf("Ara-ara, sayonara!\n");
	return 0;
}
