package lu.uni.hyperloop;

import lu.uni.hyperloop.ui.Map;
import lu.uni.hyperloop.ui.Moving_pods;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Tube {

    boolean isFree = true;
    public Station origin;
    public Station destination;
    public int duration;
    public boolean bidirectional;
    public Line path;

    /**
     * Constructor of the Tube class
     *
     * @param path Line object representing the tube on the map
     * @param origin origin Station of the tube
     * @param destination destination Station of the tube
     * @param duration Duration of the ride in seconds
     * @param bidirectional Whether the tube is bidirectional or not
     */
    public Tube(Line path, Station origin, Station destination, int duration, boolean bidirectional) {
        this.path = path;
        this.origin = origin;
        this.destination = destination;
        this.duration = duration;
        this.bidirectional = bidirectional;
    }

    /**
     * Send a pod through this tube and add it to the tube destination station
     *
     * @param p The pod to send
     */
    public synchronized void send(Pod p) {
        try {
            this.isFree = false; // "Lock" tube so that it can't send any other pods for the moment

            // Assign the correct object's value according to whether the tube is bidirectional or not
            int originID = (bidirectional && p.destination.id != this.destination.id) ? destination.id : this.origin.id;
            Station dest = (bidirectional && p.destination.id != this.destination.id) ? this.origin : destination;

            // Create a moving pod on the map which represents the current pod being sent
            Platform.runLater(() -> {
                Map.createCustomBoldText(
                        "\nSending " + p + " from Station " + originID + " to Station " + dest.id + ". Ride duration is " + this.duration + " sec...", Color.ORANGE);
                Moving_pods move_pod = new Moving_pods(origin.map_pos, path, duration, Map.root);
                move_pod.move((bidirectional && p.destination.id != this.destination.id));
            });

            Thread.sleep(this.duration * 1000);

            // Assign the new pod destination so that it can only travel between the 2 stations of the current tube
            p.destination = (bidirectional && p.destination.id != this.destination.id) ? destination : this.origin;

            this.isFree = true; // "Unlock" tube so that it can send new pods
            dest.receivePod(p); // Make the tube destination station receive the sent pod
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Tube [from " + origin.id + " to " + destination.id + "]";
    }
}
