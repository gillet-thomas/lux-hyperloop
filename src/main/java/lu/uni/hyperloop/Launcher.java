package lu.uni.hyperloop;

import lu.uni.hyperloop.dijkstra.Graph;
import lu.uni.hyperloop.ui.Map;

import javafx.event.ActionEvent;
import java.util.ArrayList;

public class Launcher {

	public static ArrayList<Station> stations = new ArrayList<>();
	public static ArrayList<Tube> tubes = new ArrayList<>();
	public static final int amountOfStations = 11;

	public static void main(ActionEvent args) throws InterruptedException {

		Station station0 = new Station(Map.circle0, 0, 5, 5);
		Station station1 = new Station(Map.circle1, 1, 8, 6);
		Station station2 = new Station(Map.circle2, 2, 9, 3);
		Station station3 = new Station(Map.circle3, 3, 6, 7);
		Station station4 = new Station(Map.circle4, 4, 4, 8);
		Station station5 = new Station(Map.circle5, 5, 7, 9);
		Station station6 = new Station(Map.circle6, 6, 6, 5);
		Station station7 = new Station(Map.circle7, 7, 7, 6);
		Station station8 = new Station(Map.circle8, 8, 8, 3);
		Station station9 = new Station(Map.circle9, 9, 5, 4);
		Station station10 = new Station(Map.circle10, 10, 7, 2);

		stations.add(station0);
		stations.add(station1);
		stations.add(station2);
		stations.add(station3);
		stations.add(station4);
		stations.add(station5);
		stations.add(station6);
		stations.add(station7);
		stations.add(station8);
		stations.add(station9);
		stations.add(station10);

		Tube tube1 = new Tube(Map.tube1, station1, station2, 20, true);
		Tube tube2 = new Tube(Map.tube2, station2, station3, 10, false);
		Tube tube3 = new Tube(Map.tube3, station3, station2, 10, false);
		Tube tube4 = new Tube(Map.tube4, station3, station4, 15, true);
		Tube tube5 = new Tube(Map.tube5, station4, station5, 10, true);
		Tube tube6 = new Tube(Map.tube6, station2, station6, 25, false);
		Tube tube7 = new Tube(Map.tube7, station6, station2, 25, false);
		Tube tube8 = new Tube(Map.tube8, station5, station6, 20, false);
		Tube tube9 = new Tube(Map.tube9, station6, station5, 20, false);
		Tube tube10 = new Tube(Map.tube10, station6, station7, 15, true);
		Tube tube11 = new Tube(Map.tube11, station6, station8, 15, false);
		Tube tube12 = new Tube(Map.tube12, station8, station6, 15, false);
		Tube tube13 = new Tube(Map.tube13, station8, station10, 10, false);
		Tube tube14 = new Tube(Map.tube14, station10, station8, 10, false);
		Tube tube15 = new Tube(Map.tube15, station8, station9, 10, false);
		Tube tube16 = new Tube(Map.tube16, station9, station8, 10, false);
		Tube tube17 = new Tube(Map.tube17, station0, station3, 5, false);
		Tube tube18 = new Tube(Map.tube18, station3, station0, 5, false);

		tubes.add(tube1);
		tubes.add(tube2);
		tubes.add(tube3);
		tubes.add(tube4);
		tubes.add(tube5);
		tubes.add(tube6);
		tubes.add(tube7);
		tubes.add(tube8);
		tubes.add(tube9);
		tubes.add(tube10);
		tubes.add(tube11);
		tubes.add(tube12);
		tubes.add(tube13);
		tubes.add(tube14);
		tubes.add(tube15);
		tubes.add(tube16);
		tubes.add(tube17);
		tubes.add(tube18);

		station1.pods.put(new Pod(station2)); // tube 1
		station2.pods.put(new Pod(station3)); // tube 2
		station3.pods.put(new Pod(station2)); // tube 3
		station3.pods.put(new Pod(station4)); // tube 4
		station4.pods.put(new Pod(station5)); // tube 5
		station2.pods.put(new Pod(station6)); // tube 6
		station6.pods.put(new Pod(station2)); // tube 7
		station5.pods.put(new Pod(station6)); // tube 8
		station6.pods.put(new Pod(station5)); // tube 9
		station7.pods.put(new Pod(station6)); // tube 10
		station6.pods.put(new Pod(station8)); // tube 11
		station8.pods.put(new Pod(station6)); // tube 12
		station8.pods.put(new Pod(station10)); // tube 13
		station10.pods.put(new Pod(station8)); // tube 14
		station8.pods.put(new Pod(station9)); // tube 15
		station9.pods.put(new Pod(station8)); // tube 16
		station0.pods.put(new Pod(station3)); // tube 17
		station3.pods.put(new Pod(station0)); // tube 18

		Graph graph = new Graph(tubes, amountOfStations);
		stations.stream().forEach(s -> { s.setGraph(graph); s.init(); });
		stations.stream().forEach(s -> s.startStation());
	}
}
