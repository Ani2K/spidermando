import engine.*;

public class Hero extends Actor{
	private int weapon;
	private int dx = 0;
	public Hero(){
		weapon = 1;
	}
	@Override
	public void act(long now) {
		
	}
	
	public void shoot(){
		Projectile proj = new Projectile();
		proj.setX(getX());
		proj.setY(getY());
		getWorld().add(proj);
	}
	
	public void changeWeapon(String key){
		if(key.equals("E")){
			weapon++;
		}else if(key.equals("Q")){
			weapon--;
		}
		if(weapon == 9){
			weapon = 0;
		}
		if(weapon == -1){
			weapon = 9;
		}
	}
	
	public void die(){
		
	}
	
	public void fall(){
		
	}
	
	public void jump(){
		
	}
}