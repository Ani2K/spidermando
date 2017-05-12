import engine.Actor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Gunner extends Actor{
	Image FLAG = new Image("/bomb_flagged.gif");;
	ImageView sprite;
	
	public Gunner(int type){
		sprite = new ImageView(FLAG);
	}
	
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}
	public void shoot(){
		Projectile eBullet = new Projectile();
		this.getWorld().add(eBullet);
	}
	
	public void die(){
		
	}

}
