package game;
import engine.*;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;

public class Hero extends Actor {
	private int weapon;
	private int dx = 0;
	private int dy = 0;
	private int gravity = 10;
	private boolean direction;
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
	}

	private Image myImage = new Image("file:images/hero_right.png");
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
		direction = true;
	}
	@Override
	public void act(long now) {
		if(getY() < 300){
			dy += gravity;
		}
		if(getY() > 300){
			setY(300);
			dy = 0;
		}
		if(getY() + dy <= 301){
			move(dx, dy);
		}else{
			move(dx, 301 - getY());
		}
	}
	
	public void shoot(double dx, double dy, double angle){
		Projectile proj = new Projectile(2);
		if(direction){
			proj.setX(getX() + getImage().getWidth());
			
		}else{
			proj.setX(getX() - getImage().getWidth() / 5);
//			proj.setRotate(-90);
		}
//		proj.setRotationAxis(Rotate.Y_AXIS);
		proj.setRotate(-1 * (angle - 90)); 
		proj.setY(getY() + getImage().getHeight() / 2);
		proj.setDx(dx);
		proj.setDy(dy);
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
	
	public boolean isDirection() {
		return direction;
	}
	public void setDirection(boolean direction) {
		this.direction = direction;
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