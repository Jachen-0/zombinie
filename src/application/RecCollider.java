package application;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class RecCollider extends WorldObject {
	private boolean isVertical;
		
	public RecCollider(double worldXPos, double worldYPos, double width, double length) {
		super(new Rectangle(), new OrderedPair(worldXPos, worldYPos), new OrderedPair(width, length), true);
		
		if (((Rectangle)hB).getWidth()>((Rectangle)hB).getHeight()) {
			isVertical = false; 
		}
		else {
			isVertical = true;
			System.out.println("Rectangle at x = " + (((Rectangle)hB).getX() + ((Rectangle)hB).getWidth()/2) + ", y = " + (((Rectangle)hB).getY() + ((Rectangle)hB).getHeight()/2));
		}
		
		hB.setFill(null);
		hB.setStroke(null);
		
		Image im = new Image("black_stretchable.png");
		image.setImage(im);
		image.setFitHeight(((Rectangle)hB).getHeight());
		image.setFitWidth(((Rectangle)hB).getWidth());
		image.setX(pos.x);
		image.setY(pos.y);
	}

	@Override
	public void move(double x, double y) {
		super.pos.x -= x;
		super.pos.y -= y;
		((Rectangle)hB).setX(super.pos.x);
		((Rectangle)hB).setY(super.pos.y);
		image.setX(pos.x);
		image.setY(pos.y);
	}
	@Override
	public boolean checkCol(double x, double y, OrderedPair objPos, double radius) {
		 if (isVertical) {
			 return ((Math.sqrt(Math.pow((((Rectangle)hB).getX() + ((Rectangle)hB).getWidth()/2 ) - x - objPos.x, 2))) <= (((Rectangle)hB).getWidth()/2 + radius))&&(objPos.y < ((Rectangle)hB).getY() + ((Rectangle)hB).getHeight() && objPos.y > ((Rectangle)hB).getY());
		 }
		 else {
			 return ((Math.sqrt(Math.pow((((Rectangle)hB).getY() + ((Rectangle)hB).getHeight()/2 ) - y - objPos.y, 2))) <= (((Rectangle)hB).getHeight()/2 + radius))&&(objPos.x < ((Rectangle)hB).getX() + ((Rectangle)hB).getWidth() && objPos.x > ((Rectangle)hB).getX());
		 }
	}

}