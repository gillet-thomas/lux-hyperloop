package lu.uni.hyperloop.ui;

import lu.uni.hyperloop.Launcher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class Map extends Application {

	public static int x = 600;
	public static int y = 600;

	public static double radius = 0.02 * x;

	// Each circle represents a station
	public static Circle circle0 = new Circle(0.425 * x, 0.255 * y, radius);
	public static Circle circle1 = new Circle(0.35 * x, 0.15 * y, radius);
	public static Circle circle2 = new Circle(0.325 * x, 0.375 * y, radius);
	public static Circle circle3 = new Circle(0.425 * x, 0.325 * y, radius);
	public static Circle circle4 = new Circle(0.575 * x, 0.425 * y, radius);
	public static Circle circle5 = new Circle(0.625 * x, 0.525 * y, radius);
	public static Circle circle6 = new Circle(0.425 * x, 0.625 * y, radius);
	public static Circle circle7 = new Circle(0.575 * x, 0.65 * y, radius);
	public static Circle circle8 = new Circle(0.35 * x, 0.75 * y, radius);
	public static Circle circle9 = new Circle(0.475 * x, 0.775 * y, radius);
	public static Circle circle10 = new Circle(0.275 * x, 0.675 * y, radius);

	// Each line represents a tube that connects two stations
	public static Line tube1 = new Line(0.35 * x, 0.15 * y, 0.325 * x, 0.375 * y);
	public static Line tube2 = new Line(0.325 * x, 0.37 * y, 0.425 * x, 0.32 * y);
	public static Line tube3 = new Line(0.425 * x, 0.33 * y, 0.325 * x, 0.38 * y);
	public static Line tube4 = new Line(0.425 * x, 0.325 * y, 0.575 * x, 0.425 * y);
	public static Line tube5 = new Line(0.575 * x, 0.425 * y, 0.625 * x, 0.525 * y);
	public static Line tube6 = new Line(0.33 * x, 0.375 * y, 0.43 * x, 0.625 * y);
	public static Line tube7 = new Line(0.42 * x, 0.625 * y, 0.32 * x, 0.375 * y);
	public static Line tube8 = new Line(0.625 * x, 0.52 * y, 0.425 * x, 0.62 * y);
	public static Line tube9 = new Line(0.425 * x, 0.63 * y, 0.625 * x, 0.53 * y);
	public static Line tube10 = new Line(0.425 * x, 0.625 * y, 0.575 * x, 0.65 * y);
	public static Line tube11 = new Line(0.43 * x, 0.625 * y, 0.355 * x, 0.75 * y);
	public static Line tube12 = new Line(0.345 * x, 0.75 * y, 0.42 * x, 0.625 * y);
	public static Line tube13 = new Line(0.35 * x, 0.745 * y, 0.275 * x, 0.67 * y);
	public static Line tube14 = new Line(0.275 * x, 0.68 * y, 0.35 * x, 0.755 * y);
	public static Line tube15 = new Line(0.35 * x, 0.745 * y, 0.475 * x, 0.77 * y);
	public static Line tube16 = new Line(0.475 * x, 0.78 * y, 0.35 * x, 0.755 * y);
	public static Line tube17 = new Line(0.42 * x, 0.255 * y, 0.42 * x, 0.325 * y);
	public static Line tube18 = new Line(0.43 * x, 0.325 * y, 0.43 * x, 0.255 * y);

	public static Group root = new Group();

	public static TextFlow output = new TextFlow();
	public static ScrollPane outputArea = new ScrollPane();
	public static ObservableList<javafx.scene.Node> outputList = output.getChildren();

	public static void createCustomText(String text, Color color) {
		outputArea.setVvalue(outputArea.vmaxProperty().doubleValue()); // Scroll the Pane to the bottom
		Text t = new Text(text);
		t.setFill(color);
		t.setFont(new Font(15));
		outputList.add(t);
	}

	public static void createCustomBoldText(String text, Color color) {
		outputArea.setVvalue(outputArea.vmaxProperty().doubleValue()); // Scroll the Pane to the bottom
		Text t = new Text(text);
		t.setFill(color);
		t.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		outputList.add(t);
	}

	/**
	 * start function building the map on the user interface
	 * UI contains a button that starts the simulation by running the main from launcher class
	 */
	@Override
	public void start(Stage stage) throws FileNotFoundException {

		Button start_button = new Button("Start the simulation");
		start_button.setOnAction(arg0 -> {
			System.out.println("Simulation running...");
			start_button.setVisible(false);
			Runnable runnable = () -> {
				try {
					Launcher.main(arg0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
			new Thread(runnable).start();
		});

		tube1.setStrokeWidth(3.0);
		tube1.setStyle("-fx-stroke: green;");

		tube2.setStrokeWidth(3.0);
		tube2.setStyle("-fx-stroke: blue;");

		tube3.setStrokeWidth(3.0);
		tube3.setStyle("-fx-stroke: red;");

		tube4.setStrokeWidth(3.0);
		tube4.setStyle("-fx-stroke: green;");

		tube5.setStrokeWidth(3.0);
		tube5.setStyle("-fx-stroke: green;");

		tube6.setStrokeWidth(3.0);
		tube6.setStyle("-fx-stroke: red;");

		tube7.setStrokeWidth(3.0);
		tube7.setStyle("-fx-stroke: blue;");

		tube8.setStrokeWidth(3.0);
		tube8.setStyle("-fx-stroke: blue;");

		tube9.setStrokeWidth(3.0);
		tube9.setStyle("-fx-stroke: red;");

		tube10.setStrokeWidth(3.0);
		tube10.setStyle("-fx-stroke: green;");

		tube11.setStrokeWidth(3.0);
		tube11.setStyle("-fx-stroke: blue;");

		tube12.setStrokeWidth(3.0);
		tube12.setStyle("-fx-stroke: red;");

		tube13.setStrokeWidth(3.0);
		tube13.setStyle("-fx-stroke: blue;");

		tube14.setStrokeWidth(3.0);
		tube14.setStyle("-fx-stroke: red;");

		tube15.setStrokeWidth(3.0);
		tube15.setStyle("-fx-stroke: blue;");

		tube16.setStrokeWidth(3.0);
		tube16.setStyle("-fx-stroke: red;");

		tube17.setStrokeWidth(3.0);
		tube17.setStyle("-fx-stroke: red;");

		tube18.setStrokeWidth(3.0);
		tube18.setStyle("-fx-stroke: blue;");

		Image image = new Image(new FileInputStream("src\\main\\resources\\map.png"));

		ImageView imageView = new ImageView(image);

		imageView.setX(-35);
		imageView.setY(-20);

		imageView.setFitHeight(y);
		imageView.setFitWidth(x);

		imageView.setPreserveRatio(true);

		start_button.setLayoutX(5);
		start_button.setLayoutY(5);

		outputArea.setLayoutX(450);
		outputArea.setLayoutY(50);
		outputArea.setPrefSize(900, 500);
		outputArea.setContent(output);

		Text text0 = new Text(circle0.getCenterX() - 5, circle0.getCenterY() + 5, "0");
		text0.setFill(Color.WHITE);
		Text text1 = new Text(circle1.getCenterX() - 5, circle1.getCenterY() + 5, "1");
		text1.setFill(Color.WHITE);
		Text text2 = new Text(circle2.getCenterX() - 5, circle2.getCenterY() + 5, "2");
		text2.setFill(Color.WHITE);
		Text text3 = new Text(circle3.getCenterX() - 5, circle3.getCenterY() + 5, "3");
		text3.setFill(Color.WHITE);
		Text text4 = new Text(circle4.getCenterX() - 5, circle4.getCenterY() + 5, "4");
		text4.setFill(Color.WHITE);
		Text text5 = new Text(circle5.getCenterX() - 5, circle5.getCenterY() + 5, "5");
		text5.setFill(Color.WHITE);
		Text text6 = new Text(circle6.getCenterX() - 5, circle6.getCenterY() + 5, "6");
		text6.setFill(Color.WHITE);
		Text text7 = new Text(circle7.getCenterX() - 5, circle7.getCenterY() + 5, "7");
		text7.setFill(Color.WHITE);
		Text text8 = new Text(circle8.getCenterX() - 5, circle8.getCenterY() + 5, "8");
		text8.setFill(Color.WHITE);
		Text text9 = new Text(circle9.getCenterX() - 5, circle9.getCenterY() + 5, "9");
		text9.setFill(Color.WHITE);
		Text text10 = new Text(circle10.getCenterX() - 5, circle10.getCenterY() + 5, "10");
		text10.setFill(Color.WHITE);

		start_button.setLayoutX(5);
		start_button.setLayoutY(5);

		root.getChildren().addAll(imageView, start_button, tube1, tube2, tube3, tube4, tube5, tube6, tube7, tube8,
				tube9, tube10, tube11, tube12, tube13, tube14, tube15, tube16, tube17, tube18, circle0, circle1,
				circle2, circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10, text0, text1, text2,
				text3, text4, text5, text6, text7, text8, text9, text10, outputArea);

		Scene scene = new Scene(root, 2.3 * x, y);
		stage.setTitle("Hyperloop");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
