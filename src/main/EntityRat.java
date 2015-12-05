package main;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityRat extends EntityEnemy{
	private SpriteSheet idleSheet;
	{
		try{
			idleSheet = new SpriteSheet("enemies/ratXL.png",100,100);
		}
		catch(SlickException e){
			System.out.println(e);
		}
	}
	
	public SpriteSheet getIdleSheet(){
		return idleSheet;
	}
	
	public EntityRat(String name, int hp, int locationX, int locationY){
		super(name, hp, locationX, locationY);
	}
	
	
	
}
