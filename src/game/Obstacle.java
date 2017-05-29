package game;

import engine.Actor;
import javafx.scene.image.Image;

public class Obstacle extends Actor{
	public Obstacle(int b){
		Image s = new Image("file:images/spikes.png");
			setFitHeight(b/2);
			setFitWidth(b);
			setImage(s);
	}

	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}
}
