package game;
import java.io.File;
import java.util.ArrayList;

import engine.*;
import game.Projectile.ProjType;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;

public class Hero extends Actor {

	//	private int speed = 100;

	private int weapon;
	private int ammo = 0;
	private double dx = 0;
	private double health = 100;
	private ArrayList<Block> blocks = new ArrayList<Block>();
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}

	//	private int dy = 0;
	//	private int gravity = 10;
	private boolean direction;
	public double getDx() {
		return dx;
	}
	public void setDx(double dx) {
		this.dx = dx;
	}

	private Image myImage = new Image("file:images/hero_right.png");
	//	public int getDx() {
	//		return dx;
	//	}
	//	public void setDx(int dx) {
	//		this.dx = dx;
	//	}
	public Hero(){
		weapon = 1;
		//		dx = 0;
		setImage(myImage);
		direction = true;
	}
	@Override
	public void act(long now) {
		 
		

		//		if(getY() < 300){
		//			dy += gravity;
		//		}
		//		if(getY() > 300){
		//			setY(300);
		//			dy = 0;
		//		}
		//		if(getY() + dy <= 301){
		//			move(0, dy);
		//		}else{
		//			move(0, 301 - getY());
		//		}
		boolean movingRight = dx > 0;
		for(int i = 0; i < Math.abs(dx); i++){
			for(Block block : blocks){
				if(getBoundsInParent().intersects(block.getBoundsInParent())){
					if(movingRight){
						if(getTranslateX() + getWidth() == block.getX() - GameWorldApp.BLOCK_SIZE){
							dx = 0;
							return;
						}
					}else if(getTranslateX() == block.getX()){
						return;
					}
				}
			}
			setTranslateX(getTranslateX() + (movingRight ? 1 : -1));
		}
		for(Projectile proj : getIntersectingObjects(Projectile.class)){
			if(proj.getT() == ProjType.ENEMY){
				getWorld().remove(proj);
				health -= 5;
				if(health <= 0){
					System.out.println("am deceased");
				}
			}else if(proj.getT() == ProjType.BOSS){
				getWorld().remove(proj);
				health -= 20;
				if(health <= 0){
					System.out.println("am deceased");
				}
			}
		}
		for(HealthPack healthP : getIntersectingObjects(HealthPack.class)){
			if(health + 25 <= 100){
				getWorld().remove(healthP);
				health += 25;
				Media a = new Media(new File(new File("images/regen.mp3").getAbsolutePath()).toURI().toString());
				MediaPlayer p = new MediaPlayer(a);
				p.setVolume(0.3);
				p.play();
			}else if(health < 100){
				getWorld().remove(healthP);
				health = 100;
				Media a = new Media(new File(new File("images/regen.mp3").getAbsolutePath()).toURI().toString());
				MediaPlayer p = new MediaPlayer(a);
				p.setVolume(0.3);
				p.play();
			}
			
		}
		for(Munition munition : getIntersectingObjects(Munition.class)){
			if(ammo < 90){
				ammo += 10;
			}else{
				ammo = 100;
			}
			Media a = new Media(new File(new File("images/ammoPickUp.mp3").getAbsolutePath()).toURI().toString());
			MediaPlayer p = new MediaPlayer(a);
			p.play();
			getWorld().remove(munition);

		}
	}

	public void shoot(double dx, double dy, double angle){
		Projectile proj = new Projectile(ProjType.HERO);
		double x;
		if(direction){
			proj.setTranslateX(getTranslateX() + getImage().getWidth() * 1.6);
			x = getTranslateX() + getImage().getWidth() * 1.4;
		}else{
			proj.setTranslateX(getTranslateX() + getImage().getWidth() * 0.4);
			x = getTranslateX() + getImage().getWidth() * 0.35;
		}
		proj.setRotate(-1 * (angle - 90)); 
		proj.setY(getTranslateY() + getImage().getHeight() * 2);
		proj.setDx(dx);
		proj.setDy(dy);
		getWorld().add(proj);
		getWorld().add(new Flash(x, getTranslateY() + getImage().getHeight() * 2));
		ammo--;
	}

	public void changeWeapon(String key){
		if(key.equals("E")){
			weapon++;
		}else if(key.equals("Q")){
			weapon--;
		}
		if(weapon == 9){
			weapon = 0;
		}
		if(weapon == -1){
			weapon = 9;
		}
	}

	public double getHealth() {
		return health;
	}
	public void setHealth(double d) {
		this.health = d;
	}
	
	public int getAmmo() {
		return ammo;
	}
	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}
	public boolean isDirection() {
		return direction;
	}
	public void setDirection(boolean direction) {
		this.direction = direction;
	}
}