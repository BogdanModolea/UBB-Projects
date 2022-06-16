#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/time.h>
#include <sys/types.h>

#define inf 315353245

int main(int argc, char **argv){
	int p2a[2], a2b[2], b2p[2];
	int n = inf;

	pipe(p2a); pipe(a2b); pipe(b2p);

	int pid = fork();
	if(pid == 0){	//child A
		close(p2a[1]); //close writing
		close(a2b[0]); // close reading
		close (b2p[0]); close(b2p[1]);

		while(1){
			if(read(p2a[0], &n, sizeof(int)) <= 0)
				break;
			if(n <= 0)
				break;
			printf("A: %d -> %d\n", n, n - 1);
			n--;
			write(a2b[1], &n, sizeof(int));
		}

		close(p2a[0]); close(a2b[1]);
		exit(0);
	}

	pid = fork();
	if(pid == 0){	//child B
		close(p2a[0]); close(p2a[1]);
		close(a2b[1]); //close writing
		close(b2p[0]); //close reading

		while(1){
			if(read(a2b[0], &n, sizeof(int)) <= 0)
				break;
			if(n <= 0)
				break;
			printf("B: %d -> %d\n", n, n - 1);
			n--;
			write(b2p[1], &n, sizeof(int));
		}

		close(a2b[0]); close(b2p[1]);
		exit(0);
	}


	close(p2a[0]); close(b2p[1]); close(a2b[0]); close(a2b[1]);
	n = 24;
	write(p2a[1], &n, sizeof(int));
	while(1){
		if(read(b2p[0], &n, sizeof(int)) <= 0)
			break;
		if(n <= 0)
			break;
		printf("P: %d -> %d\n", n, n - 1);
		n--;
		write(p2a[1], &n, sizeof(int));
	}

	close(p2a[1]); close(b2p[0]);
	wait(0); wait(0);
	return 0;
}
