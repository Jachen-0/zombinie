package application;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import application.WorldObject.OrderedPair;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Zombie extends WorldObject{
	public Zombie(double worldXPos, double worldYPos, double radius) {
		super(new Circle(), new OrderedPair(worldXPos, worldYPos), new OrderedPair(radius, radius), true);
		((Circle)hB).setStroke(Color.GREEN);
		((Circle)hB).setFill(Color.RED);
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
