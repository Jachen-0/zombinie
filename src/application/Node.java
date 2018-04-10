package application;

import application.WorldObject.OrderedPair;
import javafx.scene.shape.Circle;

public class Node extends WorldObject{

	
	public Node(double worldXPos, double worldYPos, double radius) {
		super(null, new OrderedPair(worldXPos, worldYPos), new OrderedPair(radius, radius), false);
	}
}
