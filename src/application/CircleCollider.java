package application;

import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class CircleCollider extends WorldObject {

	public CircleCollider(double worldXPos, double worldYPos, double radius) {
		//creates a world object with collisions
		super(new Circle(), new OrderedPair(worldXPos, worldYPos), new OrderedPair(radius, radius), true);
		
		//makes it invisible
		hB.setFill(null);
		hB.setStroke(null);
		
		//places an image over the object
		Image im = new Image("black_circle.png");
		image.setImage(im);
		image.setFitHeight(radius*2);
		image.setFitWidth(radius*2);
		image.setX(pos.x - image.getFitWidth()/2);
		image.setY(pos.y - image.getFitHeight()/2);
	}

	@Override
	public void move(double x, double y) {
		super.pos.x -= x;
		super.pos.y -= y;
		((Circle) hB).setCenterX(super.pos.x);
		((Circle) hB).setCenterY(super.pos.y);
		image.setX(pos.x - image.getFitWidth()/2);
		image.setY(pos.y - image.getFitHeight()/2);
	}
	@Override
	public boolean checkCol(double x, double y, OrderedPair objPos, double radius) {
		return (Math.sqrt(Math.pow(pos.x - x - objPos.x, 2) + Math.pow(pos.y - y - objPos.y, 2))) <= (((Circle)hB).getRadius() + radius);
	}
}
