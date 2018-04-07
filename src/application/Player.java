package application;

import javafx.scene.shape.Circle;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Player{
	boolean controllable;
	int health;
	
	public Circle hitBox;
	public Player(boolean controllable, Pane pane) {
		this.controllable = controllable;
		this.health = 100;
		hitBox = new Circle();
		hitBox.setCenterX(200);
		hitBox.setCenterY(200);
		hitBox.setRadius(10);
		hitBox.setStroke(Color.BLACK);
		hitBox.setFill(Color.WHITE);
		}
	
	
	public void rotate(double x, double y) {
		double angle = 	 Math.atan2(hitBox.getCenterY(), hitBox.getCenterX()) -Math.atan2(y , x);
		
		System.out.println(angle);
	}
}
