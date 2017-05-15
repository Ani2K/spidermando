import engine.Actor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Gunner extends Actor{
	Image FLAG = new Image("/bomb_flagged.gif");
	Image DEATH_SPRITE = new Image("/bomb_flagged.gif");
	ImageView sprite;
	
	
	public Gunner(int type){
		sprite = new ImageView(FLAG);
	}
	
	public void act(long now) {
		
	}
	public void shoot(){
		Projectile enBullet = new Projectile();
		this.getWorld().add(enBullet);
	}
	
	public void die(){
		if(this.getIntersectingObjects(Projectile.class).size()>0){
			sprite =  new ImageView(DEATH_SPRITE);
		}
	}

}
