package main;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityObstacle extends Entity{
	
	private SpriteSheet idleSheet;
	{
		try{
			idleSheet = new SpriteSheet("obstacleImgs/brokenRocks.png",100,100);
			
		}
		catch(SlickException e){
			System.out.println(e);
		}
	}
	
	public EntityObstacle(String name, int hp, int locationX, int locationY) {
		super(name, hp, locationX, locationY);
	}
	
	
	public int onHit(Room room, int atk){
		return 0;
	}
	
	public boolean move(Room room){
		return false;
	}
	
	public SpriteSheet getIdleSheet(){
		return idleSheet;
	}
	
}
