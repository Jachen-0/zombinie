package application;

import javafx.scene.shape.Circle;
import javafx.scene.image.Image;

	
public class Zombie extends WorldObject{
	private GM gameInfo;
	public int health;
	private boolean directChase = false;
	private double rand;
	private double speed;
	private Image[] im = {new Image("zombiebase.png"), new Image("zombie1.png"), new Image("zombie2.png")};
	private int frame = 1;
	
	public Zombie(double worldXPos, double worldYPos, double radius, GM gm, double offset) {
		super(new Circle(), new OrderedPair(worldXPos, worldYPos), new OrderedPair(radius, radius), true);
		health = 1;
		gameInfo = gm;
		rand = offset;
		speed = getSpeed();
		

		hB.setStroke(null);
		hB.setFill(null);
		
		image.setImage(im[1]);
		image.setFitHeight(radius*2);
		image.setFitWidth(radius*2);
		image.setX(pos.x - image.getFitWidth()/2);
		image.setY(pos.y - image.getFitHeight()/2);
	}
	
	//animation of zombies
	public void flickerAnimation() {
		image.setImage(im[frame]);
		if (frame == 1) {
			frame++;
		}else {
			frame--;
		}
		
	}
	public void hurt() {
		image.setImage(im[0]);
	}
	
	double getSpeed() {
		return (1 + 0.5* gameInfo.GetRound());
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
	
	public void Act() {
		Node closestToPlayer = gameInfo.getNodes()[gameInfo.nearestTo(gameInfo.GetPlayer())];
		Node closestToSelf = gameInfo.getNodes()[gameInfo.nearestTo(this)];
		directChase = (closestToPlayer.id == closestToSelf.id);
		if (directChase) {
			DirectChase();
		}
		else {
		indirectChase(closestToPlayer);
		}
	}
	
	private void indirectChase(Node destination) {
		move(clamp(pos.x - destination.pos.x + rand), clamp(pos.y - destination.pos.y + rand));
	}
	
	private void DirectChase () {
		move(clamp(pos.x - gameInfo.GetPlayer().pos.x), clamp(pos.y - gameInfo.GetPlayer().pos.y));
	}
	double clamp(double value) {
		return Math.min(Math.max(value, -speed), speed);
		}
	}
