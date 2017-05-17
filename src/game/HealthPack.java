package game;

import engine.*;
import javafx.scene.image.Image;

public class HealthPack extends Actor{
	private Image healthImage;
	
	public HealthPack(){
		healthImage = new Image("file:images/health.png");
		setImage(healthImage);
	}

	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}
}
