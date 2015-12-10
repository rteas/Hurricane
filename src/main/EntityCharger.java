package main;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityCharger extends EntityEnemy {
	

	{
		try{
			idleUp = new SpriteSheet("enemies/chargerYU.png",100,100);
			idleDown = new SpriteSheet("enemies/chargerYD.png",100,100);
			idleLeft = new SpriteSheet("enemies/chargerXL.png",100,100);
			idleRight = new SpriteSheet("enemies/chargerXR.png",100,100);
		}
		catch(SlickException e){
			System.out.println(e);
		}
	}
	
	public EntityCharger(String name, int hp, int locationX, int locationY){
		super(name, hp, locationX, locationY);
	}
	
	/*
	public SpriteSheet getIdleSheet(){
		switch(direction){
			case 'U': return idleUp;
			case 'D': return idleDown;
			case 'L': return idleLeft;
			default: return idleRight;
		}
	}
	*/
	// Overwrite? currently the same
	public int onHit(Room room, int atk){
		int dmg = atk-def;
		hp = hp-((atk-def));
		if(hp <= 0){
			updateEntity(room);
		}
		return dmg;
		
	}
	
	// Enemy moves and attacks(maybe)
	// Check EntityEnemy for hitPlayer(Room room);
	/* Customize
	public void move(Room room){


	}
	*/
}
