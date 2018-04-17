package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	public static Stage primaryStage = new Stage();

	@Override
	public void start(Stage primaryStage) throws Exception {
		final FXMLLoader menu = new FXMLLoader(getClass().getResource("StartMenu.fxml"));
		Pane pane = menu.load();
		Scene scene = new Scene(pane);
		Main.primaryStage.setScene(scene);
		Main.primaryStage.show();
	}

	public static void main(String[] args) throws InterruptedException {
		launch(args);

	}
}