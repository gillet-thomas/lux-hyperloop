package lu.uni.hyperloop.dijkstra;

/**
 * 
 * Class storing a heap node
 *
 */
public class Node {

	public final int vertex;
	public int weight;

	public Node(int vertex, int weight) {
		this.vertex = vertex;
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Node [vertex=" + vertex + ", weight=" + weight + "]";
	}
}