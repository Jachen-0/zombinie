package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import application.WorldObject.OrderedPair;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

	public static long SCHLEEP_TIME = 1000L / 60L;
	static double mouseX;
	static double mouseY;
	static boolean runLoop = true;
	static boolean setup = false;

	static Player player;
	static ArrayList<WorldObject> objs;
	static boolean up = false, down = false, left = false, right = false;
	static GM gm = new GM();
	static Pane root = new Pane();

	@Override
	public void start(Stage primaryStage) {

		try {
			
			Node nodeOne = new Node(new OrderedPair(600, 350), 0);
			Node nodeTwo = new Node(new OrderedPair(1000, 350), 1);

			Scene scene = new Scene(root, 1200, 900, Color.BEIGE);
			Node[] nodes = new Node[2];
			nodes[0] = nodeOne;
			nodes[1] = nodeTwo;
			gm.initializeNodeNetwork(nodes);
			objs = new ArrayList<WorldObject>();
			player = new Player();
			
			gm.initializePlayer(player);
			objs.add(new CircleCollider(300, 300, 20));
			objs.add(nodeOne);
			objs.add(nodeTwo);
			
			
			try {
				Scanner input = new Scanner(new File("Map Importer.txt"));
				
				while (input.hasNext()){
					String[] currentObj = input.nextLine().split(" ");
					System.out.println(currentObj[0]);
					if (currentObj[0].equals("R")) {
						objs.add(new RecCollider(Double.parseDouble(currentObj[1]), Double.parseDouble(currentObj[2]), Double.parseDouble(currentObj[3]), Double.parseDouble(currentObj[4])));
					}else if (currentObj[0].equals("C")) {
						objs.add(new CircleCollider(Double.parseDouble(currentObj[1]), Double.parseDouble(currentObj[2]), Double.parseDouble(currentObj[3])));
					}
					
				}
			} catch (FileNotFoundException e) {
				System.out.println("Error finding file");
			}
			
			
			objs.add(new Zombie(800, 150, 35, gm));
			Spawner[] spawners = new Spawner[2];
			Spawner s1 = new Spawner(new OrderedPair(800,350));
			objs.add(s1);
			Spawner s2 = new Spawner(new OrderedPair(800,350));
			objs.add(s2);
			spawners[0] = s1;
			spawners[1] = s2;
			// player.c
			root.getChildren().add(player.image);
			for (WorldObject i : objs) {
				if (i.hB != null) {
					root.getChildren().add(i.hB);
					if (i instanceof RecCollider) {
						root.getChildren().add(((RecCollider)i).image);
					}else if (i instanceof CircleCollider) {
						root.getChildren().add(((CircleCollider)i).image);
					}
				}
			}
			root.addEventFilter(MouseEvent.MOUSE_MOVED, e -> {
				mouseX = e.getSceneX();
				mouseY = e.getSceneY();
			});

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (event.getCode() == KeyCode.W)
						up = true;
					if (event.getCode() == KeyCode.S)
						down = true;
					if (event.getCode() == KeyCode.A)
						left = true;
					if (event.getCode() == KeyCode.D)
						right = true;
				}
			});
			scene.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
				if (e.getButton().equals(MouseButton.PRIMARY)) {
					WorldObject shotObj = player.shootHit(player.getLastAngle(), objs);
					if (shotObj instanceof Zombie) {
						((Zombie) shotObj).health -= 1;
						if (((Zombie) shotObj).health == 0) {
							
							objs.remove(shotObj);
							root.getChildren().remove(shotObj.hB);
							shotObj = null;
							gm.DecrementZombie();
							System.out.println("killed " + gm.ZombiesLeft());
							if (gm.ZombiesLeft() == 0) {
								gm.IncrementRound();
								for(int i = gm.ZombiesLeft(); i <= gm.GetZombieLimit(); i++) {
									Zombie z = new Zombie(spawners[0].pos.x, spawners[0].pos.y, 35, gm);
									objs.add(z);
									root.getChildren().add(z.hB);
									gm.IncrementZombie();
								}
							}
						}
					}
				}
			});

			scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (event.getCode() == KeyCode.W)
						up = false;
					if (event.getCode() == KeyCode.S)
						down = false;
					if (event.getCode() == KeyCode.A)
						left = false;
					if (event.getCode() == KeyCode.D)
						right = false;
				}
			});

			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("TopDown");

			primaryStage.setOnCloseRequest(e -> {
				runLoop = false;
			});

			primaryStage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setup = true;
	}

	public static void update() {
		final double SPEED = 3;
		double dx = 0.0, dy = 0.0;

		if (up)
			--dy;	
		if (down)
			++dy;
		if (left)
			--dx;
		if (right)
			++dx;
		boolean tooClose = false;
		if (objs != null && objs.size() > 0) {
			for (WorldObject obj : objs) {
				if (obj.collidable) {
					if (obj.checkCol(dx * SPEED, dy * SPEED, player.pos, ((Circle) player.hB).getRadius())) {
						tooClose = true;
						break;
					}
				}
			}
		}

		if (objs != null && objs.size() > 0 && !tooClose) {
			for (WorldObject obj : objs) {

				obj.move(dx * SPEED, dy * SPEED);
				if (obj instanceof Zombie) {
					((Zombie) obj).Act();
				}
			}
		}
		if (player != null) {
			player.rotate(mouseX, mouseY);
		}

	}

	public static void main(String[] args) throws InterruptedException {

		new Thread(() -> {
			while (runLoop) {
				if (setup) {
					update();
				}

				try {
					Thread.sleep(SCHLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		launch(args);

	}
}