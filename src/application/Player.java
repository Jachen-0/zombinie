package application;

import javafx.scene.shape.Circle;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Player{
	boolean controllable;
	int health;
	double speed;
	public Circle c;
	public Player(boolean controllable, Pane pane) {
		this.controllable = controllable;
		this.health = 100;
		this.speed = 2;
		c = new Circle();

		c.setCenterX(200);
		c.setCenterY(200);
		c.setRadius(10);
		c.setStroke(Color.BLACK);
		c.setFill(Color.WHITE);
		move(100,100);
	}

	public void move(double x, double y) {
		c.setCenterX(c.getCenterX() + x);
		c.setCenterY(c.getCenterY() + y);
		System.out.println("Moved");
		
	}
}
