package game;

import engine.*;
import game.Projectile.ProjType;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Boss extends Actor{
	private Image mySprite = new Image("file:images/ethanBoss.jpg");
	private double dx;
	private double dy;
	private boolean turn = true;
	private boolean fight = false;
	long prev = 0;
	int leftBound = 89 * GameWorldApp.BLOCK_SIZE;
	int rightBound = 111 * GameWorldApp.BLOCK_SIZE;
	
	public Boss(){
		setImage(mySprite);
		dx = 20;
		dy = 0;
	}

	@Override
	public void act(long now) {
		if(fight){
			// TODO Auto-generated method stub
			if(getX() + getWidth() >= rightBound){
				dx *= -1;
				setRotationAxis(Rotate.Y_AXIS);
				setRotate(getRotate() + 180);
			}else if(getX() <= leftBound){
				dx *= -1;
				setRotationAxis(Rotate.Y_AXIS);
				setRotate(getRotate() + 180);
			}
			setX(getX() + dx);
			if(now - prev >= 500000000){
				double myX = getX();
				Hero heroe = getWorld().getObjects(Hero.class).get(0);
				double heroX = heroe.getTranslateX();
				double myY = getY();
				double heroY = heroe.getTranslateY();
				double speed = 120.0;
				double tangent = Math.abs(heroY - myY) / Math.abs(myX - heroX);
				double angle = Math.atan(tangent);
				double dx;
				double dy;
				if(myX < heroX && myY < heroY){
					//dx =  -1 * speed * Math.cos(angle);
					//dy = -1 * speed * Math.sin(angle);
					angle = Math.PI - angle;
				}else{
					//dx =  speed * Math.cos(angle);
					//dy = -1 * speed * Math.sin(angle);
				}
				dx =  -1 * speed * Math.cos(angle);
				dy = speed * Math.sin(angle);
				
				angle *= (180.0 / Math.PI);
				shoot(dx, dy, angle);
				prev = now;
			}
			
			for(Projectile proj : getIntersectingObjects(Projectile.class)){
				if(proj.getT() == ProjType.HERO){
					System.out.println("ooh u got me");
					getWorld().remove(proj);
				}
			}
		}
	}
	
	void shoot(double dx, double dy, double angle){
		Projectile proj = new Projectile(ProjType.BOSS);
		double x;
		proj.setTranslateX(getTranslateX() + getImage().getWidth() * 1.6);
		x = getTranslateX() + getImage().getWidth() * 1.4;
		//proj.setRotate(-1 * (angle - 90)); 
		proj.setRotate(angle); 
		proj.setY(getY() + getImage().getHeight() / 2);
		proj.setDx(dx);
		proj.setDy(dy);
		getWorld().add(proj);
		//getWorld().add(new Flash(x, getY() + getImage().getHeight() / 2.1));
	}

	public boolean isFight() {
		return fight;
	}

	public void setFight(boolean fight) {
		this.fight = fight;
	}
	
	
}
