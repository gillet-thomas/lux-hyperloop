package lu.uni.hyperloop.dijkstra;

import lu.uni.hyperloop.Tube;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Class representing a graph object which is basically the connections between our stations
 *
 */
public class Graph {

	// This variable represent an adjacency list with a list of lists
	public List<List<Tube>> adjList;

	public Graph(List<Tube> tubes, int N) {
		adjList = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			adjList.add(new ArrayList<>());
		}

		for (Tube tube : tubes) {
			if (tube.bidirectional) {
				Tube t1 = new Tube(tube.path, tube.origin, tube.destination, tube.duration, false);
				Tube t2 = new Tube(tube.path, tube.destination, tube.origin, tube.duration, false);
				adjList.get(t1.origin.id).add(t1);
				adjList.get(t2.origin.id).add(t2);
			} else {
				adjList.get(tube.origin.id).add(tube);
			}
		}
	}

	@Override
	public String toString() {
		return "Graph -> " + adjList + "]";
	}
}