package application;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import application.WorldObject.OrderedPair;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
	
public class Zombie extends WorldObject{
	GM gameInfo;
	int health;
	boolean directChase = false;
	public Zombie(double worldXPos, double worldYPos, double radius, GM gm) {
		super(new Circle(), new OrderedPair(worldXPos, worldYPos), new OrderedPair(radius, radius), true);
		((Circle)hB).setStroke(Color.GREEN);
		((Circle)hB).setFill(Color.RED);
		health = 5;
		gameInfo = gm;
	}
	@Override
	public void move(double x, double y) {
		super.pos.x -= x;
		super.pos.y -= y;
		((Circle) hB).setCenterX(super.pos.x);
		((Circle) hB).setCenterY(super.pos.y);
	}
	@Override
	public boolean checkCol(double x, double y, OrderedPair objPos, double radius) {
		return (Math.sqrt(Math.pow(pos.x - x - objPos.x, 2) + Math.pow(pos.y - y - objPos.y, 2))) <= (((Circle)hB).getRadius() + radius);
	}
	
	public void Act() {
		Node closestToPlayer = gameInfo.getNodes()[gameInfo.nearestTo(gameInfo.GetPlayer())];
		Node closestToSelf = gameInfo.getNodes()[gameInfo.nearestTo(this)];
		directChase = (closestToPlayer.id == closestToSelf.id);
		if (directChase) {
			DirectChase();
		}else {
		indirectChase(closestToPlayer);
	}
	}
	
	private void indirectChase(Node destination) {
		
		move(clamp(pos.x - destination.pos.x, -1, 1), clamp(pos.y - destination.pos.y, -1, 1));
		if (checkCol(0,0,destination.pos, 3)) {
			
		}
	}
	
	private void DirectChase () {
		move(clamp(pos.x - gameInfo.GetPlayer().pos.x, -1, 1), clamp(pos.y - gameInfo.GetPlayer().pos.y, -1, 1));
	}
	double clamp(double value, double min, double max) {
		   return Math.min(Math.max(value, min), max);
		}
}
