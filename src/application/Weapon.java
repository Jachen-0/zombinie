package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Weapon {
	private String name;
	private int damage;
	private int ammo;
	private int clipSize;
	private int bulletsPerShot;
	
	public Weapon(String name, int damage, int ammo, int clipSize, int bulletsPerShot) {
		this.name = name;
		this.damage = damage;
		this.ammo = ammo;
		this.clipSize = clipSize;
		this.bulletsPerShot = bulletsPerShot;
	}
	public void shoot() {
		ammo = ammo - bulletsPerShot;
	}
	public void reload() {
		ammo = clipSize;
	}
	public String getName() {
		return name;
	}
	public int getDamage() {
		return damage;
	}
	public int getAmmo() {
		return ammo;
	}
	public int getClipSize() {
		return clipSize;
	}
	public int getBulletsPerShot() {
		return bulletsPerShot;
	}
}
