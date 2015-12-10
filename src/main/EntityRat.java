package main;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityRat extends EntityEnemy{

	{
		try{
			idleUp = new SpriteSheet("enemies/ratYU.png",100,100);
			idleDown = new SpriteSheet("enemies/ratYD.png",100,100);
			idleLeft = new SpriteSheet("enemies/ratXL.png",100,100);
			idleRight = new SpriteSheet("enemies/ratXR.png",100,100);
		}
		catch(SlickException e){
			System.out.println(e);
		}
	}
	
	
	public EntityRat(String name, int hp, int locationX, int locationY){
		super(name, hp, locationX, locationY);
	}
	
	
	
}
