package lu.uni.hyperloop;

import lu.uni.hyperloop.dijkstra.Graph;

import java.util.List;
import java.util.Random;

import static lu.uni.hyperloop.dijkstra.Dijkstra.findShortestPaths;

public class Passenger {

	List<Integer> path;
	int currentStation;
	int finalDestination;

	/**
	 * Constructor of the Passenger class
	 * 
	 * @param graph            the graph where the stations are nodes and the tubes are paths with weight
	 * @param fromID           at which station this Passenger appears
	 * @param amountOfStations the maximum amount of Stations
	 */
	public Passenger(Graph graph, int fromID, int amountOfStations) {
		this.currentStation = fromID;
		this.finalDestination = randomID();
		this.path = findShortestPaths(graph, currentStation, finalDestination, amountOfStations);
		this.currentStation = 1;
	}

	/**
	 * Generate a random ID, which in this Program would mean, a random Destination Station.
	 **/
	public int randomID() {
		Random r = new Random();
		int randomID;
		while ((randomID = r.nextInt(Launcher.amountOfStations)) == currentStation);
		return randomID;
	}

	/**
	 * Concatenate the values in path to a nice looking String.
	 * 
	 * @return a String representing the shortest Path.
	 */
	public String shortestPath() {
		String text = "";
		for (int i = 0; i < path.size(); i++) {
			text += (i != path.size() - 1) ? path.get(i) + "->" : path.get(i);
		}
		return text;
	}

	public String toString() {
		return "Passenger(id:" + this.hashCode() + ", Starting Station: " + currentStation + ") would like to arrive at Station: "
				+ finalDestination + ". The shortest Path is: " + shortestPath();
	}
}