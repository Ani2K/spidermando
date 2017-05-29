package game;
import engine.*;
import javafx.scene.image.Image;
public class Projectile extends Actor{
	public Image bullet = new Image("file:images/bullet.png");
	Image fireball = new Image("file:images/fireball.png");
	/*
	 * type 1 : Hero projectile
	 * type 2 : Enemy projectile
	 */
	public enum ProjType {
		HERO, ENEMY, BOSS;
	}
	private ProjType pType;
	private double dx;
	private double dy;
	private boolean enemyDirectionFirstTime = true;
	private double enemyX = 30;
	
	public double getDx() {
		return dx;
	}
	public void setDx(double dx) {
		this.dx = dx;
	}
	public double getDy() {
		return dy;
	}
	public void setDy(double dy) {
		this.dy = dy;
	}
	public Projectile(ProjType inpType){
		if(inpType != ProjType.BOSS){
			setImage(bullet);
			setFitWidth(10);
			setFitHeight(10);
		}else{
			setImage(fireball);
			setFitWidth(20);
			setFitHeight(10);
		}
		
		setRotate(90);
		this.pType = inpType;
		dx = 0;
		dy = 0;
	}
	
	public ProjType getT() {
		return pType;
	}
	@Override
	public void act(long now) {
		if(pType == ProjType.HERO){
			move(dx, dy);
		}else if(pType == ProjType.ENEMY){
			if(enemyDirectionFirstTime){
				enemyDirectionFirstTime = false;				
				if(this.getTranslateX() + this.getX() - this.getWorld().getObjects(Hero.class).get(0).getTranslateX()>0){
					setRotate(-90);
					enemyX *= -1;
				}else{
					setRotate(90);
				}
			}
			move(enemyX, 0);
		}else if(pType == ProjType.BOSS){
			move(dx, dy);
		}
		if(getIntersectingObjects(Block.class).size() != 0){
			getWorld().remove(this);
		}
//		if(pType == 1){
//			if(enemyDirectionFirstTime){
//				enemyDirectionFirstTime = false;				
//				if(this.getTranslateX() - this.getWorld().getObjects(Hero.class).get(0).getTranslateX()>0){
//					setRotate(-90);
//					enemyX *= -1;
//				}else{
//					setRotate(90);
//				}
//			}
//			move(enemyX, 0);
//		}else if(pType == 2){
//			move(dx, dy);
////			for(Hero h : getIntersectingObjects(Hero.class)){
////				h.setHealth(h.getHealth() - 20);
////				getWorld().remove(this);
////				return ;
////			}
//		}else{
//			move(dx, dy);
////			for(Hero h : getIntersectingObjects(Hero.class)){
////				h.setHealth(h.getHealth() - 20);
////				getWorld().remove(this);
////				return ;
////			}
//		}
	}
	
}