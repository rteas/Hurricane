package main;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityCharger extends EntityEnemy {
	
	private SpriteSheet idleSheet;
	{
		try{
			idleSheet = new SpriteSheet("enemies/chargerX.png",100,100);
		}
		catch(SlickException e){
			System.out.println(e);
		}
	}
	
	public EntityCharger(String name, int hp, int locationX, int locationY){
		super(name, hp, locationX, locationY);
	}
	
	public void onHit(Room room, int atk){
		hp = hp-((atk-def)/2);
		if(hp <= 0){
			updateEntity(room);
		}
	}
	
	public SpriteSheet getIdleSheet(){
		return idleSheet;
	}
	
	// Enemy moves and attacks
	public void move(Room room){
		// Checks if there is a player nearby, attack, end turn

		// Else move towards player, if in range, attack and end turn

	}
}
