package game;

import java.util.ArrayList;

import engine.Actor;

public class BarrelSpawn extends Actor{
	ArrayList steppingBlocks;
	public BarrelSpawn(ArrayList b){
		steppingBlocks = b;

	}
	long latestUpdate = 0;
	int seconds = 0;
	int count = 0;
	@Override
	public void act(long now) {
		if(now - latestUpdate >= 1000000000){
			latestUpdate = now;
			seconds++;
			if(seconds==5){
				seconds = 0;
				Barrel e = new Barrel(steppingBlocks);
				e.setX(getX());
				e.setY(getY());
				getWorld().add(e);			}
		}
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}