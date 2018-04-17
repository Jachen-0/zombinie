package application;

import application.WorldObject.OrderedPair;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Control implements Initializable {

	private static long SLEEP_TIME = 800L / 60L;
	private static long ANIM_TIME = 8000L / 60L;
	private static double mouseX;
	private static double mouseY;
	private static boolean runLoop = true;
	private static boolean setup = false;

	private static Player player;
	private static ArrayList<WorldObject> objs;
	private static boolean up = false, down = false, left = false, right = false;
	private static GM gm = new GM();
	private static boolean gameEnd = false;
	private static int animTimer = 0;

	@FXML
	private Button startGame;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		startGame.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				//clears the first scene after clicking button
				Main.primaryStage.hide();
				Pane root = new Pane();
				//hard-coded game
				try {
					ArrayList<Spawner> spawners = new ArrayList<Spawner>();
					Scene scene = new Scene(root, 1200, 900, Color.BEIGE);
					Node[] nodes = new Node[2];

					objs = new ArrayList<WorldObject>();
					player = new Player();

					gm.initializePlayer(player);
					objs.add(new CircleCollider(1700, 50, 20));

					VBox labels = new VBox();
					Label round = new Label("Round: 1");
					Label score = new Label("Score: 0");
					labels.getChildren().add(round);
					labels.getChildren().add(score);
					root.getChildren().add(labels);
					
					//reads from file to add world objects to the map
					try {
						Scanner input = new Scanner(new File("Map Importer.txt"));
						while (input.hasNext()) {
							String[] currentObj = input.nextLine().split(" ");
							System.out.println(currentObj[0]);
							if (currentObj[0].equals("R")) {
								objs.add(new RecCollider(Double.parseDouble(currentObj[1]),
										Double.parseDouble(currentObj[2]), Double.parseDouble(currentObj[3]),
										Double.parseDouble(currentObj[4])));
							} else if (currentObj[0].equals("C")) {
								objs.add(new CircleCollider(Double.parseDouble(currentObj[1]),
										Double.parseDouble(currentObj[2]), Double.parseDouble(currentObj[3])));
							} else if (currentObj[0].equals("S")) {
								Spawner s = new Spawner(new OrderedPair(Double.parseDouble(currentObj[1]),
										Double.parseDouble(currentObj[2])));
								objs.add(s);
								spawners.add(s);
							} else if (currentObj[0].equals("N")) {
								Node n = new Node(new OrderedPair(Double.parseDouble(currentObj[1]),
										Double.parseDouble(currentObj[2])), Integer.parseInt(currentObj[3]));
								objs.add(n);
								nodes[Integer.parseInt(currentObj[3])] = n;
							}
						}
						gm.initializeNodeNetwork(nodes);
					} catch (FileNotFoundException e) {
						System.out.println("Error finding file");
					}

					objs.add(new Zombie(800, 150, 35, gm, +(Math.random() * 100 - 50)));
					// player.c
					root.getChildren().add(player.image);
					
					//adds each world object to the map
					for (WorldObject i : objs) {
							if (i instanceof RecCollider) {
								root.getChildren().add(((RecCollider) i).image);
							} else if (i instanceof CircleCollider) {
								root.getChildren().add(((CircleCollider) i).image);
							} else if (i instanceof Zombie) {
								root.getChildren().add(((Zombie) i).image);
							}
						
					}
					//checks if game is over every time the mouse is moved
					root.setOnMouseMoved(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							mouseX = e.getX();
							mouseY = e.getY();
							if (gameEnd) {
								root.getChildren().clear();
								VBox endText = new VBox();
								endText.setTranslateX(scene.getWidth()/2);
								endText.setTranslateY(scene.getHeight()/9 * 4);
								Label theEnd = new Label("GAME OVER");
								Label endRound = new Label("Reached round " + gm.GetRound());
								Label endScore = new Label("Score: " + gm.GetScore());
								theEnd.setScaleX(2);
								theEnd.setScaleY(2);
								endText.getChildren().add(theEnd);
								endText.getChildren().add(endRound);
								endText.getChildren().add(endScore);
								endText.setSpacing(15);
								root.getChildren().add(endText);
							}
						}
					});

					//movement around the map
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
					
					scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							//shooting zombies/score tracker
						if (e.getButton().equals(MouseButton.PRIMARY)) {
							WorldObject shotObj = player.shootHit(player.getLastAngle(), objs);
							if (shotObj instanceof Zombie) {
								((Zombie) shotObj).health -= 1;
								((Zombie) shotObj).hurt();
								gm.RaiseScore(10);
								score.setText("Score: " + gm.GetScore());
								
								if (((Zombie) shotObj).health == 0) {
									objs.remove(shotObj);
									root.getChildren().remove(shotObj.image);
									gm.RaiseScore(50);
									score.setText("Score: " + gm.GetScore());
									gm.DecrementZombie();
									System.out.println("killed " + gm.ZombiesLeft());
									if (gm.ZombiesLeft() == 0) {
										gm.IncrementRound();
										round.setText("Round: " + gm.GetRound());
										for (int i = gm.ZombiesLeft(); i <= gm.GetZombieLimit(); i++) {
											int RandomIndex = (int) (Math.random() * 10);
											int trueIndex;
											if (RandomIndex < 2) {
												trueIndex = 0;
											} else if (RandomIndex >= 2 && RandomIndex < 4) {
												trueIndex = 1;
											} else if (RandomIndex >= 4 && RandomIndex < 6) {
												trueIndex = 2;
											} else if (RandomIndex >= 6 && RandomIndex < 8) {
												trueIndex = 3;
											} else {
												trueIndex = 4;
											}
											System.out.println(trueIndex);
											Zombie z = new Zombie(
													spawners.get(trueIndex).pos.x + (Math.random() * 200 - 100),
													spawners.get(trueIndex).pos.y + (Math.random() * 200 - 100), 35, gm,
													+(Math.random() * 200 - 100));
											objs.add(z);
											root.getChildren().add(z.image);
											gm.IncrementZombie();
										}
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

					Main.primaryStage.setScene(scene);
					Main.primaryStage.show();
					Main.primaryStage.setTitle("Zombinies");

					Main.primaryStage.setOnCloseRequest(e -> {
						runLoop = false;
					});

					Main.primaryStage.setResizable(false);
					check();
				} catch (Exception e) {
					e.printStackTrace();
				}
				setup = true;
			}
		});

	}

	private void check() {
		new Thread(() -> {
			while (runLoop) {
				if (setup) {
					update();
				}

				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					System.out.println("THREAD STOPPED");
				}
			}
		}).start();
		new Thread(() -> {
			while (runLoop) {
				if (setup) {
					runAnims();
				}

				try {
					Thread.sleep(ANIM_TIME);
				} catch (InterruptedException e) {
					System.out.println("THREAD STOPPED");
				}
			}
		}).start();

	}

	private static void update() {
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
						if (obj instanceof Zombie) {
							gameEnd = true;
						}
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

	private static void runAnims() {
		animTimer++;
		if (animTimer >= 2 ) {
			if(up || down || left || right) {
		
			player.flickerAnimation();
			animTimer = 0;
		} else {
			player.base();
		}
			for (WorldObject obj : objs) {
				if (obj instanceof Zombie) {
					((Zombie)obj).flickerAnimation();
				}
			}
	}
}

}
