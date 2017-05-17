package game;
import engine.*;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Hero extends Actor {
	private int weapon;
	private int dx = 0;
	public Image myImage = new Image("file:images/hero_right.png");
	public Image otherImage = new Image("file:images/hero_left.png");
	public int getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}
	public Hero(){
		weapon = 1;
		dx = 0;
		setImage(myImage);
	}
	@Override
	public void act(long now) {
		move(dx, 0);
	}
	
	public void shoot(){
		Projectile proj = new Projectile();
		proj.setX(getX());
		proj.setY(getY());
		getWorld().add(proj);
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
	
	public void die(){
		
	}
	
	public void fall(){
		
	}
	
	public void jump(){
		
	}
	
	private class MoveEvent implements EventHandler<KeyEvent>{

		@Override
		public void handle(KeyEvent e) {
			while(e.getCode().equals(KeyCode.RIGHT)){
				dx = 1;
				move(dx, 0);
			}
			while(e.getCode().equals(KeyCode.LEFT)){
				dx = -1;
				move(dx, 0);
			}
		}
	}
}