package game;
import engine.Actor;
import game.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Gunner extends Actor{
	Image FLAG = new Image("/bomb_flagged.gif");
	Image DEATH_SPRITE = new Image("/bomb_flagged.gif");
	ImageView sprite;
	double GUNNER_RANGE = 200;
	long latestUpdate = 0;
	public Gunner(){
		setImage(FLAG);
	}

	public void act(long now) {
		if(this.getIntersectingObjects(Projectile.class).size()!=0){
			die();
		}else{

			if(Math.abs(this.getX() -  this.getWorld().getObjects(Hero.class).get(0).getX())>GUNNER_RANGE){
				if(this.getX() - this.getWorld().getObjects(Hero.class).get(0).getX()>0){
					this.move(-5, 0);
				}else{
					this.move(5, 0);
				}
			}else{
				if(now - latestUpdate >= 1000000000){
					latestUpdate = now;
					shoot();
				}
			}
		}
	}
	public void shoot(){
		Projectile proj = new Projectile();
		proj.setX(getX());
		proj.setY(getY());
		getWorld().add(proj);
	}

	public void die(){
		if(this.getIntersectingObjects(Projectile.class).size()>0){
			sprite =  new ImageView(DEATH_SPRITE);
		}
	}

}
