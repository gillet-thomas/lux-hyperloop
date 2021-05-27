# Luxembourg Hyperloop
This program represents a real-time visualization of the Hyperloop system that could be implemented in Luxembourg.  
Passengers travel from one station to another using pods that are sent through tubes.
Each station can be connected to another one by a single or bidirectional tube. An annotated version of the map used in the program can be found in `src/main/resources/`.

## Features
When the program is first started, all stations have a defined number of passengers. Passengers are given a random station destination.  

Each passenger will compute his shortest path using Dijkstra's algorithm. It means that every passenger will exactly know which stations he has to go through in order to get as fast as possible to his final destination. When a passenger arrives at a station, if this station is his final destination, then the passenger will disappear from the program. Otherwise, he will wait for a pod going to the next station in his shortest path. He will do that until he reaches his final destination.

Since passengers disappear from the program once they arrive at their final destination, there is a passenger spawning system implemented. Every 5 seconds, passengers will spawn in each station with a 15% chance. The number of spawned passengers is again random and lies between 1 and 5.   

Pods are sent from a station to another either if they are fully boarded (4 passengers) or after a specific amount of time (4 seconds). Pods can't be sent empty.
The ride duration of a pod is relative to the distance traveled in the tube. Only one pod can travel in a tube at the same time.  

This program makes great use of threads in order to schedule the sending of pods, spawn passengers, handle concurrent sending of pods through tubes,...  

## How to run
In order to run this project, please clone this repository, go inside the folder and run the following command in a terminal: `mvn clean javafx:run`. 
Once the project is run one can start the simulation by clicking on the top left button.  

Once the program is running, an output on the right will describe the actions performed within the program (pod sent from station X to station Y, pod arrived at destination,...).
Those actions are replicated in real-time on the map on the left.  

## Collaborators
This project was done for the Algorithms 3 course at the University of Luxembourg.  
The three other collaborators are Rayan Rafdy, Hugo Cossa, and Gilles Chen.  
