package application;

import java.util.ArrayList;

import application.WorldObject.OrderedPair;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player extends WorldObject{
	private boolean controllable;
	private int health;
	private double lastAngle = 0;
	private int frame = 1;
	private Image[] im = {new Image("newplayer3.png"), new Image("newplayer1.png"), new Image("newplayer2.png")};
	public Player() {
		super(new Circle(), new OrderedPair(600,450), new OrderedPair(20,20), false);

		hB.setStroke(null);
		hB.setFill(null);
		
		image.setImage(im[0]);
		image.setFitHeight(40);
		image.setFitWidth(40);
		image.setX(580);
		image.setY(430);

	}
	public void flickerAnimation() {
		image.setImage(im[frame]);
		if (frame == 1) {
			frame++;
		}else {
			frame--;
		}
		
	}
	public void base() {
		image.setImage(im[0]);	
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
		double angle = Math.atan2(y - pos.y, x - pos.x);
		image.setRotate(Math.toDegrees(angle) + 90);
		lastAngle = Math.toDegrees(angle);
	}

	public WorldObject shootHit(double angle, ArrayList<WorldObject> objs) {
		double margin = 20;
		double trueAngle = (angle + 180) % 360;
		double dx = Math.cos(Math.toRadians(trueAngle));
		double dy = Math.sin(Math.toRadians(trueAngle));
		System.out.println("angle = " + trueAngle);
		for (int i = 1; i < 50; i++) {
			OrderedPair raySegment = new OrderedPair(pos.x - dx * i * margin, pos.y - dy * i * margin);
			
			for (WorldObject o : objs) {

				if (o.checkCol(0, 0, raySegment, 0)) {
					System.out.println("Shot a " + o.toString());
					return o;
				}
			}
		}
		return null;
	}
}
