#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>
#include <time.h>

typedef struct {
	int id, N;
	pthread_barrier_t* barrier;
	sem_t* sems;
}data;

void *f(void *arg){
	data d = *((data*)arg);
	printf("Thread %d is waiting...\n", d.id);
	pthread_barrier_wait(d.barrier);
	printf("Thread %d starting\n", d.id);
	for(int i = 0; i < d.N; i++){
		sem_wait(&d.sems[i]);
		printf("Thread %d has entered checkpoint %d\n", d.id, i);
		int n = (random() % 101 + 100) * 1000;
		usleep(n);
		sem_post(&d.sems[i]);
	}
	printf("Thread %d finished\n", d.id);
	return NULL;
}

void wait_threads(pthread_t* T, int cnt){
	for(int i = 0; i < cnt; i++){
		pthread_join(T[i], NULL);
	}
}

void cleanup(pthread_t* T, data* args, sem_t* sems){
	free(T);
	free(args);
	free(sems);
}

int main(int argc, char **argv){
	if(argc != 2){
		printf("Enter a valid number of arguments\n");
		exit(1);
	}
	int N = atoi(argv[1]);
	int M = 1;
	int step = 2;

	pthread_barrier_t barrier;
	sem_t* sems = malloc(sizeof(sem_t) * N);

	for(int i = 0; i < N; i++)
		M *= 2;

	for(int i = 0; i < N; i++){
		sem_init(&sems[i], 0, M / step);
		step *= 2;
	}
	pthread_barrier_init(&barrier, NULL, M);
	pthread_t* T = malloc(sizeof(pthread_t) * M);
	data* args = malloc(M * sizeof(data));
	int i;
	srandom(time(NULL));
	for(i = 0; i < M; i++){
		args[i].id = i;
		args[i].N = N;
		args[i].barrier = &barrier;
		args[i].sems = sems;
		pthread_create(&T[i], NULL, f, (void*)&args[i]);
	}
	wait_threads(T, M);
	pthread_barrier_destroy(&barrier);
	for(int i = 0; i < N; i++)
		sem_destroy(&sems[i]);
	cleanup(T, args, sems);
	return 0;
}
