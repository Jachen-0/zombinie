package application;

import javafx.scene.shape.Circle;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class WorldObject extends Circle {
	
	public Circle hitBox;
	
	public WorldObject(double worldXPos, double worldYPos, double radius) {
		
		hitBox = new Circle();
		hitBox.setCenterX(worldXPos);
		hitBox.setCenterY(worldYPos);
		hitBox.setRadius(radius);
	}
	public boolean checkCol(double x, double y, Player p) {
			return (Math.sqrt(Math.pow(hitBox.getCenterX() - x - p.hitBox.getCenterX(), 2) + Math.pow(hitBox.getCenterY() - y - p.hitBox.getCenterY(), 2))) <= (hitBox.getRadius() + p.hitBox.getRadius());
	}
	public void move(double x, double y) {
		hitBox.setCenterX(hitBox.getCenterX() - x);
		hitBox.setCenterY(hitBox.getCenterY() - y);
	}
}
