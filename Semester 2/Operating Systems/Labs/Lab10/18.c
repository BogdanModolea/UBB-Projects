#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

typedef struct{
	int id;
	char str[101];
}argument;

void *upcase(void *arg){
	int i;
	argument a = *((argument*)arg);
	for(i = 0; i < strlen(a.str); i++){
		if(a.str[i] >= 'a' && a.str[i] <= 'z'){
			a.str[i] -= 32;
		}
	}
	printf("Thread %d finished: %s\n", a.id, a.str);
	return NULL;
}

int main(int argc, char* argv[]){
	int i;
	pthread_t *thr = malloc((argc - 1) * sizeof(pthread_t));
	argument *args = malloc((argc - 1) * sizeof(argument));
	for(i = 1; i < argc; i++){
		args[i - 1].id = i;
		strcpy(args[i - 1].str, argv[i]);
		if(pthread_create(&thr[i - 1], NULL, upcase, (void*)&args[i - 1]) < 0){
			perror("Can't create");
		}
	}
	for(i = 1 ;i < argc; i++)
		pthread_join(thr[i - 1], NULL);
	free(thr);
	free(args);
	return 0;
}
