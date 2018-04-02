package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class Main extends Application {
	
	public static long SCHLEEP_TIME = 1000L / 60L;
	
	static boolean runLoop = true;
	static Player player;
	static ArrayList<WorldObject> objs;
	static boolean up = false, down = false, left = false, right = false;
	
	@Override
	public void start(Stage primaryStage) {
	
		try {
			objs = new ArrayList<WorldObject>();
			Pane root = new Pane();
			player = new Player(true,root);
			objs.add(new WorldObject(40, 200, 30));
			objs.add(new WorldObject(300, 300, 20));
					

			//player.c
			Scene scene = new Scene(root,400,400);
			root.getChildren().add(player.hitBox);
			for (WorldObject i : objs) {
				root.getChildren().add(i.hitBox);
			}

			
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
	}
	
	public static void update() {
		
		final double SPEED = 3;
		double dx = 0.0, dy = 0.0;
		
		if (up) {
			--dy;
			
			}
		if (down)
			++dy;
		if (left)
			--dx;
		if (right)
			++dx;
		
		boolean tooClose = false;
		if (objs != null && objs.size() > 0) {
			for (WorldObject obj : objs) {
				if (obj.checkCol(dx * SPEED, dy * SPEED, player)) {
					tooClose = true;
					System.out.println("too close");
					break;
				}
			}
		}
		
		if (objs != null && objs.size() > 0 && !tooClose
				) {
			for (WorldObject obj : objs) {
				obj.move(dx * SPEED, dy * SPEED);
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {

		new Thread(() -> {
			while (runLoop) {
				update();
				
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