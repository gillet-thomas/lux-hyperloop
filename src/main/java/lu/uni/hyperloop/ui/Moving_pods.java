package lu.uni.hyperloop.ui;

import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * Class representing a pod moving on the UI
 */
public class Moving_pods {

	Circle station;
	Line path;
	int travel_duration;
	Group root;

	public Moving_pods(Circle station, Line path, int travel_duration, Group root) {
		this.station = station;
		this.path = path;
		this.travel_duration = travel_duration;
		this.root = root;
	}

	/**
	 * Create a circle representing a pod and move it from one station to another
	 *
	 * @param isBidirectional when true the direction of the tube is changed
	 */
	public void move(boolean isBidirectional) {
		Circle pod = new Circle(station.getCenterX(), station.getCenterY(), 5);
		pod.setFill(Color.YELLOW);
		root.getChildren().add(pod);
		PathTransition transition = new PathTransition();
		transition.setNode(pod);

		if (isBidirectional) {
			Line reverse_path = new Line(path.getEndX(), path.getEndY(), path.getStartX(), path.getStartY());
			transition.setPath(reverse_path);
		} else {
			transition.setPath(path);
		}
		transition.setDuration(Duration.seconds(travel_duration));
		transition.setCycleCount(1);
		transition.play();
		transition.setOnFinished(actionEvent -> root.getChildren().remove(pod));
	}
}
