package application;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class WorldObject {
	public static class OrderedPair {
		public double x, y;

		public OrderedPair(double initX, double initY) {
			x = initX;
			y = initY;
		}
	}

	public WorldObject() {
		// WHY JAVA, WhY???
	}

	Shape hB;
	public boolean collidable;
	ImageView image = new ImageView();
	OrderedPair pos;

	public WorldObject(Shape hitbox, OrderedPair pos, OrderedPair scale, boolean collides) {
		this.pos = pos;
		hB = hitbox;
		collidable = collides;

		if (hB instanceof Circle) {
			((Circle) hB).setCenterX(pos.x);
			((Circle) hB).setCenterY(pos.y);
			((Circle) hB).setRadius(scale.x);
		} else if (hB instanceof Rectangle) {
			((Rectangle) hB).setX(pos.x);
			((Rectangle) hB).setY(pos.y);
			((Rectangle) hB).setWidth(scale.x);
			((Rectangle) hB).setHeight(scale.y);
		}
	}

	public void move(double x, double y) {
		pos.x -= x;
		pos.y -= y;
	}

	public boolean checkCol(double x, double y, OrderedPair pos, double radius) {
		// TODO Auto-generated method stub
		return false;
	}

}
