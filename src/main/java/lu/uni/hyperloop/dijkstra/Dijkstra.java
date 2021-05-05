package lu.uni.hyperloop.dijkstra;

import lu.uni.hyperloop.Tube;

import java.util.*;

public class Dijkstra {

	/**
	 * Compute the shortest path from a source to a destination station
	 * 
	 * @param graph       Graph containing all the tubes
	 * @param source      Where the passenger is starting from
	 * @param destination Where the passenger wants to go
	 * @param N           Number of nodes (stations)
	 * @return the shortest path from a node A to a node B using the getRoute() function
	 */
	public static List<Integer> findShortestPaths(Graph graph, int source, int destination, int N) {
		// Creating a min-heap and push source node having a distance 0
		PriorityQueue<Node> minHeap;
		minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight));
		minHeap.add(new Node(source, 0));

		// Setting initial distance from the source to 'v' as infinity
		List<Integer> dist;
		dist = new ArrayList<>(Collections.nCopies(N, Integer.MAX_VALUE));

		// Distance from the source to itself is zero
		dist.set(source, 0);

		// Setting a boolean array to track vertices in which minimum cost is already found
		boolean[] done = new boolean[N];
		done[source] = true;

		// Storing predecessor of a vertex in order to print the path
		int[] prev = new int[N];
		prev[source] = -1;

		List<Integer> route = new ArrayList<>();

		while (!minHeap.isEmpty()) {
			Node node = minHeap.poll();
			// get the vertex number
			int u = node.vertex;

			for (Tube tube : graph.adjList.get(u)) {
				int v = tube.destination.id;
				int weight = tube.duration;

				if (!done[v] && (dist.get(u) + weight) < dist.get(v)) {
					dist.set(v, dist.get(u) + weight);
					prev[v] = u;
					minHeap.add(new Node(v, dist.get(v)));
				}
			}
			// Mark the road 'u' as done so it will not get picked up again
			done[u] = true;
		}

		for (int i = 0; i < N; i++) {
			if (i == destination) {
				getRoute(prev, i, route);
			}
		}
		return route;
	}

	private static void getRoute(int[] previous, int i, List<Integer> route) {
		if (i >= 0) {
			getRoute(previous, previous[i], route);
			route.add(i);
		}
	}
}