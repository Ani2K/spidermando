package game;

import engine.*;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Boss extends Actor{
	private Image mySprite = new Image("file:images/ethanBoss.jpg");
	private double dx;
	private double dy;
	private boolean turn = true;
	long prev = 0;
	
	public Boss(){
		setImage(mySprite);
		dx = 20;
		dy = 0;
	}

	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		if(getTranslateX() + getWidth() >= getWorld().getWidth() && turn){
			dx *= -1;
			turn = false;
		}else if(getTranslateX() <= 0 && !turn){
			dx *= -1;
			turn = true;
		}
		move(dx, dy);
		if(now - prev >= 500000000){
			double myX = getTranslateX();
			Hero heroe = getWorld().getObjects(Hero.class).get(0);
			double heroX = heroe.getTranslateX();
			double myY = getY();
			double heroY = heroe.getY();
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
	}
	
	void shoot(double dx, double dy, double angle){
		Projectile proj = new Projectile(10);
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
}
