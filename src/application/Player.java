package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player{
	boolean controllable;
	int health;
	ImageView image = new ImageView();

	public Circle hitBox;
	public Player(boolean controllable, Pane pane) {
		this.controllable = controllable;
		this.health = 100;
		hitBox = new Circle();
		hitBox.setCenterX(200);
		hitBox.setCenterY(200);
		hitBox.setRadius(20);
		hitBox.setStroke(Color.BLACK);
		hitBox.setFill(Color.WHITE);
		image.setFitHeight(40);
		image.setFitWidth(40);
		image.setX(180);
		image.setY(180);
	  
	}
	
	
	public void rotate(double x, double y) {
		double angle = Math.atan2(y - hitBox.getCenterY(), x - hitBox.getCenterX());
		image.setRotate(Math.toDegrees(angle));
		System.out.println(Math.toDegrees(angle));
	}
}
