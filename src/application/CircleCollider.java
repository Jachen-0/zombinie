package application;

import application.WorldObject.OrderedPair;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CircleCollider extends WorldObject {

	public CircleCollider(double worldXPos, double worldYPos, double radius) {
		super(new Circle(), new OrderedPair(worldXPos, worldYPos), new OrderedPair(radius, radius), true);
	}

	@Override
	public void move(double x, double y) {
		super.pos.x -= x;
		super.pos.y -= y;
		((Circle) hB).setCenterX(super.pos.x);
		((Circle) hB).setCenterY(super.pos.y);
	}
	@Override
	public boolean checkCol(double x, double y, OrderedPair objPos, double radius) {
		return (Math.sqrt(Math.pow(pos.x - x - objPos.x, 2) + Math.pow(pos.y - y - objPos.y, 2))) <= (((Circle)hB).getRadius() + radius);
	}
}
