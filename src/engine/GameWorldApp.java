package engine;

import com.sun.security.auth.SolarisNumericGroupPrincipal;

import game.Block;
import game.Gunner;
import game.HealthPack;
import game.Hero;
import game.Level1;
import game.Munition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class GameWorldApp extends Application {
	//Dimensions
	final int BLOCK_SIZE = 64;
	final int SCREEN_WIDTH = BLOCK_SIZE * 10;
	final int SCREEN_HEIGHT = BLOCK_SIZE * 10;
	private int totalOffset = 0;
	//World
	private GameWorld world = new GameWorld();
	//Actors
	Hero heroe = new Hero();
	Gunner gunnerTest = new Gunner();
	
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Build Level
				Level1 l = new Level1();

				for(int i = 0; i < l.L1.length; i++){
					String curRow = l.L1[i];
					for(int j = 0; j < curRow.length();j++){
						if(curRow.charAt(j)=='1'){
							Block block = new Block(BLOCK_SIZE);
							block.setX(j*BLOCK_SIZE);
							block.setY(i*BLOCK_SIZE);
							world.add(block);
						}
						if(curRow.charAt(j)=='2'){
							HealthPack h = new HealthPack(BLOCK_SIZE);
							
							h.setX(j*BLOCK_SIZE);
							h.setY(i*BLOCK_SIZE);
							world.add(h);
						}
						if(curRow.charAt(j)=='3'){
							Munition m = new Munition(BLOCK_SIZE);
							m.setX(j*BLOCK_SIZE);
							m.setY(i*BLOCK_SIZE);
							world.add(m);
						}
					}
				}
				
		//Background
		Image background = new Image("file:images/background.png");
		ImageView view = new ImageView(background);
		view.setFitWidth(l.L1[0].length() * BLOCK_SIZE);
		view.setFitHeight(SCREEN_HEIGHT);
		//World
		StackPane root = new StackPane();
		BorderPane pane = new BorderPane();
		
		world.setPrefWidth(SCREEN_WIDTH);
		world.setPrefHeight(SCREEN_HEIGHT);
		
		
		
		root.getChildren().addAll(view, world, pane);
		
		world.add(heroe);
		heroe.setX(50);
		heroe.setY(300); 
		
		heroe.translateXProperty().addListener((obs,old,newValue) ->{
			int offset = newValue.intValue();
			totalOffset += offset;
			if(offset>300 && offset<l.L1[0].length() * BLOCK_SIZE - SCREEN_WIDTH - 600 + 300){
				root.setLayoutX(-1*offset + 300);
			}
		});
		//Testing Gunner Class
		
		world.add(gunnerTest);
		gunnerTest.setX(10);
		gunnerTest.setY(325);
		
		world.start();

		
		Scene scene = new Scene(root, SCREEN_WIDTH + 600, SCREEN_HEIGHT);
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
			    	//heroe.setRotate(360);
			    	heroe.setDirection(true);
			    	//System.out.println(heroe.getTranslateX());
				}
				if(e.getCode() == KeyCode.A){
					heroe.setDx(-10);
					heroe.setRotationAxis(Rotate.Y_AXIS);
			    	//heroe.setRotate(180);
			    	heroe.setDirection(false);
			    	//System.out.println(heroe.getTranslateX());
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
		root.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				//double mouseX = e.getX() + heroe.getTranslateX();
				double mouseX = e.getX();
				double heroX;
				if(heroe.isDirection()){
					heroX = heroe.getTranslateX() + heroe.getImage().getWidth() * 1.6;
				}else{
					heroX = heroe.getTranslateX() + heroe.getImage().getWidth() * 0.4;
				}
				double mouseY = e.getY();
				double heroY = heroe.getY() + heroe.getImage().getHeight() / 2;
				double speed = 80.0;
				double tangent = Math.abs(mouseY - heroY) / Math.abs(mouseX - heroX);
				double angle = Math.atan(tangent);
				double dx;
				double dy;
				if(mouseX < heroX && mouseY > heroY){
					//dx = -1 * speed * Math.cos(angle);
					//dy = speed * Math.sin(angle);
					angle = Math.PI + angle;
					heroe.setDirection(false);
					heroe.setRotationAxis(Rotate.Y_AXIS);
			    	heroe.setRotate(180);
				}else if(mouseX > heroX && mouseY > heroY){
					//dx = speed * Math.cos(angle);
					//dy = speed * Math.sin(angle);
					angle = 2 * Math.PI - angle;
					heroe.setDirection(true);
					heroe.setRotationAxis(Rotate.Y_AXIS);
			    	heroe.setRotate(360);
				}else if(mouseX < heroX && mouseY < heroY){
					//dx =  -1 * speed * Math.cos(angle);
					//dy = -1 * speed * Math.sin(angle);
					angle = Math.PI - angle;
					heroe.setDirection(false);
					heroe.setRotationAxis(Rotate.Y_AXIS);
			    	heroe.setRotate(180);
				}else{
					//dx =  speed * Math.cos(angle);
					//dy = -1 * speed * Math.sin(angle);
					heroe.setDirection(true);
					heroe.setRotationAxis(Rotate.Y_AXIS);
			    	heroe.setRotate(360);
				}
				dx = speed * Math.cos(angle);
				dy = -1 * speed * Math.sin(angle);
				
				angle *= (180.0 / Math.PI);
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
