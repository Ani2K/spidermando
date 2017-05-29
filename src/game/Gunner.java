package game;
import java.util.ArrayList;

import engine.Actor;
import game.Projectile.ProjType;
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
	int life = 3;
	ArrayList<Block> steppingBlocks;
	public Gunner(ArrayList<Block> b){
		steppingBlocks = b;
		setFitWidth(64);
		setFitHeight(64);
		setImage(FLAG);

	}

	public void act(long now) {
		moveGY(20);
		for(Projectile proj : getIntersectingObjects(Projectile.class)){
			if(proj.getT() == ProjType.HERO){
				life--;
				getWorld().remove(proj);
				if(life <= 0){
					getWorld().remove(this);
				}
			}
		}

		if(Math.abs(this.getTranslateX() - this.getWorld().getObjects(Hero.class).get(0).getTranslateX())>GUNNER_RANGE){
			if(this.getTranslateX() - this.getWorld().getObjects(Hero.class).get(0).getTranslateX()>0){
				if(direction){
					setRotationAxis(Rotate.Y_AXIS);
					setRotate(180);
					direction = false;
				}
				moveGX(5);
			}else{
				if(!direction){
					setRotationAxis(Rotate.Y_AXIS);
					setRotate(360);
					direction = true;
				}
				moveGX(-5);
				
			}
		}else{
			if(now - latestUpdate >= 1000000000){

				latestUpdate = now;
				shoot();
			}
		}
	}
	public void shoot(){
		Projectile proj = new Projectile(ProjType.ENEMY);
		proj.setX(getTranslateX());
		proj.setY(getY());
		getWorld().add(proj);
	}
	
	private void moveGX(int velocity){
		boolean movingRight = velocity > 0;
		for(int i = 0; i < Math.abs(velocity); i++){
			for(Block block : steppingBlocks){
				if(this.getBoundsInParent().intersects(block.getBoundsInParent())){
					if(movingRight){
						if(this.getTranslateX() + this.getWidth() == block.getX() - 64){
							return;
						}
					}else if(this.getTranslateX() == block.getX()){
						return;
					}
				}
			}
			this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
		}
	}

	private void moveGY(int velocity){
		boolean movingDown = velocity > 0;
		for(int i = 0; i < Math.abs(velocity); i++){
			for(Block block : steppingBlocks){
				if(this.getBoundsInParent().intersects(block.getBoundsInParent())){
					if(movingDown){
						if(this.getTranslateY() + this.getHeight() <= block.getY()){
							this.setTranslateY(this.getTranslateY()-1);
							return;
						}
					}else if(this.getTranslateY() == block.getY()){
						return;
					}
				}
			}
			this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
		}
	}
}
