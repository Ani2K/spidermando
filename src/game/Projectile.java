package game;
import engine.*;
import javafx.scene.image.Image;
public class Projectile extends Actor{
	public Image bullet = new Image("/clash2.png");
	
	public Projectile(){
		setFitWidth(10);
		setFitHeight(10);
		setRotate(90);
		setImage(bullet);
	}
	@Override
	public void act(long now) {
		if(this.getIntersectingObjects(Hero.class).size()!=0){
			getWorld().remove(this);
		}else{
			if(this.getX() - this.getWorld().getObjects(Hero.class).get(0).getX()>0){
				setRotate(180);
				this.move(-15, 0);
			}else{
				setRotate(90);
				this.move(15, 0);
			}
		}
	}
	
}