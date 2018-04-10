package application;

import java.util.ArrayList;

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
	
	@Override
	public void start(Stage primaryStage) {
	
		try {
			Image im = new Image("Test.png");
			Pane root = new Pane();

			Scene scene = new Scene(root,400,400);
			
			objs = new ArrayList<WorldObject>();
			player = new Player();
			player.image.setImage(im);
			objs.add(new CircleCollider(40, 200, 30));
			objs.add(new CircleCollider(300, 300, 20));
			objs.add(new Node(400, 250, 40));

			//player.c
			root.getChildren().add(player.hitBox);
			root.getChildren().add(player.image);

			for (WorldObject i : objs) {
				if (i.hB != null)
				root.getChildren().add(i.hB);
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
			scene.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) ->{
			if (e.getButton().equals(MouseButton.PRIMARY)) {
				click();
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
		} catch(Exception e) {
			e.printStackTrace();
		}
		setup = true;	
	}
	void click() {
		System.out.println("Hit = " +player.shootHit(player.lastAngle, objs));
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
				if (obj.checkCol(dx * SPEED, dy * SPEED, new OrderedPair(player.hitBox.getCenterX(), player.hitBox.getCenterY()), player.hitBox.getRadius())) {
					tooClose = true;
					System.out.println("too close");
					break;
				}
				}
			}
		}
		
		if (objs != null && objs.size() > 0 && !tooClose
				) {
			for (WorldObject obj : objs) {
				obj.move(dx * SPEED, dy * SPEED);
			}
		}
		if (player != null)
		player.rotate(mouseX, mouseY);
	}
	
	public static void main(String[] args) throws InterruptedException {

		new Thread(() -> {
			while (runLoop) {
				if(setup) {
					update();	
				}
				
				try {
					Thread.sleep(SCHLEEP_TIME);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();	
		launch(args);
	
	}
}