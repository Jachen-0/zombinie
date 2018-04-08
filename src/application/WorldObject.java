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
	
	public WorldObject(Shape hitbox, OrderedPair pos, OrderedPair scale) {
		hB = hitbox;
		if (hB instanceof Circle) {
			((Circle) hB).setCenterX(pos.x);
			((Circle) hB).setCenterY(pos.y);
			((Circle) hB).setRadius(scale.x);
		}else if (hB instanceof Rectangle) {
			((Rectangle)hB).setX(pos.x);
			((Rectangle)hB).setY(pos.y);
			((Rectangle)hB).setScaleX(scale.x);
			((Rectangle)hB).setScaleY(scale.y);
		}
	}

public void move(double x, double y) {
	if (hB instanceof Circle) {
		((Circle) hB).setCenterX(((Circle) hB).getCenterX() - x);
		((Circle) hB).setCenterY(((Circle) hB).getCenterY() - y);
	}else if (hB instanceof Rectangle) {
		((Rectangle)hB).setX(x);
		((Rectangle)hB).setY(y);
	}
}

public boolean checkCol(double x, double y, Player p) {

	return (Math.sqrt(Math.pow(((Circle)hB).getCenterX() - x - p.hitBox.getCenterX(), 2) + Math.pow(((Circle)hB).getCenterY() - y - p.hitBox.getCenterY(), 2))) <= (((Circle)hB).getRadius() + p.hitBox.getRadius());
}

}
