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
	
	// Overwrite? currently the same
	public int onHit(Room room, int atk){
		int dmg = atk-def;
		hp = hp-((atk-def));
		if(hp <= 0){
			updateEntity(room);
		}
		return dmg;
		
	}
	
	public SpriteSheet getIdleSheet(){
		return idleSheet;
	}
	
	// Enemy moves and attacks(maybe)
	// Check EntityEnemy for hitPlayer(Room room);
	public void move(Room room){
		// Checks if there is a player nearby, attack, end turn

		// Else move towards player, if in range, attack and end turn
		
		// set as moveleft
//		room.moveEntity(this, locationX-1, locationY);
		// set as random
		int toMove = (int)(Math.random()*100);
		int dir = toMove % 4;
		switch(dir){
		case 0: room.moveEntity(this, locationX-1, locationY); break;
		case 1: room.moveEntity(this, locationX+1, locationY); break;
		case 2: room.moveEntity(this, locationX, locationY-1); break;
		case 3: room.moveEntity(this, locationX, locationY+1); break;
		}
	}
}
