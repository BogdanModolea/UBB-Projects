#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char **argv){
	int pid = fork();
	if(pid == 0){
		execlp("grep", "grep", "-E", "\\<ana\\>", "a.txt", NULL);
		exit(1);
	}
	wait(0);
	return 0;
}
