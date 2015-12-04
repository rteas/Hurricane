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
	
	
	public void onHit(Room room, int atk){
		
	}
	
	public void move(Room room){
		
	}
	
	public SpriteSheet getIdleSheet(){
		return idleSheet;
	}
	
}
