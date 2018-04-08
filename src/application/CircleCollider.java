package application;

import javafx.scene.shape.Circle;

public class CircleCollider extends WorldObject{
	public Circle hitBox;
	
	public CircleCollider(double worldXPos, double worldYPos, double radius) {
		super(new Circle(), new OrderedPair(worldXPos, worldYPos), new OrderedPair(radius, radius));
	}
}
