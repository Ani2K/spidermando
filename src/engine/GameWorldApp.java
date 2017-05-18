package engine;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import game.*;

public class GameWorldApp extends Application {
	private GameWorld world = new GameWorld();
	Hero heroe = new Hero();
	Gunner gunnerTest = new Gunner();
	HealthPack healthtest = new HealthPack();
	Munition munitiontest = new Munition();
	
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
		world.add(heroe);
		world.add(healthtest);
		world.add(munitiontest);
		munitiontest.setX(400);
		munitiontest.setY(400);
		healthtest.setX(500);
		healthtest.setY(400);
		heroe.setX(50);
		heroe.setY(300);
		//Testing Gunner Class
		
		world.add(gunnerTest);
		gunnerTest.setX(10);
		gunnerTest.setY(325);
		
		world.start();
		
		
		Scene scene = new Scene(root, 750, 500);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new MyKeyboardHandler());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getCode() == KeyCode.D){
					heroe.setDx(10);
					heroe.setRotationAxis(Rotate.Y_AXIS);
			    	heroe.setRotate(360);
			    	heroe.setDirection(true);
				}
				if(e.getCode() == KeyCode.A){
					heroe.setDx(-10);
					heroe.setRotationAxis(Rotate.Y_AXIS);
			    	heroe.setRotate(180);
			    	heroe.setDirection(false);
				}
			}
			
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.A || event.getCode() == KeyCode.D){
					heroe.setDx(0);
				}
				if(event.getCode() == KeyCode.W && heroe.getDy() == 0){
					heroe.setDy(-50);
				}
			}
			
		});
		scene.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				double mouseX = e.getX();
				double heroX = heroe.getX();
				double mouseY = e.getY();
				double heroY = heroe.getY();
				double speed = 80.0;
				double tangent = Math.tan(Math.abs(mouseY - heroY) / Math.abs(mouseX - heroX));
				double angle = Math.atan(tangent);
				double dx;
				double dy;
				if(mouseX < heroX && mouseY > heroY){
					dx = -1 * speed * Math.cos(angle);
					dy = speed * Math.sin(angle);
					angle = 3 * Math.PI / 2 - angle;
				}else if(mouseX > heroX && mouseY > heroY){
					dx = speed * Math.cos(angle);
					dy = speed * Math.sin(angle);
					angle = 2 * Math.PI - angle;
				}else if(mouseX < heroX && mouseY < heroY){
					dx =  -1 * speed * Math.cos(angle);
					dy = -1 * speed * Math.sin(angle);
					angle = Math.PI - angle;
				}else{
					dx =  speed * Math.cos(angle);
					dy = -1 * speed * Math.sin(angle);
				}
				heroe.shoot(dx, dy, angle);
			}
			
		});
		primaryStage.show();
	}
	
	private class MyKeyboardHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent e) {
			
		}
	}
}
