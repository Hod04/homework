#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <stdbool.h>

#define NUM_PRODUCERS 3
#define NUM_CONSUMERS 3

#define STORAGE 10

// in our program we utilize the following three semaphores:
// - `full` corresponds to the amount of items that exist in the buffer;
// - `empty` corresponds to the amount of the available spots in the buffer; 
// - `mutex` is a binary semaphore, which essentially is a mutex.

sem_t full;
sem_t empty;
sem_t mutex;

// last = buffer's last element's index.
// i = arbitrary value which is assigned to the buffer upon production.

int last = 0;
int i = 0;
 
// buffer is initialized with the above defined capacity (STORAGE).

int buffer[STORAGE];

void* Produce (void* threadid) {

	while (true) {
		sleep(1);

		// empty & mutex are decremented so that:
		// i. an empty slot is being "taken" in order for a new item to be produced.
		// ii. a thread has entered the critical section.

		sem_wait (&empty);
		sem_wait (&mutex);

		// the actual item production occurs here.

		buffer[last] = i;
		i++;
		printf ("Producer %d puts %d into buffer at place %d \n", 
			(long) threadid, buffer[last], last);

		last++;
		sleep(1);

		// mutex and full are incremented so that:
		// i. a thread has exited the critical section.
		// ii. an item has been produced and is available for consumption.

		sem_post (&mutex); 
		sem_post (&full);
	}

}

void* Consume (void* threadid) {

	while (true) {
		sleep(1);

		// full & mutex are decremented so that:
		// i. a filled up slot is being "taken" in order for an existing item to be consumed.
		// ii. a thread has entered the critical section.

		sem_wait (&full);
		sem_wait (&mutex);

		printf ("Consumer %d takes %d \n", 
			(long) threadid, buffer[last - 1]);
		
		// the actual item consumption.

		last--;
		sleep(1);

		// mutex and empty are incremented so that:
		// i. a thread has exited the critical section.
		// ii. an item has been consumed.

		sem_post (&mutex); 
		sem_post (&empty);
	}

}

int main (int argc, char* argv[]) {
	pthread_t producer_threads[NUM_PRODUCERS];
	pthread_t consumer_threads[NUM_CONSUMERS];
	int rc; long t;
	sem_init (&full, 0, last);
	sem_init (&empty, 0, STORAGE);
	sem_init (&mutex, 0, 1);

	// initialize the bufer with arbitrary values.

	for (t = 0; t < STORAGE; t++) { buffer[t] = 0; }

	// initialize the producer threads and give Produce as a callback function

	for (t = 0; t < NUM_PRODUCERS; t++) {
 		rc = pthread_create (&producer_threads[t], NULL, Produce, (void *) t);
		if (rc) {
			printf ("ERROR; return code from pthread_create () is %d\n", rc);
			exit (-1);
		}
	}

	// initialize the consumer threads and give Consume as a callback function

 	for (t = 0; t < NUM_CONSUMERS; t++) {
 		rc = pthread_create (&consumer_threads[t], NULL, Consume, (void *) t);
		if (rc) {
			printf ("ERROR; return code from pthread_create () is %d\n", rc);
			exit (-1);
		}
 	}

 	for (t = 0; t < NUM_PRODUCERS; t++) {
 		pthread_join (producer_threads[t], NULL);
 	}

 	for (t = 0; t < NUM_CONSUMERS; t++) {
 		pthread_join (consumer_threads[t], NULL);
 	}

	sem_destroy (&full);
	sem_destroy (&empty);
	sem_destroy (&mutex);

	return 0;
}
