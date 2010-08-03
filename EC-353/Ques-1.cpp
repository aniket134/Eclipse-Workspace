#include <iostream>
#include <cstdlib>
#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <string>

using namespace std;

void *OneVehicle(void *arg);
bool isBridgeEmpty();
bool isAllowedToCross(int);
void ArriveBridge(int);
void CrossBridge(int);
void ExitBridge(int);
sem_t lock;
int cars_on_bridge[] = {0, 0, 0};
int current_direc = 0;

int main(){
	string cars = "10101000111000110101111000110111010";
	int len = cars.length();
	pthread_t* mythread = new pthread_t[len];
	sem_init(&lock, 0, 1);
	cout<<"Entering program. Running threads..."<<endl;
	fflush(stdout);

	for(int i = 0; i < len; i++){
		int random = rand()%100;
		sleep(((float)random)/100);
		int direc = (int)(cars[i] - '0');
		if(pthread_create(&mythread[i], NULL, OneVehicle, (void*)direc)){
			cout<<"Error creating thread.\n";
			abort();
		}
	}
	for(int i = 0; i < len; i++){
		if(pthread_join(mythread[i], NULL)){
			cout<<"Error joining threads.\n";
			abort();
		}
	}

	sem_destroy(&lock);
	cout<<endl<<cars<<endl;
	exit(0);
}

void ArriveBridge(int direc){
    while(true){
        if(isBridgeEmpty()){
    	    sem_wait(&lock);
            current_direc = direc;
            cars_on_bridge[0] = 1;
	        sem_post(&lock);
            return;
        }
        if(isAllowedToCross(direc)){
            return;
        }
	}
}

void ExitBridge(int direc){

    sem_wait(&lock);
    cars_on_bridge[0] = cars_on_bridge[1];
    cars_on_bridge[1] = cars_on_bridge[2];
    cars_on_bridge[2] = 0;
    sem_post(&lock);
    return;
}

void CrossBridge(int direc){
	int random = rand()%100;
	sleep(((float)random)/50);
    sem_wait(&lock);
    cout<<direc;
    sem_post(&lock);
    return;
}

void *OneVehicle(void *arg){
    int direc = (int)(arg);
    ArriveBridge(direc);
    CrossBridge(direc);
    ExitBridge(direc);
    return NULL;
}

bool isAllowedToCross(int direc){
    sem_wait(&lock);
    if(cars_on_bridge[0] == 0 && current_direc == direc){
        cars_on_bridge[0] = 1;
        sem_post(&lock);
        return true;
    }
    else if(cars_on_bridge[1] == 0 && current_direc == direc){
        cars_on_bridge[1] = 1;
        sem_post(&lock);
        return true;
    }
    else if(cars_on_bridge[2] == 0 && current_direc == direc){
        cars_on_bridge[2] = 1;
        sem_post(&lock);
        return true;
    }
    sem_post(&lock);
    return false;
}

bool isBridgeEmpty(){
    sem_wait(&lock);
    bool x = (cars_on_bridge[0]==0 && cars_on_bridge[1]==0 && cars_on_bridge[2]==0);
    sem_post(&lock);
    return x;
}
