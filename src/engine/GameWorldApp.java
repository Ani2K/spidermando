package engine;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameWorldApp extends Application {
	private GameWorld world = new GameWorld();
	
	public static void main(String[] args) {
		launch();
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Image background = new Image("file:images/background.png");
		ImageView view = new ImageView(background);
		
		view.setFitWidth(750);
		view.setFitHeight(500);
		
		StackPane root = new StackPane();

		world.setPrefWidth(750);
		world.setPrefHeight(500);
		
		BorderPane pane = new BorderPane();
		
		root.getChildren().addAll(view, world, pane);
		
		world.start();
		
		Scene scene = new Scene(root, 750, 500);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new MyKeyboardHandler());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	private class MyKeyboardHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent e) {
			
		}
	}
}
