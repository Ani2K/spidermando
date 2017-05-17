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
			move(10,0);
		}
	}
	
}