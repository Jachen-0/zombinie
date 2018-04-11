package application;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Zombie extends WorldObject{
	public OrderedPair worldPos;
	static Circle hitBox = new Circle();
	public Zombie(double worldXPos, double worldYPos, double radius) {
		super(hitBox, new OrderedPair(worldXPos, worldYPos), new OrderedPair(radius, radius), true);
		
		hitBox.setCenterX(worldXPos);
		hitBox.setCenterY(worldYPos);
		hitBox.setRadius(radius);
		hitBox.setStroke(Color.GREEN);
		hitBox.setFill(Color.RED);
	}
	
}
