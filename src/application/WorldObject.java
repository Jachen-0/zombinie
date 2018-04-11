package application;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;




public abstract class WorldObject{
	public static class OrderedPair {
		public double x, y;
		public OrderedPair(double initX, double initY) {
			x = initX;
			y = initY;
		}
	}
	public WorldObject(){
		//WHY JAVA, WhY???
	}
	Shape hB;
	public boolean collidable;
	public boolean isVerticle;
	OrderedPair pos;
	public WorldObject(Shape hitbox, OrderedPair pos, OrderedPair scale, boolean collides) {
		this.pos = pos;
		hB = hitbox;
		collidable = collides;
		
		if (hB instanceof Circle) {
			((Circle) hB).setCenterX(pos.x);
			((Circle) hB).setCenterY(pos.y);
			((Circle) hB).setRadius(scale.x);
		}else if (hB instanceof Rectangle) {
			((Rectangle)hB).setX(pos.x);
			((Rectangle)hB).setY(pos.y);
			((Rectangle)hB).setWidth(scale.x);
			((Rectangle)hB).setHeight(scale.y);
			
			if (((Rectangle)hB).getWidth()>((Rectangle)hB).getHeight()) {
				isVerticle = false; 
			}else {
				isVerticle = true;
				System.out.println("Rectangle at x = " + (((Rectangle)hB).getX() + ((Rectangle)hB).getWidth()/2) + ", y = " + (((Rectangle)hB).getY() + ((Rectangle)hB).getHeight()/2));
			}
		}
	}
	

	public void move(double x, double y) {
		if (hB instanceof Circle) {
			pos.x = ((Circle) hB).getCenterX() - x;
			((Circle) hB).setCenterX(((Circle) hB).getCenterX() - x);
			pos.y = ((Circle) hB).getCenterY() - y;
			((Circle) hB).setCenterY(((Circle) hB).getCenterY() - y);
		}else if (hB instanceof Rectangle) {
			((Rectangle)hB).setX(((Rectangle)hB).getX() - x);
			((Rectangle)hB).setY(((Rectangle)hB).getY() - y);		
		}

	}

public boolean checkCol(double x, double y, OrderedPair objPos, double radius) {
	if (hB instanceof Circle) {
	return (Math.sqrt(Math.pow(pos.x - x - objPos.x, 2) + Math.pow(pos.y - y - objPos.y, 2))) <= (((Circle)hB).getRadius() + radius);
 } else if (hB instanceof Rectangle) {
	 if (isVerticle) {
		 return ((Math.sqrt(Math.pow((((Rectangle)hB).getX() + ((Rectangle)hB).getWidth()/2 ) - x - objPos.x, 2))) <= (((Rectangle)hB).getWidth()/2 + radius))&&(objPos.y < ((Rectangle)hB).getY() + ((Rectangle)hB).getHeight() && objPos.y > ((Rectangle)hB).getY());
	 }else {
		 return ((Math.sqrt(Math.pow((((Rectangle)hB).getY() + ((Rectangle)hB).getHeight()/2 ) - y - objPos.y, 2))) <= (((Rectangle)hB).getHeight()/2 + radius))&&(objPos.x < ((Rectangle)hB).getX() + ((Rectangle)hB).getWidth() && objPos.x > ((Rectangle)hB).getX());
	 }
 }
	return false;
}

}
