package game;

import engine.Actor;

public class GunnerSpawn extends Actor{
	long latestUpdate = 0;
	int seconds = 0;
	int count = 0;
	@Override
	public void act(long now) {
		if(now - latestUpdate >= 1000000000){
			latestUpdate = now;
			seconds++;
			if(seconds==5 && count < 7){
				seconds = 0;
				Gunner e = new Gunner();
				e.setX(getTranslateX());
				e.setY(getY());
				getWorld().add(e);
				setCount(getCount() + 1);
			}
		}
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
