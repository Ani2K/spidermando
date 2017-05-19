package game;
import engine.*;
import javafx.scene.image.Image;
public class Block extends Actor{
	Image BLOCK = new Image("file:images/blank.gif");
	public Block(int blockSize){
		setImage(BLOCK);
		setFitWidth(blockSize);
		setFitHeight(blockSize);
	}
	
	@Override
	public void act(long now) {
		//do nothing ever
	}
	
	
	
}
