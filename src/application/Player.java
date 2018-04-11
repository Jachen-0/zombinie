package application;

import java.util.ArrayList;

import application.WorldObject.OrderedPair;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player {
	private boolean controllable;
	private int health;
	ImageView image = new ImageView();
	private double lastAngle = 0;
	public Circle hitBox;

	public Player() {
		this.health = 100;
		hitBox = new Circle();
		hitBox.setCenterX(600);
		hitBox.setCenterY(450);
		hitBox.setRadius(20);
		hitBox.setStroke(Color.BLACK);
		hitBox.setFill(Color.WHITE);
		image.setFitHeight(40);
		image.setFitWidth(40);
		image.setX(580);
		image.setY(430);

	}
	public boolean getControllable() {
		return controllable;
	}
	public int getHealth() {
		return health;
	}
	public double getLastAngle() {
		return lastAngle;
	}

	
	public void rotate(double x, double y) {
		double angle = Math.atan2(y - hitBox.getCenterY(), x - hitBox.getCenterX());
		image.setRotate(Math.toDegrees(angle));
		lastAngle = Math.toDegrees(angle);
	}

	public boolean shootHit(double angle, ArrayList<WorldObject> objs) {
		double margin = 4;
		double trueAngle = (angle + 180) % 360;
		System.out.println("Player x = " + hitBox.getCenterX() + " y = " + hitBox.getCenterY());
		double dx = Math.cos(Math.toRadians(trueAngle));
		double dy = Math.sin(Math.toRadians(trueAngle));
		System.out.println("angle = " + trueAngle);
		for (int i = 1; i < 50; i++) {
			OrderedPair raySegment = new OrderedPair(hitBox.getCenterX() - dx * i * margin, hitBox.getCenterY() - dy * i * margin);
			System.out.println("Shot " + i + " at x = " + raySegment.x + ", y = " + raySegment.y);
			for (WorldObject o : objs) {

				if (o.checkCol(0, 0, raySegment, 0)) {
					return true;
				}
			}
		}
		return false;
	}
}
