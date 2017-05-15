import engine.Actor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Gunner extends Actor{
	Image FLAG = new Image("/bomb_flagged.gif");
	Image DEATH_SPRITE = new Image("/bomb_flagged.gif");
	ImageView sprite;
	double GUNNER_RANGE = 10;
	
	public Gunner(int type){
		sprite = new ImageView(FLAG);
	}
	
	public void act(long now) {
		if(this.getIntersectingObjects(Projectile.class).size()!=0){
			die();
		}else{
		
			if(Math.abs(this.getX() - ((Actor) this.getWorld().getObjects(Hero.class)).getX())>GUNNER_RANGE){
				if(this.getX() - ((Actor) this.getWorld().getObjects(Hero.class)).getX()>0){
					this.move(5, 0);
				}else{
					this.move(-5, 0);
				}
			}else{
				shoot();
			}
		}
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
