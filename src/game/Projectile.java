package game;
import engine.*;
import javafx.scene.image.Image;
public class Projectile extends Actor{
	public Image bullet = new Image("file:images/bullet.png");
	private int type;
	private double dx;
	private double dy;
	
	public double getDx() {
		return dx;
	}
	public void setDx(double dx) {
		this.dx = dx;
	}
	public double getDy() {
		return dy;
	}
	public void setDy(double dy) {
		this.dy = dy;
	}
	public Projectile(int typo){
		setFitWidth(10);
		setFitHeight(10);
		setRotate(90);
		setImage(bullet);
		this.type = typo;
		dx = 0;
		dy = 0;
	}
	@Override
	public void act(long now) {
		if(type == 1){
			if(this.getIntersectingObjects(Hero.class).size()!=0){
				getWorld().remove(this);
			}else{
				if(this.getTranslateX() - this.getWorld().getObjects(Hero.class).get(0).getTranslateX()>0){
					setRotate(-90);
					this.move(-15, 0);
				}else{
					setRotate(90);
					this.move(15, 0);
				}
			}
		}else if(type == 2){
			move(dx, dy);
			if(this.getIntersectingObjects(Gunner.class).size()!=0){
				getWorld().remove(this);
			}
		}
	}
	
}