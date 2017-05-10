/*//////////////////////////////80 chars////////////////////////////////////////
 * Anirudh Avadhani, 4/2/17, Per 4
 * 
 * This program took 35 minutes to make
 * 
 * This program makes use of the actor and world model classes in the engine 
 * package to create a simple demo which creates a world of moving balls. This 
 * program was interesting to make as I learned about designing an engine for 
 * games which all game classes extend and are children of. This program was not 
 * very difficult to make and I only had a few problems while making it. My 
 * first problem happened when I was writing the BouncyBall class and I 
 * accidentally forgot to set fit sizes for the imageView. This resulted in the 
 * ball not displayed properly and moving oddly. Other problems I had were due 
 * to wrong implementation of the Actor and World classes and not using the 
 * right syntax. This engine demo was overall quite fun to make and I was able 
 * to see how to use engine classes.
 */

package engine;

import javafx.animation.AnimationTimer;
import javafx.css.Styleable;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import java.util.*;

public abstract class P4_Avadhani_Anirudh_World extends Pane implements EventTarget, Styleable{
	private AnimationTimer timer;
	
	public P4_Avadhani_Anirudh_World(){
		timer = new AnimationTimer(){
			long prevTime = 0;
			
			@Override
			public void handle(long now) {
				if(now - prevTime >= 100000000){
					act(now);
					for(int i = 0; i < getChildren().size(); i++){
						if(P4_Avadhani_Anirudh_Actor.class.isAssignableFrom(getChildren().get(i).getClass())){
							P4_Avadhani_Anirudh_Actor act = (P4_Avadhani_Anirudh_Actor)(getChildren().get(i));
							act.act(now);
						}
					}
					prevTime = now;
				}
			}
		};
	}
	
	public abstract void act(long now);
	
	public void add(P4_Avadhani_Anirudh_Actor actor){
		getChildren().add(actor);
	}
	
	public void remove(P4_Avadhani_Anirudh_Actor actor){
		getChildren().remove(actor);
	}
	
	public <A extends P4_Avadhani_Anirudh_Actor> java.util.List<A> getObjects(java.lang.Class<A> cls){
		ArrayList<A> list = new ArrayList<A>();
		for(Node node : getChildren()){
			if(cls.isInstance(node)){
				list.add(cls.cast(node));
			}
		}
		return list;
	}
	
	public void start(){
		timer.start();
	}
	
	public void stop(){
		timer.stop();
	}
}