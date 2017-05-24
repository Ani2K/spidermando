package game;

import engine.Actor;

public class GunnerSpawn extends Actor{
	long latestUpdate = 0;
	int seconds = 0;
	@Override
	public void act(long now) {
		if(now - latestUpdate >= 1000000000){
			latestUpdate = now;
			seconds++;
			if(seconds==5 && getWorld().getObjects(Gunner.class).size()<7){
				seconds = 0;
				Gunner e = new Gunner();
				e.setX(getTranslateX());
				e.setY(getY());
				getWorld().add(e);
			}
		}
	}

}
