package lu.uni.hyperloop;

import lu.uni.hyperloop.dijkstra.Graph;
import lu.uni.hyperloop.ui.Map;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Station {

	public int id;
	int maxPods;
	int initialPassengers;
	BlockingQueue<Pod> pods = new LinkedBlockingDeque<>();
	BlockingQueue<Passenger> passengers = new LinkedBlockingDeque<>();

	Graph g;
	Circle map_pos;

	/**
	 * Constructor of the Station class
	 *
	 * @param map_pos           Circle object representing the station on the map
	 * @param id                id of the station
	 * @param maxPods           Maximum number of pods the station can hold
	 * @param initialPassengers Initial number of passengers in the station
	 */
	public Station(Circle map_pos, int id, int maxPods, int initialPassengers) {
		this.map_pos = map_pos;
		this.id = id;
		this.maxPods = maxPods;
		this.initialPassengers = initialPassengers;
	}

	/**
	 * Create the passengers on the station's start
	 */
	public void init() {
		IntStream.range(0, initialPassengers)
				.forEach(i -> passengers.add(new Passenger(g, id, Launcher.amountOfStations)));
	}

	public void setGraph(Graph g) {
		this.g = g;
	}

	/**
	 * Create a Thread that continuously spawns Passengers based on Chance. The amount of Passengers also depends on Chance.
	 * The current implementation has a 25% chance of spawning passengers every 2 seconds. It will spawn between 1-4 Passengers.
	 */
	public synchronized void spawnPassengers() {
		Random r = new Random();
		Runnable runnable = () -> {
			while (true) {
				int num = r.nextInt(20);
				if(num%6 == 0){ // Probability of 3/20 = 0.15%
					int passengerAmount = r.nextInt(5) + 1; // spawn between 1 and 5 passengers
					for (int i = 0; i < passengerAmount; i++) {
						passengers.add(new Passenger(g, id, Launcher.amountOfStations));
					}
					// Uncomment the following line to see how many Passengers have spawned.
					System.out.println("Spawned: " + passengerAmount + " at station " + this.id);
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					Logger.getLogger(Station.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		};
		new Thread(runnable).start();
	}

	@Override
	public String toString() {
		return this.id + " [waiting passengers=" + passengers.size() + ", number of pods=" + pods.size() + "]";
	}

	/**
	 * Check whether the station is full or not (if it can accept one more pod)
	 *
	 * @return true if the station is full otherwise false
	 */
	public boolean isFull() {
		return this.pods.size() >= this.maxPods;
	}

	/**
	 * Return a free tube starting from the current station
	 *
	 * @param p The pod that will be sent through this tube
	 * @return One of the free adjacent tubes
	 */
	public Tube getNextFreeTube(Pod p) {

		// Find a tube that has same origin and the same destination as the pod
		Optional<Tube> optTube = Launcher.tubes.stream()
				.filter(t -> t.origin.id == this.id && t.destination.id == p.destination.id).findFirst();

		// If no tube were found, try to find a bidirectional tube that has origin = pod
		// destination and destination = pod origin, otherwise unwrap the optional
		Tube nextTube = optTube.isEmpty()
				? Launcher.tubes.stream().filter(t -> t.origin.id == p.destination.id && t.destination.id == this.id).findFirst().get()
				: optTube.get();

		while (true) {
			if (nextTube.isFree) {

				// Assign the correct tubeDestination according to whether the tube is bidirectional or not
				Station tubeDestination = (nextTube.bidirectional && p.destination.id != nextTube.destination.id)
						? nextTube.origin
						: nextTube.destination;

				if (tubeDestination.isFull()) {
					Platform.runLater(() -> Map.createCustomText("\nDestination " + tubeDestination + " is full. Try another one...", Color.RED));
					try {
						wait(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					return nextTube;
				}
			} else {
				Platform.runLater(() -> Map.createCustomText("\n" + nextTube + " " + nextTube.hashCode() + " is occupied. Please wait!", Color.RED));
				try {
					wait(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Receive a pod, unboard passengers and add the pod to the current station
	 *
	 * @param p The pod that has been sent and has just arrived
	 */
	public synchronized void receivePod(Pod p) {
		this.pods.add(p);

		// Count the number of passenger that have the current station as final destination
		int counter = (int) p.passengers.stream().filter(pass -> pass.finalDestination == this.id).count();
		Platform.runLater(() -> {
			String text = "\nPod " + p.hashCode() + " arrived at Station " + this + "\n";
			if(counter == 0){
				//text += "This station is not the final destination for the Passengers that have just arrived in this pod.";
			} else {
				text += "\t" + counter + " passenger(s) arrived at final destination!";
				Map.createCustomText(text, Color.GREEN);
			}
		});

		// If the passenger is at final destination remove him, otherwise add him to the current station, increase it's currentStation index and remove him
		Iterator<Passenger> iter = p.passengers.iterator();
		while (iter.hasNext()) {
			Passenger passenger = iter.next();
			if (passenger.finalDestination != this.id) {
				passenger.currentStation++;
				this.passengers.add(passenger);
			}
			iter.remove();
		}
		notify();   // Notify all threads so that they can send back the pod and the passengers that have just arrived
		sendPods(); // Resend directly the arrived pod
	}

	/**
	 * Send all the pods in the current station
	 */
	public void sendPods() {
		int counter = 0;
		while (true) {
			if (counter > 2) {
				spawnPassengers();  // If it has been 6 seconds no passengers spawned, force their spawn by calling the method explicitly
			}
			if (passengers.size() == 0) {
				try {
					Platform.runLater(() -> Map.createCustomText("\nStation " + this.id + " is currently empty", Color.RED));
					wait(3000);
					counter++;
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			} else {
				if (pods.size() == 0) {
					try {
						Platform.runLater(() -> Map.createCustomText("\nStation " + this.id + " is waiting for a pod to arrive...", Color.BLACK));
						wait(2000);
					} catch (InterruptedException e) {
						System.out.println(e);
					}
				} else {
					break;
				}
			}
		}

		// If there are passengers going to the pod destination, send the pod and remove it from the current station
		Iterator<Pod> iter = pods.iterator();
		while (iter.hasNext()) {
			Pod p = iter.next();
			int passengerNumber = (int) passengers.stream().filter(pass -> pass.path.get(pass.currentStation) == p.destination.id).count();
			if (passengerNumber != 0) {
				new Thread(() -> sendPod(p)).start();
				iter.remove();
			}
		}
	}

	/**
	 * Send a pod to it's destination
	 *
	 * @param p The pod to send
	 */
	public void sendPod(Pod p) {
		Tube nextTube;
		synchronized (this) {
			nextTube = getNextFreeTube(p);

			// Compute the number of passengers in the current station going to the pod destination
			int passengerNumber = (int) passengers.stream().filter(pass -> pass.path.get(pass.currentStation) == p.destination.id).count();

			if (passengerNumber < 4) {
				Platform.runLater(() -> Map.createCustomText("\nStation " + this.id + " next Pod is departing in 4 seconds to " + "Station " + p.destination.id, Color.BLACK));
				try {
					wait(4000);

					// Check again if after those 4sec there are still passengers in the station
					passengerNumber = (int) passengers.stream().filter(pass -> pass.path.get(pass.currentStation) == p.destination.id).count();
					if (passengerNumber == 0) {
						this.pods.add(p); // If no passengers after those 4 seconds, add the pod back to the station
						return;
					}

				} catch (InterruptedException e) {
					System.out.println(e);
				}
			}

			// Board all the passengers going to the pod's destination into the pod until the pod is full (4 persons) and remove them from current station
			Iterator<Passenger> iter = passengers.iterator();
			while (iter.hasNext()) {
				Passenger pass = iter.next();
				if (pass.path.get(pass.currentStation) == p.destination.id) {
					p.passengers.add(pass);
					iter.remove();
					if (p.passengers.size() == 4) {
						break;
					}
				}
			}
		}
		nextTube.send(p); // Send the pod through the tube
	}

	/**
	 * Start the passengers spawning and send the pods
	 */
	public synchronized void startStation() {
		spawnPassengers();
		sendPods();
		Platform.runLater(() -> Map.createCustomText("\nStarting station " + this, Color.BLACK));
	}
}
