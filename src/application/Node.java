package application;

import application.WorldObject.OrderedPair;
import javafx.scene.shape.Circle;

public class Node extends WorldObject{
	public OrderedPair worldPos;
	
	public Node(double worldXPos, double worldYPos, double radius) {
		super(new Circle(), new OrderedPair(worldXPos, worldYPos), new OrderedPair(radius, radius), false);
	}
}