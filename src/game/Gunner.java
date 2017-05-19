package game;
import engine.Actor;
import game.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class Gunner extends Actor{
	Image FLAG = new Image("file:images/enemydoge.png");
	Image DEATH_SPRITE = new Image("file:enemydoge.png");
	ImageView sprite;
	double GUNNER_RANGE = 200;
	long latestUpdate = 0;
	boolean direction = true; // true is right, false is left
	
	public Gunner(){
		setImage(FLAG);
		
	}

	public void act(long now) {
		if(this.getIntersectingObjects(Projectile.class).size()!=0){
			die();
		}else{

			if(Math.abs(this.getTranslateX() - this.getWorld().getObjects(Hero.class).get(0).getTranslateX())>GUNNER_RANGE){
				if(this.getTranslateX() - this.getWorld().getObjects(Hero.class).get(0).getTranslateX()>0){
//					if(direction){
//						setRotationAxis(Rotate.Y_AXIS);
//				    	setRotate(180);
//				    	direction = false;
//					}
					this.move(-5, 0);
				}else{
//					if(!direction){
//						setRotationAxis(Rotate.Y_AXIS);
//				    	setRotate(360);
//				    	direction = true;
//					}
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
		Projectile proj = new Projectile(1);
		proj.setX(getTranslateX());
		proj.setY(getY());
		getWorld().add(proj);
	}

	public void die(){
		if(this.getIntersectingObjects(Projectile.class).size()>0){
			sprite =  new ImageView(DEATH_SPRITE);
		}
	}

}
