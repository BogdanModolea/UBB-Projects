#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

void f(int n){
	if(n){
		if(fork() == 0){
			printf("%d\n", n);
			f(n - 1);
			exit(0);
		}
		wait(0);
	}
	//exit(0);
}


int main(int argc, char** argv){
	f(3);
	return 0;
}
