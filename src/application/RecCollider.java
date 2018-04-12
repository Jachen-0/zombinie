package application;

import application.WorldObject.OrderedPair;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class RecCollider extends WorldObject {
		private boolean isVerticle;
	public RecCollider(double worldXPos, double worldYPos, double width, double length) {
		super(new Rectangle(), new OrderedPair(worldXPos, worldYPos), new OrderedPair(width, length), true);
		if (((Rectangle)hB).getWidth()>((Rectangle)hB).getHeight()) {
			isVerticle = false; 
		}else {
			isVerticle = true;
			System.out.println("Rectangle at x = " + (((Rectangle)hB).getX() + ((Rectangle)hB).getWidth()/2) + ", y = " + (((Rectangle)hB).getY() + ((Rectangle)hB).getHeight()/2));
		}

	}

	@Override
	public void move(double x, double y) {
		super.pos.x -= x;
		super.pos.y -= y;
		((Rectangle)hB).setX(super.pos.x);
		((Rectangle)hB).setY(super.pos.y);
	}
	@Override
	public boolean checkCol(double x, double y, OrderedPair objPos, double radius) {
		
		 if (isVerticle) {
			 return ((Math.sqrt(Math.pow((((Rectangle)hB).getX() + ((Rectangle)hB).getWidth()/2 ) - x - objPos.x, 2))) <= (((Rectangle)hB).getWidth()/2 + radius))&&(objPos.y < ((Rectangle)hB).getY() + ((Rectangle)hB).getHeight() && objPos.y > ((Rectangle)hB).getY());
		 }else {
			 return ((Math.sqrt(Math.pow((((Rectangle)hB).getY() + ((Rectangle)hB).getHeight()/2 ) - y - objPos.y, 2))) <= (((Rectangle)hB).getHeight()/2 + radius))&&(objPos.x < ((Rectangle)hB).getX() + ((Rectangle)hB).getWidth() && objPos.x > ((Rectangle)hB).getX());
		 }
	}

}