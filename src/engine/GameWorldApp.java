package engine;

import java.io.File;
import java.util.ArrayList;

import game.Block;
import game.Boss;
import game.Gunner;
import game.GunnerSpawn;
import game.HealthPack;
import game.Hero;
import game.Level1;
import game.Munition;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class GameWorldApp extends Application {

	final int SPEED_OF_HERO = 20;
	public static final int BLOCK_SIZE = 64;
	final int SCREEN_WIDTH = BLOCK_SIZE * 10;
	final int SCREEN_HEIGHT = BLOCK_SIZE * 10;
	private int totalOffset = 0;
	
	private ArrayList<Block> steppingBlocks = new ArrayList<Block>();
	private Point2D playerVelocity = new Point2D(0, 0);
	boolean canJump = true;
	public static boolean isAlive = true;
	
	private long last = 0;;
	
	//World
	private GameWorld world = new GameWorld();
	public static StackPane root;
	//Actors
	Hero heroe = new Hero();
	Gunner gunnerTest = new Gunner();
	Boss bossTest = new Boss();
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
							steppingBlocks.add(block);
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
						if(curRow.charAt(j)=='4'){
							GunnerSpawn g = new GunnerSpawn();
							g.setX(j*BLOCK_SIZE);
							g.setY(i*BLOCK_SIZE);
							world.add(g);
						}
					}
				}
				
		//Background
		Image background = new Image("file:images/background.png");
		ImageView view = new ImageView(background);
		view.setFitWidth(Level1.L1[0].length() * BLOCK_SIZE * 2);
		view.setFitHeight(SCREEN_HEIGHT);
		//World
		root = new StackPane();
		BorderPane pane = new BorderPane();
		
		world.setPrefWidth(SCREEN_WIDTH);
		world.setPrefHeight(SCREEN_HEIGHT);
		
		
		
		root.getChildren().addAll(view, world, pane);
		
		world.add(heroe);
		heroe.setBlocks(steppingBlocks);
		heroe.setX(50);
		heroe.setY(100); 
		
		heroe.translateXProperty().addListener((obs,old,newValue) ->{
			int offset = newValue.intValue();
			totalOffset += offset;
			if(offset>300 && offset<l.L1[0].length() * BLOCK_SIZE - SCREEN_WIDTH - 600 + 300){
				root.setLayoutX(-1 * offset + 300);
				//world.setLayoutx(-1*offset + 300);
			}
		});
		//Testing Gunner Class
		
		world.add(gunnerTest);
		gunnerTest.setX(10);
		gunnerTest.setY(325);
		
		world.add(bossTest);
		bossTest.setX(50);
		bossTest.setY(50);
		
		world.start();

		
		Scene scene = new Scene(root, SCREEN_WIDTH + 600, SCREEN_HEIGHT);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new MyKeyboardHandler());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		
//		Image deathImage = new Image("file:images/gameOver.jpg");
//		ImageView newRoot = new ImageView(deathImage);
//		newRoot.setFitWidth(l.L1[0].length() * BLOCK_SIZE);
//		Scene deathScene = new Scene(root, SCREEN_WIDTH + 600, SCREEN_HEIGHT);
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent e) {
				if(e.getCode() == KeyCode.D){
//					heroe.setDx(SPEED_OF_HERO);
//					heroe.setRotationAxis(Rotate.Y_AXIS);
//			    	//heroe.setRotate(360);
//			    	heroe.setDirection(true);
//			    	//System.out.println(heroe.getTranslateX());
					
					heroe.setDx(40);
					//moveHeroX(SPEED_OF_HERO);
				}
				if(e.getCode() == KeyCode.A){
//					heroe.setDx(-1 * SPEED_OF_HERO);
//					heroe.setRotationAxis(Rotate.Y_AXIS);
//			    	//heroe.setRotate(180);
//			    	heroe.setDirection(false);
//			    	//System.out.println(heroe.getTranslateX());
					//moveHeroX(-1 * SPEED_OF_HERO);
					heroe.setDx(-40);
				}
				if(e.getCode() == KeyCode.W){
//					heroe.setDy(-50);	
					jumpHero();
				}
			}
			
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.A || event.getCode() == KeyCode.D){
					heroe.setDx(0);
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
				double heroY = heroe.getTranslateY() + heroe.getImage().getHeight() * 2;
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
		primaryStage.setTitle("Spidermmando");
		primaryStage.show();
		File song = new File("images/spiderman.mp3");
		String path = song.getAbsolutePath();
		System.out.println(path);
		System.out.println(new File(path).toURI().toString());
		Media spiderManSong = new Media(new File(path).toURI().toString());
		MediaPlayer spiderPlayer = new MediaPlayer(spiderManSong);
		spiderPlayer.play();
		
		StringProperty fpsString = new 	SimpleStringProperty();
		primaryStage.titleProperty().bind(fpsString);
		AnimationTimer timer = new AnimationTimer(){

			@Override
			public void handle(long arg0) {
				// TODO Auto-generated method stub
				update();
				
				long timeElapsed = arg0 - last;
				fpsString.setValue(Double.toString(1_000_000_000.0/(timeElapsed)));
				
				last = arg0;
//				if(!isAlive){
//					primaryStage.setScene(deathScene);
//					primaryStage.show();
//				}
			}
			
		};
		timer.start();
	}
	
	public static StackPane getRoot() {
		return root;
	}

	private void update(){

		if(playerVelocity.getY() < 10){
			playerVelocity = playerVelocity.add(0, 1);
		}
		moveHeroY((int)playerVelocity.getY());
	}
	
	private void moveHeroX(int velocity){
		boolean movingRight = velocity > 0;
		for(int i = 0; i < Math.abs(velocity); i++){
			for(Block block : steppingBlocks){
				if(heroe.getBoundsInParent().intersects(block.getBoundsInParent())){
					if(movingRight){
						if(heroe.getTranslateX() + heroe.getWidth() == block.getX() - BLOCK_SIZE){
							return;
						}
					}else if(heroe.getTranslateX() == block.getX()){
						return;
					}
				}
			}
			heroe.setTranslateX(heroe.getTranslateX() + (movingRight ? 1 : -1));
		}
	}
	
	private void moveHeroY(int velocity){
		boolean movingDown = velocity > 0;
		for(int i = 0; i < Math.abs(velocity); i++){
			for(Block block : steppingBlocks){
				if(heroe.getBoundsInParent().intersects(block.getBoundsInParent())){
					if(movingDown){
						if(heroe.getTranslateY() + heroe.getHeight() <= block.getY()){
							heroe.setTranslateY(heroe.getTranslateY() - 1);
							canJump = true;
							return;
						}
					}else if(heroe.getTranslateY() == block.getY()){
						return;
					}
				}
			}
			heroe.setTranslateY(heroe.getTranslateY() + (movingDown ? 1 : -1));
		}
	}
	
	private void jumpHero(){
		if(canJump){
			playerVelocity = playerVelocity.add(0, -30);
			canJump = false;
		}
		
	}
	
	private class MyKeyboardHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent e) {
			
		}
	}
}
