package game;
import engine.*;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;

public class Flash extends Actor{
	private Image myImage = new Image("file:images/muzzleFlash.png");
	DeathHandler deathTimer = new DeathHandler();
	boolean death = false;
	
	public Flash(double x, double y){
		setX(x);
		setY(y);
		setImage(myImage);
		deathTimer.start();
	}
	
	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		if(death){
			getWorld().remove(this);
		}
	}
	private class DeathHandler extends AnimationTimer{
		long init = 0;
		@Override
		public void handle(long arg0) {
			if(arg0 - init >= 20){
				death = true;
			}
		}
		
	}
}
