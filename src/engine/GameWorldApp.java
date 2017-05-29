package engine;

import java.io.File;
import java.util.ArrayList;

import game.Block;
import game.Boss;
import game.EndPoint;
import game.GunnerSpawn;
import game.HealthPack;
import game.Hero;
import game.Level1;
import game.Munition;
import game.Obstacle;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class GameWorldApp extends Application {
	boolean gameOver = false;
	final int SPEED_OF_HERO = 20;
	public static final int BLOCK_SIZE = 64;
	final int SCREEN_WIDTH = BLOCK_SIZE * 10;
	final int SCREEN_HEIGHT = BLOCK_SIZE * 10;
	private int totalOffset = 0;

	private ArrayList<Block> steppingBlocks = new ArrayList<Block>();
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

	private Point2D playerVelocity = new Point2D(0, 0);
	boolean canJump = true;
	public static boolean isAlive = true;

	private long last = 0;
	static Stage theStage;

	Button restart;
	
	long waitTime = 0;

	//World
	private GameWorld world = new GameWorld();
	public static StackPane root;
	//Actors
	Hero heroe = new Hero();
	Boss bossTest = new Boss();
	Label healthText;
	Label ammoText;

	Media gunSound = new Media(new File(new File("images/pistolsound.mp3").getAbsolutePath()).toURI().toString());
	MediaPlayer gunPlayer = new MediaPlayer(gunSound);
	Media spiderManSong;
	MediaPlayer spiderPlayer;
	boolean levelStart = false;

	Scene startScene;
	Level1 l = new Level1();

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {


		Image menuImage = new Image("file:images/menuBackGround.jpg");
		ImageView menuView = new ImageView(menuImage);
		Pane menuPane = new Pane();
		menuPane.setMaxWidth(menuImage.getWidth() * 0.95);
		menuPane.setMaxHeight(menuImage.getHeight() * 0.95);
		menuPane.getChildren().add(menuView);
		ImageView play = new ImageView(new Image("file:images/play.png"));
		ImageView howTo = new ImageView(new Image("file:images/howToPlay.png"));
		VBox menuButtons = new VBox();
		menuButtons.getChildren().addAll(play, howTo);
		menuPane.getChildren().add(menuButtons);
		menuButtons.setAlignment(Pos.CENTER);
		menuButtons.setTranslateX(menuPane.getMaxWidth() / 3.25);
		menuButtons.setTranslateY(menuPane.getMaxHeight() / 2.5);
		BorderPane ro = new BorderPane();
		ro.setCenter(menuPane);
		Scene menuScene = new Scene(ro,SCREEN_WIDTH + 600, SCREEN_HEIGHT);
		primaryStage.setScene(menuScene);

		//Build Level
		theStage = primaryStage;


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
					GunnerSpawn g = new GunnerSpawn(steppingBlocks);
					g.setX(j*BLOCK_SIZE);
					g.setY(i*BLOCK_SIZE);
					world.add(g);
				}

				if(curRow.charAt(j)=='5'){
					EndPoint g = new EndPoint(BLOCK_SIZE);
					g.setX(j*BLOCK_SIZE);
					g.setY(i*BLOCK_SIZE);
					world.add(g);
				}

				if(curRow.charAt(j)=='6'){
					Obstacle s = new Obstacle(BLOCK_SIZE, 'u');
					s.setX(j*BLOCK_SIZE);
					s.setY(i*BLOCK_SIZE + 0.5*BLOCK_SIZE);
					obstacles.add(s);
					world.add(s);
				}

				if(curRow.charAt(j)=='7'){
					Obstacle s = new Obstacle(BLOCK_SIZE, 'd');
					s.setX(j*BLOCK_SIZE);
					s.setY(i*BLOCK_SIZE);
					obstacles.add(s);
					world.add(s);
				}
				if(curRow.charAt(j)=='8'){
					Obstacle s = new Obstacle(BLOCK_SIZE, 'r');
					s.setX(j*BLOCK_SIZE - 0.3*BLOCK_SIZE);
					s.setY(i*BLOCK_SIZE+ 15);
					obstacles.add(s);
					world.add(s);
				}
				if(curRow.charAt(j)=='9'){
					Obstacle s = new Obstacle(BLOCK_SIZE, 'l');
					s.setX(j*BLOCK_SIZE + 0.3*BLOCK_SIZE);
					s.setY(i*BLOCK_SIZE+15);
					obstacles.add(s);
					world.add(s);
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
		
		Rectangle bG = new Rectangle(40,40,Color.ALICEBLUE);

		Text title = new Text();
		title.setText("Spidermmando");
		title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		title.setFill(Color.RED);
		title.setStroke(Color.web("#0d61e8"));

		healthText = new Label("Health: " + heroe.getHealth());
		healthText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		healthText.setTextFill(Color.RED);
		//healthText.setStroke(Color.web("#0d61e8"));

		ammoText = new Label("Ammo: " + heroe.getAmmo());
		ammoText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		ammoText.setTextFill(Color.RED);
		//ammoText.setStroke(Color.web("#0d61e8"));

		VBox infoBox = new VBox();
		infoBox.getChildren().addAll(healthText, ammoText);
		infoBox.setAlignment(Pos.TOP_LEFT);
		pane.setTop(title);



		world.add(heroe);
		heroe.setBlocks(steppingBlocks);
		heroe.setX(150);
		heroe.setY(100); 

		heroe.translateXProperty().addListener((obs,old,newValue) ->{
			int offset = newValue.intValue();
			totalOffset += offset;
			if(offset>300 && offset<l.L1[0].length() * BLOCK_SIZE - SCREEN_WIDTH - 600 + 300){
				root.setLayoutX(-1 * offset + 300);
				world.setLayoutx(-1 * offset + 300);
			}
		});
		//Testing Gunner Class


		world.add(bossTest);
		bossTest.setX(50);
		bossTest.setY(50);




		Scene scene = new Scene(root, SCREEN_WIDTH + 600, SCREEN_HEIGHT);

		scene.addEventHandler(KeyEvent.KEY_PRESSED, new MyKeyboardHandler());

		primaryStage.setResizable(false);
		startScene = scene;

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

					heroe.setDx(30);
					heroe.setDirection(true);
					//moveHeroX(SPEED_OF_HERO);
				}
				if(e.getCode() == KeyCode.A){
					//					heroe.setDx(-1 * SPEED_OF_HERO);
					//					heroe.setRotationAxis(Rotate.Y_AXIS);
					//			    	//heroe.setRotate(180);
					//			    	heroe.setDirection(false);
					//			    	//System.out.println(heroe.getTranslateX());
					//moveHeroX(-1 * SPEED_OF_HERO);
					heroe.setDx(-30);
					heroe.setDirection(false);
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
		AnimationTimer timer = new AnimationTimer(){

			@Override
			public void handle(long arg0) {
				// TODO Auto-generated method stub
				if(gameOver == false && levelStart){
					update();

					long timeElapsed = arg0 - last;
					//fpsString.setValue(Double.toString(1_000_000_000.0/(timeElapsed)));

					last = arg0;
					//					if(!isAlive){
					//						primaryStage.setScene(deathScene);
					//						primaryStage.show();
					//					}
					ammoText.setText("Ammo: " + heroe.getAmmo());
					healthText.setText("Health: " + heroe.getHealth());
					infoBox.setTranslateX(-1 * root.getLayoutX());
				}
			}

		};
		timer.start();
		root.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				//double mouseX = e.getX() + heroe.getTranslateX();
				if(heroe.getAmmo() > 0){
					gunPlayer.stop();
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
					gunPlayer.play();
				}
			}
		});
		primaryStage.setTitle("Spidermmando");
		primaryStage.show();
		File song = new File("images/spiderman.mp3");
		String path = song.getAbsolutePath();
		spiderManSong = new Media(new File(path).toURI().toString());
		spiderPlayer = new MediaPlayer(spiderManSong);
		spiderPlayer.setVolume(0.2);

		play.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				primaryStage.setScene(scene);
				world.start();
				spiderPlayer.play();
				levelStart = true;
				root.getChildren().addAll(view, world, infoBox);
				root.setLayoutX(0);
				infoBox.setAlignment(Pos.TOP_LEFT);
			}

		});

	}

	public static StackPane getRoot() {
		return root;
	}


	private void update(){
		for(Obstacle spike : obstacles){
			if(heroe.getBoundsInParent().intersects(spike.getBoundsInParent())){
				heroe.setHealth(heroe.getHealth()-0.5);
				if(heroe.getHealth() <= 0){
					StackPane root2 = new StackPane();
					VBox hi = new VBox();
					Label dead = new Label("This isn't volleyball. Spikes are bad");
					hi.getChildren().addAll(dead);
					hi.setAlignment(Pos.CENTER);
					gameOver = true;
					root2.getChildren().add(hi);
					theStage.setScene(new Scene(root2,world.getWidth(),world.getHeight()));
					spiderPlayer.stop();
					world.stop();
				}

			}
		}
		if(heroe.getTranslateX()>=(l.L1[0].length() - 1)*BLOCK_SIZE){
			StackPane root2 = new StackPane();
			VBox hi = new VBox();
			Label win = new Label("You have won!!");
			hi.getChildren().addAll(win);
			hi.setAlignment(Pos.CENTER);
			gameOver = true;
			root2.getChildren().add(hi);
			theStage.setScene(new Scene(root2,world.getWidth(),world.getHeight()));
			world.stop();
		}

		if(heroe.getTranslateY() < 550){

			if(playerVelocity.getY() < 10){
				playerVelocity = playerVelocity.add(0, 1);
			}
			moveHeroY((int)playerVelocity.getY());
		}else{
			StackPane root2 = new StackPane();
			VBox hi = new VBox();
			Label dead = new Label("You have fallen to death");
			hi.getChildren().addAll(dead);
			hi.setAlignment(Pos.CENTER);
			gameOver = true;
			root2.getChildren().add(hi);
			theStage.setScene(new Scene(root2,world.getWidth(),world.getHeight()));
			spiderPlayer.stop();
			world.stop();

		}
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
							heroe.setTranslateY(heroe.getTranslateY()-1);
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
