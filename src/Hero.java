import engine.*;

public class Hero extends Actor{
	
	public Hero(){
		
	}
	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}
	
	public void shoot(){
		Projectile proj = new Projectile();
		getWorld().add(proj);
	}
	
	public void changeWeapon(String key){
		
	}
	
	public void die(){
		
	}
	
	public void fall(){
		
	}
	
	public void jump(){
		
	}
}